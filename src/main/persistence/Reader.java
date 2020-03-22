package persistence;

import model.Expense;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Citation: This class is inspired by Teller Project, Reader class
//            URL:https://github.students.cs.ubc.ca/CPSC210/TellerApp.git

// Represents a reader that can read expense data from a file
public class Reader {
    public static final String DELIMITER = ",";

    // EFFECTS: returns a list of expenses parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static ArrayList<Expense> readExpenses(File file) throws IOException {
        List<String> fileContent = readFile(file);
        return parseContent(fileContent);
    }

    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // EFFECTS: returns a list of expenses parsed from list of strings
    // where each string contains data for one expense
    private static ArrayList<Expense> parseContent(List<String> fileContent) {
        ArrayList<Expense> expenses = new ArrayList<>();

        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitString(line);
            expenses.add(parseExpense(lineComponents));
        }

        return expenses;
    }

    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }

    // REQUIRES: components has size 5 where element 0 represents the
    // expeseTitle of the next expense to be constructed, element 1 represents
    // the expense amount, elements 2 represents the name of receiver, element 3 represents
    // the payment date and element 4 represents the category
    //
    // EFFECTS: returns an expense constructed from components
    private static Expense parseExpense(List<String> components) {
        String expenseTitle = components.get(0);
        double expenseAmount = Double.parseDouble(components.get(1));
        String receiver = components.get(2);
        LocalDate paymentTime = LocalDate.parse(components.get(3));
        String category = components.get(4);
        return new Expense(expenseTitle, expenseAmount, receiver, paymentTime, category);
    }
}
