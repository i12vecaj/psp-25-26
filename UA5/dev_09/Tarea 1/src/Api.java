import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Api {

    private static final String WELCOME_MESSAGE = "Hello World! from our framework-less REST API";
    private static final String BYE_MESSAGE     = "BYE! from our framework-less REST API";
    private static final int    PORT            = 8080;

    public static void main(String[] args) throws IOException {
        DataStore store  = new DataStore();
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);

        server.createContext("/api/greeting", exchange -> handleSimpleGet(exchange, WELCOME_MESSAGE));
        server.createContext("/api/bye",      exchange -> handleSimpleGet(exchange, BYE_MESSAGE));
        server.createContext("/api/person",   new PersonHandler(store));
        server.createContext("/api/hash",     new HashHandler());

        server.setExecutor(null);
        server.start();
        System.out.printf("Server running at http://localhost:%d%n", PORT);
    }

    private static void handleSimpleGet(HttpExchange exchange, String message) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            exchange.close();
            return;
        }
        sendResponse(exchange, 200, message);
    }

    static void sendResponse(HttpExchange exchange, int status, String body) throws IOException {
        byte[] bytes = body.getBytes();
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
        exchange.close();
    }
}