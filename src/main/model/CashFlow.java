package model;

// A class representing a cash flow with a status (credit/debit), category,
// date, time, and description
public class CashFlow {
    private String status;
    private String category;
    private String date;
    private String time;
    private String description;

    // REQUIRES: 
    // 1900 <= year <= 2100, 01 <= month <= 12, and date constraint should
    // follow the constraint of specific month. 
    // 00 <= hour <= 23, 00 <= minutes <= 59.
    // category must exist in the ListOfCategory.
    // account must exist in the ListOfAccount
    // EFFECTS: constructs a CashFlow object with a specified status, account,
    // category, date (formatted as YYYY/MM/DD), time (formatted as HH:MM),
    // and description
    public CashFlow(String status, String category, String date, String time, String description) {
        this.status = status;
        this.category = category;
        this.date = date;
        this.time = time;
        this.description = description;
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
    public void setAccount() {
        // STUB
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

    // EFFECTS: returns the status of the CashFlow object
    public String getStatus() {
        return this.status;
    }

    // EFFECTS: returns the account of the CashFlow object
    public String getAccount() {
        return ""; // STUB
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
}
