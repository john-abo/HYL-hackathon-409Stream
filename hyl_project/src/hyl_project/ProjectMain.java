package hyl_project;

public class ProjectMain {

	public static void main(String[] args) {
		search myJDBC = new search("jdbc:mysql://localhost/inventory","code","zhongli9");
		myJDBC.initializeConnection();
		myJDBC.searchChair("Mesh");
		

	}

}
