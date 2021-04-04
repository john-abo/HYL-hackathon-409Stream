package hyl_project;

import java.io.File;
import java.io.FileWriter;
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
	 * Normal constructor. Ensures the parameters passed are valid using regEx and throws
	 * illegal argument exceptions otherwise.
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
				
				if (quantity <= 0) {
					throw new Exception();
				}
				
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
	 * Copy Constructor, and ensures the clone is not null
	 * 
	 * @param clone
	 */
	public FormPrinter(FormPrinter clone) {
		if (clone != null) {
			
			this.furniture = clone.getFurniture();
			this.type = clone.getType();
			this.quantity = clone.getQuantity();
			
		} else {
			System.err.println("Clone is null");
			throw new IllegalArgumentException();
		}
	}
	
	/**
<<<<<<< Updated upstream:hyl_project/src/hyl_project/FormPrinter.java
=======
	 * Performs the query in order to get a result from the database. Assigns
	 * it to global result variable, if there are no possible results it will
	 * be assigned to null. User input is also verified to an extent, but the
	 * user is no longer prompted for another input.
	 */
	public boolean query() {
		
		//Begins querying the database for most optimal purchase
		myJDBC = new search("jdbc:mysql://localhost/inventory","root","ensf409");
		myJDBC.initializeConnection();
				
		result = null;
		
		if (furniture.equalsIgnoreCase("chair")) {
			System.out.println("Looking for chairs...");
			
			result = myJDBC.searchChair(type,  quantity);
		} else if (furniture.equalsIgnoreCase("desk")) {
			System.out.println("Looking for desks...");
					
			result = myJDBC.searchDesk(type,  quantity);
		} else if (furniture.equalsIgnoreCase("lamp")) {
			System.out.println("Looking for lamps...");
					
			result = myJDBC.searchLamp(type,  quantity);
		} else if (furniture.equalsIgnoreCase("filing")) {
			System.out.println("Looking for filings...");
					
			result = myJDBC.searchFiling(type,  quantity);
		} else {
			System.out.println("That furniture can't be found");
		}
		
		if (result == null) {
			return false;
		}
		
		return true;
	}
	
	/**
>>>>>>> Stashed changes:hyl_project/src/edu/ucalgary/ensf409/FormPrinter.java
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
	    FileWriter outStream = null;
	    
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
	    	outStream = new FileWriter(DIRECTORY + "/report" + Integer.toString(orderNum) + ".txt");
	    	
	    	//Write format to file
	    	//going to need to create a method to return a formatted string
	    	outStream.write(formatReport());
	    	
	    	outStream.close();
	    } catch (Exception e) {
	    	System.err.println("Unable to write to file");
	    	System.err.println("No report written");
	    }
	    
		return orderNum;
	}
	
	/**
	 * Creates a formatted string that will be written to the report. Portions are missing as
	 * they require the SQL sections, which are still a work in progress.
	 * 
	 * @return	A String that contains the formatted report that will be printed to the report text file
	 */
	public String formatReport() {
		String ret = "";
		
		ret += "Report #" + orderNum + "\n";
		ret += "Furniture Order Form\n";
		ret += "\nOriginal request: " + type + " " + furniture + ", " + quantity/* + "\n"*/;	//This needs to change once I get
																								//the SQL data
		
		//Somewhere here I need to determine whether or not a set could be found
		//I need the rest of the group's code for this
		
		return ret;
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
