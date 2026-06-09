package org.example;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class api {
    // Definición de constantes del sistema
    public static final String APP_ID = "HashService-v1";
    public static final String VERSION = "1.0.4";
    private static final int LISTEN_PORT = 8080;

    public static void main(String[] args) {
        try {
            // Inicialización del servidor en la interfaz local
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", LISTEN_PORT), 0);

            // Registro de rutas (endpoints)
            server.createContext("/api/hash/", new HashHandler());

            // Executor nulo usa la implementación por defecto del sistema
            server.setExecutor(null);
            server.start();

            System.out.printf("[%s] Servidor activo en el puerto %d%n", APP_ID, LISTEN_PORT);

        } catch (IOException e) {
            System.err.println("Error fatal al iniciar el servidor: " + e.getMessage());
        }
    }
}