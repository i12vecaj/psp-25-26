import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) throws IOException {

        String hostname = "localhost";
        int port = 8080;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-p": case "--port":
                    port = Integer.parseInt(args[++i]);
                    break;
                case "-h": case "--hostname":
                    hostname = args[++i];
                    break;
                case "-v": case "--version":
                    System.out.println("API Version: 1.0.0");
                    return;
                case "--help":
                    System.out.println("Uso: java Main [-p puerto] [-h hostname] [-v]");
                    return;
            }
        }

        HttpServer server = HttpServer.create(new InetSocketAddress(hostname, port), 0);

        server.createContext("/api/hash", new HashHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("API arrancada en http://" + hostname + ":" + port);
    }
}