package edu.ucalgary.ensf409;

import java.sql.*;
import java.util.ArrayList;
public class search {
	private Connection dbConnect;
	public final String DBURL;
	public final String  USERNAME;
	public final String PASSWORD;
	
	//comboList contains the power set or all possible combinations of the currently found item list
	private ArrayList<ArrayList<node>> comboList;
	
	/**
	 * Constructor for the class, sets the DBURL, USERNAME, and PASSWORD strings that
	 * will be used later
	 * 
	 * @param dBURL url of database
	 * @param uSERNAME username of user
	 * @param pASSWORD password of user
	 */
	public search( String dBURL, String uSERNAME, String pASSWORD) {
		DBURL = dBURL;
		USERNAME = uSERNAME;
		PASSWORD = pASSWORD;
	}
	/**
	 * connects to sql database
	 * 
	 * @throws SQLException Thrown if an error occurs with the sql
	 */
	public void initializeConnection() throws SQLException {
		 try {
			dbConnect = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			System.out.println("Unable to connect to database");
			throw e;
		}
		
	}
	
	/**
	 * finds filing order
	 * @param lookup type of filing
	 * @param needed number needed for order
	 * @return arraylist string containing ids of all ordered items, returns recommended manufacturer list for invalid order. total order price always at end of list
	 */
	public ArrayList<String> searchFiling(String lookup,int needed){
		//contains the combination with the lowest price. Price is always at the END of the arraylist. If combination cannot be created, this contains recommended manufacturer list
	    ArrayList<String> returnList = new ArrayList<String>();
	    //total order price
	    long orderPrice = 0;
	    ResultSet results;

    	try {
    		
    		//finds all entries of filing with the specified type and saves them into results
    		PreparedStatement find = dbConnect.prepareStatement("SELECT * FROM filing WHERE Type=?");
			find.setString(1,lookup);
			results = find.executeQuery();
		
			
			//used to check if combination is valid
			boolean Rails = false;
			boolean Cabinet = false;
			boolean Drawers = false;
			
			boolean total = false;
			//this matches list will contain all the filing entries that match the type
		    ArrayList<node> matches = new ArrayList<node>();
			node temp1= null;
			
			while (results.next()){
				if(results.getString("Type").equals(lookup)) {
					//saves data of each filing entry of specified type into node and then saves that node into matches list
				   filingData temp = new filingData(results.getString("ID"),results.getString("Rails"),results.getString("Cabinet"),results.getString("Drawers"),results.getInt("Price"));
				    temp1 = new node(temp);
				   matches.add(temp1);
				 
				   
				}
				
	            }
			//returns recommended manufacturer list if no results were found
			if(temp1 == null) {
				returnList.clear();
	    		  try {                    
	  	            Statement myStmt = dbConnect.createStatement();
	  	            //looks for all manufacturers with ids that make filings and saves them into results
	  	            results = myStmt.executeQuery("SELECT * FROM Manufacturer WHERE ManuID IN (002,004,005)");
	  	          while (results.next()){
	  	        	  //adds the name of each filing manufacturer to list
		             returnList.add(results.getString("Name"));
		            }
		            
		            myStmt.close();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
	  	          return returnList; 	
			}
			//iterates once to find each item required in order
			for(int b = 0; b < needed ; b++) {
				//price of current combination
				 long combinedPrice = 0;
				 //current lowest combination price
				long currentLowestComboPrice = Long.MAX_VALUE;
				//intializes all possible combination list comboList
				comboList = new ArrayList<ArrayList<node>>();
			//saves index of found combination
		    int index1 = -1;
			//empty arraylist
			ArrayList<node> g = new ArrayList<node>();
			//finds the power set/all possible combinations for the matches list

			powerSet(matches,g,0);
			//removes the empty set
			comboList.remove(0);
			//iterates over each possible combination/subset
	    	for(int i = 0; i < comboList.size(); i++) {
	    		//resets for next combination price
	    		combinedPrice = 0;
	    		//iterates over each element in combination
	    		for(int j = 0; j< comboList.get(i).size(); j++) {
	    			filingData comboItem = (filingData) comboList.get(i).get(j).element;
	    			//checks if combination is valid by checking if at least 1 element is true for each part
	    			Rails |= comboItem.Rails ;
	    		
	    		   Cabinet |= comboItem.Cabinet;
	    		   
	    		    Drawers |= comboItem.Drawers;
	    		    
	    		    
	    		    //total determines whether the combination is valid by seeing if each combination has required parts
	    		     total = Rails && Drawers && Cabinet;
	    		    
	    		   //System.out.println(comboList.get(i).get(j).arms + " " +  arm + " " + comboList.get(i).get(j).ID);
	    		     //finds combined currentLowestComboPrice of combo
	    			combinedPrice += comboItem.price;
	    			
	    		}
	    	
	    		//if combination is valid and whether the combined price of the combination is smallest possible one


	    		if(combinedPrice <= currentLowestComboPrice && total) {
	    			//saves index of currently found combination
	    			index1 = i;
	    			//updates current lowest price with combined price of combination
	    			currentLowestComboPrice = combinedPrice;
	    		}
	    		
	    		//resets variables for each iteration
	    		
	    		Rails = false;
	    		Cabinet = false;
	    		
	    		Drawers = false;
	    	}
		
	    	//if no valid combo is found, index1 is -1. Therefore order is invalid and list of recommended manufacturers are returned
	      	if(index1 ==-1) {
	      		returnList.clear();
	    		  try {                    
	  	            Statement myStmt = dbConnect.createStatement();
	  	          //looks for all manufacturers with ids that make filings and saves them into results
	  	            results = myStmt.executeQuery("SELECT * FROM Manufacturer WHERE ManuID IN (002,004,005)");
	  	          while (results.next()){	 
	  	        	  //saves the name of each filing manufacturer into list
		             returnList.add(results.getString("Name"));
		            }
		            
		            myStmt.close();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
	  	          return returnList; 	
	    	}
	    	ArrayList<node> finalCombo = comboList.get(index1);
	    	for(int h = 0; h < finalCombo.size(); h++) {
	    		//adds currently found combination items to return list
	    		returnList.add(finalCombo.get(h).id);
	    		//removes currently found combination from list of available items
	    		matches.remove(finalCombo.get(h));
	    		
	    	}
	    	//adds current lowest price to total order price
	    	orderPrice += currentLowestComboPrice;
	    	
			}
			find.close();
			results.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//adds order price to return list
		returnList.add("$" + String.valueOf(orderPrice));
		
		
		
		if (returnList != null) {
    		for (int i = 0; i < (returnList.size() -1); i++){
    			if (returnList.get(i).charAt(0) == 'F') {
    				deleteFiling(returnList.get(i));
    			}
    		}   		
    	}
    	
		return returnList;
		
		
	}
	/**
	 * finds lamp order
	 * @param lookup type of lamp needed in order
	 * @param needed number of lamps needed in order
	 * @return arraylist containing ordered lamps, with total order price always at the end. Returns recommended manufacturer list for invalid order
	 */
	public ArrayList<String> searchLamp(String lookup,int needed){
		
		//contains the combination with the lowest currentLowestComboPrice. Total price is always at the END of the arraylist. If combination cannot be created, this is contains recommended manufacturer list
    	ArrayList<String> returnList = new ArrayList<String>();
    	//total price of order
    	
    	long orderPrice = 0;
    	ResultSet results;
    	try {
    		//finds all entries of lamp with the specified type and saves them into results
    		PreparedStatement find = dbConnect.prepareStatement("SELECT * FROM LAMP WHERE Type=?");
			find.setString(1,lookup);
			results = find.executeQuery();
		
			
			
			//used to check if combination is valid
			boolean Base = false;
			boolean Bulb = false;
		
			
			boolean total = false;
			//matches will have all the lamp entries of specified type
		    ArrayList<node> matches = new ArrayList<node>();
			node temp1= null;
			
			while (results.next()){
				
				//saves data of each lamp entry of specified type into node and then saves that node into matches list
				  lampData temp = new lampData(results.getString("ID"),results.getString("Bulb"),results.getString("Base"),results.getInt("Price"));
				    temp1 = new node(temp);
				   matches.add(temp1);
				 
				   
				
			
	            }
			//returns manufacturer list immediately if no results were found
			if(temp1 == null) {
				returnList.clear();
	    		  try {                    
	  	            Statement myStmt = dbConnect.createStatement();
	  	        //looks for all manufacturer with id that make lamps and saves them into results
	  	            results = myStmt.executeQuery("SELECT * FROM Manufacturer WHERE ManuID IN (002,004,005)");
	  	          while (results.next()){	
	  	        	//adds the name of each lamp manufacturer to list
		             returnList.add(results.getString("Name"));
		            }
		            
		            myStmt.close();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
	  	          return returnList; 	
			}
		
			//iterates once for each item required in order
			for(int b = 0; b < needed ;b++) {
			//current lowest combination price
			long currentLowestComboPrice = Long.MAX_VALUE;
			//intializes all possible combination list comboList
			comboList = new ArrayList<ArrayList<node>>();
			int index1 = -1;
			//price of current combination
			long combinedPrice = 0;

			//empty arraylist
			ArrayList<node> g = new ArrayList<node>();
			//finds the power set/all possible combinations for the matches list

			powerSet(matches,g,0);
			//removes the empty set
			comboList.remove(0);
			//iterates over each possible combination/subset
	    	for(int i = 0; i < comboList.size(); i++) {
	    		//iterates over each element in combination
	    		for(int j = 0; j< comboList.get(i).size(); j++) {
	    			lampData comboItem = (lampData) comboList.get(i).get(j).element;
	    			//checks if combination is valid by checking if at least 1 element is true for each part
	    			Base |= comboItem.Base ;
	    		
	    		    Bulb |= comboItem.Bulb;
	    		    
	    		  //total determines whether the combination is valid by seeing if each combination has required parts
	    		     total = Base && Bulb;
	    		    
	    		   //System.out.println(comboList.get(i).get(j).arms + " " +  arm + " " + comboList.get(i).get(j).ID);
	    		     //finds combined price of combo
	    			combinedPrice += comboItem.price;
	    			
	    		}
	    		
	    		//if combination is valid and whether the combined price of the combination is the smallest possible one

	    		if(combinedPrice <= currentLowestComboPrice && total) {
	    			//saves current combination index
	    			index1 = i;
	    			//updates current lowest price with combined price of combination
	    			currentLowestComboPrice = combinedPrice;
	    		}
	    		
	    		//resets variables for each iteration
	    		combinedPrice = 0;
	    		Base = false;
	    		Bulb = false;
	    		
	    	
	    	}
			
	    	
	    	//if valid combo could not be found index = -1. Therefore order is invalid and list of recommended manufacturers is returned
	      	if(index1 ==-1) {
	      		returnList.clear();
	    		  try {                    
	  	            Statement myStmt = dbConnect.createStatement();
	  	          //looks for all manufacturers with ids that make lamps and saves them into results
	  	            results = myStmt.executeQuery("SELECT * FROM Manufacturer WHERE ManuID IN (002,004,005)");
	  	          while (results.next()){	
	  	        	//adds the name of each lamp manufacturer to list
		             returnList.add(results.getString("Name"));
		            }
		            
		            myStmt.close();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
	  	          return returnList; 	
	    	}
	    	ArrayList<node> finalCombo = comboList.get(index1);
	    	for(int h = 0; h < finalCombo.size(); h++) {
	    		//adds currently found combination items to return list
	    		returnList.add(finalCombo.get(h).id);
	    		//removes currently found combo items from available items
	    		matches.remove(finalCombo.get(h));
	    		//System.out.println(returnList.get(h));
	    	}
	    	//increases price of order by currently found combo price
	    	orderPrice += currentLowestComboPrice;
			}
			//go.printList();
			results.close();
			find.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//adds order price to return list
    	returnList.add("$" + String.valueOf(orderPrice));
    	
    	
		if (returnList != null) {
    		for (int i = 0; i < (returnList.size() -1); i++){
    			if (returnList.get(i).charAt(0) == 'L') {
    				deleteLamp(returnList.get(i));
    			}
    		}   		
    	}
    	
    	
		return returnList;
	}
	/**
	 * finds desk order
	 * @param lookup type of desk needed for order
	 * @param needed number of desks needed for order
	 * @return string arraylist containing ids in desk order.Returns recommended manufacturer list for invalid order. Total order price always at the end as string.
	 */
	public ArrayList<String> searchDesk(String lookup, int needed){
		//total price of order
		long orderPrice = 0;

		//contains the combination with the lowest total price. Total price is always at the END of the arraylist. If combination cannot be created, this contains manufacturer list
    	ArrayList<String> returnList = new ArrayList<String>();
    	ResultSet results;
    	try {
    		//finds all entries of desk with the specified type and saves them into results
    		PreparedStatement find = dbConnect.prepareStatement("SELECT * FROM DESK WHERE Type=?");
			find.setString(1,lookup);
			results = find.executeQuery();
			
			
			
			//used to check if combination is valid
			boolean Top = false;
			boolean legs = false;
			boolean Drawer = false;
			
			boolean total = false;
			//matches will contain all the desk entries with specified type
		    ArrayList<node> matches = new ArrayList<node>();
			node temp1= null;
			
			while (results.next()){
				//saves data of each desk entry of type needed into temp node and then saves that temp node into matches list
				deskData temp = new deskData(results.getString("ID"),results.getString("Legs"),results.getString("Top"),results.getString("Drawer"),results.getInt("Price"));
				temp1 = new node<deskData>(temp);
				matches.add(temp1);
				 
				   
				
				
	            }
			//returns recommended manufacturer list immediately if no results were found
			if(temp1 == null) {
				returnList.clear();
	    		  try {                    
	  	            Statement myStmt = dbConnect.createStatement();
	  	        //looks for all manufacturers with id that make desks and saves them into results
	  	            results = myStmt.executeQuery("SELECT * FROM Manufacturer WHERE ManuID IN (001,002,004,005)");
	  	          while (results.next()){
	  	        	  //saves the name of each desk manufacturer into list
		             returnList.add(results.getString("Name"));
		            }
		            
		            myStmt.close();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
	  	          return returnList; 	
			}
			//iterates once for each item needed in order
			for(int b= 0 ; b < needed ; b++) {
			//current lowest combination price
			long currentLowestComboPrice = Long.MAX_VALUE;
			//current combination price
			long combinedPrice = 0;
			//intializes all possible combination list comboList
			comboList = new ArrayList<ArrayList<node>>();
			//index of found combination
			int index1 = -1;
			ArrayList<node> g = new ArrayList<node>();
			//finds the power set/all possible combinations for the matches list

			powerSet(matches,g,0);
			//removes the empty set
			comboList.remove(0);
			//iterates over each possible combination/subset
	    	for(int i = 0; i < comboList.size(); i++) {
	    		
	    		for(int j = 0; j< comboList.get(i).size(); j++) {
	    			deskData comboItem = (deskData) comboList.get(i).get(j).element;
	    			//checks if combination is valid by checking if at least 1 element is true for each part
	    			Top |= comboItem.top ;
	    		
	    		    legs |= comboItem.legs;
	    		   
	    		    Drawer |= comboItem.Drawer;
	    		    
	    		    
	    		    //total determines whether the combination is valid by seeing if each combination has required parts
	    		     total = Top && legs && Drawer && legs;
	    		    
	    		   //System.out.println(comboList.get(i).get(j).arms + " " +  arm + " " + comboList.get(i).get(j).ID);
	    		     //finds combined price of combo
	    			combinedPrice += comboItem.price;
	    			
	    		}
	    	
	    		//if combination is valid and whether the combined price of the combination is the smallest possible one
	    		if(combinedPrice <= currentLowestComboPrice && total) {
	    			//saves index of combination
	    			index1 = i;
	    			//updates current lowest price with combined price of combination
	    			currentLowestComboPrice = combinedPrice;
	    		}
	    		
	    		//resets variables for each iteration
	    		combinedPrice = 0;
	    		Top = false;
	    		legs = false;
	    		
	    		Drawer = false;
	    	}
			
	    	//this means no combination was found and therefore order is not possible.Recommended manufacturer list is returned.
	      	if(index1 ==-1) {
	      		returnList.clear();
	    		  try {                    
	  	            Statement myStmt = dbConnect.createStatement();
	  	          //looks for all manufacturers with ids that make desks and saves them into results
	  	            results = myStmt.executeQuery("SELECT * FROM Manufacturer WHERE ManuID IN (001,002,004,005)");
	  	          while (results.next()){	 
	  	        	  //saves the name of each desk manufacturer into list
		             returnList.add(results.getString("Name"));
		            }
		            
		            myStmt.close();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
	  	          return returnList; 	        
	    	}
	    	ArrayList<node> finalCombo = comboList.get(index1);
	    	for(int h = 0; h < finalCombo.size(); h++) {
	    		//adds currently found combo items to return list
	    		returnList.add(finalCombo.get(h).id);
	    		//removes currently found combo items from list of possible items
	    		matches.remove(finalCombo.get(h));
	    	}
	    	//increases order price of order by currently found combo price
	    	orderPrice += currentLowestComboPrice;
	    	
			}
			find.close();
			results.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	returnList.add("$"+String.valueOf(orderPrice));
    	
		if (returnList != null) {
    		for (int i = 0; i < (returnList.size() -1); i++){
    			if (returnList.get(i).charAt(0) == 'D') {
    				deleteDesk(returnList.get(i));
    			}
    		}   		
    	}
    	
		return returnList;
		
	}
	/**
	 * finds chair order
	 * @param lookup type of chair
	 * @param needed amount needed for order
	 * @return string arraylist of chair ids ordered. String containing total price is always at the end. Returns recommended manufacturer list for invalid order.
	 */
	public ArrayList<String> searchChair(String lookup,int needed) {
		//total order price
		long orderPrice = 0;

    	//contains the combination with the lowest currentLowestComboPrice. Order Price is always at the END of the arraylist. If combination cannot be created, this contains recommended manufacturer list
    	ArrayList<String> returnList = new ArrayList<String>();
    	ResultSet results;
    	
    	try {
    		//finds all entries of chair with the specified type and saves them into results
    		PreparedStatement find = dbConnect.prepareStatement("SELECT * FROM CHAIR WHERE Type=?");
			find.setString(1,lookup);
			results = find.executeQuery();
	
			
		
			//used to check if combination is valid
			boolean arm = false;
			boolean legs = false;
			boolean seat = false;
			boolean cushion = false;
			boolean total = false;
			//contains the chair entries for required type
		    ArrayList<node> matches = new ArrayList<node>();
			node temp1= null;
			
			while (results.next()){				
				//saves data of each chair entry with specified type into temp node and adds it to matches list
				chairData temp = new chairData(results.getString("ID"),results.getString("Legs"),results.getString("Arms"),results.getString("Seat"),results.getString("Cushion"),results.getInt("Price"));
				temp1 = new node(temp);
				matches.add(temp1);

	         }
			//returns recommended manufacturer list immediately if no results were found
			if(temp1 == null) {
				returnList.clear();
	    		  try {                    
	  	            Statement myStmt = dbConnect.createStatement();
	  	          //looks for all manufacturers with ids that make chairs and saves them into results
	  	            results = myStmt.executeQuery("SELECT * FROM Manufacturer WHERE ManuID IN (002,003,004,005)");
	  	          while (results.next()){
	  	        	//saves the name of each chair manufacturer into list
		             returnList.add(results.getString("Name"));
		            }
		            
		            myStmt.close();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
	  	          return returnList; 	        
			}
			//iterates once for each item required in order
			for(int b = 0; b < needed ; b++) {
			//current lowest combination price	
			long currentLowestComboPrice = Long.MAX_VALUE;
			//saves index of found combination
			int index1 = -1;
			//intializes all possible combination list comboList
			comboList = new ArrayList<ArrayList<node>>();
			//this is the current combination price
			long combinedPrice = 0;

			//empty arraylist
			ArrayList<node> g = new ArrayList<node>();
			//finds the power set/all possible combinations for the matches list

			powerSet(matches,g,0);
			//removes the empty set
			comboList.remove(0);
			//iterates over each possible combination/subset
	    	for(int i = 0; i < comboList.size(); i++) {
	    		//iterates over each element in combination
	    		for(int j = 0; j< comboList.get(i).size(); j++) {
	    			chairData comboItem = (chairData) comboList.get(i).get(j).element;
	    			//checks if combination is valid by checking if at least 1 element is true for each part
	    			arm |= comboItem.arms ;
	    		
	    		    seat |= comboItem.seat;
	    		   
	    		    cushion |= comboItem.cushion;
	    		    
	    		    legs |= comboItem.legs;
	    		    //total determines whether the combination is valid by seeing if each combination has required parts
	    		     total = seat && arm && cushion && legs;
	    		    
	    		   
	    		     //finds combined price of combo
	    			combinedPrice += comboItem.price;
	    			
	    		}
	    		
	    		//if combination is valid and whether the combined price of the combination is smallest possible one
	    		if(combinedPrice <= currentLowestComboPrice && total) {
	    			//saves index of found combination
	    			index1 = i;
	    			//updates current lowest price with combined price of combination
	    			currentLowestComboPrice = combinedPrice;
	    		}
	    		
	    		//resets variables for each iteration
	    		combinedPrice = 0;
	    		arm = false;
	    		seat = false;
	    		cushion = false;
	    		legs = false;
	    	}
			
	    	
	    	//if index1 = -1, then no valid combo could be found and therefore order is invalid. Recommended manufacturer list is returned.
	    	if(index1 ==-1) {
	    		returnList.clear();
	    		  try {                    
	  	            Statement myStmt = dbConnect.createStatement();
	  	          //looks for all manufacturers with ids that make chairs and saves them into results
	  	            results = myStmt.executeQuery("SELECT * FROM Manufacturer WHERE ManuID IN (002,003,004,005)");
	  	          while (results.next()){	 
	  	        	//saves the name of each chair manufacturer into list
		             returnList.add(results.getString("Name"));
		            }
		            
		            myStmt.close();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
	  	          return returnList; 	          
	    	}
	    	ArrayList<node> finalCombo = comboList.get(index1);
	    	for(int h = 0; h < finalCombo.size(); h++) {
	    		//adds currently found combo items to return list
	    		returnList.add(finalCombo.get(h).id);
	    		//removes currently found combo items from list of available items
	    		matches.remove(finalCombo.get(h));
	    		
	    	}
	    	//increases total price of order by currently found lowest combo price
	    	orderPrice += currentLowestComboPrice;
			}
			find.close();
			results.close();
			//go.printList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//adds order price to return list
    	returnList.add("$"+String.valueOf(orderPrice));
    	

    	
		if (returnList != null) {
    		for (int i = 0; i < (returnList.size() -1); i++){
    			if (returnList.get(i).charAt(0) == 'C') {
    				deleteChair(returnList.get(i));
    			}
    		}   		
    	}
    	
    	
    	
		return returnList;
		
		
		
	}
	/**
	 * finds the power set / all possible combinations of a given arg list
	 * @param arg list to find power set/ all possible combinations of arg
	 * @param currentSet currently created subset/combination
	 * @param index index of element being looked at in the arg list
	 */
	private void powerSet(ArrayList<node> arg,ArrayList<node> currentSet,int index) {
		//base case
		if(index == arg.size()) {
			//adds the current set to the power set / list of all possible combinations
			comboList.add(currentSet);
			return;
		}
		//copy of current set/combination before adding to it. This contains the current set/combination without the current element being looked at in the arg set
		ArrayList<node> copyOfCurrentSet = (ArrayList<node>) currentSet.clone();
		//adds current element being looked at to current set(one case of any subset/combination)
		currentSet.add(arg.get(index));
		
		powerSet(arg,copyOfCurrentSet,index+1);
		//This contains the current set/combination with the current element being looked at in the arg set/list(other case of any subset/combination)
		powerSet(arg,currentSet,index+1);
		
	}
	/**
	 * finds all possible  manufacturers
	 * @param  furniture type of furniture where manufacturers are being looked into (e.g, chair, filing, desk, lamp).
	 * This needs to be in all lowercase
	 * @return returns a list of chair manufacturers
	 * @throws SQLException 
	 */
	
	/**
	 * Method to delete the chair currently bought from the database by using the chairID
	 * preparedStatement connects to database using parameter query which "DELETES from chair" for a certain chairID, which is the input to this method.
	 * SetString method for preparedStatement used to make the change, and then executeUpdate executes the Statement so that the database is impacted.
	 * Everything is closed properly using the close method and try catch block is there to catch any SQLExceptions.
	 * @param ChairID ChairID of the chair to be deleted
	 */
	
	public void deleteChair(String ChairID) throws IllegalArgumentException{
		try {
            String query = "DELETE FROM chair WHERE ID = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setString(1, ChairID);
            int resultCount = myStmt.executeUpdate();
            myStmt.close();

            if (resultCount == 0) {
            	throw new IllegalArgumentException();
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
	}
	/**
	 * Method to delete the desk currently bought from the database by using the deskID
	 * preparedStatement connects to database using parameter query which "DELETES from desk" for a certain deskID, which is the input to this method.
	 * SetString method for preparedStatement used to make the change, and then executeUpdate executes the Statement so that the database is impacted.
	 * Everything is closed properly using the close method and try catch block is there to catch any SQLExceptions.
	 * @param DeskID DeskID of the desk to be deleted
	 */
	public void deleteDesk(String DeskID) {
		try {
            String query = "DELETE FROM desk WHERE ID = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setString(1, DeskID);
            int resultCount = myStmt.executeUpdate();
            myStmt.close();

            if (resultCount == 0) {
            	throw new IllegalArgumentException();
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
	}
	/**
	 * Method to delete the filing currently bought from the database by using the filingID
	 * preparedStatement connects to database using parameter query which "DELETES from filing" for a certain filingID, which is the input to this method.
	 * SetString method for preparedStatement used to make the change, and then executeUpdate executes the Statement so that the database is impacted.
	 * Everything is closed properly using the close method and try catch block is there to catch any SQLExceptions.
	 * @param FilingID FilingID of the filing to be deleted
	 */
	public void deleteFiling(String FilingID) {
		try {
            String query = "DELETE FROM filing WHERE ID = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setString(1, FilingID);
            int resultCount = myStmt.executeUpdate();
            myStmt.close();

            if (resultCount == 0) {
            	throw new IllegalArgumentException();
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
	}
	/**
	 * Method to delete the lamp currently bought from the database by using the lampID
	 * preparedStatement connects to database using parameter query which "DELETES from lamp" for a certain lampID, which is the input to this method.
	 * SetString method for preparedStatement used to make the change, and then executeUpdate executes the Statement so that the database is impacted.
	 * Everything is closed properly using the close method and try catch block is there to catch any SQLExceptions.
	 * @param LampID LampID of the lamp to be deleted
	 */
	public void deleteLamp(String LampID) {
		try {
            String query = "DELETE FROM lamp WHERE ID = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setString(1, LampID);
            int resultCount = myStmt.executeUpdate();
            myStmt.close();

            if (resultCount == 0) {
            	throw new IllegalArgumentException();
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
	}
	
	/**
	 * Method to delete the manufacturer by using ManuID
	 * preparedStatement connects to database using parameter query which "DELETES from manufacturer" for a certain ManuID, which is the input to this method.
	 * SetString method for preparedStatement used to make the change, and then executeUpdate executes the Statement so that the database is impacted.
	 * Everything is closed properly using the close method and try catch block is there to catch any SQLExceptions.
	 * @param ManuID, ManuID of the manufacturer to be deleted
	 */
	public void deleteManufacturer(String ManuID) {
		try {
            String query = "DELETE FROM manufacturer WHERE ID = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setString(1, ManuID);
            int resultCount = myStmt.executeUpdate();
            myStmt.close();

            if (resultCount == 0) {
            	throw new IllegalArgumentException();
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
	}
	
	
}
