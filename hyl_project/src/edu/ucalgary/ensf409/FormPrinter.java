package edu.ucalgary.ensf409;

import java.io.File;
import java.io.FileWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormPrinter {
	private String type;
	private String furniture;
	private int quantity;
	
	private static String REGEX = "(.*?) (.*?), (.*?) ";
	private static String REGEX1 = "(.*?) (.*?) (.*?) ";
	private static Pattern PATTERN = Pattern.compile(REGEX);
	private static Pattern PATTERN1 = Pattern.compile(REGEX1);
	
	private final static String DIRECTORY = "OUT";
	private static int orderNum = 0;
	
	private search myJDBC;
	
	private ArrayList<String> result;
	
	/**
	 * Normal constructor. Ensures the parameters passed are valid using regEx and throws
	 * illegal argument exceptions otherwise.
	 * 
	 * @param request	The string request that the user enters. will be stripped for spaces
	 * 					and perhaps could be made case insensitive.
	 */
 	public FormPrinter() {
	}

	public FormPrinter(String request) {
		String s = "";
		Matcher match = PATTERN.matcher(request + " ");
		
		System.out.println("User request: " + request);
		
		if (match.find()) {
			s = match.group(1);
			
			if (s != null) {
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
				System.err.println("This is not represent a valid number of items to order. Passed: \"" + s + "\"");
				throw new IllegalArgumentException();
			}
		} else {
			System.err.println("The input does not represent a valid order.");
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
	 * Performs the initial query in order to get SQL directory and Login
	 * information. If the information is invalid, access will be denied.
	 *
	 * @param request String consisting of DBUrl, Username and Password separated by whitespaces.
	 * @return
	 */
	public boolean querySQL(String request) {
		
		String Dburl;
		String Username;
		String Password;


		String s = "";
		Matcher match = PATTERN1.matcher(request + " ");
		
		System.out.println("SQL request: " + request);
		
		if (match.find()) {
			s = match.group(1);
			
			if (s != null) {
				Dburl = s;
			} else {
				//I don't think it's possible to reach these exceptions
				System.err.println("Invalid Dburl passed. Passed: \"" + s + "\"");
				throw new IllegalArgumentException();
			}
			
			s = match.group(2);
			
			if (s != null) {
				Username = s;
			} else {
				//I don't think it's possible to reach these exceptions
				System.err.println("Invalid Username passed. Passed: \"" + s + "\"");
				throw new IllegalArgumentException();
			}
			
			s = match.group(3);
			
			if (s != null) {
				Password = s;
			} else {
				//I don't think it's possible to reach these exceptions
				System.err.println("Invalid Password passed. Passed: \"" + s + "\"");
				throw new IllegalArgumentException();
			}

		} else {
			System.err.println("This input does not represent a valid order");
			throw new IllegalArgumentException();
		}		
		
		return true;
	}
	
	/**
	 * Performs the query in order to get a result from the database. Assigns
	 * it to global result variable, if there are no possible results it will
	 * be assigned to null. User input is also verified to an extent, but the
	 * user is no longer prompted for another input.
	 * @throws SQLException 
	 */
	public boolean query(String request) {
		
		//Begins querying the database for most optimal purchase
		
		String Dburl;
		String Username;
		String Password;


		String s = "";
		Matcher match = PATTERN1.matcher(request + " ");
				
		if (match.find()) {
			s = match.group(1);
			
			if (s != null) {
				Dburl = s;
			} else {
				//I don't think it's possible to reach these exceptions
				System.err.println("Invalid Dburl passed. Passed: \"" + s + "\"");
				throw new IllegalArgumentException();
			}
			
			s = match.group(2);
			
			if (s != null) {

				Username = s;
			} else {
				//I don't think it's possible to reach these exceptions
				System.err.println("Invalid Username passed. Passed: \"" + s + "\"");
				throw new IllegalArgumentException();
			}
			
			s = match.group(3);
			
			if (s != null) {
				Password = s;
			} else {
				//I don't think it's possible to reach these exceptions
				System.err.println("Invalid Password passed. Passed: \"" + s + "\"");
				throw new IllegalArgumentException();
			}

		} else {
			System.err.println("No match found");
			throw new IllegalArgumentException();
		}




		myJDBC = new search(Dburl, Username, Password);
		try {
			myJDBC.initializeConnection();
		} catch (SQLException e) {
			System.err.println("Invalid login credentials, user is not authorized");
			e.printStackTrace();
		}
				
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
		//this checks if the recommended manufacturer list was returned, because if it was returned there would be no price at the end
		if (result.get(result.size()-1).charAt(0) != '$') {
			System.out.println("Order cannot be fulfilled based on current inventory. Suggested manufacturers are ");
			for(int i = 0; i < result.size(); i++) {
				System.out.println(result.get(i));
			}
			return false;
		}
		
		return true;
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
	    	String fileHeader = "";
	    	fileHeader += "Report #" + orderNum + "\n";
			fileHeader += "Furniture Order Form\n";
			
			fileHeader += "\nFaculty Name:";
			fileHeader += "\nContact:";
			fileHeader += "\nDate:\n";
			
	    	outStream.write(fileHeader + formatReport());
	    	
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
		
		ret += "\nOriginal request: " + type + " " + furniture + ", " + quantity + "\n";	//This needs to change once I get
																								//the SQL data
		ret += "\nItems Ordered\n";
		
		if (result != null) {
			int i;
			
			for (i = 0; i < result.size() - 1 ; i++) {
				ret += "ID: " + result.get(i) + "\n";
			}
			
			ret += "\nTotal Cost: " + result.get(i);
		}
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
