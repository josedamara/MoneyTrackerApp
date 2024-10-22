package persistence;

import model.CashFlow;
import model.MoneySummary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReaderTest extends JsonTest {
    List<String> expectedDebitCategories = new ArrayList<>();
    List<String> expectedCreditCategories = new ArrayList<>();
    List<String> expectedAccounts = new ArrayList<>();

    @BeforeEach
    void runBefore() {
        Collections.addAll(expectedDebitCategories, "Cheque", "Parents", "Other", "Salary", "Sport");
        Collections.addAll(expectedCreditCategories, "Education", "Grocery", "Hobby", "Other", "Sport");
        Collections.addAll(expectedAccounts, "ScotiaBank", "RBC");
    }

    @Test
    void testReaderInvalidFile() {
        JsonReader reader = new JsonReader("./data/invalidfile.json");
        try {
            MoneySummary ms = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReaderEmptyMoneySummary() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyMoneySummary.json");
        try {
            MoneySummary ms = reader.read();
            assertEquals("My Money Summary", ms.getName());
            assertEquals(0, ms.getCashflows().size());
            assertEquals(0, ms.getDebitCategories().size());
            assertEquals(0, ms.getCreditCategories().size());
            assertEquals(0, ms.getAccounts().size());
        } catch (IOException e) {
            fail("Couldn't read from the file specified");
        }
    }

    @Test
    void testReaderGeneralMoneySummary() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralMoneySummary.json");
        try {
            MoneySummary ms = reader.read();

            assertEquals("My Money Summary", ms.getName());

            List<CashFlow> cashflows = ms.getCashflows();
            assertEquals(3, cashflows.size());
            checkCashFlow("credit", "ScotiaBank", "Sport", "2024/01/01", "22:40", "Buying a new basketball", 50,
                    cashflows.get(0));
            checkCashFlow("debit", "RBC", "Salary", "2024/02/20", "11:00", "Monthly salary", 5000,
                    cashflows.get(1));
            checkCashFlow("credit", "ScotiaBank", "Hobby", "2020/05/21", "18:00", "Buying a new guitar", 500,
                    cashflows.get(2));

            List<String> debitCategories = ms.getDebitCategories();
            checkCategory(expectedDebitCategories, debitCategories);

            List<String> creditCategories = ms.getCreditCategories();
            checkCategory(expectedCreditCategories, creditCategories);

            List<String> accounts = ms.getDebitCategories();
            checkAccount(expectedAccounts, accounts);
        } catch (IOException e) {
            fail("Couldn't read from the file specified");
        }
    }
}
