package edu.ucalgary.ensf409;

public class list implements Cloneable {
node head;
node tail;
int size = 0;

public list() {
	
	head =tail =null;
}

public void add(node temp) {
   if(temp == null) {
	   return;
   }
   if(tail == null && head == null){
	head = temp;
	   tail = temp;
	  size++;
	   return;
	   }
tail.next = temp;
   tail = temp;
   size++;
}
/**
 * copy constructor
 * @param list to copyFrom
 */
public list clone()
        throws CloneNotSupportedException
    {
        return (list)super.clone();
    }
public void printList() {
	node temp = head;

	while(temp !=null){
	temp = temp.next;
}
}
public node getStart() {
	
	return head;
}
public node getNext(node getNext) {
	if(getNext != null) {
	return getNext.next;
	}
	return null;
}
public node pop(){
	node temp = head;
	if(head !=null) {
	head = head.next;
	}
	 if(head == null ){
		tail = null;
		
	}
	size--;
	return temp;
	
}
}