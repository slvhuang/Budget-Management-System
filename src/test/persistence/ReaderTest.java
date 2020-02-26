package persistence;

import model.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Unit test for Reader class
class ReaderTest {
    private Reader testReader;

    @BeforeEach
    void runBefore() {
        testReader = new Reader();
    }

    @Test
    void testParseExpensesFile1() {
        try {
            ArrayList<Expense> expenses = testReader.readExpenses(new File("./data/testFile1.txt"));
            Expense exp1 = expenses.get(0);
            assertEquals("Grocery Shopping", exp1.getExpenseTitle());
            assertEquals(23.8, exp1.getExpenseAmount());
            assertEquals("Walmart", exp1.getReceiver());
            assertEquals(LocalDate.of(2020, 01, 23), exp1.getPaymentTime());
            assertEquals("Food", exp1.getCategory());

            Expense exp2 = expenses.get(1);
            assertEquals("Feb Housing", exp2.getExpenseTitle());
            assertEquals(1138.58, exp2.getExpenseAmount());
            assertEquals("UBC SHHS", exp2.getReceiver());
            assertEquals(LocalDate.of(2020, 02, 01), exp2.getPaymentTime());
            assertEquals("Rent", exp2.getCategory());

            Expense exp3 = expenses.get(2);
            assertEquals("ECON Textbook", exp3.getExpenseTitle());
            assertEquals(113.7, exp3.getExpenseAmount());
            assertEquals("Pearson", exp3.getReceiver());
            assertEquals(LocalDate.of(2019, 12, 31), exp3.getPaymentTime());
            assertEquals("Book", exp3.getCategory());

            Expense exp4 = expenses.get(3);
            assertEquals("Car2go car rent", exp4.getExpenseTitle());
            assertEquals(42.17, exp4.getExpenseAmount());
            assertEquals("Car2go", exp4.getReceiver());
            assertEquals(LocalDate.of(2020, 01, 9), exp4.getPaymentTime());
            assertEquals("Transportation", exp4.getCategory());

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testIOException() {
        try {
            testReader.readExpenses(new File("./path/does/not/exist/testAccount.txt"));
        } catch (IOException e) {
            // expected
        }
    }
}