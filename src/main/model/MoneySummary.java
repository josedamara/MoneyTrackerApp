package model;

import java.util.List;
import java.util.ArrayList;

// A class representing the summary of someone's money, including the
// specification of their list of cash flows, list of debit and credit
// categories, and list of accounts
public class MoneySummary {
    // EFFECTS: constructs a MoneySummary object with an empty list of
    // cash flows initially, list of some provided debit and credit categories,
    // and empty list of accounts initially
    public MoneySummary() {
        // STUB
    }

    // REQUIRES: the specified CashFlow must not exist in the cashflows
    // MODIFIES: this
    // EFFECTS: adds a CashFlow object to the cashflows
    public void addCashFlow(CashFlow cf) {
        // STUB
    }

    // REQUIRES: the specified CashFlow exists in the cashflows
    // MODIFIES: this
    // EFFECTS: deletes a CashFlow object from the cashflows
    public void deleteCashFlow(CashFlow cf) {
        // STUB
    }

    // REQUIRES: debitCategory must not exist in the debitCategories
    // MODIFIES: this
    // EFFECTS: adds a debitCategory to the debitCategories
    public void addDebitCategory(String debitCategory) {
        // STUB
    }

    // REQUIRES: debitCategory exists in the debitCategories
    // MODIFIES: this
    // EFFECTS: deletes a debitCategory in the debitCategories
    public void deleteDebitCategory(String debitCategory) {
        // STUB
    }

    // REQUIRES: creditCategory must not exist in the creditCategories
    // MODIFIES: this
    // EFFECTS: adds a creditCategory to the creditCategories
    public void addCreditCategory(String creditCategory) {
        // STUB
    }

    // REQUIRES: creditCategory exists in the creditCategories
    // MODIFIES: this
    // EFFECTS: deletes a creditCategory in the creditCategories
    public void deleteCreditCategory(String creditCategory) {
        // STUB
    }
    
    // REQUIRES: account must not exist in the accounts
    // MODIFIES: this
    // EFFECTS: adds a account to the accounts
    public void addAccount(String account) {
        // STUB
    }

    // REQUIRES: account exists in the accounts
    // MODIFIES: this
    // EFFECTS: deletes a account in the accounts
    public void deleteAcccount(String account) {
        // STUB
    }

    // REQUIRES: status must be 'credit' or 'debit'
    // EFFECTS: filters the cashflows with a specified status
    public List<CashFlow> filterStatus(String status) {
        return new ArrayList<>(); // STUB
    }

    // REQUIRES: account must exist in accounts
    // EFFECTS: filters the cashflows with a specified account
    public List<CashFlow> filterAccount(String account) {
        return new ArrayList<>(); // STUB
    }

    // REQUIRES: debitCategory must exist in debitCategories
    // EFFECTS: filters the cashflows with a specified debitCategory
    public List<CashFlow> filterDebitCategory(String debitCategory) {
        return new ArrayList<>(); // STUB
    }

    // REQUIRES: creditCategory must exist in creditCategories
    // EFFECTS: filters the cashflows with a specified creditCategory
    public List<CashFlow> filterCreditCategory(String creditCategory) {
        return new ArrayList<>(); // STUB
    }

    // REQUIRES: 1900 <= year <= 2100
    // EFFECTS: filters the cashflows with a specified year
    public List<CashFlow> filterYear(int year) {
        return new ArrayList<>(); // STUB
    }

    // REQUIRES: 01 <= month <= 12
    // EFFECTS: filters the cashflows with a specified month
    public List<CashFlow> filterMonth(int month) {
        return new ArrayList<>(); // STUB
    }

    // REQUIRES: date constraint must follow its specific month constraint
    // EFFECTS: filters the cashflows with a specified date
    public List<CashFlow> filterDate(int date) {
        return new ArrayList<>(); // STUB
    }

    // EFFECTS: finds CashFlow in the cashflows with a specified description
    public List<CashFlow> filterDescription(String account) {
        return new ArrayList<>(); // STUB
    }

    // MODIFIES: this
    // EFFECTS: deletes all CashFlow in the MoneySummary when its category is
    // deleted from the debitCategories
    public void deleteAllCashFlowOfTheDebitCategory(String debitCategory) {
        // STUB
    }

    // MODIFIES: this
    // EFFECTS: deletes all CashFlow in the MoneySummary when its category is
    // deleted from the creditCategories
    public void deleteAllCashFlowOfTheCreditCategory(String creditCategory) {
        // STUB
    }

    // MODIFIES: this
    // EFFECTS: deletes all CashFlow in the MoneySummary when its account is
    // deleted from the accounts
    public void deleteAllCashFlowOfTheAccount(String account) {
        // STUB
    }

    // EFFECTS: returns the cashflows of the MoneySummary object
    public List<CashFlow> getCashflows() {
        return new ArrayList<>(); // STUB
    }

    // EFFECTS: returns the debitCategories of the MoneySummary object
    public List<String> getDebitCategories() {
        return new ArrayList<>(); // STUB
    }

    // EFFECTS: returns the creditCategories of the MoneySummary object
    public List<String> getCreditCategories() {
        return new ArrayList<>(); // STUB
    }

    // EFFECTS: returns the accounts of the MoneySummary object
    public List<String> getAccounts() {
        return new ArrayList<>(); // STUB
    }
}
