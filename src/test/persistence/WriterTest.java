package persistence;

import model.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//Unit test for Writer class
class WriterTest {
    private static final String TEST_FILE = "./data/testFile2.txt";
    private Writer testWriter;
    private Expense exp1;
    private Expense exp2;
    private Expense exp3;
    private ArrayList<Expense> expRecord;

    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        testWriter = new Writer(new File(TEST_FILE));
        exp1 = new Expense("Grocery Shopping",
                23.8,
                "Walmart",
                LocalDate.of(2020, 01, 23),
                "Food");

        exp2 = new Expense("Feb Housing",
                1138.58,
                "UBC SHHS",
                LocalDate.of(2020, 02, 01),
                "Rent");

        exp3 = new Expense("ECON Textbook",
                113.7,
                "Pearson",
                LocalDate.of(2019, 12, 31), "Book");

        expRecord = new ArrayList<>();
        expRecord.add(exp1);
        expRecord.add(exp2);
        expRecord.add(exp3);

    }

    @Test
    void testWriteAccounts() {
        // save newly added expenses to file
        for (int i = 0; i < expRecord.size(); i++) {
            Expense exp = expRecord.get(i);
            testWriter.write(exp);
        }
        testWriter.close();

        // now read them back in and verify
        try {
            ArrayList<Expense> expenses = Reader.readExpenses(new File(TEST_FILE));
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

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
