package com.tvc.soap.mocks.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;



public class CSVConverter {
	
	  private static boolean writeCSVToConsole = true;
	    private static boolean writeCSVToFile = true;
	    private static String destinationCSVFile = "R:\\wiremocksoapstubbingNew\\src\\test\\convertedCSV.csv";
	    private static boolean sortTheList = true;

	    public static void main(String[] args) {
	    	CSVConverter util = new CSVConverter();
	        List<String> sampleList = util.createSampleList();
	        util.convertAndPrint(sampleList, writeCSVToConsole, writeCSVToFile, sortTheList);

	    }

	    /**
	     * @param sampleList - input list of string
	     * @param writeToConsole - if this flag is true, writes to console
	     * @param writeToFile - if this flag is true writes to file.
	     * @param sortTheList - if the list is to be sorted before conversion
	     */
	    private void convertAndPrint(List<String> sampleList,
	                                 boolean writeToConsole, boolean writeToFile, boolean sortTheList) {
	        String commaSeparatedValues = "";

	        /** If the list is not null and the list size is not zero, do the processing**/
	        if (sampleList != null) {
	            /** Sort the list if sortTheList was passed as true**/
	            if(sortTheList) {
	                Collections.sort(sampleList);
	            }
	            /**Iterate through the list and append comma after each values**/
	            Iterator<String> iter = sampleList.iterator();
	            while (iter.hasNext()) {
	                commaSeparatedValues += iter.next() + ",";
	            }
	            /**Remove the last comma**/
	            if (commaSeparatedValues.endsWith(",")) {
	                commaSeparatedValues = commaSeparatedValues.substring(0,
	                        commaSeparatedValues.lastIndexOf(","));
	            }
	        }
	        /** If writeToConsole flag was passed as true, output to console**/
	        if(writeToConsole) {
	            System.out.println(commaSeparatedValues);
	        }
	        /** If writeToFile flag was passed as true, output to File**/
	        if(writeToFile) {
	            try {
	                FileWriter fstream = new FileWriter(destinationCSVFile, false);
	                BufferedWriter out = new BufferedWriter(fstream);
	                for(int i=1;i<5;i++) {
	                    out.write( commaSeparatedValues );
	                    out.newLine();
	                }
	                out.close();
	                System.out.println("*** Also wrote this information to file: " + destinationCSVFile);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }

	    }

	    /**
	     * Creates a sample list to be used by the convertAndPrint method
	     * and returns it to the calling method.
	     */
	    private List<String> createSampleList() {
	        List<String> sampleList = new ArrayList<String>();
	        sampleList.add("Nebraska");
	        sampleList.add("Iowa");
	        sampleList.add("Illinois");
	        sampleList.add("Idaho");
	        return sampleList;
	    }

}
