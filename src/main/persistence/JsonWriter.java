package persistence;

import model.MoneySummary;

import java.io.*;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// A class representing a writer that writes moneysummary into a JSON file
public class JsonWriter {
    // EFFECTS: construct writer object to write to destination file
    public JsonWriter(String destination) {
        // STUB
    }

    // MODIFIES: this
    // EFFECTS: opens the writer; throws FileNotFoundException error when the
    // specified file cannot be opened for writing
    public void open() throws FileNotFoundException {
        // STUB
    }

    // MODIFIES: this
    // EFFECTS: writes moneysummary into a JSON file
    public void write(MoneySummary ms) {
        // STUB
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        // STUB
    }

    // MODIFIES: this
    // EFFECTS: writes string to the file
    public void saveToFile(String json) {
        // STUB
    }
}
