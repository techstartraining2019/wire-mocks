package wiremock.transformation.learning;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ExceldataRead {

        static HashMap<String,Integer> dataRowcount = new HashMap<String,Integer>();

        public static void main(String arg[]) throws IOException, InterruptedException {

                String filePath = "R:\\wiremocksoapstubbingNew\\src\\test";
                String fileName = "Book1.xlsx";
                String sheetName = "Sheet1";
                ReadExcel(filePath,fileName,sheetName);
        }

        public static void ReadExcel(String filePath, String fileName, String sheetName)throws InterruptedException,IOException {
                File file=new File(filePath+"\\"+fileName);
                //Create an object of FileInputStream class to read excel file
                HashMap<String, List<String>> hashmap =
                        new HashMap<String, List<String>>();
                FileInputStream inputStream=new FileInputStream(file);
                Workbook AddCatalog=null;
                //Find the file extension by splitting file name in substring  and getting only extension name
                String fileExtensionName=fileName.substring(fileName.indexOf("."));
                //Check condition if the file is a .xls file or .xlsx file
                if(fileExtensionName.equals(".xls")){
                        //If it is .xls file then create object of HSSFWorkbook class
                        AddCatalog=new HSSFWorkbook(inputStream);
                }
                else if(fileExtensionName.equals(".xlsx")){
                        //If it is .xlsx file then create object of XSSFWorkbook class
                        AddCatalog=new XSSFWorkbook(inputStream);
                }
                //Read sheet inside the workbook by its name
                Sheet AddCatalogSheet=AddCatalog.getSheet(sheetName);
                //Find number of rows in excel file
                int rowcount=AddCatalogSheet.getLastRowNum()-AddCatalogSheet.getFirstRowNum();
                System.out.println("Total row number: "+rowcount);
                int count = 0;
                for(int i=1;i<rowcount+1;i++){
                        //Create a loop to get the cell values of a row for one iteration
                        Row row=AddCatalogSheet.getRow(i);
                        Row lastrow = AddCatalogSheet.getRow(i-1);

                        List<String> arrName=new ArrayList<String>();
                        for(int j=0;j<row.getLastCellNum();j++){
                                // Create an object reference of 'Cell' class
                                Cell cell=row.getCell(j);
                                // Add all the cell values of a particular row
                                arrName.add(cell.getStringCellValue());
                        }
                        System.out.println(row.getCell(0).getStringCellValue()+" "+lastrow.getCell(0).getStringCellValue());
                        if(!row.getCell(0).getStringCellValue().equals(lastrow.getCell(0).getStringCellValue())){

                                count = 1;
                        }
                        hashmap.put(row.getCell(0).getStringCellValue()+"."+count,arrName);
                        dataRowcount.put(row.getCell(0).getStringCellValue(),count);
                        System.out.println(hashmap);
//                        System.out.println("Size of the arrayList: "+arrName.size());
                        // Create an iterator to iterate through the arrayList- 'arrName'
                        Iterator<String> itr=arrName.iterator();
                        while(itr.hasNext()){
                                System.out.println("arrayList values: "+itr.next());
                        }
                        count=count+1;
                }
                System.out.println(dataRowcount);
        }

}