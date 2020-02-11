package model;

import java.time.LocalDate;

// Represents a expense having a:
//  - title
//  - amount(in dollars)
//  - payment time (localtime)
//  - category
//  - receiver name

public class Expense {
    private String expenseTitle;
    private double expenseAmount;
    private String receiver;
    private LocalDate paymentTime;
    private String category;

    // REQUIRES: expenseAmount > 0
    // EFFECTS: construct an expense with a title, amount(in dollars)，receiver name, payment time, and category
    public Expense(String expenseTitle, double expenseAmount, String receiver,
                   LocalDate paymentTime, String category) {
        this.expenseTitle = expenseTitle;
        this.expenseAmount = expenseAmount;
        this.receiver = receiver;
        this.paymentTime = paymentTime;
        this.category = category;

    }

    // methods

    //REQUIRES: y must have 4 digits, m >= 1 and <= 12
    //EFFECTS: produce true if the expense is recorded as year y and month m
    public boolean withinMonth(int y, int m) {
        int month = paymentTime.getMonthValue();
        int year = paymentTime.getYear();
        if (year == y) {
            if (month == m) {
                return true;
            }
        }
        return false;
    }

    //EFFECTS: return a string represents a expense
    public String toString() {
        return expenseTitle + ": $" + expenseAmount
                + ", receiver: " + receiver
                + ", time: " + paymentTime.toString()
                + ", category: " + category + "\n";
    }



    // getters
    public String getExpenseTitle() {
        return expenseTitle;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public String getReceiver() {
        return receiver;
    }

    public LocalDate getPaymentTime() {
        return paymentTime;
    }

    public String getCategory() {
        return category;
    }


    // setters
    public void setExpenseTitle(String expenseTitle) {
        this.expenseTitle = expenseTitle;
    }

    public void setExpenseAmount(double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setPaymentTime(LocalDate paymentTime) {
        this.paymentTime = paymentTime;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
