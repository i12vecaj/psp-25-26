import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PersonHandler implements HttpHandler {

    private DataStore store;

    public PersonHandler(DataStore store) {
        this.store = store;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            handleGet(exchange);
        } else if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            handlePost(exchange);
        } else {
            exchange.sendResponseHeaders(405, -1);
        }

        exchange.close();
    }

    private void handleGet(HttpExchange exchange) throws IOException {

        String query = exchange.getRequestURI().getQuery();
        String response;

        if (query == null) {
            response = store.getAll().toString();
        } else {
            Map<String, String> params = parseQuery(query);
            String name = params.get("name");

            if (name != null && store.get(name) != null) {
                response = name + "=" + store.get(name);
            } else {
                response = "Person not found";
            }
        }

        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void handlePost(HttpExchange exchange) throws IOException {

        InputStream is = exchange.getRequestBody();
        String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);

        Map<String, String> params = parseQuery(body);

        String name = params.get("name");
        String value = params.get("value");

        if (name != null && value != null) {
            store.put(name, value);
            String response = "Person stored successfully";
            exchange.sendResponseHeaders(201, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            exchange.sendResponseHeaders(400, -1);
        }
    }

    private Map<String, String> parseQuery(String query) throws IOException {
        Map<String, String> result = new HashMap<>();

        for (String pair : query.split("&")) {
            String[] parts = pair.split("=");
            if (parts.length == 2) {
                result.put(
                        URLDecoder.decode(parts[0], StandardCharsets.UTF_8),
                        URLDecoder.decode(parts[1], StandardCharsets.UTF_8)
                );
            }
        }
        return result;
    }
}
