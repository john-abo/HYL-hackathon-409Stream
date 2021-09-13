# Supply Chain Management, Final Project for ENSF409 - Principles of Software Development

## Authors
   * John Abo
   * Farhad Alishov
   * Kyle Hasan
   * Mohamed Yassin
   
## How The System Works:
Our supply chain management system is designed to assist office furniture supply chains manage their inventory by re-using surplus items and thus losing as little money as possible. There is also an environmental benefit to this because fewer materials will be thrown away for recycling and such. In quick summary, here is how the system works:
1. User inputs a request for furniture by inputting the furniture category, the type of the furniture, and the number of pieces of that specific furniture.
2. Algorithm iterates through the database and finds the cheapest combination of parts that will fulfill customer order.
3. If the request is possible to be fulfilled based on inventory, an order form is produced in a text file format.
4. If the request is not possible because of missing parts, the system suggests nearby manufacturers that may have the items.

## How It Was Built:
The system was built using Java and MySQL. Throughout the process, consistent effort was made to include easy-to-understand documentation, naming conventions, comments as well as object-oriented programming strategies to ensure the program can be expanded in the future if necessary. It can run locally on a user's system without the need for an internet connection provided all the required files are included. 
## Example Screenshots:


## How To Run The Program (requires MySQL and Java):
Hello, Welcome To the Read Me text document for our Inventory Management System. 

We will show you step by step how to get our program running!
Note that these are example commands for a Windows Operating System. Change them accordingly if you use Mac or Linux.
If the commands do not work, please re-read the instructions or make sure everything is on class path and build path.

***	NOTE: The search is case sensitive, and that should be taken into account when looking for
	the furniture. Small filing and small filing are not the same

Required Files: 
		hamcrest-core-1.3.jar
		junit-4.13.2.jar
		mysql-connector-java-8.0.23.jar



Step 1: First, Extract the zip file FinishedBuild.zip to a destination of your choosing, we personally think extracting to Desktop is most convenient.

Step 2: Now, click on the FinishedBuild folder you have extracted, right as you click on the file, you can paste the required files we have listed above. Please do not put the required files anywhere else in the folder, (In the .zip file, we have included those files for your convenience, so feel free to skip this step).
they have to be in the "FinishedBuild" directory so our commands work immediately.

Step 3: To test the combination finder and report generator, open the command line, and navigate to the directory by copying the address off the group18 directory and pasting it into cmd. For my case, since I saved it to my desktop, 
the command would be: cd C:\Users\moeya\Desktop\FinishedBuild

Step 3.5: compile classes chairData, deskData, filingData, FormPrinter, node and search using the following commands:
	javac edu/ucalgary/ensf409/chairData.java
	javac edu/ucalgary/ensf409/deskData.java
	javac edu/ucalgary/ensf409/filingData.java
	javac edu/ucalgary/ensf409/lampData.java
	javac edu/ucalgary/ensf409/node.java
	javac edu/ucalgary/ensf409/FormPrinter.java
	javac -cp .;mysql-connector-java-8.0.23.jar;. edu/ucalgary/ensf409/search.java

Alternatively if this does not compile/run properly try this command instead javac -cp .;mysql-connector-8.0.23.jar;hamcrest-core-1.3.jar;junit-4.13.2.jar;. edu/ucalgary/ensf409/*.java to compile everything at once.
If you choose to run this command instead, you no longer need to use any more javac commands and just use the java commands in the steps to run the compiled class files. If this command does not work, use the commands at the
start of step 3.5 to compile the files 1 by 1 and make sure that you use the javac commands in the next steps.

Step 4: compile and run ProjectMain (combination finder and report generator) using the following commands: 
	javac -cp .;mysql-connector-java-8.0.23.jar;. edu/ucalgary/ensf409/ProjectMain.java

	java -cp .;mysql-connector-java-8.0.23.jar;. edu/ucalgary/ensf409/ProjectMain

Follow the instructions on the cmd terminal: For the first input section, write the DBURL, Username and Password in the following format: jdbc:mysql://localhost/inventory username password

For the second input section, write the order in the following format: "Mesh" "Chair", 1   ("mesh" "chair", 1 is just an example, you can input whatever items you want, make sure to not forget the quotations and the comma)

Now, you will see the results of your order in the command prompt, and you will see that in the group18 directory, there will be a new directory called "OUT". This will have the report for the items you ordered. (called report1).


Step 5: compile and run MainTests (Tests for ProjectMain.java) using the following commands: (In the same directory as earlier)
	javac -cp .;junit-4.13.2.jar;hamcrest-core-1.3.jar;mysql-connector-java-8.0.23.jar;. edu\ucalgary\ensf409\MainTests.java

	java -cp .;junit-4.13.2.jar;hamcrest-core-1.3.jar;mysql-connector-java-8.0.23.jar;. org.junit.runner.JUnitCore edu.ucalgary.ensf409.MainTests


Step 6: compile and run SearchTests(tests for search.java) using the following commands: (In the same directory as earlier)
	javac -cp .;junit-4.13.2.jar;hamcrest-core-1.3.jar;mysql-connector-java-8.0.23.jar;. edu\ucalgary\ensf409\SearchTests.java

	java -cp .;junit-4.13.2.jar;hamcrest-core-1.3.jar;mysql-connector-java-8.0.23.jar;. org.junit.runner.JUnitCore edu.ucalgary.ensf409.SearchTests

	
Step 7: Note that in the OUT directory in group18 directory, there will be 3 new reports titled report1, report2 and report3. These are from MainTests. (report1 is not the same report frome earlier, it's overwritten by MainTests)


Step 8: Finished! You have successfully ran our program!
