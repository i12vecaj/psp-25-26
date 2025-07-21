import com.google.gson.Gson;
import static java.nio.file.Files.delete;
import static spark.Spark.*;

public class Main {
    private static DataStore dataStore = DataStore.getInstance();
    private static Gson gson = new Gson();

    public static void main(String[] args) {
        // Configurar el puerto
        port(8080);

        // Endpoint: Obtener todas las personas
        get("/persons", (req, res) -> {
            res.type("application/json");
            return dataStore.getAllPersons().values();
        }, gson::toJson);

        // Endpoint: Obtener persona por ID
        get("/persons/:id", (req, res) -> {
            try {
                int id = Integer.parseInt(req.params(":id"));
                Person person = dataStore.getPerson(id);
                if (person != null) {
                    return person;
                } else {
                    res.status(404);
                    return "Persona no encontrada";
                }
            } catch (NumberFormatException e) {
                res.status(400);
                return "ID inválido";
            }
        }, gson::toJson);

        // Endpoint: Crear persona
        post("/persons", (req, res) -> {
            Person newPerson = gson.fromJson(req.body(), Person.class);
            int newId = dataStore.getNextId();
            Person personWithId = new Person(newId, newPerson.getName(), newPerson.getAbout(), newPerson.getBirthYear());
            dataStore.addPerson(personWithId);
            res.status(201);
            return personWithId;
        }, gson::toJson);

        // Endpoint: Eliminar persona por ID
        delete("/persons/:id", (req, res) -> {
            try {
                int id = Integer.parseInt(req.params(":id"));
                if (dataStore.getPerson(id) != null) {
                    dataStore.deletePerson(id);
                    return "Persona eliminada";
                } else {
                    res.status(404);
                    return "Persona no encontrada";
                }
            } catch (NumberFormatException e) {
                res.status(400);
                return "ID inválido";
            }
        }, gson::toJson);
    }
}