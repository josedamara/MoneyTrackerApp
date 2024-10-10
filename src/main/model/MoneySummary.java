package model;

import java.util.List;
import java.util.ArrayList;

// A class representing the summary of someone's money, including the
// specification of their list of cash flows, list of categories, and
// list of accounts
public class MoneySummary {
    // EFFECTS: constructs a MoneySummary object with an empty list of
    // cash flows initially, list of some provided categories, and empty list
    // of accounts initially
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

    // REQUIRES: category must not exist in the categories
    // MODIFIES: this
    // EFFECTS: adds a category to the categories
    public void addCategory(String category) {
        // STUB
    }

    // REQUIRES: category exists in the categories
    // MODIFIES: this
    // EFFECTS: deletes a category in the categories
    public void deleteCategory(String category) {
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

    // REQUIRES: category must exist in categories
    // EFFECTS: filters the cashflows with a specified category
    public List<CashFlow> filterCategory(String category) {
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
    // deleted from the categories
    public void deleteAllOfTheCategory(String category) {
        // STUB
    }

    // MODIFIES: this
    // EFFECTS: deletes all CashFlow in the MoneySummary when its account is
    // deleted from the accounts
    public void deleteAllOfTheAccount(String account) {
        // STUB
    }
}
