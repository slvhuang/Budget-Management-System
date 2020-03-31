package ui.tools;

import ui.BudgetManageSystem;

import javax.swing.*;

// Citation: the structure and some methods of this class and its subclasses are inspired by the "Tool" class
// in the DrawingPlayer Project of this course

// Represents a abstract class Tool
public abstract class Tool {

    protected JButton button;
    protected BudgetManageSystem system;

    public Tool(BudgetManageSystem system, JComponent parent) {
        this.system = system;
        createButton(parent);
        addToParent(parent);
        addListener();
    }

    // MODIFIES: this
    // EFFECTS: customizes the button used for this tool
    protected JButton customizeButton(JButton button) {
        button.setBorderPainted(true);
        button.setFocusPainted(true);
        button.setContentAreaFilled(true);
        return button;
    }

    // EFFECTS: creates button to activate tool
    protected abstract void createButton(JComponent parent);

    // EFFECTS: adds a listener for this tool
    protected abstract void addListener();

    // MODIFIES: parent
    // EFFECTS:  adds the given button to the parent component
    public void addToParent(JComponent parent) {
        parent.add(button);
    }

}
