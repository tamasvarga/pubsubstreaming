package Tools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Event implements Serializable {

    private String type;
    private StringBuilder appliedActions= new StringBuilder();
    private Map<String, String> properties = new HashMap<>();
    public Event(String json) {

        JSONObject jobject = new JSONObject(json);
        this.type = jobject.getString("type");

        JSONArray properties = jobject.getJSONArray("properties");
        for (int i = 0; i < properties.length(); i++) {
            JSONObject jsonObject = properties.getJSONObject(i);
            String key = jsonObject.getString("key");
            String value = jsonObject.getString("value");
            this.properties.put(key, value);
        }

    }

    public String GetProperty(String key) {
        return properties.get(key);
    }

    public void AddAction(String action) {

        appliedActions.append(action);
        appliedActions.append(System.lineSeparator());
    }

    public String GetActions () {
        return appliedActions.toString();
    }
    public String getType() {
        return type;
    }
}

