package wiremock.transformation.learning;

import com.github.tomakehurst.wiremock.common.ListOrSingle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Map;

public class DB_Result {

    private static String csvPath = "./src/test/resources/csv";
    //private static int rows = 0;
    public static String xmlbody;
    private Object[] paraKey;

    public String executequery(Map<String, ListOrSingle<String>> queryParm) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        Element results = doc.createElement("empAddress");
        doc.appendChild(results);
        Class.forName("org.hsqldb.jdbcDriver").newInstance();
        Connection con = DriverManager.getConnection("jdbc:hsqldb:file:" + csvPath, "CSV", "");
        int qryParmcnt = queryParm.size();
        paraKey = queryParm.keySet().toArray();
        StringBuilder querycondition = new StringBuilder();
        for (int i = 0 ;i<qryParmcnt;i++){
            querycondition.append("where ");
            querycondition.append("\""+paraKey[i]+"\"");
            querycondition.append("= '");
            querycondition.append(queryParm.get(paraKey[i]));
            querycondition.append("'");
        }

        String BaseQuery = "select * from emp_address "+querycondition.toString();
        ResultSet rs = con.createStatement().executeQuery(BaseQuery);
        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();
        int rows = 1;
        while (rs.next()) {
            Element row = doc.createElement("AddressLine"+rows);
            results.appendChild(row);
            for (int i = 1; i <= colCount; i++) {
                String columnName = rsmd.getColumnName(i);
                Object value = rs.getObject(i);
                Element node = doc.createElement(columnName);
                node.appendChild(doc.createTextNode(value.toString()));
                row.appendChild(node);
            }
            rows = rows + 1;
        }
        DOMSource domSource = new DOMSource(doc);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
        StringWriter sw = new StringWriter();
        StreamResult sr = new StreamResult(sw);
        transformer.transform(domSource, sr);
        xmlbody = sw.toString();
        con.close();
        rs.close();
        return xmlbody;
    }
}