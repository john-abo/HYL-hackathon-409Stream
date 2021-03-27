package hyl_project;
import java.sql.*;
import java.util.ArrayList;
public class search {
	private Connection dbConnect;
	public final String DBURL;
	public final String  USERNAME;
	public final String PASSWORD;
	private ResultSet results;
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
		//checks if studio exists in table in database
    	//query to find studio with specific name
    
    	int s = 0;
    	ArrayList<String> done2 = new ArrayList<String>();
    	ArrayList<node>[] subsets;
    	try {
    		PreparedStatement find = dbConnect.prepareStatement("SELECT * FROM CHAIR WHERE Type=?");
			find.setString(1,lookup);
			results = find.executeQuery();
			int price = 0;
			boolean arm = false;
			boolean legs = false;
			boolean seat = false;
			boolean cushion = false;
			ArrayList<node> hasLegs = new ArrayList<node>();
			ArrayList<node> hasSeat = new ArrayList<node>();
			ArrayList<node> hasCushion = new ArrayList<node>();
			ArrayList<node> hasArms = new ArrayList<node>();
		    ArrayList<node> matches = new ArrayList<node>();
			
			
			while (results.next()){
				if(results.getString("Type").equals(lookup)) {
					
				   node temp = new node(results.getString("ID"),results.getString("Legs"),results.getString("Arms"),results.getString("Seat"),results.getString("Cushion"),results.getInt("Price"));
				   matches.add(temp);
				   if(temp.arms) {
					   hasArms.add(temp);
				   }
				   if(temp.legs) {
					   hasLegs.add(temp);
				   }
				   if(temp.seat) {
					   hasSeat.add(temp);
				   }
				   if(temp.cushion) {
					   hasCushion.add(temp);
				   }
				   
				}
				
	            }
		
			ArrayList<node> g = new ArrayList<node>();
			powerSet(matches,g,0);
			
	    	for(int i = 0; i < done.size(); i++) {
	    		System.out.println(done.get(i).size());
	    	}
			
	
			//go.printList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
		return done2;
		
		
		
	}
	
	public void powerSet(ArrayList<node> arg,ArrayList<node> currentSet,int index) {
		if(index == arg.size()) {
			done.add(currentSet);
			return;
		}
		ArrayList<node> b = (ArrayList<node>) currentSet.clone();
		currentSet.add(arg.get(index));
		powerSet(arg,b,index+1);
	
		powerSet(arg,currentSet,index+1);
		
	}
	
	

}
