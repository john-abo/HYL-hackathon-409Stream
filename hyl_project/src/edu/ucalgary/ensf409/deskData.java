package edu.ucalgary.ensf409;
//class to store desk data
public class deskData {
	//all data for desk entry
	 protected boolean legs = false;
	 protected boolean top = false;
	 protected boolean Drawer = false;
	 protected String ID = "";
	 protected int price;
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
	 * getter method for top
	 * @return returns top
	 */
	public boolean isTop() {
		return top;
	}
	/**
	 * setter method for top
	 * @param top input for setter
	 */
	public void setTop(boolean top) {
		this.top = top;
	}
	/**
	 * getter method for Drawer
	 * @return returns Drawer
	 */
	public boolean isDrawer() {
		return Drawer;
	}
	/**
	 * setter method for Drawer
	 * @param drawer input for Drawer
	 */
	public void setDrawer(boolean drawer) {
		Drawer = drawer;
	}
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
