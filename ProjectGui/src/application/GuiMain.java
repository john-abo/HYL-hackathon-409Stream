package application;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.util.Scanner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import hyl_project.FormPrinter;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class GuiMain {

	protected Shell shell;
	private Text userInputBox;
	
	private Label requestNotif;
	private static String userIn = "";
	
	/**
	 * Gets the user input. The message printed is meant to notifty the user about the format
	 * and the meaning of each input. This is run in console
	 */
	@SuppressWarnings("unused") //Suppressed for now, as I am not using it
	private static void userInputConsole() {
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
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		//FormPrinter printer;
		
		//userInputConsole();
		//System.out.println("User request: " + userIn);
		
		//printer = new FormPrinter(userIn);
		//System.out.println(printer.getType() + ", " + printer.getFurniture() + ", " + printer.getQuantity());
		
		//printer.writeReport();	//Writes report
		
		try {
			GuiMain window = new GuiMain();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(500, 600);
		shell.setText("ENSF 409 Project");
		
		Label lblPleaseEnterRequest = new Label(shell, SWT.NONE);
		lblPleaseEnterRequest.setBounds(10, 157, 203, 15);
		lblPleaseEnterRequest.setText("Please Enter Request");
		
		userInputBox = new Text(shell, SWT.BORDER);
		userInputBox.setBounds(10, 178, 193, 21);
		
		//Listener called when button is pressed. Runs the request
		Button btnMakeRequest = new Button(shell, SWT.NONE);
		btnMakeRequest.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				FormPrinter printer;
				
				System.out.println("Button pressed");
				requestNotif.setText("Request made");
				
				//gets text in box
				userIn = userInputBox.getText();
				
				System.out.println("User text: \"" + userIn + "\"");
				
				//This is where main would have ran
				try {
					printer = new FormPrinter(userIn);
					System.out.println(printer.getType() + ", " + printer.getFurniture() + ", " + printer.getQuantity());
				
					printer.writeReport();	//Writes report
				} catch (IllegalArgumentException exc) {
					System.out.println("Check failed");
					requestNotif.setText("Request failed: Format Error");
				}
			}
		});
		btnMakeRequest.setBounds(10, 205, 94, 25);
		btnMakeRequest.setText("Make Request");
		
		Label requestLn1 = new Label(shell, SWT.NONE);
		requestLn1.setBounds(10, 10, 464, 15);
		requestLn1.setText("Enter request in the format");
		
		Label requestLn2 = new Label(shell, SWT.NONE);
		requestLn2.setBounds(10, 52, 464, 15);
		requestLn2.setText("<furniture type> <furniture>, <quantity>");
		
		Label requestLn3 = new Label(shell, SWT.NONE);
		requestLn3.setBounds(10, 73, 464, 15);
		requestLn3.setText("<Furniture type>:		Type of furniture, such as mesh, Adjustable, or Swing Arm");
		
		Label requestLn4 = new Label(shell, SWT.NONE);
		requestLn4.setBounds(10, 94, 464, 15);
		requestLn4.setText("<Furniture>:		Furniture that's being checked, such as Desk, Lamp, Chair");
		
		Label requestLn5 = new Label(shell, SWT.NONE);
		requestLn5.setBounds(10, 115, 464, 15);
		requestLn5.setText("<Quantity>:		Amount of specific furniture you are looking for");
		
		//Padding label
		Label Padding1 = new Label(shell, SWT.NONE);
		Padding1.setBounds(10, 31, 55, 15);
		
		Label Padding2 = new Label(shell, SWT.NONE);
		Padding2.setBounds(10, 136, 55, 15);
		
		Label Padding3 = new Label(shell, SWT.NONE);
		Padding3.setBounds(10, 236, 55, 15);
		
		requestNotif = new Label(shell, SWT.NONE);
		requestNotif.setBounds(10, 257, 464, 15);
		requestNotif.setText(" ");

	}
}
