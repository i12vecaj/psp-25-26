import java.util.HashMap;
import java.util.Map;

class Persona {
    String name;
    String about;
    int birthYear;

    public Persona(String name, String about, int birthYear) {
        this.name = name;
        this.about = about;
        this.birthYear = birthYear;
    }
}

interface Repository {
    void save(Persona p);
    Persona findByName(String name);
    boolean update(Persona p);
    boolean delete(String name);
}


class DataWarehouse implements Repository {
    private static Map<String, Persona> personas = new HashMap<>();

    @Override
    public void save(Persona p) { personas.put(p.name.toLowerCase(), p); }
    @Override
    public Persona findByName(String name) { return personas.get(name.toLowerCase()); }
    @Override
    public boolean update(Persona p) {
        if (personas.containsKey(p.name.toLowerCase())) {
            personas.put(p.name.toLowerCase(), p);
            return true;
        }
        return false;
    }
    @Override
    public boolean delete(String name) { return personas.remove(name.toLowerCase()) != null; }
}