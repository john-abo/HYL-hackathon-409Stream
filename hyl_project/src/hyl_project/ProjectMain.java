package hyl_project;

import java.util.Scanner;

public class ProjectMain {
	
	private static String userIn = "";
	
	/**
	 * Gets the user input
	 */
	private static void userInput() {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter request in the format");
		System.out.println("\n<furniture type> <furniture>, <quantity>\n");
		System.out.println("<Furniture type>:	Type of furniture, such as mesh, Adjustable, or Swing Arm");
		System.out.println("<Furniture>:		Furniture that's being checked, such as Desk, Lamp, Chair");
		System.out.println("<Quantity>:			Amount of specific furniture you are looking for");
		System.out.println("\nEnter:\n");
		
		userIn = input.nextLine();
		
		input.close();
	}

	public static void main(String[] args) {
		FormPrinter printer;
		userInput();
		System.out.println("User in: " + userIn);
		
		printer = new FormPrinter(userIn);
		System.out.println(printer.getType() + ", " + printer.getFurniture() + ", " + printer.getQuantity());
		
		printer.writeReport();	//Writes report
	}

}
