/**
@author Kyle Hasan, John Abo , Farhad Alishov, Mohamed Yassin
@version 1.3
@since 1.0
*/


package hyl_project_TESTS;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.ucalgary.ensf409.FormPrinter;
import edu.ucalgary.ensf409.search;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainTests {
	
	public FormPrinter subject;
	
	/*
	 * Constructor tests. All of which are passed invalid arguments except the last one
	 * That means all tests must throw an exception except for the last one in order
	 * to pass
	 */
	
	//The login details that will be used for the tests
	//Entered in the same way the program would have normally
	private String loginDetails = "jdbc:mysql://localhost/inventory root Pound_multiple_demonstration_watching"; //Hand in with this: "jdbc:mysql://localhost/inventory root ensf409"
	
	/**
	 * Tests a constructor to see if it successfully creates an object
	 * despite the improper integer passed
	 */
	@Test (expected = IllegalArgumentException.class)
	public void invalidIntegerConst() throws SQLException {
		subject = new FormPrinter("\"mesh\" \"chair\", fail", loginDetails);
	}
	
	/**
	 * Test a constructor to see if it successfully creates an object
	 * despite the improper integer passed.
	 */
	@Test (expected = IllegalArgumentException.class)
	public void zeroIntegerConst() {
		subject = new FormPrinter("\"mesh\" \"chair\", 0", loginDetails);
	}
	
	/**
	 * Tests the copy constructor to see if it successfully creates an object
	 * despite the invalid parameter passed
	 */
	@Test (expected = IllegalArgumentException.class)
	public void invalidCloneConst() {
		subject = new FormPrinter((FormPrinter)null);
	}
	
	/**
	 * Tests the normal constructor to see if it fails to create an object
	 * despite the parameters being valid
	 */
	@Test
	public void validConst() {
		subject = new FormPrinter("\"mesh\" \"chair\", 1", loginDetails);
	}
	
	/**
	 * Tests the copy constructor to see if it fails to create an object despite object
	 * passed being valid
	 */
	@Test
	public void validCopyConst() {
		subject = new FormPrinter("\"mesh\" \"chair\", 1", loginDetails);
		FormPrinter subject2 = new FormPrinter(subject);
		
		String reportType = subject2.getType();
		String reportFurniture = subject2.getFurniture();
		int reportInt = subject2.getQuantity();
		
		assertTrue("reportType is incorrect. Expected: " + subject.getType() + ", Actual: \"" + reportType + "\"", reportType.equalsIgnoreCase(subject.getType()));
		assertTrue("reportFurniture is incorrect. Expected: " + subject.getFurniture() + ", Actual: \"" + reportFurniture + "\"", reportFurniture.equalsIgnoreCase(subject.getFurniture()));
		assertTrue("reportInt is incorrect. Expected: " + subject.getQuantity() + ", Actual: \"" + reportInt + "\"", reportInt == subject.getQuantity());
	}
	/** 
	 * These tests are for invalid user inputs
	 */
	
	/**
	 * Tests to see if the program performs a proper query despite the invalid furniture
	 */
	@Test (expected = IllegalArgumentException.class)
	public void nonExistantFurniture() {
		subject = new FormPrinter("\"mesh\" \"cwhair\", 2", loginDetails);
		subject.query();
	}
	
	/**
	 * Tests to see if the program performs a proper query despire the invalid
	 * formatting
	 */
	@Test (expected = IllegalArgumentException.class)
	public void invalidInputFormat() {
		subject = new FormPrinter("mesh chair 2", loginDetails);
		subject.query();
	}
	
	/**
	 * Test to see if the object is created despite login being invalid format
	 */
	@Test (expected = IllegalArgumentException.class)
	public void badLogin() {
		subject = new FormPrinter("\"mesh\" \"chair\", 1", "this is not a proper login");
		
		
	}
	
	/**
	 * Test to see if the object is created despite the password being incorrect
	 */
	@Test (expected = IllegalArgumentException.class)
	public void badLogin2() {
		subject = new FormPrinter("\"mesh\" \"chair\", 1", "\"jdbc:mysql://localhost/inventory userdoesntexist notThepassword\";");
		
		
	}

	/**
	 * these tests are for the getter methods
	 */
	
	/**
	 * Tests to see if the type getter doesn't return the correct type
	 */
	@Test
	public void typeGetterTests() {
		subject = new FormPrinter("\"mesh\" \"chair\", 1", loginDetails);
		String reportType = subject.getType();
		
		//Expected: mesh
		assertTrue("reportType is incorrect. Expected: \"mesh\", Actual: \"" + reportType + "\"", reportType.equalsIgnoreCase("mesh"));
	}
	
	/**
	 * Tests to see if the furniture getter doesn't return the correct type
	 */
	@Test
	public void furnitureGetterTests() {
		subject = new FormPrinter("\"mesh\" \"chair\", 1", loginDetails);
		String reportFurniture = subject.getFurniture();
		
		//Expected: chair
		assertTrue("reportFurniture is incorrect. Expected: \"chair\", Actual: \"" + reportFurniture + "\"", reportFurniture.equalsIgnoreCase("chair"));
	}
	
	/**
	 * Tests to see if the quantity getter doesn't return the correct type
	 */
	@Test
	public void quantityGetterTests() {
		subject = new FormPrinter("\"mesh\" \"chair\", 1", loginDetails);
		int reportInt = subject.getQuantity();
		
		//Expected: 1
		assertTrue("reportInt is incorrect. Expected: 1, Actual: " + reportInt, reportInt == 1);
		
		subject = new FormPrinter("\"mesh\" \"chair\", 100", loginDetails);
		reportInt = subject.getQuantity();
		assertTrue("reportInt is incorrect. Expected: 100, Actual: " + reportInt, reportInt == 100);
		
		subject = new FormPrinter("\"mesh\" \"chair\", 56216", loginDetails);
		reportInt = subject.getQuantity();
		assertTrue("reportInt is incorrect. Expected: 56216, Actual: " + reportInt, reportInt == 56216);
	}
	
	/**
	 * Tests to see if the files created have the correct numbering
	 */
	@Test
	public void fileIOTest() {
		int reportInt = 0;
		
		//not sure how to test for this
		subject = new FormPrinter("\"mesh\" \"chair\", 1", loginDetails);
		
		reportInt = subject.writeReport();	//report1.txt
		assertTrue("reportInt is incorrect. Expected: 1, Actual: " + reportInt, reportInt == 1);
		
		reportInt = subject.writeReport();	//report2.txt
		assertTrue("reportInt is incorrect. Expected: 2, Actual: " + reportInt, reportInt == 2);
		
		reportInt = subject.writeReport();	//report3.txt
		assertTrue("reportInt is incorrect. Expected: 3, Actual: " + reportInt, reportInt == 3);
	}
}
