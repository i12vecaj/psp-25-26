import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;


public class api {
    public static String welcomeMessage = "Hello World! from our framework-less REST API";
    public static String byebyeMessage = "BYE! from our framework-less REST API";

    public static void main(String[] args) throws IOException {
        DataStore store = new DataStore();

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
        
        server.createContext("/api/greeting", (exchange -> {

            if ("GET".equals(exchange.getRequestMethod())) {
                String responseText = welcomeMessage;
                exchange.sendResponseHeaders(200, responseText.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(responseText.getBytes());
                output.flush();
            } 
            else
            {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));

        server.createContext("/api/bye", (exchange -> {

            if ("GET".equals(exchange.getRequestMethod())) {
                String responseText = "bye bye! from our framework-less REST API\n";
                exchange.sendResponseHeaders(200, responseText.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(responseText.getBytes());
                output.flush();
            } 
            else
            {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));
               
        server.createContext("/api/person", new PersonHandler(store));       

        // All contexts has been created
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("The framework-less REST API server is listening on " + server.getAddress().getAddress() + ":" + server.getAddress().getPort());
    }
}