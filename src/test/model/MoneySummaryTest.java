package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MoneySummaryTest {
    private MoneySummary testMoneySummary;
    private CashFlow cf1;
    private CashFlow cf2;
    private CashFlow cf3;
    

    @BeforeEach
    void runBefore() {
        testMoneySummary = new MoneySummary();
        cf1 = new CashFlow("credit", "ScotiaBank", "Sport", "2024/01/01",
        "22:40", "Buying a new basketball");
        cf2 = new CashFlow("debit", "RBC", "Salary", "2024/02/20",
        "10:00", "Monthly salary");
        cf3 = new CashFlow("credit", "ScotiaBank", "Hobby", "2020/05/21",
        "18:00", "Buying a new guitar");
    }

    @Test
    void testConstructor() {
        assertEquals(testMoneySummary.getCashflows().size(), 0);
        assertEquals(testMoneySummary.getDebitCategories().size(), 5);
        assertEquals(testMoneySummary.getCreditCategories().size(), 5);
        assertEquals(testMoneySummary.getAccounts().size(), 0);
    }

    @Test
    void testAddSingleCashFlow() {
        addSingleCashFlow();
        assertEquals(testMoneySummary.getCashflows().size(), 1);
        assertEquals(testMoneySummary.getCashflows().get(0), cf1);
    }

    @Test
    void testAddMultipleCashFlow() {
        addMultipleCashFlow();
        assertEquals(testMoneySummary.getCashflows().size(), 3);
        assertEquals(testMoneySummary.getCashflows().get(0), cf1);
        assertEquals(testMoneySummary.getCashflows().get(1), cf2);
    }

    @Test
    void testDeleteSingleCashFlow() {
        addSingleCashFlow();
        assertEquals(testMoneySummary.getCashflows().size(), 1);
        testMoneySummary.deleteCashFlow(cf1);
        assertEquals(testMoneySummary.getCashflows().size(), 0);
    }

    @Test
    void testDeleteMultipleCashFlow() {
        addMultipleCashFlow();
        assertEquals(testMoneySummary.getCashflows().size(), 3);
        deleteMultipleCashFlow();
        assertEquals(testMoneySummary.getCashflows().size(), 1);
    }

    @Test
    void testAddDebitCategory() {
        testMoneySummary.addDebitCategory("Freelance");
        assertEquals(testMoneySummary.getDebitCategories().size(), 6);
        testMoneySummary.addDebitCategory("Online Shop");
        assertEquals(testMoneySummary.getDebitCategories().size(), 7);
    }

    @Test
    void testDeleteDebitCategory() {
        testMoneySummary.deleteDebitCategory("Salary");
        assertEquals(testMoneySummary.getDebitCategories().size(), 4);
        testMoneySummary.deleteDebitCategory("Cheque");
        assertEquals(testMoneySummary.getDebitCategories().size(), 3);
    }

    @Test
    void testAddCreditCategory() {
        testMoneySummary.addCreditCategory("Games");
        assertEquals(testMoneySummary.getCreditCategories().size(), 6);
        testMoneySummary.addCreditCategory("Food");
        assertEquals(testMoneySummary.getCreditCategories().size(), 7);
    }

    @Test
    void testDeleteCreditCategory() {
        testMoneySummary.deleteCreditCategory("Education");
        assertEquals(testMoneySummary.getCreditCategories().size(), 4);
        testMoneySummary.deleteCreditCategory("Hobby");
        assertEquals(testMoneySummary.getCreditCategories().size(), 3);
    }

    @Test
    void testAddAccount() {
        testMoneySummary.addAccount("RBC");
        assertEquals(testMoneySummary.getAccounts().size(), 1);
        testMoneySummary.addAccount("ScotiaBank");
        assertEquals(testMoneySummary.getAccounts().size(), 2);
    }

    @Test
    void testDeleteAccount() {
        testMoneySummary.addAccount("RBC");
        testMoneySummary.addAccount("ScotiaBank");
        assertEquals(testMoneySummary.getAccounts().size(), 2);
        testMoneySummary.deleteAccount("ScotiaBank");
        assertEquals(testMoneySummary.getAccounts().size(), 1);
        testMoneySummary.deleteAccount("RBC");
        assertEquals(testMoneySummary.getAccounts().size(), 0);
    }

    @Test
    void testFilterStatus() {
        addMultipleCashFlow();
        List<CashFlow> filteredCredit = testMoneySummary.filterStatus("credit");
        assertEquals(filteredCredit.size(), 2);
    }

    @Test
    void testFilterAccount() {
        addMultipleCashFlow();
        List<CashFlow> filteredRBC = testMoneySummary.filterAccount("RBC");
        assertEquals(filteredRBC.size(), 1);
    }

    @Test
    void testFilterDebitCategory() {
        addMultipleCashFlow();
        List<CashFlow> filteredSalary = testMoneySummary.filterDebitCategory("Salary");
        assertEquals(filteredSalary.size(), 1);
    }

    @Test
    void testFilterCreditCategory() {
        addMultipleCashFlow();
        List<CashFlow> filteredSport = testMoneySummary.filterCreditCategory("Sport");
        assertEquals(filteredSport.size(), 2);
    }

    @Test
    void testFilterYear() {
        addMultipleCashFlow();
        List<CashFlow> filteredYear = testMoneySummary.filterYear(2024);
        assertEquals(filteredYear.size(), 2);
    }

    @Test
    void testFilterMonth() {
        addMultipleCashFlow();
        List<CashFlow> filteredMonth = testMoneySummary.filterMonth(02);
        assertEquals(filteredMonth.size(), 1);
    }

    @Test
    void testFilterDate() {
        addMultipleCashFlow();
        List<CashFlow> filteredDate = testMoneySummary.filterDate(20);
        assertEquals(filteredDate.size(), 1);
    }

    @Test
    void testFilterDescription() {
        addMultipleCashFlow();
        List<CashFlow> filteredNew = testMoneySummary.filterDescription("new");
        assertEquals(filteredNew.size(), 2);
    }

    @Test
    void testDeleteAllCashFlowOfTheDebitCategory() {
        addMultipleCashFlow();
        testMoneySummary.deleteAllCashFlowOfTheDebitCategory("Salary");
        assertEquals(testMoneySummary.getCashflows().size(), 2);
    }

    @Test
    void testDeleteAllCashFlowOfTheCreditCategory() {
        addMultipleCashFlow();
        testMoneySummary.deleteAllCashFlowOfTheCreditCategory("Sport");
        assertEquals(testMoneySummary.getCashflows().size(), 2);
    }

    @Test
    void testDeleteAllCashFlowOfTheAccount() {
        addMultipleCashFlow();
        testMoneySummary.deleteAllCashFlowOfTheAccount("ScotiaBank");
        assertEquals(testMoneySummary.getCashflows().size(), 1);
    }

    // @Test
    // void testGetCashflows() {
    //     addMultipleCashFlow();
    //     testMoneySummary.getCashflows();
    // }

    void addSingleCashFlow() {
        testMoneySummary.addCreditCategory("Sport");
        testMoneySummary.addAccount("ScotiaBank");
        testMoneySummary.addCashFlow(cf1);
    }

    void addMultipleCashFlow() {
        testMoneySummary.addCreditCategory("Sport");
        testMoneySummary.addCreditCategory("Hobby");
        testMoneySummary.addDebitCategory("Salary");
        testMoneySummary.addAccount("ScotiaBank");
        testMoneySummary.addAccount("RBC");
        testMoneySummary.addCashFlow(cf1);
        testMoneySummary.addCashFlow(cf2);
        testMoneySummary.addCashFlow(cf3);
    }

    void deleteMultipleCashFlow() {
        testMoneySummary.deleteCashFlow(cf1);
        testMoneySummary.deleteCashFlow(cf2);
    }
}
