# Budget Management System

### Function Introduction
This *budget management system* is designed to help users keep track of their financial
 flows and make budget plans. The system could track users' bank accounts balance, 
 income, expenses, and set budget goals. It can also produce financial reports by
 visualizing user's total wealth, spending records in charts.

### User Group
- Personal
- Small Group
- Company

### Point of Interest
The budget management software that I am using are too complicated and cannot have all 
features that I want, so I decided to design one by myself. 

### User Stories
- As a user, I want to be able to add a expense to my expense record
- As a user, I want to be able to view a list of the titles and dates 
of expenses in my expense record
- As a user, I want to be able to know my total amount of expenses within a given month of year
- As a user, I want to be able to select a expense in my expense record and view it in detail
- As a user, I want the option to save my expense record to file before quit
- As a user, I want to be able to optionally load my expense record from file when the program starts

### Instructions for Grader
- You can generate the first required event by:
-- open the application by run "BudgetManageSystem" in the ui package
-- a file chooser window will appear, select "cancel" button first
-- click the button labelled "Add Expense"
-- a new text field window will appear, insert expense information to the text field
-- click the button labelled "Add", the newly added expense will appear in main window
-- you can click the "Add" button multiple times, or you can close that

- You can generate the second required event by:
-- click the newly add expense row in the scroll pane in main window
-- a detailed information about your selected expense would appear

- You can locate my visual component by:
-- click the button labelled "Load Record"
-- select a file to load(all files are in ./data/... directory in the project folder)
-- after succefully load a new file, a smile face will appear

- You can save the state of my application by
-- click the button labelled "Save Record"
-- You can either save the record to current file by click "Save To This File" Button
-- Or you can save the record to a new file by click "Save To A New File" Button" and insert the file name

- You can reload the state of my application by either:
-- before the program start: in the file chooser, select cancel(load empty file) or choose a file in ./data/ package in the project folder
-- after the program start: click the "Load Record" button, then select file in the file chooser


### Phase 4: Task 2
I choose to implement the second task (include a type hierarchy):
- The classes are: the abstract class "Tool" and its subclasses "AddExpenseTool", "LoadRecordTool",
 "SaveRecordTool", and "ViewMonthSumTool" which represent different tool buttons with 
 different functionality
- The methods are: two abstract methods "createButton" and "addListener" in the "Tool" class. They have been
override in each subclass to create different buttons with different customized listener
