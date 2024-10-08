package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CashFlowTest {
    private CashFlow testCashFlow;
    
    @BeforeEach
    void runBefore() {
        testCashFlow = new CashFlow("credit", "ScotiaBank", "Sport", "2024/01/01",
        "22:40", "Buying a new basketball");
    }

    @Test
    void testConstructor() {
        assertEquals(testCashFlow.getStatus(), "credit");
        assertEquals(testCashFlow.getAccount(), "ScotiaBank");
        assertEquals(testCashFlow.getCategory(), "Sport");
        assertEquals(testCashFlow.getDate(), "2024/01/01");
        assertEquals(testCashFlow.getTime(), "22:40");
        assertEquals(testCashFlow.getDescription(), "Buying a new basketball");
    }

    @Test
    void testSetAsCredit() {
        testCashFlow.setAsDebit();
        testCashFlow.setAsCredit();
        assertEquals(testCashFlow.getStatus(), "credit");
        testCashFlow.setAsCredit();
        assertEquals(testCashFlow.getStatus(), "credit");
    }

    @Test
    void testSetAsDebit() {
        testCashFlow.setAsDebit();
        assertEquals(testCashFlow.getStatus(), "debit");
        testCashFlow.setAsDebit();
        assertEquals(testCashFlow.getStatus(), "debit");
    }

    @Test
    void testSetAccount() {
        testCashFlow.setAccount("RBC");
        assertEquals(testCashFlow.getAccount(), "RBC");
        testCashFlow.setAccount("RBC");
        assertEquals(testCashFlow.getAccount(), "RBC");
    }

    @Test
    void testSetCategory() {
        testCashFlow.setCategory("Food");
        assertEquals(testCashFlow.getCategory(), "Food");
    }

    @Test
    void testSetDate() {
        testCashFlow.setDate("2023/02/01");
        assertEquals(testCashFlow.getDate(), "2023/02/01");
    }

    @Test
    void testSetTime() {
        testCashFlow.setTime("20:00");
        assertEquals(testCashFlow.getTime(), "20:00");
    }

    @Test
    void testSetDescription() {
        testCashFlow.setDescription("Buying a racket");
        assertEquals(testCashFlow.getDescription(), "Buying a racket");
    }
}
