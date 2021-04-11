/**
@author Kyle Hasan, John Abo , Farhad Alishov, Mohamed Yassin
@version 1.3
@since 1.0
*/

package edu.ucalgary.ensf409;

 class node<e>{
	
	//this class is used to store data from furniture objects
	
	
	//this element contains all the data the furniture object needs. For a chair for example, e contains a chairData object
    e element;
    //id of item in element
    String id;
    //price of item in element
    int price;
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



	
}