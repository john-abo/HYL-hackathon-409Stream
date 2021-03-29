package hyl_project;

class node<e>{
	
	//basis of a linked list
	
	node next = null;
	//this element contains all the data the node needs. For a chair for example, e contains all the chair data
    e element;
    String id;
	public node(e element) {
		this.element = element;
		try {
			id = (String) element.getClass().getDeclaredField("ID").get(this.element);
		} catch (Exception e1) {
			
		}
	}
	


	
}