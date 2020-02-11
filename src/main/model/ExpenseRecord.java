package model;

import com.sun.xml.internal.ws.resources.UtilMessages;

import java.util.ArrayList;
import java.time.LocalDate;

// Represents a expense record have a list of expenses
public class ExpenseRecord {

    ArrayList<Expense> expenseRecord;
    int numExpenses;
    double totalExpenseAmount;

    //EFFECTS: constructs an empty expense record
    public ExpenseRecord() {
        expenseRecord = new ArrayList<>();
        numExpenses = 0;
        totalExpenseAmount = 0;
    }


    //methods

    //MODIFIES: this
    //EFFECTS: add a expense to the expense record
    public void addExpense(Expense expense) {
        expenseRecord.add(expense);
        numExpenses = numExpenses + 1;
        totalExpenseAmount = totalExpenseAmount + expense.getExpenseAmount();
    }

    //EFFECTS: produce a numbered list of the titles and dates of expenses in the expense record
    public String viewTitleAndDateList() {
        String build = "";
        for (int i = 0; i < expenseRecord.size(); i++) {
            Expense e = expenseRecord.get(i);
            int num = i + 1;
            build = build + num + ". " + format(e);
        }
        return build;
    }

    // EFFECTS: return a line of string for a single expense's title and date
    public String format(Expense e) {
        return e.getExpenseTitle() + ": " + e.getPaymentTime().toString() + "\n";
    }


    //EFFECTS: return the total amount of the expenses whose transaction happens within the given month and year
    public double totalExpenseOfMonth(int y, int m) {
        double initial = 0;
        for (int i = 0; i < expenseRecord.size(); i++) {
            Expense exp = expenseRecord.get(i);
            if (exp.withinMonth(y,m)) {
                initial = initial + exp.getExpenseAmount();
            }
        }
        return initial;
    }


    //EFFECTS: return a string represents a expense which have the same title and payment time,
    //         otherwise return false
    public String viewSelectedExpense(String title, LocalDate date) {
        for (Expense e: expenseRecord) {
            if (e.getExpenseTitle() == title) {
                if (e.getPaymentTime().isEqual(date)) {
                    return e.toString();
                }
            }
        }
        return "none";
    }

    // return total number of expenses of the expense record
    public int getNumExpenses() {
        return expenseRecord.size();
    }

    // return total amount of expenses of the expense record
    public double getTotalExpenseAmount() {
        return totalExpenseAmount;
    }



}
