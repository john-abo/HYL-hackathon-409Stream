package hyl_project_TESTS;

import static org.junit.Assert.assertTrue;

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
	public void zeroIntegerConst() {
		subject = new FormPrinter("mesh chair, 0");
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
		String reportInt = subject.getType();
		
		//Expected: mesh
		assertTrue("reportNumber is incorrect. Expected: \"mesh\", Actual: \"" + reportInt + "\"", reportInt.equalsIgnoreCase("mesh"));
	}
	
	@Test
	public void furnitureGetterTests() {
		subject = new FormPrinter("mesh chair, 1");
		String reportInt = subject.getFurniture();
		
		//Expected: chair
		assertTrue("reportNumber is incorrect. Expected: \"chair\", Actual: \"" + reportInt + "\"", reportInt.equalsIgnoreCase("chair"));
	}
	
	@Test
	public void quantityGetterTests() {
		subject = new FormPrinter("mesh chair, 1");
		int reportInt = subject.getQuantity();
		
		//Expected: 1
		assertTrue("reportNumber is incorrect. Expected: 1, Actual: " + reportInt, reportInt == 1);
		
		subject = new FormPrinter("mesh chair, 100");
		reportInt = subject.getQuantity();
		assertTrue("reportNumber is incorrect. Expected: 100, Actual: " + reportInt, reportInt == 100);
		
		subject = new FormPrinter("mesh chair, 56216");
		reportInt = subject.getQuantity();
		assertTrue("reportNumber is incorrect. Expected: 56216, Actual: " + reportInt, reportInt == 56216);
	}
	
	@Test
	public void fileIOTest() {
		int reportInt = 0;
		
		//not sure how to test for this
		subject = new FormPrinter("mesh chair, 1");
		
		reportInt = subject.writeReport();	//report1.txt
		assertTrue("reportNumber is incorrect. Expected: 1, Actual: " + reportInt, reportInt == 1);
		
		reportInt = subject.writeReport();	//report2.txt
		assertTrue("reportNumber is incorrect. Expected: 2, Actual: " + reportInt, reportInt == 2);
		
		reportInt = subject.writeReport();	//report3.txt
		assertTrue("reportNumber is incorrect. Expected: 3, Actual: " + reportInt, reportInt == 3);
	}
}