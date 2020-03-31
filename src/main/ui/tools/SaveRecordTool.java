package ui.tools;

import model.Expense;
import persistence.Writer;
import ui.BudgetManageSystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import static javax.swing.JOptionPane.YES_OPTION;

// Represents a button and a panel after clicked allow user to save expense data to file
public class SaveRecordTool extends Tool {

    public SaveRecordTool(BudgetManageSystem system, JComponent parent) {
        super(system, parent);
    }

    // MODIFIES: this
    // EFFECTS: create a JButton to save expense data to file
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Save Record");
        button = customizeButton(button);
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a new listener object which is added to the JButton
    @Override
    protected void addListener() {
        button.addActionListener(new SaveRecordTool.SaveRecordToolClickHandler());
    }

    private void saveFile() {
        JFrame frame = new JFrame();
        Object[] options = {"Save To A New File",
                "Save To This File"};
        int n = JOptionPane.showOptionDialog(frame,
                "Do You Want To Save this Expense Record To File?",
                "Save Record",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);

        if (n == YES_OPTION) {
            saveNewFileDialog();
        } else {
            saveRecord(system.getFile());
        }
    }

    // EFFECTS: create a dialog to allow user choose save data mode
    private void saveNewFileDialog() {
        JFrame frame = new JFrame();
        String s = (String) JOptionPane.showInputDialog(
                frame,
                "Please Type The File Name",
                "Save To New File",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "record");

        //If a string was returned, say so.
        if ((s != null) && (s.length() > 0)) {
            String fileName = "./data/" + s + ".txt";
            saveRecord(fileName);
        }
    }

    // EFFECTS: saves state of expense record to file
    private void saveRecord(String fileName) {
        try {
            Writer writer = new Writer(new File(fileName));
            for (int i = 0; i < system.getExpRecord().getExpenseRecord().size(); i++) {
                Expense exp = system.getExpRecord().getExpenseRecord().get(i);
                writer.write(exp);
            }
            writer.close();
            JOptionPane.showMessageDialog(new JFrame(), "Record saved to file " + fileName);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Unable to save record to " + fileName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // this is due to a programming error
        }
    }

    private class SaveRecordToolClickHandler implements ActionListener {

        // EFFECTS: sets active tool to the save record tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            saveFile();
        }
    }

}
