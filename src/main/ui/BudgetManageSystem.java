package ui;


import model.Expense;
import model.ExpenseRecord;

import java.time.LocalDate;
import java.util.Scanner;

// Budget Management System
public class BudgetManageSystem {


    private ExpenseRecord expenseRecord = new ExpenseRecord();
    private Scanner input;


    // EFFECTS: run the management system
    public BudgetManageSystem() {
        runSystem();
    }


    // MODIFIES: this
    // EFFECTS: process user input
    public void runSystem() {
        boolean keepRunning = true;
        String command = null;
        input = new Scanner(System.in);

        while (keepRunning) {
            display();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("d")) {
                keepRunning = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nSee you next time!");
    }

    // EFFECTS: displays menu of options to user
    private void display() {
        System.out.println("\nWelcome to your Budget Management System!\nWhat do you want to do next?");
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Add a Expense Record");
        System.out.println("\tb -> View All Expense Record");
        System.out.println("\tc -> View Monthly Summary");
        System.out.println("\td -> Quit the System");
    }

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
            System.out.println("Selection Not Valid");
        }
    }

    //MODIFIES:
    //EFFECTS:
    public void doAddExpense() {
        Expense exp;
        String expTitle = insertTitle();
        String expReceiver = insertReceiver();
        Double expAmount;
        LocalDate expDate;
        String expCategory = insertCategory();

        insertAmount();
        expAmount = insertAmount();

        insertDate();
        expDate = insertDate();

        exp = new Expense(expTitle,expAmount,expReceiver,expDate,expCategory);
        expenseRecord.addExpense(exp);
        System.out.println("New Expense Added Successfully!\n");
    }


    //EFFECTS:
    public String insertTitle() {
        System.out.println("\nPlease Type The Title Of The Expense (eg: Grocery)");
        String title = input.next();

        if (title.length() == 0) {
            System.out.println("Title Saved As <Untitled>");
            return "Untitled";
        } else {
            return title;
        }
    }


    //EFFECTS:
    public String insertReceiver() {
        System.out.println("\nPlease Type The Receiver Of The Expense (eg: UBC SHHS)");
        String receiver = input.next();

        if (receiver.length() == 0) {
            System.out.println("Receiver Saved As <Unknown>");
            return "Unknown";
        } else {
            return receiver;
        }

    }


    //EFFECTS:
    public double insertAmount() {
        System.out.println("\nPlease Type The Amount Of The Expense (positive value)");
        System.out.print("Enter Amount To Record: $");
        double amount = input.nextDouble();

        if (amount < 0.0) {
            System.out.println("Cannot Record Negative Amount...\n");
            return 0.0;
        } else {
            return amount;
        }

    }

    //EFFECTS:
    public LocalDate insertDate() {
        System.out.println("\nPlease Type The Year Of The Expense (eg: 2018)");
        int year = input.nextInt();
        System.out.println("\nPlease Type The Month Of the Expense (eg: 09)");
        int month = input.nextInt();
        System.out.println("\nPlease Type The Date Of the Expense (eg: 25)");
        int date = input.nextInt();

        if (year <= 1970 | year >= 2050 | date < 1 | date > 31 | month < 1 | month > 12) {
            System.out.println("Cannot Record This Date...\n");
            return LocalDate.now();
        } else {
            return LocalDate.of(year,month,date);
        }

    }

    //EFFECTS:
    public String insertCategory() {
        System.out.println("\nPlease Type The Category Of The Expense (eg: Transportation)");
        String category = input.next();

        if (category.length() == 0) {
            System.out.println("Category Saved As <Unclassified>");
            return "Unclassified";
        } else {
            return category;
        }

    }


    //MODIFIES:
    //EFFECTS:
    public void doViewRecord() {
        String record = expenseRecord.viewTitleAndDateList();
        if (record == "") {
            System.out.println("The Record Is Empty, Please Insert Information");
        } else {
            System.out.println(record);
        }
    }


    //MODIFIES:
    //EFFECTS:
    public void doViewMonthlySum() {
        System.out.println("\n Please Enter The Year For The Query (eg: 2018)");
        int year = input.nextInt();
        System.out.println("\n Please Enter The Month For The Query (eg: 09)");
        int month = input.nextInt();
        double sum = expenseRecord.totalExpenseOfMonth(year,month);

        if (year <= 1970 | year >= 3000 |  month < 1 | month > 12) {
            System.out.println("The Selected Period Is Invalid...\n");
        } else if (sum == 0) {
            System.out.println("Recorded Total Expense Amount For This Month is Zero\n");
        } else {
            System.out.print(sum);
        }

    }

}
