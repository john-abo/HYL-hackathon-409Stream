package hyl_project;

class node<e>{
	
	//basis of a linked list
	
	node next = null;
	//this element contains all the data the node needs. For a chair for example, e contains all the chair data
    e element;
    String id;
    int price;
	public node(e element)  {
		this.element = element;

		
		this.next = next;

		try {
			
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