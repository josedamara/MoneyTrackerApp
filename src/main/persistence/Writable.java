package persistence;

import org.json.JSONObject;

// Interface for JsonWriter and JsonReader
public interface Writable {
    // EFFECTS: returns this as JSON Object
    JSONObject toJson();
}
