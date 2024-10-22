package persistence;

import static org.junit.Assert.assertEquals;

import java.util.List;

import model.CashFlow;

public class JsonTest {
    protected void checkCategory(List<String> expectedCats, List<String> cats) {
        assertEquals(expectedCats.size(), cats.size());
        for (int i = 0; i < expectedCats.size(); i++) {
            assertEquals(expectedCats.get(i), cats.get(i));
        }
    }

    protected void checkAccount(List<String> expectedAccs, List<String> accs) {
        assertEquals(expectedAccs.size(), accs.size());
        for (int i = 0; i < expectedAccs.size(); i++) {
            assertEquals(expectedAccs.get(i), accs.get(i));
        }
    }

    protected void checkCashFlow(String status, String account, String category, String date, String time,
            String description, double amount, CashFlow cf) {
        assertEquals(status, cf.getStatus());
        assertEquals(account, cf.getAccount());
        assertEquals(category, cf.getCategory());
        assertEquals(date, cf.getDate());
        assertEquals(time, cf.getTime());
        assertEquals(description, cf.getDescription());
        assertEquals(amount, cf.getAmount(), 0.1f);
    }
}
