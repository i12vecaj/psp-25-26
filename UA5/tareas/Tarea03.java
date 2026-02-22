import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;

public class Tarea03 {
    public static void main(String[] args) throws Exception {
        int port = 8443;
        String hostname = "localhost";

        HttpsServer server = HttpsServer.create(new InetSocketAddress(hostname, port), 0);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        char[] password = "password".toCharArray();
        KeyStore ks = KeyStore.getInstance("JKS");
        FileInputStream fis = new FileInputStream("keystore.jks");
        ks.load(fis, password);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, password);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ks);

        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        server.setHttpsConfigurator(new HttpsConfigurator(sslContext));

        server.createContext("/api/secure", (exchange) -> {
            String resp = "{\"status\":\"Conexión Segura Establecida\"}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, resp.length());
            exchange.getResponseBody().write(resp.getBytes());
            exchange.getResponseBody().close();
        });

        server.setExecutor(null);
        System.out.println("Servidor HTTPS funcionando en: https://" + hostname + ":" + port);
        server.start();
    }
}