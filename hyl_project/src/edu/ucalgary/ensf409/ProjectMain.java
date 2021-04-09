package edu.ucalgary.ensf409;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProjectMain {
	
	private static String sqlIn = "";
	private static String userIn = "";
	
	/**
	 * Gets the user input. The message printed is meant to notifty the user about the format
	 * and the meaning of each input.
	 */
	private static void userInput() {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter your SQL Information in the format");
		System.out.println("\n<dbURL> <Username> <Password>\n");
		System.out.println("<dbURL>:		For our Project it should be jdbc:mysql://localhost/inventory");
		System.out.println("<Username>:		It can be either 'root' or your own custom Username");
		System.out.println("<Password>:		Your Password for this particular Username");
		System.out.println("\nEnter:");
		
		sqlIn = input.nextLine();

		System.out.println("Enter request in the format");
		System.out.println("*** note the quotations around the first 2 groups, and the comma before the number");
		System.out.println("\n\"<furniture type>\" \"<furniture>\", <quantity>\n");
		System.out.println("<Furniture type>:	Type of furniture, such as mesh, Adjustable, or Swing Arm");
		System.out.println("<Furniture>:		Furniture that's being checked, such as Desk, Lamp, Chair");
		System.out.println("<Quantity>:		Amount of specific furniture you are looking for");
		System.out.println("\nEnter:");
		
		userIn = input.nextLine();
		
		input.close();
	}

	/**
	 * Main method
	 * 
	 * @param args
	 * @throws SQLException Thrown if an SQL error is encountered
	 */
	public static void main(String[] args) throws SQLException {
	
		// TESTING NOW
		System.out.println("Starting software...");
		
		FormPrinter printer = new FormPrinter();
		userInput();
		
		printer = new FormPrinter(userIn, sqlIn);
		try {
		if (printer.query()) {
			System.out.println(printer.formatReport());
			//System.out.println(printer.getType() + ", " + printer.getFurniture() + ", " + printer.getQuantity());
						
			printer.writeReport();	//Writes report

		} 
		}
		catch(IllegalArgumentException e){
		
			
		}
	}
}

