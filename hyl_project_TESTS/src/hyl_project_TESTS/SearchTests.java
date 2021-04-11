/**
@author Kyle Hasan, John Abo , Farhad Alishov, Mohamed Yassin
@version 1.3
@since 1.0
*/

package hyl_project_TESTS;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.*;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ucalgary.ensf409.search;

//these tests can take a while, please be patient. 
public class SearchTests {
	
	private search subject;
	private Connection dbConnect;
	
	public final String DBURL = "jdbc:mysql://localhost/inventory";
	public final String  USERNAME = "root";
	public final String PASSWORD = "Pound_multiple_demonstration_watching"; //Hand in with: ensf409
	
	/**
     * Setup method that is invoked before each test method, initializing connection
     * with SQL database. The database used for testing is the one provided in the 
     * inventory.sql file
     * 
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    	//Logs in with my current credentials
    	try {
    		dbConnect = DriverManager.getConnection(DBURL,USERNAME,PASSWORD);
    		
    		if (dbConnect == null) {
    			throw new SQLException();
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    		System.out.println("If even this fails, you're done lol");
    	}

    	//This part is about to get ugly, avert your eyes
    	
    	initDatabase();	//Initializes database based on the the sample data given
    	
    	subject = new search(DBURL,USERNAME,PASSWORD);
    	subject.initializeConnection();
    }

    /**
     * Teardown method that is invoked after each test method. Not sure what
     * this needs to do exactly
     */
    @After
    public void tearDown() {
    	
    	initDatabase();	//Reinitializes the database, in case something breaks
    	
    	//Don't think I need these, but it'd be nice to get rid of stuff I guess
        subject = null;
        dbConnect = null;
    }
    
    /*
     * Constructor tests
     * These are pretty straight forward, if the constructor is supposed to throw an exception
     * and it doesn't, the tests fails. If it's supposed to succeed, nothing is thrown and the
     * test is passed. No other functionality is tested with that case, as an object will be
     * made no matter what
     */
    
    /**
     * Tests if the object fails to be created despite valid login credentials
     */
    @Test
    public void constructorValidUserTest() throws SQLException {
    	subject = new search(DBURL,USERNAME,PASSWORD);
    	subject.initializeConnection();
    }
    
    /**
     * Test if the object is created despite the invalid URL
     */
    @Test (expected = SQLException.class)
    public void constructorInalidUrl() throws SQLException {
    	subject = new search("invalid",USERNAME,PASSWORD);
    	subject.initializeConnection();
    }
    
    /**
     * Test if the object is created despite the invalid Username
     */
    @Test (expected = SQLException.class)
    public void constructorInalidUsername() throws SQLException {
    	subject = new search(DBURL,"invalid",PASSWORD);
    	subject.initializeConnection();
    }
    
    /**
     * Test if the object is created despite the invalid Password
     */
    @Test (expected = SQLException.class)
    public void constructorInvalidPassword() throws SQLException {
    	subject = new search(DBURL,USERNAME,"invalid");
    	subject.initializeConnection();
    }
    
    /*
     * Tests delete methods
     * Thankfully I have the initDatabase method, so these won't actually change the
     * database permanently. Running the main program will cause it to change, however.
     */
    
    /**
     * Tests if the method fails to remove the chair despite the chair existing
     */
    @Test
    public void deleteExistingChair() throws SQLException {
    	subject = new search(DBURL,USERNAME,PASSWORD);
    	subject.initializeConnection();
    	
    	if (getChairByID("C1320")) {
    	
    		subject.deleteChair("C1320");
    	
    		assertTrue("Chair still exists after attempt to delete it", !getChairByID("C1320"));
    	} else {
    		throw new SQLException();
    	}
    }
    
    /**
     * Tests to see if the chair is removed despite not existing
     */
    @Test (expected = IllegalArgumentException.class) 
    public void deleteNonexistingChair() throws SQLException {
    	subject = new search(DBURL,USERNAME,PASSWORD);
    	subject.initializeConnection();
    	
    	//Just using invalid ID, such as empty string
    	//results should be the same whether id is in
    	//the right format or not
    	if (!getChairByID("")) {
    		
    		subject.deleteChair("");
    		
    	}
    }
    
    /**
     * Tests if the method fails to remove the desk despite the chair existing
     */
    @Test
    public void deleteExistingDesk() throws SQLException {
    	subject = new search(DBURL,USERNAME,PASSWORD);
    	subject.initializeConnection();
    	
    	if (getDeskByID("D3820")) {
    	
    		subject.deleteDesk("D3820");
    		
    		assertTrue("Desk still exists after attempt to delete it", !getDeskByID("D3820"));
    	} else {
    		throw new SQLException();
    	}
    }
    
    /**
     * Test to see if the desk is removed despite not existing
     */
    @Test (expected = IllegalArgumentException.class)
    public void deleteNonexistingDesk() throws SQLException {
    	subject = new search(DBURL,USERNAME,PASSWORD);
    	subject.initializeConnection();
    	
    	if (!getDeskByID("")) {
    		
    		subject.deleteDesk("");
    		
    	}
    }
    
    /**
     * Tests if the method fails to remove the lamp despite the chair existing
     */
    @Test
    public void deleteExistingLamp() throws SQLException {
    	subject = new search(DBURL,USERNAME,PASSWORD);
    	subject.initializeConnection();
    	
    	if (getLampByID("L132")) {
    	
    		subject.deleteLamp("L132");
    		
    		assertTrue("Lamp still exists after attempt to delete it", !getLampByID("L132"));
    	} else {
    		throw new SQLException();
    	}
    }
    
    /**
     * Test to see if the lamp is removed despite not existing
     */
    @Test (expected = IllegalArgumentException.class)
    public void deleteNonexistingLamp() throws SQLException {
    	subject = new search(DBURL,USERNAME,PASSWORD);
    	subject.initializeConnection();

    	if (!getLampByID("")) {
    		
    		subject.deleteLamp("");
    		
    	}
    }
    
    /**
     * Tests if the method fails to remove the filing despite the chair existing
     */
    @Test
    public void deleteExistingFiling() throws SQLException {
    	subject = new search(DBURL,USERNAME,PASSWORD);
    	subject.initializeConnection();
    	
    	if (getFilingByID("F001")) {
    	
    		subject.deleteFiling("F001");
    		
    		assertTrue("Filing still exists after attempt to delete it", !getFilingByID("F001"));
    	} else {
    		throw new SQLException();
    	}
    }
    
    /**
     * Test to see if the filing is removed despite not existing
     */
    @Test (expected = IllegalArgumentException.class)
    public void deleteNonexistingFiling() throws SQLException {
    	subject = new search(DBURL,USERNAME,PASSWORD);
    	subject.initializeConnection();

    	if (!getFilingByID("")) {
    		
    		subject.deleteFiling("");
    		
    	}
    }
    
    /* 
     * Meat and potatoes of the tests, the searching
     * 
     * Hopefully these can just be copy pasted, but for sure
     * there's going to need to be a helper method where it
     * compared arrayLists? I think there's already a method
     * for that. (There wasn't lol)
     * 
     * The search methods also return a combo with the lowest price
     * The combinations with lowest ID numbers first are prioritized.
     */
    
    /**
     * Tests to see if the searchChair method returns the correct list of
     * chairs as well as a price
     */
    @Test
    public void searchChairPossible() throws SQLException {
    	subject = new search(DBURL,USERNAME,PASSWORD);
    	subject.initializeConnection();
    	
    	ArrayList<String> expected = new ArrayList<String>();
		ArrayList<String> actual = subject.searchChair("mesh", 1);
		
		expected.add("C6748");
		expected.add("C8138");
		expected.add("C9890");
		expected.add("$200");
		
		assertTrue("actual did not return as expected\n" + actual, areEqualArraylists(expected, actual));
    }
    
    /**
     * Tests to see if the searchDesk method returns the correct list of
     * desks as well as a price
     */
    @Test
    public void searchDeskPossible() throws SQLException {
    	subject = new search(DBURL,USERNAME,PASSWORD);
    	subject.initializeConnection();
    	
    	ArrayList<String> expected = new ArrayList<String>();
		ArrayList<String> actual = subject.searchDesk("Adjustable", 2);
		
		
		expected.add("D7373");
		expected.add("D3682");
		expected.add("D2746");
		expected.add("D1030");
		expected.add("$800");
		
		assertTrue("actual did not return as expected\n" + actual, areEqualArraylists(expected, actual));
    }

    /**
     * Tests to see if the searchLamp method returns the correct list of
     * lamps as well as a price
     */
    @Test
    public void searchLampPossible() throws SQLException {
    	subject = new search(DBURL,USERNAME,PASSWORD);
    	subject.initializeConnection();
    	
    	ArrayList<String> expected = new ArrayList<String>();
		ArrayList<String> actual = subject.searchLamp("Desk", 2);
		
		expected.add("L013");
		expected.add("L208");
		expected.add("L112");
		expected.add("L342");
		expected.add("$40");
		
		assertTrue("actual did not return as expected\n" + actual, areEqualArraylists(expected, actual));
    }

    /**
     * Tests to see if the searchFiling method returns the correct list of
     * filing as well as a price
     */
    @Test
    public void searchFilingPossible() throws SQLException {
    	subject = new search(DBURL,USERNAME,PASSWORD);
    	subject.initializeConnection();
    	
    	ArrayList<String> expected = new ArrayList<String>();
		ArrayList<String> actual = subject.searchFiling("Medium", 2);
		
		expected.add("F007");
		expected.add("F008");
		expected.add("F009");
		expected.add("F002");
		expected.add("$400");
		
		assertTrue("actual did not return as expected\n" + actual, areEqualArraylists(expected, actual));
    }

    /* 
     * These tests search for an order that cannot be fulfilled and are supposed to fail
     * The method should not return a list with a price
     */
    
    /**
     * Tests to see if a valid list of chairs is returned despite the order
     * being impossible to fulfill
     */
    @Test
    public void searchChairImpossible() throws SQLException {
    	subject = new search(DBURL,USERNAME,PASSWORD);
    	subject.initializeConnection();
    	
    	ArrayList<String> expected = new ArrayList<String>();
		ArrayList<String> actual = subject.searchChair("Task",100);
		
		String endOfActual = actual.get(actual.size() - 1);
		
		//If the first character were a $, then a valid list was given
		assertTrue("Actual was a valid list, and should not have been", endOfActual.charAt(0) != '$');
    }
    
    /**
     * Tests to see if a valid list of chairs is returned despite the order
     * being impossible to fulfill
     */
    @Test
    public void searchChairImpossible2() throws SQLException {
    	subject = new search(DBURL,USERNAME,PASSWORD);
    	subject.initializeConnection();
    	
    	ArrayList<String> expected = new ArrayList<String>();
		ArrayList<String> actual = subject.searchChair("L",1);
		
		String endOfActual = actual.get(actual.size() - 1);
		
		//If the first character were a $, then a valid list was given
		assertTrue("Actual was either null or a valid list, and should not have been", endOfActual.charAt(0) != '$');
    }
    
    /**
     * Tests to see if a valid list of desks is returned despite the order
     * being impossible to fulfill
     */
    @Test
    public void searchDeskImpossible() throws SQLException {
    	subject = new search(DBURL,USERNAME,PASSWORD);
    	subject.initializeConnection();
    	
    	ArrayList<String> expected = new ArrayList<String>();
		ArrayList<String> actual = subject.searchDesk("Standing",100);
		
		String endOfActual = actual.get(actual.size() - 1);
		
		//If the first character were a $, then a valid list was given
		assertTrue("Actual was a valid list, and should not have been", endOfActual.charAt(0) != '$');
    }
    
    /**
     * Tests to see if a valid list of desks is returned despite the order
     * being impossible to fulfill
     */
    @Test
    public void searchDeskImpossible2() throws SQLException {
    	subject = new search(DBURL,USERNAME,PASSWORD);
    	subject.initializeConnection();
    	
    	ArrayList<String> expected = new ArrayList<String>();
		ArrayList<String> actual = subject.searchDesk("L",1);
		
		String endOfActual = actual.get(actual.size() - 1);
		
		//If the first character were a $, then a valid list was given
		assertTrue("Actual was either a valid list or null, and should not have been", endOfActual.charAt(0) != '$');
    }

    /**
     * Tests to see if a valid list of lamps is returned despite the order
     * being impossible to fulfill
     */
    @Test
    public void searchLampImpossible() throws SQLException {
    	subject = new search(DBURL,USERNAME,PASSWORD);
    	subject.initializeConnection();
    	
    	ArrayList<String> expected = new ArrayList<String>();
		ArrayList<String> actual = subject.searchLamp("Desk",100);
		
		String endOfActual = actual.get(actual.size() - 1);
		
		//If the first character were a $, then a valid list was given
		assertTrue("Actual was a valid list, and should not have been", endOfActual.charAt(0) != '$');
    }
    
    /**
     * Tests to see if a valid list of lamps is returned despite the order
     * being impossible to fulfill
     */
    @Test
    public void searchLampImpossible2() throws SQLException {
    	subject = new search(DBURL,USERNAME,PASSWORD);
    	subject.initializeConnection();
    	
    	ArrayList<String> expected = new ArrayList<String>();
		ArrayList<String> actual = subject.searchLamp("L",1);
		
		String endOfActual = actual.get(actual.size() - 1);
		
		//If the first character were a $, then a valid list was given
		assertTrue("Actual was either a valid list or null, and should not have been", endOfActual.charAt(0) != '$');
    }
    
    /**
     * Tests to see if a valid list of filings is returned despite the order
     * being impossible to fulfill
     */
    @Test
    public void searchFilingImpossible() throws SQLException {
    	subject = new search(DBURL,USERNAME,PASSWORD);
    	subject.initializeConnection();
    	
    	ArrayList<String> expected = new ArrayList<String>();
		ArrayList<String> actual = subject.searchFiling("Large",100);
		
		String endOfActual = actual.get(actual.size() - 1);

		//If the first character were a $, then a valid list was given
		assertTrue("Actual was a valid list, and should not have been", endOfActual.charAt(0) != '$');
    }
    
    /**
     * Tests to see if a valid list of filings is returned despite the order
     * being impossible to fulfill
     */
    @Test
    public void searchFilingImpossible2() throws SQLException {
    	subject = new search(DBURL,USERNAME,PASSWORD);
    	subject.initializeConnection();
    	
    	ArrayList<String> expected = new ArrayList<String>();
		ArrayList<String> actual = subject.searchFiling("L",1);
		
		String endOfActual = actual.get(actual.size() - 1);

		//If the first character were a $, then a valid list was given
		assertTrue("Actual was either a valid list or null, and should not have been", endOfActual.charAt(0) != '$');
    }
    
    /**
     * Initiallizes the database based on inventory.sql that was provided
     * by the class. This is before the last second updated .sql was given
     */
    private void initDatabase() {
    	//LOL look at this garbage
    	try {
    		//executeUpdate(""); this effectively cleans up some of the shenanigans. Helps clean up the code a bit too
    		
    		executeUpdate("DROP DATABASE IF EXISTS INVENTORY");
    		executeUpdate("CREATE DATABASE INVENTORY");
    		executeUpdate("USE INVENTORY");
    		executeUpdate("DROP TABLE IF EXISTS MANUFACTURER;");
    		executeUpdate("CREATE TABLE MANUFACTURER (\r\n"
    				+ "	ManuID			char(3) not null,\r\n"
    				+ "	Name			varchar(25),\r\n"
    				+ "	Phone			char(12),\r\n"
    				+ "    Province		char(2),\r\n"
    				+ "	primary key (ManuID)\r\n"
    				+ ")");
    		executeUpdate("INSERT INTO MANUFACTURER (ManuID, Name, Phone, Province)\r\n"
    				+ "VALUES\r\n"
    				+ "('001',	'Academic Desks',	'236-145-2542',	'BC'),\r\n"
    				+ "('002',	'Office Furnishings',	'587-890-4387',	'AB'),\r\n"
    				+ "('003',	'Chairs R Us',	'705-667-9481',	'ON'),\r\n"
    				+ "('004',	'Furniture Goods',	'306-512-5508',	'SK'),\r\n"
    				+ "('005',	'Fine Office Supplies',	'403-980-9876',	'AB')");
    		executeUpdate("DROP TABLE IF EXISTS CHAIR");
    		executeUpdate("CREATE TABLE CHAIR (\r\n"
    				+ "	ID				char(5)	not null,\r\n"
    				+ "	Type			varchar(25),\r\n"
    				+ "	Legs			char(1),\r\n"
    				+ "	Arms			char(1),\r\n"
    				+ "	Seat			char(1),\r\n"
    				+ "	Cushion			char(1),\r\n"
    				+ "    Price			integer,\r\n"
    				+ "	ManuID			char(3),\r\n"
    				+ "	primary key (ID),\r\n"
    				+ "	foreign key (ManuID) references MANUFACTURER(ManuID) ON UPDATE CASCADE\r\n"
    				+ ")");
    		executeUpdate("INSERT INTO CHAIR (ID, Type, Legs, Arms, Seat, Cushion, Price, ManuID)\r\n"
    				+ "VALUES\r\n"
    				+ "('C1320',	'Kneeling',	'Y',	'N',	'N',	'N',	50,	'002'),\r\n"
    				+ "('C3405',	'Task',	'Y',	'Y',	'N',	'N',	100,	'003'),\r\n"
    				+ "('C9890',	'Mesh',	'N',	'Y',	'N',	'Y',	50,	'003'),\r\n"
    				+ "('C7268',	'Executive',	'N',	'N',	'Y',	'N',	75,	'004'),\r\n"
    				+ "('C0942',	'Mesh',	'Y',	'N',	'Y',	'Y',	175,	'005'),\r\n"
    				+ "('C4839',	'Ergonomic',	'N',	'N',	'N',	'Y',	50,	'002'),\r\n"
    				+ "('C2483',	'Executive',	'Y',	'Y',	'N',	'N',	175,	'002'),\r\n"
    				+ "('C5789',	'Ergonomic',	'Y',	'N',	'N',	'Y',	125,	'003'),\r\n"
    				+ "('C3819',	'Kneeling',	'N',	'N',	'Y',	'N',	75,	'005'),\r\n"
    				+ "('C5784',	'Executive',	'Y',	'N',	'N',	'Y',	150,	'004'),\r\n"
    				+ "('C6748',	'Mesh',	'Y',	'N',	'N',	'N',	75,	'003'),\r\n"
    				+ "('C0914',	'Task',	'N',	'N',	'Y',	'Y',	50,	'002'),\r\n"
    				+ "('C1148',	'Task',	'Y',	'N',	'Y',	'Y',	125,	'003'),\r\n"
    				+ "('C5409',	'Ergonomic',	'Y',	'Y',	'Y',	'N',	200,	'003'),\r\n"
    				+ "('C8138',	'Mesh',	'N',	'N',	'Y',	'N',	75,	'005')");
    		executeUpdate("DROP TABLE IF EXISTS DESK");
    		executeUpdate("CREATE TABLE DESK (\r\n"
    				+ "	ID				char(5)	not null,\r\n"
    				+ "	Type			varchar(25),\r\n"
    				+ "	Legs			char(1),\r\n"
    				+ "	Top			char(1),\r\n"
    				+ "	Drawer			char(1),\r\n"
    				+ "    Price			integer,\r\n"
    				+ "	ManuID			char(3),\r\n"
    				+ "	primary key (ID),\r\n"
    				+ "	foreign key (ManuID) references MANUFACTURER(ManuID) ON UPDATE CASCADE\r\n"
    				+ ")");
    		executeUpdate("INSERT INTO DESK (ID, Type, Legs, Top, Drawer, Price, ManuID)\r\n"
    				+ "VALUES\r\n"
    				+ "('D3820',	'Standing',	'Y',	'N',	'N',	150,	'001'),\r\n"
    				+ "('D4475',	'Adjustable',	'N',	'Y',	'Y',	200,	'002'),\r\n"
    				+ "('D0890',	'Traditional',	'N',	'N',	'Y',	25,	'002'),\r\n"
    				+ "('D2341',	'Standing',	'N',	'Y',	'N',	100,	'001'),\r\n"
    				+ "('D9387',	'Standing',	'Y',	'Y',	'N',	250,	'004'),\r\n"
    				+ "('D7373',	'Adjustable',	'Y',	'Y',	'N',	350,	'005'),\r\n"
    				+ "('D2746',	'Adjustable',	'Y',	'N',	'Y',	250,	'004'),\r\n"
    				+ "('D9352',	'Traditional',	'Y',	'N',	'Y',	75,	'002'),\r\n"
    				+ "('D4231',	'Traditional',	'N',	'Y',	'Y',	50,	'005'),\r\n"
    				+ "('D8675',	'Traditional',	'Y',	'Y',	'N',	75,	'001'),\r\n"
    				+ "('D1927',	'Standing',	'Y',	'N',	'Y',	200,	'005'),\r\n"
    				+ "('D1030',	'Adjustable',	'N',	'Y',	'N',	150,	'002'),\r\n"
    				+ "('D4438',	'Standing',	'N',	'Y',	'Y',	150,	'004'),\r\n"
    				+ "('D5437',	'Adjustable',	'Y',	'N',	'N',	200,	'001'),\r\n"
    				+ "('D3682',	'Adjustable',	'N',	'N',	'Y',	50,	'005')");
    		executeUpdate("DROP TABLE IF EXISTS LAMP");
    		executeUpdate("CREATE TABLE LAMP (\r\n"
    				+ "	ID				char(4)	not null,\r\n"
    				+ "	Type			varchar(25),\r\n"
    				+ "	Base			char(1),\r\n"
    				+ "	Bulb			char(1),\r\n"
    				+ "    Price			integer,\r\n"
    				+ "	ManuID			char(3),\r\n"
    				+ "	primary key (ID),\r\n"
    				+ "	foreign key (ManuID) references MANUFACTURER(ManuID) ON UPDATE CASCADE\r\n"
    				+ ")");
    		executeUpdate("INSERT INTO LAMP (ID, Type, Base, Bulb, Price, ManuID)\r\n"
    				+ "VALUES\r\n"
    				+ "('L132',	'Desk',	'Y',	'N',	18,	'005'),\r\n"
    				+ "('L980',	'Study',	'N',	'Y',	2,	'004'),\r\n"
    				+ "('L487',	'Swing Arm',	'Y',	'N',	27,	'002'),\r\n"
    				+ "('L564',	'Desk',	'Y',	'Y',	20,	'004'),\r\n"
    				+ "('L342',	'Desk',	'N',	'Y',	2,	'002'),\r\n"
    				+ "('L982',	'Study',	'Y',	'N',	8,	'002'),\r\n"
    				+ "('L879',	'Swing Arm',	'N',	'Y',	3,	'005'),\r\n"
    				+ "('L208',	'Desk',	'N',	'Y',	2,	'005'),\r\n"
    				+ "('L223',	'Study',	'N',	'Y',	2,	'005'),\r\n"
    				+ "('L928',	'Study',	'Y',	'Y',	10,	'002'),\r\n"
    				+ "('L013',	'Desk',	'Y',	'N',	18,	'004'),\r\n"
    				+ "('L053',	'Swing Arm',	'Y',	'N',	27,	'002'),\r\n"
    				+ "('L112',	'Desk',	'Y',	'N',	18,	'005'),\r\n"
    				+ "('L649',	'Desk',	'Y',	'N',	18,	'004'),\r\n"
    				+ "('L096',	'Swing Arm',	'N',	'Y',	3,	'002')");
    		executeUpdate("DROP TABLE IF EXISTS FILING");
    		executeUpdate("CREATE TABLE FILING (\r\n"
    				+ "	ID				char(4)	not null,\r\n"
    				+ "	Type			varchar(25),\r\n"
    				+ "	Rails			char(1),\r\n"
    				+ "	Drawers			char(1),\r\n"
    				+ "	Cabinet			char(1),\r\n"
    				+ "    Price			integer,\r\n"
    				+ "	ManuID			char(3),\r\n"
    				+ "	primary key (ID),\r\n"
    				+ "	foreign key (ManuID) references MANUFACTURER(ManuID) ON UPDATE CASCADE\r\n"
    				+ ")");
    		executeUpdate("INSERT INTO FILING (ID, Type, Rails, Drawers, Cabinet, Price, ManuID)\r\n"
    				+ "VALUES\r\n"
    				+ "('F001',	'Small',	'Y',	'Y',	'N',	50,	'005'),\r\n"
    				+ "('F002',	'Medium',	'N',	'N',	'Y',	100,	'004'),\r\n"
    				+ "('F003',	'Large',	'N',	'N',	'Y',	150,	'002'),\r\n"
    				+ "('F004',	'Small',	'N',	'Y',	'Y',	75,	'004'),\r\n"
    				+ "('F005',	'Small',	'Y',	'N',	'Y',	75,	'005'),\r\n"
    				+ "('F006',	'Small',	'Y',	'Y',	'N',	50,	'005'),\r\n"
    				+ "('F007',	'Medium',	'N',	'Y',	'Y',	150,	'002'),\r\n"
    				+ "('F008',	'Medium',	'Y',	'N',	'N',	50,	'005'),\r\n"
    				+ "('F009',	'Medium',	'Y',	'Y',	'N',	100,	'004'),\r\n"
    				+ "('F010',	'Large',	'Y',	'N',	'Y',	225,	'002'),\r\n"
    				+ "('F011',	'Large',	'N',	'Y',	'Y',	225,	'005'),\r\n"
    				+ "('F012',	'Large',	'N',	'Y',	'N',	75,	'005'),\r\n"
    				+ "('F013',	'Small',	'N',	'N',	'Y',	50,	'002'),\r\n"
    				+ "('F014',	'Medium',	'Y',	'Y',	'Y',	200,	'002'),\r\n"
    				+ "('F015',	'Large',	'Y',	'N',	'N',	75,	'004')");
    		
		} catch (SQLException e) {
			System.out.println("If even this fails, you're done lol");
			System.out.println("Of course, this one was pretty much guaranteed to fail");
			e.printStackTrace();
		}
    	
    	//System.out.println("The deed is done, but what sort of man have you become");
    }
    
    /**
     * Manually executes an update, this allows the test to repair the database after any
     * change has been done
     * 
     * @param update 		the update line that will be executed
     * @throws SQLException Incase anything goes wrong with execution
     */
    @SuppressWarnings("unused")
	private void executeUpdate(String update) throws SQLException{
    	PreparedStatement stmt;
		stmt = dbConnect.prepareStatement(update);
		int test = stmt.executeUpdate();
		stmt.close();
    }
    
    /*
     * Lets me search the data base for a specific piece of furniture
     * based on it's ID
     */
    
    /**
     * Gets a chair from the database based on the ID provided. Then returns a boolean
     * based on whether it exists or not
     * 
     * @param ID	ID of the chair that will be searched for
     * @return		Boolean, true if it exists and false otherwise
     */
    private boolean getChairByID(String ID) {
    	try {
    		String query = "SELECT * FROM CHAIR WHERE ID=?";
    		PreparedStatement stmt = dbConnect.prepareStatement(query);
    		
    		stmt.setString(1, ID);
    		ResultSet queryResult = stmt.executeQuery();
    		
    		if (!queryResult.next()) {
    			System.out.println("Chair " + ID + " does not exist");
    			return false;
    		}
    		
    	} catch (SQLException e) {
    		System.out.println("Failed query");
    		return false;
    	}
    	return true;
    }
    
    /**
     * Gets a desk from the database based on the ID provided. Then returns a boolean
     * based on whether it exists or not
     * 
     * @param ID	ID of the desk that will be searched for
     * @return		Boolean, true if it exists and false otherwise
     */
    private boolean getDeskByID(String ID) {
    	try {
    		String query = "SELECT * FROM DESK WHERE ID=?";
    		PreparedStatement stmt = dbConnect.prepareStatement(query);
    		
    		stmt.setString(1, ID);
    		ResultSet queryResult = stmt.executeQuery();
    		
    		if (!queryResult.next()) {
    			System.out.println("Desk " + ID + " does not exist");
    			return false;
    		}
    		
    	} catch (SQLException e) {
    		System.out.println("Failed query");
    		return false;
    	}
    	return true;
    }
    
    /**
     * Gets a lamp from the database based on the ID provided. Then returns a boolean
     * based on whether it exists or not
     * 
     * @param ID	ID of the lamp that will be searched for
     * @return		Boolean, true if it exists and false otherwise
     */
    private boolean getLampByID(String ID) {
    	try {
    		String query = "SELECT * FROM Lamp WHERE ID=?";
    		PreparedStatement stmt = dbConnect.prepareStatement(query);
    		
    		stmt.setString(1, ID);
    		ResultSet queryResult = stmt.executeQuery();
    		
    		if (!queryResult.next()) {
    			System.out.println("Lamp " + ID + " does not exist");
    			return false;
    		}
    		
    	} catch (SQLException e) {
    		System.out.println("Failed query");
    		return false;
    	}
    	return true;
    }
    
    /**
     * Gets a filing from the database based on the ID provided. Then returns a boolean
     * based on whether it exists or not
     * 
     * @param ID	ID of the filing that will be searched for
     * @return		Boolean, true if it exists and false otherwise
     */
    private boolean getFilingByID(String ID) {
    	try {
    		String query = "SELECT * FROM Filing WHERE ID=?";
    		PreparedStatement stmt = dbConnect.prepareStatement(query);
    		
    		stmt.setString(1, ID);
    		ResultSet queryResult = stmt.executeQuery();
    		
    		if (!queryResult.next()) {
    			System.out.println("Filing " + ID + " does not exist");
    			return false;
    		}
    		
    	} catch (SQLException e) {
    		System.out.println("Failed query");
    		return false;
    	}
    	return true;
    }
  
    /**
     * Compares 2 sets to see if they are equal, using arraylists.
     * For all String that are elements of expected, String is also an element of actual
     * 
     * This allows the comparison to ignore the order elements in the list
     * 
     * @param expected	The arraylist of known contents
     * @param actual
     * @return
     */
    private boolean areEqualArraylists(ArrayList<String> expected, ArrayList<String> actual) {
    	
    	//Both sets must be the same size to be equal
    	if (expected.size() != actual.size()) {
    		return false;
    	}
    	
    	for (String exp : expected) {
    		
    		//If there exists an element in expected that is not an element of actual
    		//then they cannot be equal
    		if (!actual.contains(exp)) {
    			return false;
    		}
    	}
    	
    	//Returns true if they are equal
    	return true;
    }
}
