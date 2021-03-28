package edu.ucalgary.ensf409;
import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;

public class prototypeOne {
	private ResultSet results;
    private Connection dbConnect;
    private static LinkedList<String> itemIDs;
    
    
    public final String DBURL; //store the database url information
    public final String USERNAME; //store the user's account username
    public final String PASSWORD; //store the user's account password
    
    /**
     * Constructor of registration, takes in database URL, user's USERNAME and PASSWORD so that the connection can later be set.
     * @param DBURL the database URL (location and name of the database)
     * @param USERNAME designated database user
     * @param PASSWORD corresponding account password
     */
    public prototypeOne(String DBURL, String USERNAME, String PASSWORD) {
    	this.DBURL = DBURL;
    	this.USERNAME = USERNAME;
    	this.PASSWORD = PASSWORD;
    	itemIDs = new LinkedList<String>();
    }
    /**
     * getter method for database URL
     * @return returns DBURL
     */
    public String getDburl() {
		return this.DBURL;
	}

    /**
     * getter method for USERNAME
     * @return returns USERNAME
     */
	public String getUsername() {
		return this.USERNAME;
	}
	/**
	 * getter method for PASSWORD
	 * @return returns PASSWORD
 	 */

	public String getPassword() {
		return this.PASSWORD;
	}
	public void initializeConnection() {
		String a = getDburl();
		String b = getUsername();
		String c = getPassword();
		  try{
	            dbConnect = DriverManager.getConnection(a, b, c);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}
	public void close() {
        try {
            results.close();
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	public String userRequest(String furnitureCategory, String Type, int Number) {
		 String x  = furnitureCategory;
		
		 try {
			 String query = "SELECT * FROM furnitureCategory WHERE Type = '" + Type + "'";
			 Statement myStmt = dbConnect.createStatement();
			 results = myStmt.executeQuery(query.replaceAll("furnitureCategory", furnitureCategory));
			 while (results.next()) {
				 itemIDs.add(results.getString("ID"));
				 
			 }
			 myStmt.close();
		 } 
		 
		 catch (SQLException ex) {
          ex.printStackTrace();
	  	}
		 return x;
	}

	public static void main(String[] args) {
		 prototypeOne myJDBC = new prototypeOne("jdbc:mysql://localhost/inventory","mouser3","ensf409");
	        myJDBC.initializeConnection();
	        
	        System.out.println(myJDBC.userRequest("chair", "mesh", 1));
	        for (Object o: itemIDs) {
	        	System.out.println(o);
	        
	}	
  }
}
