package org.example;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static final String API_NAME = "My API Name";
    public static final String API_VERSION = "1.0.0";
    
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
        server.createContext("/api/hash/", new HashHandler());
        
        server.setExecutor(null);
        server.start();
        System.out.println("Servidor API escuchando en " + 
                         server.getAddress().getAddress() + ":" + 
                         server.getAddress().getPort());
    }
}