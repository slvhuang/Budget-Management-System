package ui.tools;

import ui.BudgetManageSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("View Monthly Summary");
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a new listener object which is added to the JButton
    @Override
    protected void addListener() {
        button.addActionListener(new ViewMonthSumTool.ViewMonthSumToolClickHandler());
    }

    private void createQueryPanel() {
        JFrame frame = new JFrame("Insert Interested Date");
        frame.add(new ViewMonthSumToolClickHandler());
        frame.pack();
        frame.setVisible(true);
    }

    private class ViewMonthSumToolClickHandler extends JPanel implements ActionListener {

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

        // EFFECTS: sets active tool to the addexpense tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            createQueryPanel();
        }

        private void setUpFormats() {
            yearFormat = new SimpleDateFormat("yyyy");
            monthFormat = new SimpleDateFormat("MM");
        }

        private void addButton() {
            JButton doneButton = new JButton("Search");
            doneButton.setBorderPainted(true);
            doneButton.setFocusPainted(true);
            doneButton.setContentAreaFilled(true);
            doneButton.addActionListener(new ViewMonthSumTool.DoneButtonClickHandeler());
            add(doneButton, BorderLayout.SOUTH);
        }

        private void createLabels() {
            yearLabel = new JLabel(queryYearString);
            monthLabel = new JLabel(queryMonthString);
        }

        private void createTextField() {
            yearField = new JFormattedTextField(yearFormat);
            yearField.setColumns(10);
            monthField = new JFormattedTextField(monthFormat);
            monthField.setColumns(10);
        }

        private void tellAccessibility() {
            yearLabel.setLabelFor(yearField);
            monthLabel.setLabelFor(monthField);
        }

        public void layoutLabel() {
            JPanel labelPane = new JPanel(new GridLayout(0, 1));
            labelPane.add(yearLabel);
            labelPane.add(monthLabel);
            add(labelPane, BorderLayout.CENTER);
        }

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


        private void viewMonthSum() {
            try {
                int year = Integer.parseInt(yearField.getText());
                int month = Integer.parseInt(monthField.getText());
                double sum = system.getExpRecord().totalExpenseOfMonth(year, month);

                if (sum == 0) {
                    JOptionPane.showMessageDialog(new JFrame(), "No Expense Record Found, "
                            + "Or Total Expense Amount For This Month was $0.0");
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Total Expense Amount in " + year + "-"
                            + String.format("%02d", month) + " was $" + sum);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(new JFrame(), "Invalid Insertion");
            }
        }

    }

}
