package hyl_project;
import java.sql.*;
import java.util.ArrayList;
public class search {
	private Connection dbConnect;
	public final String DBURL;
	public final String  USERNAME;
	public final String PASSWORD;
	private ResultSet results;
	private filingData[] deletedFilingData;
	private deskData[] deletedDeskData;
	private chairData[] deletedChairData;
	private lampData[] deletedLampData;
	//done contains the power set
	private ArrayList<ArrayList<node>> done = new ArrayList<ArrayList<node>>();
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
	 * 
	 * @param lookup type of filing
	 * @param needed number needed for order
	 * @return arraylist string containing ids of all ordered items, returns null if order not possible
	 */
	public ArrayList<String> searchFiling(String lookup,int needed){
		//contains the combination with the lowest price. Price is always at the END of the arraylist. If combination cannot be created, this is empty
	    ArrayList<String> done2 = new ArrayList<String>();
	    int orderPrice = 0;
	    int combinedPrice = 0;
    	try {
    		
    		//finds all entries of chair with the specified type and saves them into results
    		PreparedStatement find = dbConnect.prepareStatement("SELECT * FROM filing WHERE Type=?");
			find.setString(1,lookup);
			results = find.executeQuery();
			//current price to save
			
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
				int price = 2147483647;
				
				done = new ArrayList<ArrayList<node>>();
			
		    int index1 = -1;
			//empty arraylist
			ArrayList<node> g = new ArrayList<node>();
			//finds the power set for the matches list

			powerSet(matches,g,0);
			//removes the empty set
			done.remove(0);
			//iterates over each subset in power set
	    	for(int i = 0; i < done.size(); i++) {
	    		combinedPrice = 0;
	    		//iterates over each element in subset
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
	    		//System.out.println(combinedPrice);
	    		//if combination is valid and whether the combined price of the combination is smallest possible one
	    		if(combinedPrice < price && total) {
	    			index1 = i;
	    			//System.out.println(combinedPrice);
	    			price = combinedPrice;
	    		}
	    		//System.out.println();
	    		//resets variables for each iteration
	    		
	    		Rails = false;
	    		Cabinet = false;
	    		
	    		Drawers = false;
	    	}
			
	    	//System.out.println(price);
	    	//if no valid combo is found, index1 is -1. Therefore order is invalid
	      	if(index1 ==-1) {
	      		
	    		return null;
	    	}
	    	ArrayList<node> finalCombo = done.get(index1);
	    	for(int h = 0; h < finalCombo.size(); h++) {
	    		done2.add(finalCombo.get(h).id);
	    		//removes currently found combination from list of available items
	    		matches.remove(finalCombo.get(h));
	    		
	    	}
	    	
	    	orderPrice += price;
	    	
			}
			//go.printList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		done2.add("$" + String.valueOf(orderPrice));
		
		deletedFilingData = new filingData[done2.size()-1];
		for (int i = 0; i < deletedFilingData.length; i++) {
			deletedFilingData[i] = registerFilingHelper(done2.get(i));
		}

			
		if (done2 != null) {
    		for (int i = 0; i < (done2.size() -1); i++){
    			if (done2.get(i).charAt(0) == 'F') {
    				deleteFiling(done2.get(i));
    			}
    		}   		
    	}
    	
    	for (int i = 0; i < deletedFilingData.length; i++) {
    		registerFiling(deletedFilingData[i]);
    	}
    	
    	System.out.println(done2);
		return done2;
		
		
	}
	/**
	 * 
	 * @param lookup type of lamp needed in order
	 * @param needed number of lamps needed in order
	 * @return arraylist containing ordered lamps, with price always at the end. Returns null for invalid orders
	 */
	public ArrayList<String> searchLamp(String lookup,int needed){
		
		//contains the combination with the lowest price. Price is always at the END of the arraylist. If combination cannot be created, this is empty
    	ArrayList<String> done2 = new ArrayList<String>();
    	//price of order
    	int orderPrice = 0;
    	try {
    		//finds all entries of chair with the specified type and saves them into results
    		PreparedStatement find = dbConnect.prepareStatement("SELECT * FROM LAMP WHERE Type=?");
			find.setString(1,lookup);
			results = find.executeQuery();
			//current price to save
			int price = 2147483647;
			
			//used to check if combination is valid
			boolean Base = false;
			boolean Bulb = false;
		
			
			boolean total = false;
			//list will all the matches
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
			done = new ArrayList<ArrayList<node>>();
			int index1 = -1;
			int combinedPrice = 0;
			//empty arraylist
			ArrayList<node> g = new ArrayList<node>();
			//finds the power set for the matches list

			powerSet(matches,g,0);
			//removes the empty set
			done.remove(0);
			//iterates over each subset in power set
	    	for(int i = 0; i < done.size(); i++) {
	    		//iterates over each element in subset
	    		for(int j = 0; j< done.get(i).size(); j++) {
	    			lampData v = (lampData) done.get(i).get(j).element;
	    			//checks if combination is valid by checking if at least 1 element is true for each part
	    			Base |= v.Base;
	    		
	    		    Bulb |= v.Bulb;
	    		   
	    		
	    		    
	    		    Bulb |= v.Bulb;
	    		    //total determines whether the combination is valid by seeing if each combination has required parts
	    		     total = Base && Bulb;
	    		    
	    		   //System.out.println(done.get(i).get(j).arms + " " +  arm + " " + done.get(i).get(j).ID);
	    		     //finds combined price of subset
	    			combinedPrice += v.price;
	    			
	    		}
	    		//System.out.println(combinedPrice);
	    		//if combination is valid and whether the combined price of the combination is smallest possible one
	    		if(combinedPrice < price && total) {
	    			index1 = i;
	    			price = combinedPrice;
	    		}
	    		//System.out.println();
	    		//resets variables for each iteration
	    		combinedPrice = 0;
	    		Base = false;
	    		Bulb = false;
	    		
	    	
	    	}
			
	    	//System.out.println(price);
	    	//if valid combo could not be found index = -1. Therefore order is invalid
	      	if(index1 ==-1) {
	    		return null;
	    	}
	    	ArrayList<node> finalCombo = done.get(index1);
	    	for(int h = 0; h < finalCombo.size(); h++) {
	    		
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
    	deletedLampData = new lampData[done2.size()-1];
		for (int i = 0; i < deletedLampData.length; i++) {
			deletedLampData[i] = registerLampHelper(done2.get(i));
		}

			
		if (done2 != null) {
    		for (int i = 0; i < (done2.size() -1); i++){
    			if (done2.get(i).charAt(0) == 'L') {
    				deleteLamp(done2.get(i));
    			}
    		}   		
    	}
    	
    	for (int i = 0; i < deletedLampData.length; i++) {
    		registerLamp(deletedLampData[i]);
    	}
    	
    	System.out.println(done2);
		return done2;
	}
	/**
	 * orders desks
	 * @param lookup type of desk needed for order
	 * @param needed number of desks needed for order
	 * @return arraylist containg ids of desks order.Returns null for invalid order. Price always at the end as string.
	 */
	public ArrayList<String> searchDesk(String lookup, int needed){
		int orderPrice = 0;
		//contains the combination with the lowest price. Price is always at the END of the arraylist. If combination cannot be created, this is empty
    	ArrayList<String> done2 = new ArrayList<String>();
    
    	try {
    		//finds all entries of chair with the specified type and saves them into results
    		PreparedStatement find = dbConnect.prepareStatement("SELECT * FROM DESK WHERE Type=?");
			find.setString(1,lookup);
			results = find.executeQuery();
			//current price to save
			int price = 2147483647;
			
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
			//empty arraylist
			for(int b= 0 ; b < needed ; b++) {
			int combinedPrice = 0;
			done = new ArrayList<ArrayList<node>>();
			int index1 = -1;
			ArrayList<node> g = new ArrayList<node>();
			//finds the power set for the matches list

			powerSet(matches,g,0);
			//removes the empty set
			done.remove(0);
			//iterates over each subset in power set
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
	    			index1 = i;
	    			price = combinedPrice;
	    		}
	    		//System.out.println();
	    		//resets variables for each iteration
	    		combinedPrice = 0;
	    		Top = false;
	    		legs = false;
	    		
	    		Drawer = false;
	    	}
			
	    	//System.out.println(price);
	      	if(index1 ==-1) {
	      		
	    		return null;
	    	}
	    	ArrayList<node> finalCombo = done.get(index1);
	    
	    	for(int h = 0; h < finalCombo.size(); h++) {
	    		
	    		done2.add(finalCombo.get(h).id);
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
   
    	done2.add("$"+String.valueOf(orderPrice));
    	deletedDeskData = new deskData[done2.size()-1];
		for (int i = 0; i < deletedDeskData.length; i++) {
			deletedDeskData[i] = registerDeskHelper(done2.get(i));
		}

			
		if (done2 != null) {
    		for (int i = 0; i < (done2.size() -1); i++){
    			if (done2.get(i).charAt(0) == 'D') {
    				deleteDesk(done2.get(i));
    			}
    		}   		
    	}
    	
    	for (int i = 0; i < deletedDeskData.length; i++) {
    		registerDesk(deletedDeskData[i]);
    	}
    	
    	System.out.println(done2);
    	
    	
    	
		return done2;
		
	}
	/**
	 * finds chair order
	 * @param lookup type of chair
	 * @param needed amount needed for order
	 * @return arraylist of ids ordered. String containg total price is always at the end.If order is invalid, null is returned.
	 */
	public ArrayList<String> searchChair(String lookup,int needed) {
		int orderPrice = 0;
		
    	//contains the combination with the lowest price. Price is always at the END of the arraylist. If combination cannot be created, this is empty
    	ArrayList<String> done2 = new ArrayList<String>();
    	
    	
    	try {
    		//finds all entries of chair with the specified type and saves them into results
    		PreparedStatement find = dbConnect.prepareStatement("SELECT * FROM CHAIR WHERE Type=?");
			find.setString(1,lookup);
			results = find.executeQuery();
			//current price to save
			int price = 2147483647;
		
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
			int index1 = -1;
			done = new ArrayList<ArrayList<node>>();
			int combinedPrice = 0;
			//empty arraylist
			ArrayList<node> g = new ArrayList<node>();
			//finds the power set for the matches list

			powerSet(matches,g,0);
			//removes the empty set
			done.remove(0);
			//iterates over each subset in power set
	    	for(int i = 0; i < done.size(); i++) {
	    		//iterates over each element in subset
	    		for(int j = 0; j< done.get(i).size(); j++) {
	    			chairData v = (chairData) done.get(i).get(j).element;
	    			//checks if combination is valid by checking if at least 1 element is true for each part
	    			arm |= v.arms ;
	    		
	    		    seat |= v.seat;
	    		   
	    		    cushion |= v.cushion;
	    		    
	    		    legs |= v.legs;
	    		    //total determines whether the combination is valid by seeing if each combination has required parts
	    		     total = seat && arm && cushion && legs;
	    		    
	    		   //System.out.println(done.get(i).get(j).arms + " " +  arm + " " + done.get(i).get(j).ID);
	    		     //finds combined price of subset
	    			combinedPrice += v.price;
	    			
	    		}
	    		//System.out.println(combinedPrice);
	    		//if combination is valid and whether the combined price of the combination is smallest possible one
	    		if(combinedPrice < price && total) {
	    			index1 = i;
	    			
	    			price = combinedPrice;
	    		}
	    		//System.out.println();
	    		//resets variables for each iteration
	    		combinedPrice = 0;
	    		arm = false;
	    		seat = false;
	    		cushion = false;
	    		legs = false;
	    	}
			
	    	//System.out.println(price);
	    	//if index1 = -1, then no valid combo could be found and therefore order is invalid
	    	if(index1 ==-1) {
	    		return null;
	    	}
	    	ArrayList<node> finalCombo = done.get(index1);
	    	for(int h = 0; h < finalCombo.size(); h++) {
	    		done2.add(finalCombo.get(h).id);
	    		
	    		//removes currently found combo from list of availiable items
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
    	done2.add("$"+String.valueOf(orderPrice));
    	deletedChairData = new chairData[done2.size()-1];
		for (int i = 0; i < deletedChairData.length; i++) {
			deletedChairData[i] = registerChairHelper(done2.get(i));
		}

			
		if (done2 != null) {
    		for (int i = 0; i < (done2.size() -1); i++){
    			if (done2.get(i).charAt(0) == 'C') {
    				deleteChair(done2.get(i));
    			}
    		}   		
    	}
    	
    	for (int i = 0; i < deletedChairData.length; i++) {
    		registerChair(deletedChairData[i]);
    	}
    	
    	System.out.println(done2);
 
		return done2;
	}
	/**
	 * 
	 * @param arg set to find power set of
	 * @param currentSet current created subset
	 * @param index index of element being looked at in the arg set
	 */
	public void powerSet(ArrayList<node> arg,ArrayList<node> currentSet,int index) {
		//base case
		if(index == arg.size()) {
			//adds the current set to the power set
			done.add(currentSet);
			return;
		}
		//copy of current set before adding to it. This contains the current set without the current element being looked at in the arg set
		ArrayList<node> b = (ArrayList<node>) currentSet.clone();
		//adds current element being looked at to current set(one case of any subset)
		currentSet.add(arg.get(index));
		
		powerSet(arg,b,index+1);
		//This contains the current set with the current element being looked at in the arg set(other case of any subset)
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
	
	public deskData registerDeskHelper(String DeskID) {
		 String ID = DeskID;
		 String Type = null;
		 String Legs = null;
		 String Top = null;
		 String Drawer = null;
		 int Price = 0;
		 String ManuID = null;
		 try {
			 String query = "SELECT * FROM desk WHERE ID = '" + DeskID + "'";
			 Statement myStmt = dbConnect.createStatement();
			 results = myStmt.executeQuery(query);
	
			 while (results.next()){
				     
	                Type = results.getString("Type");
	                Legs = results.getString("Legs");
	                Top = results.getString("Top");
	                Drawer = results.getString("Drawer");
	                Price += results.getInt("Price");
	                ManuID = results.getString("ManuID");
	               
			 }
	          	deskData x = new deskData(ID, Type, Legs, Top, Drawer, Price, ManuID);
	            myStmt.close();
	            return x;
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
		return null;
	}
	public void registerDesk(deskData abc) {
		 try {
	            
	            String query = "INSERT INTO desk (ID, Type, Legs, Top, Drawer, Price, ManuID) VALUES (?,?,?,?,?,?,?)";
	            PreparedStatement myStmt = dbConnect.prepareStatement(query);
	            
	            myStmt.setString(1, abc.ID2);
	            myStmt.setString(2, abc.Type2);
	            myStmt.setString(3, abc.Legs2);
	            myStmt.setString(4, abc.Top2);
	            myStmt.setString(5, abc.Drawer2);
	            myStmt.setInt(6, abc.Price2);
	            myStmt.setString(7, abc.ManuID2);
	            myStmt.executeUpdate();
	            
	            myStmt.close();

	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	
	}
	public filingData registerFilingHelper(String FilingID) {
		
		 String ID = FilingID;
		 String Type = null;
		 String Rails = null;
		 String Drawers = null;
		 String Cabinet = null;
		 int Price = 0;
		 String ManuID = null;
		 try {
			 String query = "SELECT * FROM filing WHERE ID = '" + FilingID + "'";
			 Statement myStmt = dbConnect.createStatement();
			 results = myStmt.executeQuery(query);
	
			 while (results.next()){
				     
	                Type = results.getString("Type");
	                Rails = results.getString("Rails");
	                Drawers = results.getString("Drawers");
	                Cabinet = results.getString("Cabinet");
	                Price += results.getInt("Price");
	                ManuID = results.getString("ManuID");
	                
	                
			 }
			 filingData x = new filingData(ID, Type, Rails, Drawers, Cabinet, Price, ManuID);
			 myStmt.close();
			 return x;
	           
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
		return null;
	}
	public void registerFiling(filingData abc) {
		
		 try {
	            String query = "INSERT INTO filing (ID, Type, Rails, Drawers, Cabinet, Price, ManuID) VALUES (?,?,?,?,?,?,?)";
	            PreparedStatement myStmt = dbConnect.prepareStatement(query);
	            
	            myStmt.setString(1, abc.ID2);
	            myStmt.setString(2, abc.Type2);
	            myStmt.setString(3, abc.Rails2);
	            myStmt.setString(4, abc.Drawers2);
	            myStmt.setString(5, abc.Cabinet2);
	            myStmt.setInt(6, abc.Price2);
	            myStmt.setString(7, abc.ManuID2);
	            myStmt.executeUpdate();
	            
	            myStmt.close();

	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	}
	
	public chairData registerChairHelper(String ChairID) {
		
		 String ID = ChairID;
		 String Type = null;
		 String Legs = null;
		 String Arms = null;
		 String Seat = null;
		 String Cushion = null;
		 int Price = 0;
		 String ManuID = null;
		 try {
			 String query = "SELECT * FROM chair WHERE ID = '" + ChairID + "'";
			 Statement myStmt = dbConnect.createStatement();
			 results = myStmt.executeQuery(query);
	
			 while (results.next()){
				     
	                Type = results.getString("Type");
	                Legs = results.getString("Legs");
	                Arms = results.getString("Arms");
	                Seat = results.getString("Seat");
	                Cushion = results.getString("Cushion");
	                Price += results.getInt("Price");
	                ManuID = results.getString("ManuID");
	                
	                
			 }
			 chairData x = new chairData(ID, Type, Legs, Arms, Seat, Cushion, Price, ManuID);
			 myStmt.close();
			 return x;
	           
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
		return null;
	}
	public void registerChair(chairData abc) {
		
		 try {
	            String query = "INSERT INTO chair (ID, Type, Legs, Arms, Seat, Cushion, Price, ManuID) VALUES (?,?,?,?,?,?,?,?)";
	            PreparedStatement myStmt = dbConnect.prepareStatement(query);
	            
	            myStmt.setString(1, abc.ID2);
	            myStmt.setString(2, abc.Type2);
	            myStmt.setString(3, abc.Legs2);
	            myStmt.setString(4, abc.Arms2);
	            myStmt.setString(5, abc.Seat2);
	            myStmt.setString(6, abc.Cushion2);
	            myStmt.setInt(7, abc.Price2);
	            myStmt.setString(8, abc.ManuID2);
	            myStmt.executeUpdate();
	            
	            myStmt.close();

	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	}
	
	public lampData registerLampHelper(String LampID) {
		
		 String ID = LampID;
		 String Type = null;
		 String Base = null;
		 String Bulb = null;
		 int Price = 0;
		 String ManuID = null;
		 try {
			 String query = "SELECT * FROM lamp WHERE ID = '" + LampID + "'";
			 Statement myStmt = dbConnect.createStatement();
			 results = myStmt.executeQuery(query);
	
			 while (results.next()){
				     
	                Type = results.getString("Type");
	                Base = results.getString("Base");
	                Bulb = results.getString("Bulb"); 
	                Price += results.getInt("Price");
	                ManuID = results.getString("ManuID");
	                
	                
			 }
			 lampData x = new lampData(ID, Type, Base, Bulb, Price, ManuID);
			 myStmt.close();
			 return x;
	           
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
		return null;
	}
	public void registerLamp(lampData abc) {
		
		 try {
	            String query = "INSERT INTO lamp (ID, Type, Base, Bulb, Price, ManuID) VALUES (?,?,?,?,?,?)";
	            PreparedStatement myStmt = dbConnect.prepareStatement(query);
	            
	            myStmt.setString(1, abc.ID2);
	            myStmt.setString(2, abc.Type2);
	            myStmt.setString(3, abc.Base2);
	            myStmt.setString(4, abc.Bulb2);
	            myStmt.setInt(5, abc.Price2);
	            myStmt.setString(6, abc.ManuID2);
	            myStmt.executeUpdate();
	            
	            myStmt.close();

	        } catch (SQLException ex) {
	            ex.printStackTrace();
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
}
