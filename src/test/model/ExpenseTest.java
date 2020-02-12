package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

//Unit tests for the Expense class
class ExpenseTest {
    private Expense testExpense;

    @BeforeEach
    void setTestExpense() {
        testExpense = new Expense("Grocery", 8.69, "Walmart",
                LocalDate.of(2020,01,23), "Food");
    }

    @Test
    void testConstructor() {
        assertEquals("Grocery", testExpense.getExpenseTitle());
        assertEquals(8.69, testExpense.getExpenseAmount());
        assertEquals("Walmart", testExpense.getReceiver());
        assertEquals(LocalDate.of(2020,01,23), testExpense.getPaymentTime());
        assertEquals("Food", testExpense.getCategory());
    }

    @Test
    void testWithinMonth() {
        assertTrue(testExpense.withinMonth(2020,01));
        assertFalse(testExpense.withinMonth(2019,12));
        assertFalse(testExpense.withinMonth(2020,02));
        assertFalse(testExpense.withinMonth(2019,01));
    }

    @Test
    void testToString() {
        assertEquals("Grocery: $8.69, receiver: Walmart, time: 2020-01-23, category: Food\n",
                testExpense.toString());
    }

}
