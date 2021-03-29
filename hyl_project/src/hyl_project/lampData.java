package hyl_project;

public class lampData {
	 boolean Bulb = false;
	 boolean Base = false;
	 
	
	
	 String ID = "";
	
	 int price;
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

}
