# Inventory Management System
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



Step 1: First, Extract the zip file group18.zip to a destination of your choosing, we personally think extracting to Desktop is most convenient.

Step 2: Now, click on the group18 folder you have extracted, right as you click on the file, you can paste the required files we have listed above. Please do not put the required files anywhere else in the folder,
they have to be in the "group18" directory so our commands work immediately.

Step 3: To test the combination finder and report generator, open the command line, and navigate to the directory by copying the address off the group18 directory and pasting it into cmd. For my case, since I saved it to my desktop, 
the command would be: cd C:\Users\moeya\Desktop\group18

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
