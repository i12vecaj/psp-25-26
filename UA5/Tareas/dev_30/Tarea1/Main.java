package com.proyect;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static final String API_NAME = "My API Name";
    public static final String API_VERSION = "1.0.0";
    
    public static void main(String[] args) throws IOException {
        // Crear servidor HTTP en localhost:8080
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
        
        // Crear el endpoint /api/hash/
        server.createContext("/api/hash/", new HashHandler());
        
        // Configurar el executor e iniciar el servidor
        server.setExecutor(null); // crea un executor por defecto
        server.start();
        
        System.out.println("Servidor API escuchando en " + 
                         server.getAddress().getAddress() + ":" + 
                         server.getAddress().getPort());
    }
}