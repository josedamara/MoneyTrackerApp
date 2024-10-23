package persistence;

import model.CashFlow;
import model.MoneySummary;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Tests the JsonWriter class
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            MoneySummary ms = new MoneySummary();
            JsonWriter writer = new JsonWriter("./data/invalidfile.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriterEmptyMoneySumamry() {
        try {
            MoneySummary ms = new MoneySummary();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyMoneySummary.json");
            writer.open();
            writer.write(ms);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyMoneySummary.json");
            ms = reader.read();
            assertEquals("My Money Summary", ms.getName());
            assertEquals(0, ms.getCashflows().size());
            assertEquals(0, ms.getDebitCategories().size());
            assertEquals(0, ms.getCreditCategories().size());
            assertEquals(0, ms.getAccounts().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralMoneySummary() {
        try {
            MoneySummary ms = new MoneySummary();
            initMoneySummary(ms);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyMoneySummary.json");
            writer.open();
            writer.write(ms);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyMoneySummary.json");
            ms = reader.read();
            assertEquals("My Money Summary", ms.getName());
            List<CashFlow> cashflows = ms.getCashflows();
            assertEquals(3, ms.getCashflows().size());

            checkCashFlow("credit", "ScotiaBank", "Sport", "2024/01/01", "22:40", "Buying a new basketball", 50,
                    cashflows.get(0));
            checkCashFlow("debit", "RBC", "Salary", "2024/02/20", "10:00", "Monthly salary", 5000,
                    cashflows.get(1));
            checkCashFlow("credit", "ScotiaBank", "Hobby", "2020/05/21", "18:00", "Buying a new guitar", 500,
                    cashflows.get(2));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    void initMoneySummary(MoneySummary ms) {
        ms.addInitialCreditCategories();
        ms.addInitialDebitCategories();
        ms.addAccount("ScotiaBank");
        ms.addAccount("RBC");
        CashFlow cf1 = new CashFlow("credit", "ScotiaBank", "Sport", "2024/01/01", "22:40",
                "Buying a new basketball", 50);
        CashFlow cf2 = new CashFlow("debit", "RBC", "Salary", "2024/02/20", "10:00", "Monthly salary", 5000);
        CashFlow cf3 = new CashFlow("credit", "ScotiaBank", "Hobby", "2020/05/21", "18:00", "Buying a new guitar", 500);
        ms.addCashFlow(cf1);
        ms.addCashFlow(cf2);
        ms.addCashFlow(cf3);
    }
}
