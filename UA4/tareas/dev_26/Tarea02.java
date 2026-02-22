import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

class Persona {
    String name;
    String about;
    int birthYear;

    public Persona(String name, String about, int birthYear) {
        this.name = name;
        this.about = about;
        this.birthYear = birthYear;
    }
}

public class Tarea02 {
    private static List<Persona> deposito = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        server.createContext("/api/greeting", exchange -> enviarRespuesta(exchange, "Hola!", 200));
        server.createContext("/api/bye", exchange -> enviarRespuesta(exchange, "Adiós!", 200));
        
        server.createContext("/api/person", new PersonHandler());

        server.setExecutor(null);
        System.out.println("Servidor iniciado en http://localhost:8080");
        server.start();
    }

    static class PersonHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            Map<String, String> params = getQueryParams(exchange.getRequestURI().getQuery());
            
            if (params.isEmpty() || (method.equals("GET") && !params.containsKey("name")) || 
               ((method.equals("POST") || method.equals("PUT")) && !params.containsKey("name"))) {
                enviarRespuesta(exchange, "400 Bad Request: Parámetros incorrectos", 400);
                return;
            }

            String name = params.get("name");

            switch (method) {
                case "GET":
                    handleRead(exchange, name);
                    break;
                case "POST":
                    handleCreate(exchange, params);
                    break;
                case "PUT":
                    handleUpdate(exchange, params);
                    break;
                case "DELETE":
                    handleDelete(exchange, name);
                    break;
                default:
                    enviarRespuesta(exchange, "Método no soportado", 405);
            }
        }

        private void handleRead(HttpExchange exchange, String name) throws IOException {
            for (Persona p : deposito) {
                if (p.name.equalsIgnoreCase(name)) {
                    String res = "Nombre: " + p.name + ", Info: " + p.about + ", Año: " + p.birthYear;
                    enviarRespuesta(exchange, res, 200);
                    return;
                }
            }
            enviarRespuesta(exchange, "404 Not Found", 404);
        }

        private void handleCreate(HttpExchange exchange, Map<String, String> params) throws IOException {
            try {
                Persona nueva = new Persona(params.get("name"), params.get("about"), Integer.parseInt(params.get("birthYear")));
                deposito.add(nueva);
                enviarRespuesta(exchange, "Persona creada con éxito", 200);
            } catch (Exception e) {
                enviarRespuesta(exchange, "400 Bad Request: Datos inválidos", 400);
            }
        }

        private void handleUpdate(HttpExchange exchange, Map<String, String> params) throws IOException {
            for (Persona p : deposito) {
                if (p.name.equalsIgnoreCase(params.get("name"))) {
                    p.about = params.get("about");
                    p.birthYear = Integer.parseInt(params.get("birthYear"));
                    enviarRespuesta(exchange, "Persona actualizada", 200);
                    return;
                }
            }
            enviarRespuesta(exchange, "404 Not Found", 404);
        }

        private void handleDelete(HttpExchange exchange, String name) throws IOException {
            boolean removed = deposito.removeIf(p -> p.name.equalsIgnoreCase(name));
            if (removed) enviarRespuesta(exchange, "Persona eliminada", 200);
            else enviarRespuesta(exchange, "404 Not Found", 404);
        }
    }
    private static void enviarRespuesta(HttpExchange exchange, String resp, int code) throws IOException {
        exchange.sendResponseHeaders(code, resp.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(resp.getBytes());
        os.close();
    }

    private static Map<String, String> getQueryParams(String query) {
        Map<String, String> result = new HashMap<>();
        if (query == null) return result;
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) result.put(entry[0], entry[1].replace("+", " "));
            else result.put(entry[0], "");
        }
        return result;
    }
}
