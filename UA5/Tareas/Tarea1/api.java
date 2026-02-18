import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;

public class api {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);

        // Crear contexto para el endpoint de hash MD5
        server.createContext("/api/hash/", new HashHandler());

        // Iniciar el servidor
        server.setExecutor(null);
        server.start();
        System.out.println("[" + new Date() + "] Hash API server is listening on " +
                server.getAddress().getAddress() + ":" + server.getAddress().getPort());
    }
}
