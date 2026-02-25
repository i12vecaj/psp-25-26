import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class api {
    public static String welcomeMessage = "Hello World! from our framework-less REST API";
    public static String byebyeMessage = "BYE! from our framework-less REST API";

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);

        server.createContext("/api/greeting", exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                sendText(exchange, 200, welcomeMessage);
            } else {
                exchange.sendResponseHeaders(405, -1);
                exchange.close();
            }
        });

        server.createContext("/api/bye", exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                sendText(exchange, 200, byebyeMessage);
            } else {
                exchange.sendResponseHeaders(405, -1);
                exchange.close();
            }
        });

        server.createContext("/api/hash", new HashHandler());
        server.createContext("/api/hash/", new HashHandler());

        server.setExecutor(null);
        server.start();

        System.out.println(
                "The framework-less REST API server is listening on "
                        + server.getAddress().getHostString()
                        + ":"
                        + server.getAddress().getPort()
        );
    }

    private static void sendText(com.sun.net.httpserver.HttpExchange exchange, int statusCode, String text) throws IOException {
        byte[] payload = text.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, payload.length);
        try (OutputStream output = exchange.getResponseBody()) {
            output.write(payload);
            output.flush();
        }
    }
}
