package edu.ucalgary.ensf409;

import java.util.ArrayList;
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
		System.out.println("Starting software...");
		
		FormPrinter printer;
		userInput();
		
		try {
			printer = new FormPrinter(userIn);
			
			if (printer.query()) {
				System.out.println(printer.formatReport());
				//System.out.println(printer.getType() + ", " + printer.getFurniture() + ", " + printer.getQuantity());
						
				printer.writeReport();	//Writes report
			} else {
				printer.failed();
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Bruh");
		}
		

	}
}
