package hyl_project;

class node<e>{
	
	//this class is used to store data from furniture objects
	
	node next = null;
	//this element contains all the data the furniture object needs. For a chair for example, e contains all the chair data
    e element;
    //id of item in element
    String id;
    //price of item in element
    int price;
	public node(e element)  {
		this.element = element;

		
		
		try {
		//sets price and id parameters using data from element
		price = (int) (element.getClass().getDeclaredField("price").get(this.element));
		id = (String) (element.getClass().getDeclaredField("ID").get(this.element));
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public node() {
		
	}
	public node(node copy) {
		this.element = (e) copy.element;
		this.id = copy.id;
		this.price = copy.price;
	}
	public void updatePrice() {
		
		try {
			
			price = (int) (element.getClass().getDeclaredField("price").get(this.element));
			
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
	}


	
}