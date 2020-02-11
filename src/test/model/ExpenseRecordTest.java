package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

//Unit tests for the ExpenseRecord Test
public class ExpenseRecordTest {

    ExpenseRecord testRecord;
    Expense e1;
    Expense e2;
    Expense e3;

    @BeforeEach
    void setUp() {
        testRecord = new ExpenseRecord();
        e1 = new Expense("Grocery", 8.69, "Walmart",
                LocalDate.of(2020,01,23), "Food");
        e2 = new Expense("November Rent", 1138.58, "UBC SHHS",
                LocalDate.of(2019,11,01), "Rent");
        e3 = new Expense("Car2go Car Rent", 42.30, "Car2go",
                LocalDate.of(2020,01,12), "Transportation");
    }

    @Test
    void testConstructor() {
        assertEquals(0, testRecord.getNumExpenses());
        assertEquals(0,testRecord.getTotalExpenseAmount());
    }

    @Test
    void testAddSingleExpense() {
        testRecord.addExpense(e1);
        assertEquals(1, testRecord.getNumExpenses());
        assertEquals(8.69, testRecord.getTotalExpenseAmount());
    }

    @Test
    void testAddMultipleExpenses() {
        testRecord.addExpense(e1);
        testRecord.addExpense(e2);
        testRecord.addExpense(e3);
        assertEquals(3, testRecord.getNumExpenses());
        assertEquals(8.69 + 1138.58 + 42.30, testRecord.getTotalExpenseAmount());
    }

    @Test
    void testViewSingleTitleList() {
        assertEquals("", testRecord.viewTitleAndDateList());
        testRecord.addExpense(e1);
        assertEquals("1. Grocery: 2020-01-23\n", testRecord.viewTitleAndDateList());
    }

    @Test
    void testViewMultipleTitleList() {
        testRecord.addExpense(e1);
        testRecord.addExpense(e2);
        testRecord.addExpense(e3);
        assertEquals("1. Grocery: 2020-01-23\n2. November Rent: 2019-11-01\n3. Car2go Car Rent: 2020-01-12\n",
                testRecord.viewTitleAndDateList());
    }

    @Test
    void testTotalExpenseOfMonth() {
        testRecord.addExpense(e1);
        testRecord.addExpense(e2);
        testRecord.addExpense(e3);
        assertEquals(8.69 + 42.30, testRecord.totalExpenseOfMonth(2020,01));
    }

    @Test
    void testViewSelectedExpense() {
        testRecord.addExpense(e1);
        testRecord.addExpense(e2);
        testRecord.addExpense(e3);
        assertEquals(e1.toString(), testRecord.viewSelectedExpense("Grocery",
                LocalDate.of(2020,01,23)));
        assertEquals("none", testRecord.viewSelectedExpense("Grocery",
                LocalDate.of(2020,02,23)));

    }
}