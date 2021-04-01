package hyl_project;

public class lampData {
	 boolean Bulb = false;
	 boolean Base = false;
	 String ID = "";
	 int price;
	 
	 String ID2 = null;
	 String Type2 = null;
	 String Base2 = null;
	 String Bulb2 = null;
	 int Price2 = 0;
	 String ManuID2 = null;
	 /**
	  * 
	  * @param id id of lamp entry
	  * @param base1 base of lamp entry
	  * @param bulb1 bulb of lamp entry
     
	  * @param price price of lamp entry
	
	  */

	public lampData( String id, String base1, String bulb1, int price) {
		this.ID = id;
		this.price = price;
		//converts Y and N into booleans
		if(base1.equals("Y")) {
			Base = true;
			
		}
		
		if(bulb1.equals("Y")) {
			Bulb = true;
			
		}
	
	}
	public lampData(String ID, String Type, String Base, String Bulb, int Price, String ManuID) {
		this.ID2 = ID;
		this.Type2 = Type;
		this.Base2 = Base;
		this.Bulb2 = Bulb;
		this.Price2 = Price;
		this.ManuID2 = ManuID;
	}

}
