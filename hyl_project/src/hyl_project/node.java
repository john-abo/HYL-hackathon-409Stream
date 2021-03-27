package hyl_project;

class node{
	
	
	 node next = null;
	 boolean legs = false;
	 boolean arms = false;
	 boolean seat = false;
	 boolean cushion = false;
	 int parts = 0;
	 String id;
	
	 int price;
	 

	public node( String id, String legs1, String arms1,
			String seat1, String cushion1, int price) {
		this.id = id;
		this.price = price;
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