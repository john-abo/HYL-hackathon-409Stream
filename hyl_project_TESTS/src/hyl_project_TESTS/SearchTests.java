package hyl_project_TESTS;

import org.junit.After;
import org.junit.Before;

import edu.ucalgary.ensf409.search;

public class SearchTests {
	
	private search database;
	
	/**
     * Setup method that is invoked before each test method, initializing connection
     * with SQL database
     * 
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    	//Logs in with my current credentials
    	database = new search("jdbc:mysql://localhost/inventory","root","Pound_multiple_demonstration_watching");
    }

    /**
     * Teardown method that is invoked after each test method. Not sure what
     * this needs to do exactly
     */
    @After
    public void tearDown() {
        
    }
}
