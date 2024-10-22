package model;

import java.util.List;
import java.util.ArrayList;

// A class representing the summary of someone's money, including the
// specification of their list of cash flows, list of debit and credit
// categories, and list of accounts
public class MoneySummary {
    private List<CashFlow> cashflows;
    private ArrayList<String> debitCategories;
    private ArrayList<String> creditCategories;
    private ArrayList<String> accounts;

    // EFFECTS: constructs a MoneySummary object with an empty list of
    // cash flows initially, list of some provided debit and credit categories,
    // and empty list of accounts initially
    public MoneySummary() {
        this.cashflows = new ArrayList<>();
        this.debitCategories = new ArrayList<>();
        this.creditCategories = new ArrayList<>();
        addInitialDebitCategories();
        addInitialCreditCategories();
        this.accounts = new ArrayList<>();
    }

    // REQUIRES: the specified CashFlow must not exist in the cashflows
    // MODIFIES: this
    // EFFECTS: adds a CashFlow object to the cashflows
    public void addCashFlow(CashFlow cf) {
        this.cashflows.add(cf);
    }

    // REQUIRES: the specified CashFlow exists in the cashflows
    // MODIFIES: this
    // EFFECTS: deletes a CashFlow object from the cashflows
    public void deleteCashFlow(CashFlow cf) {
        this.cashflows.remove(cf);
    }

    // REQUIRES: debitCategory must not exist in the debitCategories
    // MODIFIES: this
    // EFFECTS: adds a debitCategory to the debitCategories
    public void addDebitCategory(String debitCategory) {
        this.debitCategories.add(debitCategory);
    }

    // REQUIRES: debitCategory exists in the debitCategories
    // MODIFIES: this
    // EFFECTS: deletes a debitCategory in the debitCategories
    public void deleteDebitCategory(String debitCategory) {
        this.debitCategories.remove(debitCategory);
    }

    // REQUIRES: creditCategory must not exist in the creditCategories
    // MODIFIES: this
    // EFFECTS: adds a creditCategory to the creditCategories
    public void addCreditCategory(String creditCategory) {
        this.creditCategories.add(creditCategory);
    }

    // REQUIRES: creditCategory exists in the creditCategories
    // MODIFIES: this
    // EFFECTS: deletes a creditCategory in the creditCategories
    public void deleteCreditCategory(String creditCategory) {
        this.creditCategories.remove(creditCategory);
    }
    
    // REQUIRES: account must not exist in the accounts
    // MODIFIES: this
    // EFFECTS: adds a account to the accounts
    public void addAccount(String account) {
        this.accounts.add(account);
    }

    // REQUIRES: account exists in the accounts
    // MODIFIES: this
    // EFFECTS: deletes a account in the accounts
    public void deleteAccount(String account) {
        this.accounts.remove(account);
    }

    // REQUIRES: status must be 'credit' or 'debit'
    // EFFECTS: filters the cashflows with a specified status
    public List<CashFlow> filterStatus(String status) {
        List<CashFlow> filteredByStatus = new ArrayList<>();

        for (int i = 0; i < this.cashflows.size(); i++) {
            if (this.cashflows.get(i).getStatus().equals(status)) {
                filteredByStatus.add(this.cashflows.get(i));
            }
        }

        return filteredByStatus;
    }

    // REQUIRES: account must exist in accounts
    // EFFECTS: filters the cashflows with a specified account
    public List<CashFlow> filterAccount(String account) {
        List<CashFlow> filteredByAccount = new ArrayList<>();

        for (int i = 0; i < this.cashflows.size(); i++) {
            if (this.cashflows.get(i).getAccount().equals(account)) {
                filteredByAccount.add(this.cashflows.get(i));
            }
        }

        return filteredByAccount;
    }

    // REQUIRES: debitCategory must exist in debitCategories
    // EFFECTS: filters the cashflows with a specified debitCategory
    public List<CashFlow> filterDebitCategory(String debitCategory) {
        List<CashFlow> filteredByDebitCategory = new ArrayList<>();

        for (int i = 0; i < this.cashflows.size(); i++) {
            if (this.cashflows.get(i).getCategory().equals(debitCategory)
                    && this.cashflows.get(i).getStatus().equals("debit")) {
                filteredByDebitCategory.add(this.cashflows.get(i));
            }
        }
        
        return filteredByDebitCategory;
    }

    // REQUIRES: creditCategory must exist in creditCategories
    // EFFECTS: filters the cashflows with a specified creditCategory
    public List<CashFlow> filterCreditCategory(String creditCategory) {
        List<CashFlow> filteredByCreditCategory = new ArrayList<>();

        for (int i = 0; i < this.cashflows.size(); i++) {
            if (this.cashflows.get(i).getCategory().equals(creditCategory)
                    && this.cashflows.get(i).getStatus().equals("credit")) {
                filteredByCreditCategory.add(this.cashflows.get(i));
            }
        }
        
        return filteredByCreditCategory;
    }

    // REQUIRES: 1900 <= year <= 2100
    // EFFECTS: filters the cashflows with a specified year
    public List<CashFlow> filterYear(int year) {
        List<CashFlow> filteredByYear = new ArrayList<>();

        for (int i = 0; i < this.cashflows.size(); i++) {
            if (this.cashflows.get(i).getDate().substring(0,4).equals(Integer.toString(year))) {
                filteredByYear.add(this.cashflows.get(i));
            }
        }
        
        return filteredByYear;
    }

    // REQUIRES: 01 <= month <= 12
    // EFFECTS: filters the cashflows with a specified month
    public List<CashFlow> filterMonth(String month) {
        List<CashFlow> filteredByMonth = new ArrayList<>();

        for (int i = 0; i < this.cashflows.size(); i++) {
            if (this.cashflows.get(i).getDate().substring(5,7).equals(month)) {
                filteredByMonth.add(this.cashflows.get(i));
            }
        }
        
        return filteredByMonth;
    }

    // REQUIRES: date constraint must follow its specific month constraint
    // EFFECTS: filters the cashflows with a specified date
    public List<CashFlow> filterDate(String date) {
        List<CashFlow> filteredByDate = new ArrayList<>();

        for (int i = 0; i < this.cashflows.size(); i++) {
            if (this.cashflows.get(i).getDate().substring(8,10).equals(date)) {
                filteredByDate.add(this.cashflows.get(i));
            }
        }
        
        return filteredByDate;
    }

    // EFFECTS: finds CashFlow in the cashflows with a specified description
    public List<CashFlow> filterDescription(String description) {
        List<CashFlow> filteredByDescription = new ArrayList<>();

        for (int i = 0; i < this.cashflows.size(); i++) {
            if (this.cashflows.get(i).getDescription().indexOf(description) != -1) {
                filteredByDescription.add(this.cashflows.get(i));
            }
        }
        
        return filteredByDescription;
    }

    // MODIFIES: this
    // EFFECTS: deletes all CashFlow in the MoneySummary when its category is
    // deleted from the debitCategories
    public void deleteAllCashFlowOfTheDebitCategory(String debitCategory) {
        for (int i = 0; i < this.cashflows.size(); i++) {
            if (this.cashflows.get(i).getCategory().equals(debitCategory) 
                    && this.cashflows.get(i).getStatus().equals("debit")) {
                this.cashflows.remove(i);
                i--;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes all CashFlow in the MoneySummary when its category is
    // deleted from the creditCategories
    public void deleteAllCashFlowOfTheCreditCategory(String creditCategory) {
        for (int i = 0; i < this.cashflows.size(); i++) {
            if (this.cashflows.get(i).getCategory().equals(creditCategory)
                    && this.cashflows.get(i).getStatus().equals("credit")) {
                this.cashflows.remove(i);
                i--;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes all CashFlow in the MoneySummary when its account is
    // deleted from the accounts
    public void deleteAllCashFlowOfTheAccount(String account) {
        for (int i = 0; i < this.cashflows.size(); i++) {
            if (this.cashflows.get(i).getAccount().equals(account)) {
                this.cashflows.remove(i);
                i--;
            }
        }
    }

    // EFFECTS: returns the name of the MoneySummary object
    public String getName() {
        // STUB
    }

    // EFFECTS: returns the cashflows of the MoneySummary object
    public List<CashFlow> getCashflows() {
        return this.cashflows;
    }

    // EFFECTS: returns the debitCategories of the MoneySummary object
    public List<String> getDebitCategories() {
        return this.debitCategories;
    }

    // EFFECTS: returns the creditCategories of the MoneySummary object
    public List<String> getCreditCategories() {
        return this.creditCategories;
    }

    // EFFECTS: returns the accounts of the MoneySummary object
    public List<String> getAccounts() {
        return this.accounts;
    }

    // EFFECTS: add some initial common debit categories
    private void addInitialDebitCategories() {
        this.debitCategories.add("Cheque");
        this.debitCategories.add("Parents");
        this.debitCategories.add("Other");
        this.debitCategories.add("Salary");
        this.debitCategories.add("Sport");
    }

    // EFFECTS: add some initial common credit categories
    private void addInitialCreditCategories() {
        this.creditCategories.add("Education");
        this.creditCategories.add("Grocery");
        this.creditCategories.add("Hobby");
        this.creditCategories.add("Other");
        this.creditCategories.add("Sport");
    }
}
