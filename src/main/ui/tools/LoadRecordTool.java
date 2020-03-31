package ui.tools;

import model.Expense;
import ui.BudgetManageSystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a button and a panel after clicked allow user to add load new data
public class LoadRecordTool extends Tool {

    public LoadRecordTool(BudgetManageSystem system, JComponent parent) {
        super(system, parent);
    }

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Load Record");
        button = customizeButton(button);
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a new listener object which is added to the JButton
    @Override
    protected void addListener() {
        button.addActionListener(new LoadRecordTool.LoadRecordToolClickHandler());
    }

    // MODIFIES: this
    // EFFECTS: create a file chooser panel to load data in file to system
    public void loadFile() {
        JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(system);
        String file = fc.getSelectedFile().getName();
        system.setFile("./data/" + file);
        system.loadRecord(system.getFile());
        system.cleanModel();
        for (int i = 0; i < system.getExpRecord().getExpenseRecord().size(); i++) {
            Expense e = system.getExpRecord().getExpenseRecord().get(i);
            int num = i + 1;
            String[] rowData = {Integer.toString(num), e.getExpenseTitle(), e.getPaymentTime().toString()};
            system.getModel().addRow(rowData);
        }
    }

    private class LoadRecordToolClickHandler implements ActionListener {

        // EFFECTS: sets active tool to the load expense tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                loadFile();
            } catch (Exception exc) {
                // do nothing
            }
        }
    }

}
