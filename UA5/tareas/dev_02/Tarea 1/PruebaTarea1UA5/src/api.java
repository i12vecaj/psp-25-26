import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Date;

public class api {

    public static String welcomeMessage = "Hello World! from our framework-less REST API";
    public static String byebyeMessage = "BYE! from our framework-less REST API";

    public static void main(String[] args) throws IOException {

        DataStore store = new DataStore();

        // Crear el servidor en localhost:8080
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);

        // Contexto: /api/greeting
        server.createContext("/api/greeting", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String responseText = welcomeMessage;
                exchange.sendResponseHeaders(200, responseText.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(responseText.getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
            exchange.close();
        }));

        // Contexto: /api/bye
        server.createContext("/api/bye", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String responseText = byebyeMessage + "\n";
                exchange.sendResponseHeaders(200, responseText.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(responseText.getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
            exchange.close();
        }));

        // Contexto: /api/person
        server.createContext("/api/person", new PersonHandler(store));

        // Contexto: /api/hash (nuevo endpoint)
        server.createContext("/api/hash", new HashHandler());

        // Iniciar servidor
        server.setExecutor(null); // default executor
        server.start();
        System.out.println("The framework-less REST API server is listening on "
                + server.getAddress().getAddress() + ":" + server.getAddress().getPort()
                + " at " + new Date());
    }

}
