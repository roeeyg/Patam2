package AppProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerConfig implements NakedObject {
    private Map<String, String> fields = new HashMap<>();

    public ServerConfig() {
        //Set default values
        fields.put("Server", "localhost");
        fields.put("Port", "6400");
    }

    @Override
    public List<String> fieldNames() {
        return new ArrayList<>(fields.keySet());
    }

    @Override
    public void fieldChanged(String fieldName, String newValue) {
        fields.put(fieldName, newValue);
    }

    @Override
    public String getFieldValue(String fieldName) {
        return fields.get(fieldName);
    }

    public String getIP() {
        return fields.get("Server");
    }

    public String getPort() {
        return fields.get("Port");
    }
}
