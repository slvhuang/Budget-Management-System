package ui.tools;

import model.Expense;
import ui.BudgetManageSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

// Represents a button and a panel after clicked allow user to add expense data
public class AddExpenseTool extends Tool {

    //Strings for the labels
    private static String titleString = "Expense Title: ";
    private static String amountString = "Expense Amount($): ";
    private static String receiverString = "Receiver: ";
    private static String paymentTimeString = "Payment Date (YYYY-MM-DD): ";
    private static String categoryString = "Category: ";
    //Labels to identify the fields
    private JLabel titleLabel;
    private JLabel amountLabel;
    private JLabel receiverLabel;
    private JLabel paymentTimeLabel;
    private JLabel categoryLabel;
    //Fields for data entry
    private JFormattedTextField titleField;
    private JFormattedTextField amountField;
    private JFormattedTextField receiverField;
    private JFormattedTextField paymentTimeField;
    private JFormattedTextField categoryField;

    //Formats to format and parse numbers
    private NumberFormat amountFormat;
    private DateFormat paymentDateFormat;

    public AddExpenseTool(BudgetManageSystem system, JComponent parent) {
        super(system, parent);
    }

    // MODIFIES: this
    // EFFECTS: create a JButton to add expense
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Add Expense");
        button = customizeButton(button);
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a new listener object which is added to the JButton
    @Override
    protected void addListener() {
        button.addActionListener(new AddExpenseToolClickHandler());
    }

    // EFFECTS: create a new formattedfilefield panel to allow insert expense detail
    private void createInsertPanel() {
        JFrame frame = new JFrame("Insert Expense Information");
        frame.add(new AddExpenseToolClickHandler());
        frame.pack();
        frame.setVisible(true);
    }


    private class AddExpenseToolClickHandler extends JPanel implements ActionListener {

        // EFFECTS: set up the text filed for inserting data
        public AddExpenseToolClickHandler() {
            super(new BorderLayout());
            setUpFormats();
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            createLabels();
            createTextField();
            tellAccessibility();
            layoutLabel();
            layoutTextFields();
            addButton();
        }

        // EFFECTS: sets active tool to the addexpense tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            createInsertPanel();
        }

        // MODIFIES: this
        // EFFECTS: set up the format for date and amount
        private void setUpFormats() {
            amountFormat = new DecimalFormat("#.##");
            paymentDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }


        // EFFECTS: add a JButton at the button of the text filed panel to add expense
        private void addButton() {
            JButton doneButton = new JButton("Add");
            doneButton.setBorderPainted(true);
            doneButton.setFocusPainted(true);
            doneButton.setContentAreaFilled(true);
            doneButton.addActionListener(new DoneButtonClickHandeler());
            add(doneButton, BorderLayout.SOUTH);
        }

        // MODIFIES: this
        // EFFECTS: construct labels
        private void createLabels() {
            titleLabel = new JLabel(titleString);
            amountLabel = new JLabel(amountString);
            receiverLabel = new JLabel(receiverString);
            paymentTimeLabel = new JLabel(paymentTimeString);
            categoryLabel = new JLabel(categoryString);
        }

        // MODIFIES: this
        // EFFECTS: construct text fields
        private void createTextField() {
            titleField = new JFormattedTextField();
            titleField.setColumns(10);
            amountField = new JFormattedTextField(amountFormat);
            amountField.setColumns(10);
            receiverField = new JFormattedTextField();
            receiverField.setColumns(10);
            paymentTimeField = new JFormattedTextField(paymentDateFormat);
            paymentTimeField.setColumns(10);
            categoryField = new JFormattedTextField();
            categoryField.setColumns(10);
        }


        // EFFECTS: match the label and the text fields
        private void tellAccessibility() {
            titleLabel.setLabelFor(titleField);
            amountLabel.setLabelFor(amountField);
            receiverLabel.setLabelFor(receiverField);
            paymentTimeLabel.setLabelFor(paymentTimeField);
            categoryLabel.setLabelFor(categoryField);
        }

        // EFFECTS: layout the labels in the panel
        public void layoutLabel() {
            JPanel labelPane = new JPanel(new GridLayout(0, 1));
            labelPane.add(titleLabel);
            labelPane.add(amountLabel);
            labelPane.add(receiverLabel);
            labelPane.add(paymentTimeLabel);
            labelPane.add(categoryLabel);
            add(labelPane, BorderLayout.CENTER);
        }

        // EFFECTS: layout the text fields in the panel
        public void layoutTextFields() {
            JPanel fieldPane = new JPanel(new GridLayout(0, 1));
            fieldPane.add(titleField);
            fieldPane.add(amountField);
            fieldPane.add(receiverField);
            fieldPane.add(paymentTimeField);
            fieldPane.add(categoryField);
            add(fieldPane, BorderLayout.LINE_END);
        }
    }


    private class DoneButtonClickHandeler extends JPanel implements ActionListener {

        // EFFECTS: sets active tool to the addexpense tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            addExpense();
        }

        //MODIFIES: this
        //EFFECTS: add a expense to the expense record
        private void addExpense() {
            try {
                Expense exp;
                String expTitle = insertTitle();
                String expReceiver = insertReceiver();
                double expAmount = insertAmount();
                LocalDate expDate = insertDate();
                String expCategory = insertCategory();

                exp = new Expense(expTitle, expAmount, expReceiver, expDate, expCategory);
                system.getExpRecord().addExpense(exp);
                String[] rowData = {Integer.toString(system.getExpRecord().getNumExpenses()),
                        exp.getExpenseTitle(), exp.getPaymentTime().toString()};
                system.getModel().addRow(rowData);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(new JFrame(), "Invalid Insertion");
            }

        }

        //EFFECTS: return the title for the insertion
        public String insertTitle() {
            String title = titleField.getText();

            if (title.length() == 0) {
                return "Untitled";
            } else {
                return title;
            }
        }


        //EFFECTS: return the name of receiver for the insertion
        public String insertReceiver() {
            String receiver = receiverField.getText();

            if (receiver.length() == 0) {
                return "Unknown";
            } else {
                return receiver;
            }

        }


        //EFFECTS: return the amount for the insertion if > 0, otherwise return 0.0
        public double insertAmount() {
            double amount = Double.parseDouble(amountField.getText());

            if (amount < 0.0) {
                return 0.0;
            } else {
                return amount;
            }

        }

        //EFFECTS: return the date for the insertion if valid, otherwise return current date
        public LocalDate insertDate() {
            try {
                LocalDate paymentTime = LocalDate.parse(paymentTimeField.getText());
                return paymentTime;
            } catch (DateTimeParseException e) {
                return LocalDate.now();
            }
        }

        //EFFECTS: return the category for the insertion
        public String insertCategory() {
            String category = categoryField.getText().toLowerCase();

            if (category.length() == 0) {
                return "Unclassified";
            } else {
                return category;
            }

        }

    }
}
