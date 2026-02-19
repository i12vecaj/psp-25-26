import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import com.sun.net.httpserver.HttpExchange;

class PersonHandler extends BasicHandler {

    private DataStore store;

    public PersonHandler(DataStore store) {
        this.store = store;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());
        String name = params.get("name");

        Person person = store.getPerson(name);

        if (person == null) {
            exchange.sendResponseHeaders(404, -1);
            return;
        }

        String response = "{"
                + "\"name\":\"" + person.getName() + "\","
                + "\"about\":\"" + person.getAbout() + "\","
                + "\"birthYear\":" + person.getBirthYear()
                + "}";

        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, response.length());

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
