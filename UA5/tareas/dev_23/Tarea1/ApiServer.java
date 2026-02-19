import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class ApiServer {

    public static final String API_NAME = "My API Name";
    public static final String API_VERSION = "1.0.0";
    public static final String API_USER = "guest";

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/api/hash/", new Md5Endpoint());

        server.setExecutor(null);
        server.start();

        System.out.println("Servidor iniciado en http://localhost:8080/api/hash/");
    }
}
