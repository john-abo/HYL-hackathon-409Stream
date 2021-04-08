package edu.ucalgary.ensf409;
//class to store lamp data
public class lampData {
	//contains data for lamp entry
	 protected boolean Bulb = false;
	 protected boolean Base = false;
	 protected String ID = "";
	 protected int price;
	 /**
	  * Constructor
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
	/**
	 * getter method for Bulb
	 * @return returns Bulb
	 */
	public boolean isBulb() {
		return Bulb;
	}
	/**
	 * setter method for Bulb
	 * @param bulb input for setter
	 */
	public void setBulb(boolean bulb) {
		Bulb = bulb;
	}
	/**
	 * getter method for Base
	 * @return returns Base
	 */
	public boolean isBase() {
		return Base;
	}
	/**
	 * setter method for base
	 * @param base input for setter
	 */
	public void setBase(boolean base) {
		Base = base;
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
