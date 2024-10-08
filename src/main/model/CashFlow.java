package model;

// A class representing a cash flow with a status (credit/debit), category,
// date, time, and description
public class CashFlow {
    // REQUIRES: 
    // 1900 <= year <= 2100, 01 <= month <= 12, and date constraint should
    // follow the constraint of specific month. 
    // 00 <= hour <= 23, 00 <= minutes <= 59.
    // category should be exist in the ListOfCategory.
    // EFFECTS: constructs a CashFlow object with a specified status,
    // category, date (formatted as YYYY/MM/DD), time (formatted as HH:MM),
    // and description
    public CashFlow(String status, String category, String date, String time, String description) {
        // STUB
    }

    // MODIFIES: this
    // EFFECTS: changes the status of the CashFlow object to credit
    public void setAsCredit() {
        // STUB
    }

    // MODIFIES: this
    // EFFECTS: changes the status of the CashFlow object to debit
    public void setAsDebit() {
        // STUB
    }

    // MODIFIES: this
    // EFFECTS: changes the category of the CashFlow object to inputted
    // category
    public void setCategory(String category) {
        // STUB
    }

    // REQUIRES: 1900 <= year <= 2100, 01 <= month <= 12, and date constraint
    // should follow the constraint of specific month
    // MODIFIES: this
    // EFFECTS: changes the date of the CashFlow object to inputted date
    public void setDate(String date) {
        // STUB
    }

    // REQUIRES: 00 <= hour <= 23, 00 <= minutes <= 59
    // MODIFIES: this
    // EFFECTS: changes the time of the CashFlow object to inputted time
    public void setTime(String time) {
        // STUB
    }

    // MODIFIES: this
    // EFFECTS: changes the description of the CashFlow object to inputted
    // description
    public void setDescription(String description) {
        // STUB
    }

    // EFFECTS: returns the status of the CashFlow object
    public String getStatus() {
        return ""; // STUB
    }

    // EFFECTS: returns the category of the CashFlow object
    public String getCategory() {
        return ""; // STUB
    }

    // EFFECTS: returns the date of the CashFlow object
    public String getDate() {
        return ""; // STUB
    }

    // EFFECTS: returns the time of the CashFlow object
    public String getTime() {
        return ""; // STUB
    }

    // EFFECTS: returns the description of the CashFlow object
    public String getDescription() {
        return ""; // STUB
    }
}
