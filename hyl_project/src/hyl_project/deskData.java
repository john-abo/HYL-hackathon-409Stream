package hyl_project;
//class to store desk data
public class deskData {
	//all data for desk entry
	 boolean legs = false;
	 boolean top = false;
	 boolean Drawer = false;
	String ID = "";
	int price;
	 /**
	  * Constructor
	  * @param id id of desk entry
	  * @param legs1 legs value of desk entry
	  * @param top top value of  desk entry
	  * @param drawer drawer value of desk entry
	  * 
	  * @param price price of desk entry
	  */

	public deskData( String id, String legs1, String top1,
			String Drawer1,  int price) {
		this.ID = id;
		this.price = price;
		//converts Y and N into booleans
		if(legs1.equals("Y")) {
			legs = true;
			
		}
		
		if(top1.equals("Y")) {
			top = true;
			
		}
		if(Drawer1.equals("Y")) {
			Drawer = true;
			
		}
	}

}
