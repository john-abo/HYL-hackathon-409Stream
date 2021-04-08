package edu.ucalgary.ensf409;
//class to store filing data
public class filingData {
	//all data for filing entry
	 protected boolean Rails = false;
	 protected boolean Cabinet = false;
	 protected boolean Drawers = false;
	 protected int price;
	
	 protected String ID = "";

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
	/**
	 * getter method for Rails
	 * @return returns Rails
	 */
	public boolean isRails() {
		return Rails;
	}
	/**
	 * setter method for Rails
	 * @param rails input for setter
	 */
	public void setRails(boolean rails) {
		Rails = rails;
	}
	/**
	 * getter method for Cabinet
	 * @return returns Cabinet
	 */
	public boolean isCabinet() {
		return Cabinet;
	}
	/**
	 * setter method for Cabinet
	 * @param cabinet input for setter
	 */
	public void setCabinet(boolean cabinet) {
		Cabinet = cabinet;
	}
	/**
	 * getter method for Drawers
	 * @return returns Drawers
	 */
	public boolean isDrawers() {
		return Drawers;
	}
	/**
	 * setter method for Drawers
	 * @param drawers input for setter
	 */
	public void setDrawers(boolean drawers) {
		Drawers = drawers;
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
	
	

}
