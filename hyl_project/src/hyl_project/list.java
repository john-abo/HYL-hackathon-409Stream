package hyl_project;

public class list {
node head = null;
node tail = null;
int size = 0;

public list() {
	
	head =tail =null;
}

public void add(node temp) {
   
   if(temp == null) {
	   return;
   }
   if(tail == null && head == null){
	//System.out.println(temp.e.payload);
	   head = temp;
	   tail = temp;
	  size++;
	   return;
   }
 //  //System.out.println(temp.e.payload);
   tail.next = temp;
   tail = temp;
   size++;
	//printList();
	
}
public void printList() {
	node temp = head;
	int i = 0;
	////System.out.println("hi");
	////System.out.println(head);
	while(temp !=null){
		
	System.out.println(temp.price + " item " + i);
		temp = temp.next;
		i++;
	}
}
public node peek() {
	
	return head;
}
public node getNext(node f) {
	if(f != null) {
	return f.next;
	}
	return null;
}
public node dequeue(){
	
	node temp = head;
	////System.out.println(head.e.payload);
	
	
	
	if(head !=null) {
	head = head.next;
	}
	 if(head == null ){
		tail = null;
		
	}
	
	
	 ////System.out.println(temp.e.payload);
	size--;
	return temp;
	
}
}