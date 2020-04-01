package ui;


import model.Expense;
import model.ExpenseRecord;
import persistence.Reader;
import ui.tools.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


// The GUI interface for the Budget Management System
public class BudgetManageSystem extends JFrame {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    public String file;
    public List<Tool> tools;
    public ExpenseRecord expRecord;
    String[][] data;
    JTable table;
    JPanel panel;
    JScrollPane scrollPane;
    DefaultTableModel model;
    ListSelectionModel listSelectionModel;

    // EFFECTS: constructor for the Budget Management System
    public BudgetManageSystem() {
        super("Budget Managament System");
        initializeFields();
        try {
            loadFile();
        } catch (Exception exc) {
            // do nothing
        }
        initializeGraphics();
    }

    public static void main(String[] args) {
        new BudgetManageSystem();
    }

    // getters
    public ExpenseRecord getExpRecord() {
        return expRecord;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public void setModel(DefaultTableModel model) {
        this.model = model;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    // MODIFIES: this
    // EFFECTS:  sets activeTool to null, and instantiates tools with ArrayList
    //           this method is called by the BudgetManageSystem constructor
    private void initializeFields() {
        file = "./data/untitled.txt";
        data = null;
        tools = new ArrayList<>();
        expRecord = new ExpenseRecord();
    }

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this BudgetManageSystem will operate
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        createTools();
        createDataField();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    // MODIFIES: this
    // EFFECTS:  a helper method which declares and instantiates all tools
    private void createTools() {
        JPanel toolArea = new JPanel();
        toolArea.setLayout(new GridLayout(0, 1));
        toolArea.setSize(new Dimension(0, 0));
        add(toolArea, BorderLayout.SOUTH);

        AddExpenseTool addExpenseTool = new AddExpenseTool(this, toolArea);
        tools.add(addExpenseTool);

        ViewMonthSumTool viewMonthSumTool = new ViewMonthSumTool(this, toolArea);
        tools.add(viewMonthSumTool);

        LoadRecordTool loadRecordTool = new LoadRecordTool(this, toolArea);
        tools.add(loadRecordTool);

        SaveRecordTool saveRecordTool = new SaveRecordTool(this, toolArea);
        tools.add(saveRecordTool);

    }


    //MODIFIES: this
    //EFFECTS: Create a panel and add components to it.
    private void createDataField() {
        panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Expense Record", TitledBorder.CENTER, TitledBorder.TOP));
        String[] header = {"No.", "Title", "Date"};
        model = new DefaultTableModel(header, 0);

        printRecord(expRecord);

        table = new JTable(model);
        addTableSelectionListener();
        scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
        add(panel, BorderLayout.CENTER);
        setSize(450, 400);
        setVisible(true);

    }

    //MODIFIES: this
    //EFFECTS: add listSelectionListener to table to allow selection
    private void addTableSelectionListener() {
        listSelectionModel = table.getSelectionModel();
        listSelectionModel.addListSelectionListener(new SharedListSelectionHandler());
        table.setSelectionModel(listSelectionModel);
    }

    //MODIFIES: this
    //EFFECTS: clean the data loaded to table
    public void cleanModel() {
        panel.remove(scrollPane);

        String[] header = {"No.", "Title", "Date"};
        model = new DefaultTableModel(header, 0);
        table = new JTable(model);
        addTableSelectionListener();
        scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
        add(panel, BorderLayout.CENTER);
        setSize(450, 400);
        setVisible(true);
    }


    // Citation: This method is inspired by Teller Project, TellerApp class, method LoadAccounts()
    //            URL:https://github.students.cs.ubc.ca/CPSC210/TellerApp.git

    // MODIFIES: this
    // EFFECTS: loads expenses from EXPENSE_RECORD_FILE, if that file exists;
    // otherwise initializes empty expense record
    public void loadRecord(String fileName) {
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
            ImageIcon icon = new ImageIcon("./data/smile.png");
            JOptionPane.showMessageDialog(new JFrame(), "Record Loaded Successfully!",
                    null, JOptionPane.INFORMATION_MESSAGE, icon);
        } catch (IOException e) {
            expRecord = new ExpenseRecord();
            JOptionPane.showMessageDialog(new JFrame(),
                    "Cannot Find This File, An Empty Record With Inserted Name Created", "Inane warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    //MODIFIES: this
    //EFFECTS: create a new Jfilechooser before program start to allow load previous file
    public void loadFile() {
        JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(this);
        String file = fc.getSelectedFile().getName();
        this.setFile("./data/" + file);
        this.loadRecord(this.getFile());
        printRecord(expRecord);
    }

    public void printRecord(ExpenseRecord expRecord) {
        for (int i = 0; i < expRecord.getExpenseRecord().size(); i++) {
            Expense e = expRecord.getExpenseRecord().get(i);
            int num = i + 1;
            String[] rowData = {Integer.toString(num), e.getExpenseTitle(), e.getPaymentTime().toString()};
            model.addRow(rowData);
        }
    }


    private class SharedListSelectionHandler implements ListSelectionListener {


        //EFFECTS: return the expense detail dialog when a row is selected
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();

            if (lsm.isSelectionEmpty()) {
                //output
            } else {
                // Find out which indexes are selected.
                int minIndex = lsm.getMinSelectionIndex();
                int maxIndex = lsm.getMaxSelectionIndex();
                for (int i = minIndex; i <= maxIndex; i++) {
                    if (lsm.isSelectedIndex(i)) {
                        Expense exp = expRecord.getExpenseRecord().get(i);
                        String detail = exp.toString();
                        JOptionPane.showMessageDialog(new JFrame(), detail);
                    }
                }
            }
        }
    }
}


