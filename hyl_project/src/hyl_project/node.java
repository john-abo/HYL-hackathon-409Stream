package hyl_project;

class node{
	
	//contains all the attributes of an entry in the chair table
	
	node next = null;
	 boolean legs = false;
	 boolean arms = false;
	 boolean seat = false;
	 boolean cushion = false;
	 int parts = 0;
	 String ID = "";
	
	 int price;
	 /**
	  * 
	  * @param id id of table entry
	  * @param legs1 legs value of table entry
	  * @param arms1 arms value of table entry
	  * @param seat1 seat value of table entry
	  * @param cushion1 cushion value of table entry
	  * @param price price of table entry
	  */

	public node( String id, String legs1, String arms1,
			String seat1, String cushion1, int price) {
		this.ID = id;
		this.price = price;
		//converts Y and N into booleans
		if(legs1.equals("Y")) {
			legs = true;
			parts++;
		}
		if(arms1.equals("Y")) {
			arms = true;
			parts++;
		}
		if(seat1.equals("Y")) {
			seat = true;
			parts++;
		}
		if(cushion1.equals("Y")) {
			cushion = true;
			parts++;
		}
	}
	public node() {
		
	}


	
}