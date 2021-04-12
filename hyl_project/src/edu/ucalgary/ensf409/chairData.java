/**
 * Holds data pertaining to a specific chair in the database
 * 
 *	@author Kyle Hasan, John Abo , Farhad Alishov, Mohamed Yassin
 *	@version 1.3
 *	@since 1.0
 */

package edu.ucalgary.ensf409;
//class to store chair data 
class chairData {
	//all data for chair entry
	 boolean legs = false;
	 boolean arms = false;
	 boolean seat = false;
	 boolean cushion = false;
	 String ID = "";
	 int price;
	 
	
	 /**
	  * Constructor
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
	

}
