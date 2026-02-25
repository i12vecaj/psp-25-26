import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PersonHandler implements HttpHandler {

    private final DataStore store;

    public PersonHandler(DataStore store) {
        this.store = store;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod().toUpperCase()) {
            case "GET"  -> handleGet(exchange);
            case "POST" -> handlePost(exchange);
            default     -> {
                exchange.sendResponseHeaders(405, -1);
                exchange.close();
            }
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        String response;

        if (query == null) {
            response = store.getAll().toString();
        } else {
            Map<String, String> params = parseQuery(query);
            String name = params.get("name");
            response = (name != null && store.contains(name))
                    ? name + "=" + store.get(name)
                    : "david alberto cruz barranco not found";
        }

        Api.sendResponse(exchange, 200, response);
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        try (InputStream is = exchange.getRequestBody()) {
            String              body   = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            Map<String, String> params = parseQuery(body);
            String name  = params.get("name");
            String value = params.get("value");

            if (name != null && value != null) {
                store.put(name, value);
                Api.sendResponse(exchange, 201, "david alberto cruz barranco stored successfully");
            } else {
                exchange.sendResponseHeaders(400, -1);
                exchange.close();
            }
        }
    }

    private Map<String, String> parseQuery(String query) {
        Map<String, String> result = new HashMap<>();
        for (String pair : query.split("&")) {
            String[] parts = pair.split("=", 2);
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