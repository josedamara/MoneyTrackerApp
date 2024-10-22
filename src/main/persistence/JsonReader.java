package persistence;

import java.io.IOException;

import org.json.JSONObject;

import model.MoneySummary;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// A class representing a reader that reads moneysummary from the JSON file
public class JsonReader {
    // EFFECTS: constructs a reader to read from the source file
    public JsonReader(String source) {
        // STUB
    }

    // EFFECTS: reads moneysummary from the file and return it;
    // throws IOException if an error occurs during reading data
    public MoneySummary read() throws IOException {
        return new MoneySummary(); // STUB
    }

    // MODIFIES: ms
    // EFFECTS: parses cashflows from JSON object and adds them to moneysummary
    private void addCashflows(MoneySummary ms, JSONObject jsonObject) {
        // STUB
    }

    // MODIFIES: ms
    // EFFECTS: parses debitCategories from JSON object and adds them to moneysummary
    private void addDebitCategories(MoneySummary ms, JSONObject jsonObject) {
        // STUB
    }

    // MODIFIES: ms
    // EFFECTS: parses creditCategories from JSON object and adds them to moneysummary
    private void addCreditCategories(MoneySummary ms, JSONObject jsonObject) {
        // STUB
    }

    // MODIFIES: ms
    // EFFECTS: parses accounts from JSON object and adds them to moneysummary
    private void addAccounts(MoneySummary ms, JSONObject jsonObject) {
        // STUB
    }
}
