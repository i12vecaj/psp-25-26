import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class api {

    public static void main(String[] args) throws IOException {

        DataStore store = new DataStore();

        // Escucha en todas las interfaces
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Greeting
        server.createContext("/api/greeting", exchange -> {
            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                String response = "Hello World!";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        });

        // Person
        server.createContext("/api/person", new PersonHandler(store));

        // HASH MD5 (FUNCIONANDO)
        server.createContext("/api/hash", new HashHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("Servidor iniciado en http://localhost:8080");
    }
}
