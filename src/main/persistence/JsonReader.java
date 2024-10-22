package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

import model.CashFlow;
import model.MoneySummary;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// A class representing a reader that reads moneysummary from the JSON file
public class JsonReader {
    private String source;

    // EFFECTS: constructs a reader to read from the source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads moneysummary from the file and return it;
    // throws IOException if an error occurs during reading data
    public MoneySummary read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMoneySummary(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses moneysummary from JSON object and returns it
    private MoneySummary parseMoneySummary(JSONObject jsonObject) {
        MoneySummary ms = new MoneySummary();
        addCashflows(ms, jsonObject);
        addDebitCategories(ms, jsonObject);
        addCreditCategories(ms, jsonObject);
        addAccounts(ms, jsonObject);
        return ms;
    }

    // MODIFIES: ms
    // EFFECTS: parses cashflows from JSON object and adds them to moneysummary
    private void addCashflows(MoneySummary ms, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("cashflows");
        for (Object json : jsonArray) {
            JSONObject nextCashflow = (JSONObject) json;
            addCashflow(ms, nextCashflow);
        }
    }

    // MODIFIES: ms
    // EFFECTS: parses thingy from JSON object and adds it to moneysummary
    private void addCashflow(MoneySummary ms, JSONObject jsonObject) {
        String status = jsonObject.getString("status");
        String account = jsonObject.getString("account");
        String category = jsonObject.getString("category");
        String date = jsonObject.getString("date");
        String time = jsonObject.getString("time");
        String description = jsonObject.getString("description");
        double amount = jsonObject.getDouble("amount");
        CashFlow cashflow = new CashFlow(status, account, category, date, time, description, amount);
        ms.addCashFlow(cashflow);
    }

    // MODIFIES: ms
    // EFFECTS: parses debitCategories from JSON object and adds them to moneysummary
    private void addDebitCategories(MoneySummary ms, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("debitCategories");
        for (Object json : jsonArray) {
            String nextDebitCategory = (String) json;
            ms.addDebitCategory(nextDebitCategory);
        }
    }

    // MODIFIES: ms
    // EFFECTS: parses creditCategories from JSON object and adds them to moneysummary
    private void addCreditCategories(MoneySummary ms, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("creditCategories");
        for (Object json : jsonArray) {
            String nextCreditCategory = (String) json;
            ms.addCreditCategory(nextCreditCategory);
        }
    }

    // MODIFIES: ms
    // EFFECTS: parses accounts from JSON object and adds them to moneysummary
    private void addAccounts(MoneySummary ms, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("accounts");
        for (Object json : jsonArray) {
            String nextAccount = (String) json;
            ms.addAccount(nextAccount);
        }
    }
}
