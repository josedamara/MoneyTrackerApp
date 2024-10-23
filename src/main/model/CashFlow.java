package model;

import java.util.Collection;

import org.json.JSONObject;

import persistence.Writable;

// A class representing a cash flow with a status (credit/debit), category,
// date, time, and description
public class CashFlow implements Writable {
    private String status;
    private String account;
    private String category;
    private String date;
    private String time;
    private String description;
    private double amount;

    // REQUIRES: 
    // 1900 <= year <= 2100, 01 <= month <= 12, and date constraint should
    // follow the constraint of specific month. 
    // 00 <= hour <= 23, 00 <= minutes <= 59.
    // category must exist in the ListOfCategory.
    // account must exist in the ListOfAccount.
    // amount > 0.0
    // EFFECTS: constructs a CashFlow object with a specified status, account,
    // category, date (formatted as YYYY/MM/DD), time (formatted as HH:MM),
    // and description
    public CashFlow(String status, String account, String category, String date, String time, 
            String description, double amount) {
        this.status = status;
        this.account = account;
        this.category = category;
        this.date = date;
        this.time = time;
        this.description = description;
        this.amount = amount;
    }

    // MODIFIES: this
    // EFFECTS: changes the status of the CashFlow object to credit
    public void setAsCredit() {
        this.status = "credit";
    }

    // MODIFIES: this
    // EFFECTS: changes the status of the CashFlow object to debit
    public void setAsDebit() {
        this.status = "debit";
    }

    // REQUIRES: inputted account must exist in the ListOfAccount
    // MODIFIES: this
    // EFFECTS: changes the account of the CashFlow object to inputted account
    public void setAccount(String account) {
        this.account = account;
    }

    // REQUIRES: inputted category must exist in the ListOfCategory
    // MODIFIES: this
    // EFFECTS: changes the category of the CashFlow object to inputted
    // category
    public void setCategory(String category) {
        this.category = category;
    }

    // REQUIRES: 1900 <= year <= 2100, 01 <= month <= 12, and date constraint
    // should follow the constraint of specific month
    // MODIFIES: this
    // EFFECTS: changes the date of the CashFlow object to inputted date
    public void setDate(String date) {
        this.date = date;
    }

    // REQUIRES: 00 <= hour <= 23, 00 <= minutes <= 59
    // MODIFIES: this
    // EFFECTS: changes the time of the CashFlow object to inputted time
    public void setTime(String time) {
        this.time = time;
    }

    // MODIFIES: this
    // EFFECTS: changes the description of the CashFlow object to inputted
    // description
    public void setDescription(String description) {
        this.description = description;
    }

    // MODIFIES: this
    // EFFECTS: changes the amount of money in the CashFlow object to inputted
    // amount
    public void setAmount(double amount) {
        this.amount = amount;
    }
 
    // EFFECTS: returns the status of the CashFlow object
    public String getStatus() {
        return this.status;
    }

    // EFFECTS: returns the account of the CashFlow object
    public String getAccount() {
        return this.account;
    }

    // EFFECTS: returns the category of the CashFlow object
    public String getCategory() {
        return this.category;
    }

    // EFFECTS: returns the date of the CashFlow object
    public String getDate() {
        return this.date;
    }

    // EFFECTS: returns the time of the CashFlow object
    public String getTime() {
        return this.time;
    }

    // EFFECTS: returns the description of the CashFlow object
    public String getDescription() {
        return this.description;
    }

    // EFFECTS: returns the amount of the CashFlow object
    public double getAmount() {
        return this.amount;
    }

    // EFFECTS: return cashflow object as a JSON Object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("status", status);
        json.put("account", account);
        json.put("category", category);
        json.put("date", date);
        json.put("time", time);
        json.put("description", description);
        json.put("amount", amount);
        return json;
    }
}
