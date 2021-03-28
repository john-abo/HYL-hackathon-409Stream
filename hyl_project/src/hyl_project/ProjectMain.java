package hyl_project;

import java.util.Scanner;

public class ProjectMain {
	
	private static String userIn = "";
	
	/**
	 * Gets the user input. The message printed is meant to notifty the user about the format
	 * and the meaning of each input.
	 */
	private static void userInput() {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter request in the format");
		System.out.println("\n<furniture type> <furniture>, <quantity>\n");
		System.out.println("<Furniture type>:	Type of furniture, such as mesh, Adjustable, or Swing Arm");
		System.out.println("<Furniture>:		Furniture that's being checked, such as Desk, Lamp, Chair");
		System.out.println("<Quantity>:			Amount of specific furniture you are looking for");
		System.out.println("\nEnter:");
		
		userIn = input.nextLine();
		
		input.close();
	}

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TESTING NOW

		search myJDBC = new search("jdbc:mysql://localhost/inventory","code","zhongli9");
		myJDBC.initializeConnection();
		myJDBC.searchChair("Task");

		// args = String  furnitureCategory, String furnitureType, and int requestedAmount
	}
	
	/**
	 * Lets not forget to document our methods as well :D
	 */
	public void calculateOption() {

		//Lets get this working
		//if (/*requestPossible*/){
		//	produceOutput();
		//} else {
		//	//printSuggestions();
		//}

	}
	
	/**
	 * Lets not forget to document our methods as well :D
	 */
	public void produceOutput() {
		
		//TEST
		FormPrinter printer;
		userInput();
		System.out.println("User request: " + userIn);
		
		printer = new FormPrinter(userIn);
		System.out.println(printer.getType() + ", " + printer.getFurniture() + ", " + printer.getQuantity());
		
		printer.writeReport();	//Writes report
	}

}
