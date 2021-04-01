package hyl_project;

public class chairData {
	 boolean legs = false;
	 boolean arms = false;
	 boolean seat = false;
	 boolean cushion = false;
	 String ID = "";
	 int price;
	 
	 String ID2 = null;
	 String Type2 = null;
	 String Legs2 = null;
	 String Arms2 = null;
	 String Seat2 = null;
	 String Cushion2 = null;
	 int Price2 = 0;
	 String ManuID2 = null;
	 /**
	  * 
	  * @param id id of table entry
	  * @param legs1 legs value of table entry
	  * @param arms1 arms value of table entry
	  * @param seat1 seat value of table entry
	  * @param cushion1 cushion value of table entry
	  * @param price price of table entry
	  */

	public chairData( String id, String legs1, String arms1,
			String seat1, String cushion1, int price) {
		this.ID = id;
		this.price = price;
		//converts Y and N into booleans
		if(legs1.equals("Y")) {
			legs = true;
			
		}
		if(arms1.equals("Y")) {
			arms = true;
			
		}
		if(seat1.equals("Y")) {
			seat = true;
			
		}
		if(cushion1.equals("Y")) {
			cushion = true;
			
		}
	}
	
	public chairData(String ID, String Type, String Legs, String Arms, String Seat, String Cushion, int Price, String ManuID) {
		this.ID2 = ID;
		this.Type2 = Type;
		this.Legs2 = Legs;
		this.Arms2 = Arms;
		this.Seat2 = Seat;
		this.Cushion2 = Cushion;
		this.Price2 = Price;
		this.ManuID2 = ManuID;
	}
}
