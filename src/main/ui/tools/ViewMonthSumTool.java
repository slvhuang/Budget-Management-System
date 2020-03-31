package ui.tools;

import ui.BudgetManageSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

// Represents a button and a panel after clicked allow user to search monthly data
public class ViewMonthSumTool extends Tool {

    //Strings for the labels
    private static String queryYearString = "Year (YYYY): ";
    private static String queryMonthString = "Month (MM): ";
    //Labels to identify the fields
    private JLabel yearLabel;
    private JLabel monthLabel;
    //Fields for data entry
    private JFormattedTextField monthField;
    private JFormattedTextField yearField;

    //Formats to format and parse numbers
    private DateFormat yearFormat;
    private DateFormat monthFormat;

    public ViewMonthSumTool(BudgetManageSystem system, JComponent parent) {
        super(system, parent);
    }

    // MODIFIES: this
    // EFFECTS: create a JButton to view monthly summary
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("View Monthly Summary");
        button = customizeButton(button);
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a new listener object which is added to the JButton
    @Override
    protected void addListener() {
        button.addActionListener(new ViewMonthSumTool.ViewMonthSumToolClickHandler());
    }


    // EFFECTS: construct a new text filed for insert month and year when the button is clicked
    private void createQueryPanel() {
        JFrame frame = new JFrame("Insert Interested Date");
        frame.add(new ViewMonthSumToolClickHandler());
        frame.pack();
        frame.setVisible(true);
    }

    private class ViewMonthSumToolClickHandler extends JPanel implements ActionListener {

        // EFFECTS: set up the text filed for inserting query information
        public ViewMonthSumToolClickHandler() {
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

        // EFFECTS: sets active tool to the view month sum tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            createQueryPanel();
        }


        // EFFECTS: set up the format for year and month
        private void setUpFormats() {
            yearFormat = new SimpleDateFormat("yyyy");
            monthFormat = new SimpleDateFormat("MM");
        }

        // EFFECTS: add a JButton at the button of the text filed panel to produce monthly sum
        private void addButton() {
            JButton doneButton = new JButton("Search");
            doneButton.setBorderPainted(true);
            doneButton.setFocusPainted(true);
            doneButton.setContentAreaFilled(true);
            doneButton.addActionListener(new ViewMonthSumTool.DoneButtonClickHandeler());
            add(doneButton, BorderLayout.SOUTH);
        }

        // MODIFIES: this
        // EFFECTS: construct labels
        private void createLabels() {
            yearLabel = new JLabel(queryYearString);
            monthLabel = new JLabel(queryMonthString);
        }

        // MODIFIES: this
        // EFFECTS: construct text fields
        private void createTextField() {
            yearField = new JFormattedTextField(yearFormat);
            yearField.setColumns(10);
            monthField = new JFormattedTextField(monthFormat);
            monthField.setColumns(10);
        }

        // EFFECTS: match the label and the text fields
        private void tellAccessibility() {
            yearLabel.setLabelFor(yearField);
            monthLabel.setLabelFor(monthField);
        }

        // EFFECTS: layout the labels in the panel
        public void layoutLabel() {
            JPanel labelPane = new JPanel(new GridLayout(0, 1));
            labelPane.add(yearLabel);
            labelPane.add(monthLabel);
            add(labelPane, BorderLayout.CENTER);
        }

        // EFFECTS: layout the text fields in the panel
        public void layoutTextFields() {
            JPanel fieldPane = new JPanel(new GridLayout(0, 1));
            fieldPane.add(yearField);
            fieldPane.add(monthField);
            add(fieldPane, BorderLayout.LINE_END);
        }
    }

    private class DoneButtonClickHandeler extends JPanel implements ActionListener {

        // EFFECTS: sets active tool to the addexpense tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            viewMonthSum();
        }

        // EFFECTS: produce a new dialog about monthly sum information
        private void viewMonthSum() {
            try {
                DecimalFormat df = new DecimalFormat("#.##");
                df.setRoundingMode(RoundingMode.FLOOR);
                int year = Integer.parseInt(yearField.getText());
                int month = Integer.parseInt(monthField.getText());
                double sum = system.getExpRecord().totalExpenseOfMonth(year, month);

                if (sum == 0) {
                    JOptionPane.showMessageDialog(new JFrame(), "No Expense Record Found, "
                            + "Or Total Expense Amount For This Month was $0.0");
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Total Expense Amount in " + year + "-"
                            + String.format("%02d", month) + " was $" + df.format(sum));
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(new JFrame(), "Invalid Insertion");
            }
        }

    }

}
