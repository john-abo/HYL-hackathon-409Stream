package hyl_project;
import java.sql.*;
import java.util.ArrayList;
public class search {
	private Connection dbConnect;
	public final String DBURL;
	public final String  USERNAME;
	public final String PASSWORD;
	private ResultSet results;
	//done contains the power set or all possible combinations of the currently found item list
	private ArrayList<ArrayList<node>> done;
	/**
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
	 */
	public void initializeConnection() {
		 try {
			dbConnect = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * finds filing order
	 * @param lookup type of filing
	 * @param needed number needed for order
	 * @return arraylist string containing ids of all ordered items, returns null if order not possible
	 */
	public ArrayList<String> searchFiling(String lookup,int needed){
		//contains the combination with the lowest price. Price is always at the END of the arraylist. If combination cannot be created, this is empty
	    ArrayList<String> done2 = new ArrayList<String>();
	    //total order price
	    long orderPrice = 0;
	   

    	try {
    		
    		//finds all entries of chair with the specified type and saves them into results
    		PreparedStatement find = dbConnect.prepareStatement("SELECT * FROM filing WHERE Type=?");
			find.setString(1,lookup);
			results = find.executeQuery();
		
			
			//used to check if combination is valid
			boolean Rails = false;
			boolean Cabinet = false;
			boolean Drawers = false;
			
			boolean total = false;
			//list will all the matches
		    ArrayList<node> matches = new ArrayList<node>();
			node temp1= null;
			
			while (results.next()){
				if(results.getString("Type").equals(lookup)) {
					
				   filingData temp = new filingData(results.getString("ID"),results.getString("Rails"),results.getString("Cabinet"),results.getString("Drawers"),results.getInt("Price"));
				    temp1 = new node(temp);
				   matches.add(temp1);
				 
				   
				}
				
	            }
			//returns immediately if no results were found
			if(temp1 == null) {
				return null;
			}
			//iterates once to find each item required in order
			for(int b = 0; b < needed ; b++) {
				//price of current combination
				 long combinedPrice = 0;
				 //current lowest combination price
				long price = Long.MAX_VALUE;
				//intializes all possible combination list done
				done = new ArrayList<ArrayList<node>>();
			//saves index of found combination
		    int index1 = -1;
			//empty arraylist
			ArrayList<node> g = new ArrayList<node>();
			//finds the power set/all possible combinations for the matches list

			powerSet(matches,g,0);
			//removes the empty set
			done.remove(0);
			//iterates over each possible combination/subset
	    	for(int i = 0; i < done.size(); i++) {
	    		//resets for next combination price
	    		combinedPrice = 0;
	    		//iterates over each element in combination
	    		for(int j = 0; j< done.get(i).size(); j++) {
	    			filingData v = (filingData) done.get(i).get(j).element;
	    			//checks if combination is valid by checking if at least 1 element is true for each part
	    			Rails |= v.Rails ;
	    		
	    		   Cabinet |= v.Cabinet;
	    		   
	    		    Drawers |= v.Drawers;
	    		    
	    		    
	    		    //total determines whether the combination is valid by seeing if each combination has required parts
	    		     total = Rails && Drawers && Cabinet;
	    		    
	    		   //System.out.println(done.get(i).get(j).arms + " " +  arm + " " + done.get(i).get(j).ID);
	    		     //finds combined price of subset
	    			combinedPrice += v.price;
	    			
	    		}
	    	
	    		//if combination is valid and whether the combined price of the combination is smallest possible one


	    		if(combinedPrice <= price && total) {
	    			//saves index of currently found combination
	    			index1 = i;
	    			//updates current lowest combination price with price of current combination
	    			price = combinedPrice;
	    		}
	    		
	    		//resets variables for each iteration
	    		
	    		Rails = false;
	    		Cabinet = false;
	    		
	    		Drawers = false;
	    	}
		
	    	//if no valid combo is found, index1 is -1. Therefore order is invalid
	      	if(index1 ==-1) {
	      		
	    		return null;
	    	}
	    	ArrayList<node> finalCombo = done.get(index1);
	    	for(int h = 0; h < finalCombo.size(); h++) {
	    		//adds currently found combination items to return list
	    		done2.add(finalCombo.get(h).id);
	    		//removes currently found combination from list of available items
	    		matches.remove(finalCombo.get(h));
	    		
	    	}
	    	//adds current lowest price to total order price
	    	orderPrice += price;
	    	
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//adds price to return list
		done2.add("$" + String.valueOf(orderPrice));
		return done2;
		
		
	}
	/**
	 * finds lamp order
	 * @param lookup type of lamp needed in order
	 * @param needed number of lamps needed in order
	 * @return arraylist containing ordered lamps, with price always at the end. Returns null for invalid orders
	 */
	public ArrayList<String> searchLamp(String lookup,int needed){
		
		//contains the combination with the lowest price. Price is always at the END of the arraylist. If combination cannot be created, this is empty
    	ArrayList<String> done2 = new ArrayList<String>();
    	//total price of order

    	long orderPrice = 0;

    	try {
    		//finds all entries of chair with the specified type and saves them into results
    		PreparedStatement find = dbConnect.prepareStatement("SELECT * FROM LAMP WHERE Type=?");
			find.setString(1,lookup);
			results = find.executeQuery();
		
			
			
			//used to check if combination is valid
			boolean Base = false;
			boolean Bulb = false;
		
			
			boolean total = false;
			//list will have all the matches
		    ArrayList<node> matches = new ArrayList<node>();
			node temp1= null;
			
			while (results.next()){
				if(results.getString("Type").equals(lookup)) {
					
				  lampData temp = new lampData(results.getString("ID"),results.getString("Bulb"),results.getString("Base"),results.getInt("Price"));
				    temp1 = new node(temp);
				   matches.add(temp1);
				 
				   
				}
				
	            }
			//returns immediately if no results were found
			if(temp1 == null) {
				return null;
			}
		
			//iterates once for each item required in order
			for(int b = 0; b < needed ;b++) {
			//current lowest combination price
			long price = Long.MAX_VALUE;
			//intializes all possible combination list done
			done = new ArrayList<ArrayList<node>>();
			int index1 = -1;
			//price of current combination
			long combinedPrice = 0;

			//empty arraylist
			ArrayList<node> g = new ArrayList<node>();
			//finds the power set/all possible combinations for the matches list

			powerSet(matches,g,0);
			//removes the empty set
			done.remove(0);
			//iterates over each possible combination/subset
	    	for(int i = 0; i < done.size(); i++) {
	    		//iterates over each element in combination
	    		for(int j = 0; j< done.get(i).size(); j++) {
	    			lampData v = (lampData) done.get(i).get(j).element;
	    			//checks if combination is valid by checking if at least 1 element is true for each part
	    			Base |= v.Base ;
	    		
	    		    Bulb |= v.Bulb;
	    		   
	    		
	    		    
	    		   
	    		    //total determines whether the combination is valid by seeing if each combination has required parts
	    		     total = Base && Bulb;
	    		    
	    		   //System.out.println(done.get(i).get(j).arms + " " +  arm + " " + done.get(i).get(j).ID);
	    		     //finds combined price of subset
	    			combinedPrice += v.price;
	    			
	    		}
	    		//System.out.println(combinedPrice);
	    		//if combination is valid and whether the combined price of the combination is smallest possible one

	    		if(combinedPrice <= price && total) {

	    			index1 = i;
	    			//updates current lowest combination price with price of current combination
	    			price = combinedPrice;
	    		}
	    		
	    		//resets variables for each iteration
	    		combinedPrice = 0;
	    		Base = false;
	    		Bulb = false;
	    		
	    	
	    	}
			
	    	
	    	//if valid combo could not be found index = -1. Therefore order is invalid
	      	if(index1 ==-1) {
	    		return null;
	    	}
	    	ArrayList<node> finalCombo = done.get(index1);
	    	for(int h = 0; h < finalCombo.size(); h++) {
	    		//adds currently found combination items to return list
	    		done2.add(finalCombo.get(h).id);
	    		//removes currently found combo items from available items
	    		matches.remove(finalCombo.get(h));
	    		//System.out.println(done2.get(h));
	    	}
	    	//increases price of order by currently found combo price
	    	orderPrice += price;
			}
			//go.printList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	done2.add("$" + String.valueOf(orderPrice));
    	
		return done2;
	}
	/**
	 * finds desk order
	 * @param lookup type of desk needed for order
	 * @param needed number of desks needed for order
	 * @return arraylist containg ids of desks order.Returns null for invalid order. Price always at the end as string.
	 */
	public ArrayList<String> searchDesk(String lookup, int needed){
		//total price of order
		long orderPrice = 0;

		//contains the combination with the lowest price. Price is always at the END of the arraylist. If combination cannot be created, this is empty
    	ArrayList<String> done2 = new ArrayList<String>();
    
    	try {
    		//finds all entries of chair with the specified type and saves them into results
    		PreparedStatement find = dbConnect.prepareStatement("SELECT * FROM DESK WHERE Type=?");
			find.setString(1,lookup);
			results = find.executeQuery();
			
			
			
			//used to check if combination is valid
			boolean Top = false;
			boolean legs = false;
			boolean Drawer = false;
			
			boolean total = false;
			//list will all the matches
		    ArrayList<node> matches = new ArrayList<node>();
			node temp1= null;
			
			while (results.next()){
				
				if(results.getString("Type").equals(lookup)) {
					
				   deskData temp = new deskData(results.getString("ID"),results.getString("Legs"),results.getString("Top"),results.getString("Drawer"),results.getInt("Price"));
				    temp1 = new node<deskData>(temp);
				   matches.add(temp1);
				 
				   
				}
				
	            }
			//returns immediately if no results were found
			if(temp1 == null) {
				
				return null;
			}
			//iterates once for each item needed in order
			for(int b= 0 ; b < needed ; b++) {
			//current lowest combination price
			long price = Long.MAX_VALUE;
			//current combination price
			long combinedPrice = 0;
			//intializes all possible combination list done
			done = new ArrayList<ArrayList<node>>();
			//index of found combination
			int index1 = -1;
			ArrayList<node> g = new ArrayList<node>();
			//finds the power set/all possible combinations for the matches list

			powerSet(matches,g,0);
			//removes the empty set
			done.remove(0);
			//iterates over each possible combination/subset
	    	for(int i = 0; i < done.size(); i++) {
	    		
	    		for(int j = 0; j< done.get(i).size(); j++) {
	    			deskData v = (deskData) done.get(i).get(j).element;
	    			//checks if combination is valid by checking if at least 1 element is true for each part
	    			Top |= v.top ;
	    		
	    		    legs |= v.legs;
	    		   
	    		    Drawer |= v.Drawer;
	    		    
	    		    
	    		    //total determines whether the combination is valid by seeing if each combination has required parts
	    		     total = Top && legs && Drawer && legs;
	    		    
	    		   //System.out.println(done.get(i).get(j).arms + " " +  arm + " " + done.get(i).get(j).ID);
	    		     //finds combined price of subset
	    			combinedPrice += v.price;
	    			
	    		}
	    		//System.out.println(combinedPrice);
	    		//if combination is valid and whether the combined price of the combination is smallest possible one
	    		if(combinedPrice <= price && total) {
	    			//saves index of combination
	    			index1 = i;
	    			//updates current lowest combination price with price of current combination
	    			price = combinedPrice;
	    		}
	    		
	    		//resets variables for each iteration
	    		combinedPrice = 0;
	    		Top = false;
	    		legs = false;
	    		
	    		Drawer = false;
	    	}
			
	    	//this means no combination was found and therefore order is not possible
	      	if(index1 ==-1) {
	      		
	    		return null;
	    	}
	    	ArrayList<node> finalCombo = done.get(index1);
	    	for(int h = 0; h < finalCombo.size(); h++) {
	    		//adds currently found combo items to return list
	    		done2.add(finalCombo.get(h).id);
	    		//removes currently found combo items from list of possible items
	    		matches.remove(finalCombo.get(h));
	    	}
	    	//increases price of order by currently found combo price
	    	orderPrice += price;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	done2.add("$"+String.valueOf(orderPrice));
		return done2;
		
	}
	/**
	 * finds chair order
	 * @param lookup type of chair
	 * @param needed amount needed for order
	 * @return arraylist of ids ordered. String containg total price is always at the end.If order is invalid, null is returned.
	 */
	public ArrayList<String> searchChair(String lookup,int needed) {
		//total order price
		long orderPrice = 0;

    	//contains the combination with the lowest price. Price is always at the END of the arraylist. If combination cannot be created, this is empty
    	ArrayList<String> done2 = new ArrayList<String>();
    	
    	
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
			//list will all the matches
		    ArrayList<node> matches = new ArrayList<node>();
			node temp1= null;
			
			while (results.next()){
				if(results.getString("Type").equals(lookup)) {
					
				   chairData temp = new chairData(results.getString("ID"),results.getString("Legs"),results.getString("Arms"),results.getString("Seat"),results.getString("Cushion"),results.getInt("Price"));
				    temp1 = new node(temp);
				   matches.add(temp1);
				 
				   
				}
				
	            }
			//returns immediately if no results were found
			if(temp1 == null) {
				return null;
			}
			//iterates once for each item required in order
			for(int b = 0; b < needed ; b++) {
			//current lowest combination price	
			long price = Long.MAX_VALUE;
			//saves index of found combination
			int index1 = -1;
			//intializes all possible combination list done
			done = new ArrayList<ArrayList<node>>();
			//this is the current combination price
			long combinedPrice = 0;

			//empty arraylist
			ArrayList<node> g = new ArrayList<node>();
			//finds the power set/all possible combinations for the matches list

			powerSet(matches,g,0);
			//removes the empty set
			done.remove(0);
			//iterates over each possible combination/subset
	    	for(int i = 0; i < done.size(); i++) {
	    		//iterates over each element in combination
	    		for(int j = 0; j< done.get(i).size(); j++) {
	    			chairData v = (chairData) done.get(i).get(j).element;
	    			//checks if combination is valid by checking if at least 1 element is true for each part
	    			arm |= v.arms ;
	    		
	    		    seat |= v.seat;
	    		   
	    		    cushion |= v.cushion;
	    		    
	    		    legs |= v.legs;
	    		    //total determines whether the combination is valid by seeing if each combination has required parts
	    		     total = seat && arm && cushion && legs;
	    		    
	    		   
	    		     //finds combined price of subset
	    			combinedPrice += v.price;
	    			
	    		}
	    		
	    		//if combination is valid and whether the combined price of the combination is smallest possible one
	    		if(combinedPrice <= price && total) {
	    			//saves index of found combination
	    			index1 = i;
	    			//updates current lowest combination price with price of current combination
	    			price = combinedPrice;
	    		}
	    		
	    		//resets variables for each iteration
	    		combinedPrice = 0;
	    		arm = false;
	    		seat = false;
	    		cushion = false;
	    		legs = false;
	    	}
			
	    	
	    	//if index1 = -1, then no valid combo could be found and therefore order is invalid
	    	if(index1 ==-1) {
	    		return null;
	    	}
	    	ArrayList<node> finalCombo = done.get(index1);
	    	for(int h = 0; h < finalCombo.size(); h++) {
	    		//adds currently found combo items to return list
	    		done2.add(finalCombo.get(h).id);
	    		//removes currently found combo items from list of available items
	    		matches.remove(finalCombo.get(h));
	    		
	    	}
	    	//increases price of order by currently found combo price
	    	orderPrice += price;
			}
			//go.printList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//adds order price to return list
    	done2.add("$"+String.valueOf(orderPrice));
		return done2;
		
		
		
	}
	/**
	 * finds the power set / all possible combinations of a given arg list
	 * @param arg list to find power set/ all possible combinations of arg
	 * @param currentSet currently created subset/combination
	 * @param index index of element being looked at in the arg list
	 */
	public void powerSet(ArrayList<node> arg,ArrayList<node> currentSet,int index) {
		//base case
		if(index == arg.size()) {
			//adds the current set to the power set / list of all possible combinations
			done.add(currentSet);
			return;
		}
		//copy of current set/combination before adding to it. This contains the current set/combination without the current element being looked at in the arg set
		ArrayList<node> b = (ArrayList<node>) currentSet.clone();
		//adds current element being looked at to current set(one case of any subset/combination)
		currentSet.add(arg.get(index));
		
		powerSet(arg,b,index+1);
		//This contains the current set/combination with the current element being looked at in the arg set/list(other case of any subset/combination)
		powerSet(arg,currentSet,index+1);
		
	}
	
	public void deleteChair(String ChairID) {
		try {
            String query = "DELETE FROM chair WHERE ID = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setString(1, ChairID);
            myStmt.executeUpdate();
            myStmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
	}
	public void deleteDesk(String DeskID) {
		try {
            String query = "DELETE FROM desk WHERE ID = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setString(1, DeskID);
            myStmt.executeUpdate();
            myStmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
	}
	
	public void deleteFiling(String FilingID) {
		try {
            String query = "DELETE FROM filing WHERE ID = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setString(1, FilingID);
            myStmt.executeUpdate();
            myStmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
	}
	public void deleteLamp(String LampID) {
		try {
            String query = "DELETE FROM lamp WHERE ID = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setString(1, LampID);
            myStmt.executeUpdate();
            myStmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
	}
	public void deleteManufacturer(String ManuID) {
		try {
            String query = "DELETE FROM manufacturer WHERE ID = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setString(1, ManuID);
            myStmt.executeUpdate();
            myStmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
	}
	
	
	
	

}
