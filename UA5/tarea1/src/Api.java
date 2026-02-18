import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Api {

    public static String welcomeMessage = "Hello World! from our framework-less REST API";
    public static String byebyeMessage = "BYE! from our framework-less REST API";

    public static void main(String[] args) throws IOException {

        DataStore store = new DataStore();

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);

        // Greeting
        server.createContext("/api/greeting", exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String responseText = welcomeMessage;
                exchange.sendResponseHeaders(200, responseText.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(responseText.getBytes());
                output.close();
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
            exchange.close();
        });

        // Bye
        server.createContext("/api/bye", exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String responseText = byebyeMessage;
                exchange.sendResponseHeaders(200, responseText.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(responseText.getBytes());
                output.close();
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
            exchange.close();
        });

        // Person
        server.createContext("/api/person", new PersonHandler(store));

        // HASH MD5 (LO IMPORTANTE DE LA TAREA)
        server.createContext("/api/hash", new HashHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("Server running at http://localhost:8080");
    }
}
