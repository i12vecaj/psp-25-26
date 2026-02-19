import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class api {
    private static final String API_NAME = "My API Name";
    private static final String API_VERSION = "1.0.0";

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
        server.createContext("/api/hash", new HashHandler(API_NAME, API_VERSION));
        server.createContext("/api/hash/", new HashHandler(API_NAME, API_VERSION));
        server.setExecutor(null);
        server.start();
        System.out.println("The framework-less REST API server is listening on " + server.getAddress().getHostString() + ":" + server.getAddress().getPort());
    }
}
