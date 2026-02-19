import java.util.HashMap;
import java.util.Map;

public class DataStore {

    private Map<String, Person> personMap = new HashMap<>();

    public DataStore() {
        personMap.put("Ada", new Person("Ada", "First programmer", 1815));
        personMap.put("Kevin", new Person("Kevin", "Author", 1986));
    }

    public Person getPerson(String name) {
        return personMap.get(name);
    }
}
