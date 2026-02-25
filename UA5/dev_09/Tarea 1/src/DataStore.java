import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataStore {

    private final Map<String, String> data = new ConcurrentHashMap<>();

    public void put(String key, String value) {
        data.put(key, value);
    }

    public String get(String key) {
        return data.get(key);
    }

    public boolean contains(String key) {
        return data.containsKey(key);
    }

    public Map<String, String> getAll() {
        return Collections.unmodifiableMap(data);
    }
}