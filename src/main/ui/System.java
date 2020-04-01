package ui;

import model.Expense;
import model.ExpenseRecord;
import persistence.Reader;
import persistence.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

// Budget Management System (Console based version)
public class System {
    private String file = "./data/untitled.txt";
    private ExpenseRecord expRecord;
    private Scanner input;


    // Citation: This method is inspired by Teller Project, TellerApp class, method TellerApp()
    //            URL:https://github.students.cs.ubc.ca/CPSC210/TellerApp.git

    // EFFECTS: run the management system
    public System() {
        runSystem();
    }


    // Citation: This method is inspired by Teller Project, TellerApp class, method runTeller()
    //            URL:https://github.students.cs.ubc.ca/CPSC210/TellerApp.git

    // MODIFIES: this
    // EFFECTS: loads expenseRecord from EXPENSE_RECORD_FILE, if that

    // MODIFIES: this
    // EFFECTS: process user input
    public void runSystem() {
        boolean keepRunning = true;
        String command;
        input = new Scanner(java.lang.System.in);

        selectLoad();

        while (keepRunning) {
            display();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                selectSave();
                keepRunning = false;
            } else {
                processCommand(command);
            }
        }

        java.lang.System.out.println("\nSee you next time!\n");
    }


    // MODIFIES: this
    // EFFECTS: select whether to load expenses from file
    // otherwise initializes empty expense record
    private void selectLoad() {
        String choice = "";

        while (!(choice.equals("t") || choice.equals("f"))) {
            java.lang.System.out.println("\nWelcome to your Budget Management System!");
            java.lang.System.out.println("\nDo You Want To Load A Expense Record From File?");
            java.lang.System.out.println("Select from:");
            java.lang.System.out.println("\tt -> Yes");
            java.lang.System.out.println("\tf -> No");
            choice = input.next();
            choice = choice.toLowerCase();
        }

        if (choice.equals("t")) {
            java.lang.System.out.println("\nPlease Type The File Name Of Your Record:");
            String file = input.next();
            String fileName = "./data/" + file + ".txt";
            this.file = fileName;
            loadRecord(fileName);
        } else {
            expRecord = new ExpenseRecord();
            java.lang.System.out.println("\nAn Empty Record Created\n");
        }
    }


    // Citation: This method is inspired by Teller Project, TellerApp class, method LoadAccounts()
    //            URL:https://github.students.cs.ubc.ca/CPSC210/TellerApp.git

    // MODIFIES: this
    // EFFECTS: loads expenses from EXPENSE_RECORD_FILE, if that file exists;
    // otherwise initializes empty expense record
    private void loadRecord(String fileName) {
        try {
            expRecord = new ExpenseRecord();
            ArrayList<Expense> expenses = Reader.readExpenses(new File(fileName));
            expRecord.setExpenseRecord(expenses);
            expRecord.setNumExpenses(expenses.size());
            double total = 0;
            for (int i = 0; i < expRecord.getExpenseRecord().size(); i++) {
                Expense exp = expRecord.getExpenseRecord().get(i);
                total = total + exp.getExpenseAmount();
            }
            expRecord.setTotalExpenseAmount(total);
            java.lang.System.out.println("\nRecord Loaded Successfully!\n");
        } catch (IOException e) {
            expRecord = new ExpenseRecord();
            java.lang.System.out.println("\nCannot Find This File, An Empty Record With Inserted Name Created\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: select whether to save expenses to file
    private void selectSave() {
        String choice = "";

        while (!(choice.equals("t") || choice.equals("n") || choice.equals("f"))) {
            java.lang.System.out.println("\nDo You Want To Save this Expense Record To File?");
            java.lang.System.out.println("Select from:");
            java.lang.System.out.println("\tt -> Save the Record To A New File");
            java.lang.System.out.println("\tn -> Save the Changes In This File");
            java.lang.System.out.println("\tf -> Do No Save");
            choice = input.next();
            choice = choice.toLowerCase();
        }

        if (choice.equals("t")) {
            java.lang.System.out.println("\nPlease Type The File Name Of Your Record:");
            String file = input.next();
            String fileName = "./data/" + file + ".txt";
            saveRecord(fileName);
        } else if (choice.equals("n")) {
            saveRecord(file);
            java.lang.System.out.println("\nNew Changes Successfully Saved!");
        } else {
            java.lang.System.out.println("\nGoodbye!\n");
        }
    }


    // EFFECTS: saves state of expense record to file
    private void saveRecord(String fileName) {
        try {
            Writer writer = new Writer(new File(fileName));
            for (int i = 0; i < expRecord.getExpenseRecord().size(); i++) {
                Expense exp = expRecord.getExpenseRecord().get(i);
                writer.write(exp);
            }
            writer.close();
            java.lang.System.out.println("Record saved to file " + fileName);
        } catch (FileNotFoundException e) {
            java.lang.System.out.println("Unable to save record to " + fileName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // this is due to a programming error
        }
    }


    // Citation: This method is inspired by Teller Project, TellerApp class, method displayMenu()
    //            URL:https://github.students.cs.ubc.ca/CPSC210/TellerApp.git

    // EFFECTS: displays menu of options to user
    private void display() {
        java.lang.System.out.println("\nWhat do you want to do next?");
        java.lang.System.out.println("\nSelect from:");
        java.lang.System.out.println("\ta -> Add A Expense To Your Record");
        java.lang.System.out.println("\tb -> View All Expenses In Your Record");
        java.lang.System.out.println("\tc -> View Monthly Summary");
        java.lang.System.out.println("\tq -> Quit The System");
    }


    // Citation: This method is inspired by Teller Project, TellerApp class, method processCommand()
    //            URL:https://github.students.cs.ubc.ca/CPSC210/TellerApp.git

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddExpense();
        } else if (command.equals("b")) {
            doViewRecord();
        } else if (command.equals("c")) {
            doViewMonthlySum();
        } else {
            java.lang.System.out.println("\nSelection Is Not Valid...\n");
        }
    }


    //MODIFIES: this
    //EFFECTS: add a expense to the expense record
    public void doAddExpense() {
        Expense exp;
        String expTitle = insertTitle();
        String expReceiver = insertReceiver();
        double expAmount = insertAmount();
        LocalDate expDate = insertDate();
        String expCategory = insertCategory();

        exp = new Expense(expTitle, expAmount, expReceiver, expDate, expCategory);
        expRecord.addExpense(exp);
        java.lang.System.out.println("\nNew Expense Added Successfully!\n");
    }


    //EFFECTS: return the title for the insertion
    public String insertTitle() {
        java.lang.System.out.println("\nPlease Type The Title Of The Expense (eg: Grocery)");
        String title = input.next();

        if (title.length() == 0) {
            java.lang.System.out.println("Title Saved As <Untitled>");
            return "Untitled";
        } else {
            return title;
        }
    }


    //EFFECTS: return the name of receiver for the insertion
    public String insertReceiver() {
        java.lang.System.out.println("\nPlease Type The Receiver Of The Expense (eg: Walmart)");
        String receiver = input.next();

        if (receiver.length() == 0) {
            java.lang.System.out.println("Receiver Saved As <Unknown>");
            return "Unknown";
        } else {
            return receiver;
        }

    }


    //EFFECTS: return the amount for the insertion if > 0, otherwise return 0.0
    public double insertAmount() {
        java.lang.System.out.println("\nPlease Type The Amount Of The Expense (positive value)");
        java.lang.System.out.print("\nEnter Amount To Record: $");
        double amount = input.nextDouble();

        if (amount < 0.0) {
            java.lang.System.out.println("\nCannot Record Negative Amount, Amount Saved as $0.0\n");
            return 0.0;
        } else {
            return amount;
        }

    }

    //EFFECTS: return the date for the insertion if valid, otherwise return current date
    public LocalDate insertDate() {
        java.lang.System.out.println("\nPlease Type The Year Of The Expense (eg: 2018)");
        int year = input.nextInt();
        java.lang.System.out.println("\nPlease Type The Month Of the Expense (eg: 09)");
        int month = input.nextInt();
        java.lang.System.out.println("\nPlease Type The Date Of the Expense (eg: 25)");
        int date = input.nextInt();

        if (year <= 1970 | year >= 2050 | date < 1 | date > 31 | month < 1 | month > 12) {
            java.lang.System.out.println("\nCannot Record This Date, Date Saved as Current Date");
            return LocalDate.now();
        } else {
            return LocalDate.of(year, month, date);
        }

    }

    //EFFECTS: return the category for the insertion
    public String insertCategory() {
        java.lang.System.out.println("\nPlease Type The Category Of The Expense (eg: Transportation)");
        String category = input.next();

        if (category.length() == 0) {
            java.lang.System.out.println("Category Saved As <Unclassified>");
            return "Unclassified";
        } else {
            return category;
        }

    }


    //MODIFIES: this
    //EFFECTS: view a list of titles and dates of all expenses in the record, and view a expense in detail
    public void doViewRecord() {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.FLOOR);
        String record = expRecord.viewTitleAndDateList();
        if (record.equals("")) {
            java.lang.System.out.println("\nThe Record Is Empty, Please Insert Information\n");
        } else {
            java.lang.System.out.println("\nNo. | Title | Date");
            java.lang.System.out.println(record);
            java.lang.System.out.println("Number Of Expenses Recorded: " + expRecord.getNumExpenses());
            java.lang.System.out.println("Total Amount Of Expenses In This Record: $"
                    + df.format(expRecord.getTotalExpenseAmount()));
            viewDetail();
        }
    }

    // Citation: This method is inspired by Teller Project, TellerApp class, method selectAccount()
    //            URL:https://github.students.cs.ubc.ca/CPSC210/TellerApp.git

    // EFFECTS: prompts user to select whether or not view a expense in detail
    public void viewDetail() {
        String queryTitle;
        LocalDate queryDate;
        String choice = "";

        while (!(choice.equals("t") || choice.equals("f"))) {
            java.lang.System.out.println("\nDo You Want To View A Expense In Detail?");
            java.lang.System.out.println("Select from:");
            java.lang.System.out.println("\tt -> Yes");
            java.lang.System.out.println("\tf -> No");
            choice = input.next();
            choice = choice.toLowerCase();
        }

        if (choice.equals("t")) {
            queryTitle = searchTitle();
            queryDate = searchDate();
            String exp = expRecord.viewSelectedExpense(queryTitle, queryDate);

            if (exp.equals("none")) {
                java.lang.System.out.println("\nCannot Find Your Selected Expense...\n");
            } else {
                java.lang.System.out.println("\n" + exp);
            }
        } else {
            java.lang.System.out.println("\nGoodbye!\n");
        }


    }


    // return the input as the title of expense for query
    public String searchTitle() {
        java.lang.System.out.println("\nPlease Type The Title Of The Selected Expense (eg: Grocery)");
        String title = input.next();

        if (title.length() == 0) {
            java.lang.System.out.println("Title Inserted As <Untitled>");
            return "Untitled";
        } else {
            return title;
        }
    }

    // return the input as the date of expense for query
    public LocalDate searchDate() {
        java.lang.System.out.println("\nPlease Type The Year Of The Selected Expense (eg: 2018)");
        int year = input.nextInt();
        java.lang.System.out.println("\nPlease Type The Month Of the Selected Expense (eg: 09)");
        int month = input.nextInt();
        java.lang.System.out.println("\nPlease Type The Date Of the Selected Expense (eg: 25)");
        int date = input.nextInt();

        if (year <= 1970 | year >= 2050 | date < 1 | date > 31 | month < 1 | month > 12) {
            return LocalDate.now();
        } else {
            return LocalDate.of(year, month, date);
        }

    }


    //MODIFIES: this
    //EFFECTS: produce a string showing total expense amount for a month
    public void doViewMonthlySum() {
        java.lang.System.out.println("\nPlease Type The Year Of Expense Incurred For The Query (eg: 2018)");
        int year = input.nextInt();
        java.lang.System.out.println("\nPlease Type The Month Of Expense Incurred For The Query (eg: 09)");
        int month = input.nextInt();
        double sum = expRecord.totalExpenseOfMonth(year, month);

        if (year <= 1970 | year >= 3000 | month < 1 | month > 12) {
            java.lang.System.out.println("\nThe Selected Time Period Is Invalid...\n");
        } else if (sum == 0) {
            java.lang.System.out.println("\nNo Expense Record Found, Or Total Expense Amount For This Month was $0.0");
        } else {
            java.lang.System.out.println("\nTotal Expense Amount in " + year + "-" + String.format("%02d", month)
                    + " was $" + sum + "\n");
        }

    }

}
