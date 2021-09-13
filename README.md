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
## Some Screenshots Of The Program:

![image](https://user-images.githubusercontent.com/73013959/133030387-5f717255-5b5f-44ae-9d83-32a6d5bfe025.png)

![image](https://user-images.githubusercontent.com/73013959/133030404-b718bfa1-27f4-42c1-aa1c-5e32b64db6fd.png)


## How To Run The Program (requires MySQL and Java):
Follow the instructions in the "SetupInstructions.txt" file attached in the repository.

## UML and Code
We created a UML diagram that shows how our code is connected, click on the "uml.pdf" file attached in the repository. To view the code, we recommend downloading the FinishedBuild.zip file, extracting it and then browsing through the .java files on a text editor of your choice. 
