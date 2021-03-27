package hyl_project;

public class ProjectMain {
		
	public static void main(String[] args) {
		FormPrinter printer;
		
		System.out.println("TEST");
		
		//Can't seem to get REGEX to find it without end of string having a space
		printer = new FormPrinter("mesh chair, 1 ");
		
		System.out.println(printer.getType() + ", " + printer.getFurniture() + ", " + printer.getQuantity());
		
		printer.writeReport();	//Writes report
	}

}
