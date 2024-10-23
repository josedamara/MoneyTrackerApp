package persistence;

import model.MoneySummary;

import java.io.*;

import org.json.JSONObject;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// A class representing a writer that writes moneysummary into a JSON file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: construct writer object to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens the writer; throws FileNotFoundException error when the
    // specified file cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes moneysummary into a JSON file
    public void write(MoneySummary ms) {
        JSONObject json = ms.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to the file
    public void saveToFile(String json) {
        writer.print(json);
    }
}
