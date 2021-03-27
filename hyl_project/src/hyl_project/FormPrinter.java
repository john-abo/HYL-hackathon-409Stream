package hyl_project;

import java.io.File;
import java.io.FileOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormPrinter {
	private String type;
	private String furniture;
	private int quantity;
	
	private static String REGEX = "(.*?) (.*?), (.*?) ";
	private static Pattern PATTERN = Pattern.compile(REGEX);
	
	private final static String DIRECTORY = "OUT";
	private static int orderNum = 0;
	
	/**
	 * Normal constructor
	 * 
	 * @param request	The string request that the user enters. will be stripped for spaces
	 * 					and perhaps could be made case insensitive.
	 */
	public FormPrinter(String request) {
		String s = "";
		Matcher match = PATTERN.matcher(request + " ");
		
		if (match.find()) {
			s = match.group(1);
			
			if (s != null ) {
				type = s;
			} else {
				//I don't think it's possible to reach these exceptions
				System.err.println("Invalid Type passed. Passed: \"" + s + "\"");
				throw new IllegalArgumentException();
			}
			
			s = match.group(2);
			
			if (s != null) {
				furniture = s;
			} else {
				//I don't think it's possible to reach these exceptions
				System.err.println("Invalid Furniture passed. Passed: \"" + s + "\"");
				throw new IllegalArgumentException();
			}
			
			s = match.group(3);
			
			try {
				quantity = Integer.parseInt(s);
			} catch (Exception e) {
				System.err.println("Invalid Integer passed. Passed: \"" + s + "\"");
				throw new IllegalArgumentException();
			}
		} else {
			System.err.println("No match found");
			throw new IllegalArgumentException();
		}
		
	}
	
	/**
	 * Copy Constructor
	 * 
	 * @param clone
	 */
	public FormPrinter(FormPrinter clone) {
		if (clone != null) {
			
		} else {
			System.err.println("Clone is null");
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Writes report to .txt file. the number with which the report is associated
	 * to is determined by orderNum static variable
	 * 
	 * @return	The order number that was written.
	 */
	public int writeReport() {
		orderNum++;
		
		// If the directory does not exist, create it. If it does exist, make sure
	    // it is a directory.
	    File directory = new File(DIRECTORY);
	    FileOutputStream outStream = null;
	    
	    try {
	    	if (!directory.exists()) {
	    		directory.mkdir();
	    	} else {
	    		if (!directory.isDirectory()) {
	    			System.err.println("File " + DIRECTORY + " exists but is not a directory");
	    		}
	    	}
	    } catch (Exception e) {
	    	System.err.println("Unable to make directory \"" + DIRECTORY + "\" for unknown reasons");
	    }
	    
	    try {
	    	outStream = new FileOutputStream(DIRECTORY + "/report" + Integer.toString(orderNum) + ".txt");
	    	
	    	outStream.close();
	    } catch (Exception e) {
	    	System.err.println("Unable to write to file");
	    	System.err.println("No report written");
	    }
	    
		return orderNum;
	}
	
	public String getType() {
		return type;
	}

	
	public String getFurniture() {
		return furniture;
	}

	
	public int getQuantity() {
		return quantity;
	}	
	
	public int getOrderNumber() {
		return orderNum;
	}
}
/*



*/
