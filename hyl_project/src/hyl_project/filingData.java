package hyl_project;

public class filingData {
	 boolean Rails = false;
	 boolean Cabinet = false;
	 boolean Drawers = false;
	 int price;
	
	 String ID = "";
	
	 String ID2 = null;
	 String Type2 = null;
	 String Rails2 = null;
	 String Drawers2 = null;
	 String Cabinet2 = null;
	 int Price2 = 0;
	 String ManuID2 = null;
	 
	 /**
	  * 
	  * @param id id of filing entry
	  * @param Rails1 Rails value of filing entry
	  * @param cabinet cabinet value of  filing entry
	  * @param Drawers Drawers value of filing entry
	  * 
	  * @param price price of filing entry
	  */
	
	public filingData( String id, String Rails1, String cabinet1,
			String Drawers1,  int price) {
		this.ID = id;
		this.price = price;
		//converts Y and N into booleans
		if(Rails1.equals("Y")) {
			Rails = true;
			
		}
		
		if(cabinet1.equals("Y")) {
			Cabinet = true;
			
		}
		if(Drawers1.equals("Y")) {
			Drawers = true;
			
		}
	}
	
	
	public filingData(String ID, String Type, String Rails, String Drawers, String Cabinet, int Price, String ManuID) {
		this.ID2 = ID;
		this.Type2 = Type;
		this.Rails2 = Rails;
		this.Drawers2 = Drawers;
		this.Cabinet2 = Cabinet;
		this.Price2 = Price;
		this.ManuID2 = ManuID;
	}

}
