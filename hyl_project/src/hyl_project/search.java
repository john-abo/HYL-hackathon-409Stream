package hyl_project;
import java.sql.*;
import java.util.ArrayList;
public class search {
	private Connection dbConnect;
	public final String DBURL;
	public final String  USERNAME;
	public final String PASSWORD;
	private ResultSet results;
	//done contains the power set
	private ArrayList<ArrayList<node>> done = new ArrayList<ArrayList<node>>();
	public search( String dBURL, String uSERNAME, String pASSWORD) {
		
		DBURL = dBURL;
		USERNAME = uSERNAME;
		PASSWORD = pASSWORD;
	}

	public void initializeConnection() {
		 try {
			dbConnect = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public ArrayList<String> searchChair(String lookup) {
		
    
    	//contains the combination with the lowest price. Price is always at the END of the arraylist. If combination cannot be created, this is empty
    	ArrayList<String> done2 = new ArrayList<String>();
    	
    	try {
    		//finds all entries of chair with the specified type and saves them into results
    		PreparedStatement find = dbConnect.prepareStatement("SELECT * FROM CHAIR WHERE Type=?");
			find.setString(1,lookup);
			results = find.executeQuery();
			//current price to save
			int price = 0;
			//used to check if combination is valid
			boolean arm = false;
			boolean legs = false;
			boolean seat = false;
			boolean cushion = false;
			//list will all the matches
		    ArrayList<node> matches = new ArrayList<node>();
			
			
			while (results.next()){
				if(results.getString("Type").equals(lookup)) {
					
				   node temp = new node(results.getString("ID"),results.getString("Legs"),results.getString("Arms"),results.getString("Seat"),results.getString("Cushion"),results.getInt("Price"));
				   matches.add(temp);
				 
				   
				}
				
	            }
			//empty arraylist
			ArrayList<node> g = new ArrayList<node>();
			//finds the power set for the matches list
			powerSet(matches,g,0);
			
	    	for(int i = 0; i < done.size(); i++) {
	    		for(int j = 0; j< done.get(i).size(); j++) {
	    			System.out.println(done.get(i).get(j).ID);
	    		
	    		}
	    		System.out.println();
	    	}
			
	
			//go.printList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
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
	
	

}
