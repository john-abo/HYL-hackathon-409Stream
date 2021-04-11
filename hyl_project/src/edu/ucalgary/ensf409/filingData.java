/**
@author Kyle Hasan, John Abo , Farhad Alishov, Mohamed Yassin
@version 1.3
@since 1.0
*/


package edu.ucalgary.ensf409;
//class to store filing data
class filingData {
	//all data for filing entry
	 boolean Rails = false;
	 boolean Cabinet = false;
	 boolean Drawers = false;
	 int price;
	
	 String ID = "";
	

	 
	 /**
	  * Constructor
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
	
	

}
