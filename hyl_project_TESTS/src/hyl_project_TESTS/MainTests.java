package hyl_project_TESTS;

import org.junit.Test;
import hyl_project.FormPrinter;

public class MainTests {
	
	public FormPrinter subject;
	
	@Test (expected = IllegalArgumentException.class)
	public void invalidTypeConst() {
		subject = new FormPrinter("");	//no idea how to test this case exactly
													//I think I can query for existing types
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void invalidFurnitureConst() {
		subject = new FormPrinter("");	//no idea how to test this case exactly
													//And here I can query tables
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void invalidIntegerConst() {
		subject = new FormPrinter("mesh chair, fail");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void invalidCloneConst() {
		subject = new FormPrinter((FormPrinter)null);
	}
	
	@Test
	public void validConst() {
		subject = new FormPrinter("mesh chair, 1");
	}
	
	@Test
	public void typeGetterTests() {
		subject = new FormPrinter("mesh chair, 1");
		//Expected: mesh
	}
	
	@Test
	public void furnitureGetterTests() {
		subject = new FormPrinter("mesh chair, 1");
		
		//Expected: chair
	}
	
	@Test
	public void quantityGetterTests() {
		subject = new FormPrinter("mesh chair, 1");
		
		//Expected: 1
	}
	
	@Test
	public void fileIOTest() {
		
		subject = new FormPrinter("mesh chair, 1");
		subject.writeReport();	//report1.txt
		subject.writeReport();	//report2.txt
		subject.writeReport();	//report3.txt
	}
}