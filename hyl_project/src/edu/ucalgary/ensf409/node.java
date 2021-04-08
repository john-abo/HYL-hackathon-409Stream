package edu.ucalgary.ensf409;

 class node<e>{
	
	//this class is used to store data from furniture objects
	
	
	//this element contains all the data the furniture object needs. For a chair for example, e contains a chairData object
    protected e element;
    //id of item in element
    protected String id;
    //price of item in element
    protected int price;
    /**
     * Constructor
     * @param element object that contains the actual furniture data. Object type changes depending on what furniture is stored
     */
	public node(e element)  {
		this.element = element;
        try {
		//sets price and id parameters using data from stored element
		price = (int) (element.getClass().getDeclaredField("price").get(this.element));
		id = (String) (element.getClass().getDeclaredField("ID").get(this.element));
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	/** 
	 * default constructor
	 */
	public node() {
		
	}
	/**
	 * getter method for element
	 * @return returns element
	 */
	public e getElement() {
		return element;
	}
	/**
	 * setter method for element
	 * @param element element chosen as input for setter
	 */
	public void setElement(e element) {
		this.element = element;
	}
	/**
	 * getter method for id
	 * @return returns id
	 */
	public String getId() {
		return id;
	}
	/**
	 * setter method for id
	 * @param id id chosen as input for setter
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @param price price chosen as input for setter
	 */
	public void setPrice(int price) {
		this.price = price;
	}



	
}