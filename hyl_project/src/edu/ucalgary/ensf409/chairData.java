package edu.ucalgary.ensf409;
//class to store chair data 
public class chairData {
	//all data for chair entry
	 protected boolean legs = false;
	 protected boolean arms = false;
	 protected boolean seat = false;
	 protected boolean cushion = false;
	 protected String ID = "";
	 protected int price;
	 
	
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

	/**
	 * getter method for legs
	 * @return returns legs
	 */
	public boolean isLegs() {
		return legs;
	}
	/**
	 * setter method for legs
	 * @param legs input for setter
	 */
	
	public void setLegs(boolean legs) {
		this.legs = legs;
	}

	/**
	 * getter method for arms
	 * @return returns arms
	 */
	public boolean isArms() {
		return arms;
	}
	/**
	 * setter method for arms
	 * @param arms input for setter
	 */
	public void setArms(boolean arms) {
		this.arms = arms;
	}
	/**
	 * getter method for seat
	 * @return returns seat
	 */

	public boolean isSeat() {
		return seat;
	}
	/**
	 * setter method for seat
	 * @param seat input for setter
	 */

	public void setSeat(boolean seat) {
		this.seat = seat;
	}
	/**
	 * getter method for cushion
	 * @return returns cushion
	 */

	public boolean isCushion() {
		return cushion;
	}
	/**
	 * setter method for cushion
	 * @param cushion input for setter
	 */

	public void setCushion(boolean cushion) {
		this.cushion = cushion;
	}

	/**
	 * getter method for ID
	 * @return returns ID
	 */
	public String getID() {
		return ID;
	}
	/**
	 * setter method for ID
	 * @param iD input for setter
	 */

	public void setID(String iD) {
		ID = iD;
	}

	/**
	 * getter method for price
	 * @return returns price
	 */
	public int getPrice() {
		return price;
	}
	/**
	 * setter method for price
	 * @param price input for setter
	 */

	public void setPrice(int price) {
		this.price = price;
	}
	

}
