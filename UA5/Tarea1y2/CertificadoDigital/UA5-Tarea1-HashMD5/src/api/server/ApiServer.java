package api.server;

import api.controllers.HashController;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Punto de entrada de la API.
 *
 * Inicia el servidor HTTP en localhost:8080 y registra los endpoints disponibles.
 *
 * Endpoints disponibles:
 *   GET /api/hash/?algorithm=md5&text=TuTexto
 *
 * Ejemplos:
 *   http://localhost:8080/api/hash/?text=Hola%20mundo
 *   http://localhost:8080/api/hash/?algorithm=md5&text=Generando+un+MD5+de+un+texto
 *   http://localhost:8080/api/hash/?algorithm=sha256&text=ejemplo
 */
public class ApiServer {

    public static void main(String[] args) throws IOException {

        // Crear el servidor HTTP
        HttpServer server = HttpServer.create(
                new InetSocketAddress(ApiConfig.HOST, ApiConfig.PORT), 0);

        // Registrar controladores (endpoints)
        server.createContext(ApiConfig.ENDPOINT_HASH, new HashController());

        // Usar un pool de hilos para atender peticiones concurrentes
        server.setExecutor(Executors.newCachedThreadPool());

        // Arrancar el servidor
        server.start();

        System.out.println("===========================================");
        System.out.println("  API Server iniciado correctamente");
        System.out.println("  Nombre   : " + ApiConfig.API_NAME);
        System.out.println("  Versión  : " + ApiConfig.API_VERSION);
        System.out.println("  URL base : http://" + ApiConfig.HOST + ":" + ApiConfig.PORT);
        System.out.println("-------------------------------------------");
        System.out.println("  Endpoints disponibles:");
        System.out.println("  GET http://" + ApiConfig.HOST + ":" + ApiConfig.PORT
                           + ApiConfig.ENDPOINT_HASH + "?algorithm=md5&text=TuTexto");
        System.out.println("===========================================");
        System.out.println("  Presiona Ctrl+C para detener el servidor.");
    }
}