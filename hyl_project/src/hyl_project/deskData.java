package hyl_project;

public class deskData {
	 boolean legs = false;
	 boolean top = false;
	 boolean Drawer = false;
	 int price;
	 String ID = "";
	 
	 String ID2 = null;
	 String Type2 = null;
	 String Legs2 = null;
	 String Top2 = null;
	 String Drawer2 = null;
	 int Price2 = 0;
	 String ManuID2 = null;
	
	
	 /**
	  * 
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
	
	public deskData(String id, String Type, String Legs, String Top, String Drawer, int Price, String ManuID) {
		this.ID2 = id;
		this.Type2 = Type;
		this.Legs2 = Legs;
		this.Top2 = Top;
		this.Drawer2 = Drawer;
		this.Price2 = Price;
		this.ManuID2 = ManuID;
	}

}
