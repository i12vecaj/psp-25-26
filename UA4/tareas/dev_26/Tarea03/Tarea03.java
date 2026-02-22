import com.sun.net.httpserver.*;
import org.json.JSONObject;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.HashMap;

interface Repository {
    Persona findByName(String name);
    void save(Persona p);
    boolean update(Persona p);
    boolean delete(String name);
}

class DataWarehouse implements Repository {
    private Map<String, Persona> storage = new HashMap<>();

    @Override
    public Persona findByName(String name) {
        return storage.get(name);
    }

    @Override
    public void save(Persona p) {
        storage.put(p.name, p);
    }

    @Override
    public boolean update(Persona p) {
        if (storage.containsKey(p.name)) {
            storage.put(p.name, p);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String name) {
        return storage.remove(name) != null;
    }
}

class Persona {
    public String name;
    public String about;
    public int birthYear;

    public Persona(String name, String about, int birthYear) {
        this.name = name;
        this.about = about;
        this.birthYear = birthYear;
    }
}

public class Tarea03 {
    private static String version = "1.0.0";
    private static String hostname = "localhost";
    private static int port = 8080;
    private static Repository db = new DataWarehouse(); // capa de abstracción

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-v": case "--version":
                    System.out.println("API Version: " + version);
                    return;
                case "-h": case "--hostname":
                    hostname = args[++i];
                    break;
                case "-p": case "--port":
                    port = Integer.parseInt(args[++i]);
                    break;
                case "--help":
                    mostrarAyuda();
                    return;
            }
        }

        HttpServer server = HttpServer.create(new InetSocketAddress(hostname, port), 0);
        

        server.createContext("/api/greeting", new GreetingHandler());
        server.createContext("/api/bye", new ByeHandler());
        server.createContext("/api/person", new PersonHandler(db));

        server.setExecutor(null);
        System.out.println("API " + version + " iniciada en http://" + hostname + ":" + port);
        server.start();
    }

    public static void sendResponse(HttpExchange ex, int status, String message, JSONObject bodyData) throws IOException {
        JSONObject response = new JSONObject();
        
        JSONObject header = new JSONObject();
        header.put("api_name", "My API Refactored");
        header.put("api_version", version);
        header.put("api_endpoint", ex.getRequestURI().getPath());
        header.put("http_request_method", ex.getRequestMethod());
        header.put("http_response_status", status);
        header.put("http_response_message", message);
        header.put("response_time", System.currentTimeMillis());
        
        response.put("header", header);
        response.put("body", bodyData != null ? bodyData : new JSONObject());

        byte[] bytes = response.toString(2).getBytes(); // Pretty print indent=2
        ex.getResponseHeaders().set("Content-Type", "application/json");
        ex.sendResponseHeaders(status, bytes.length);
        OutputStream os = ex.getResponseBody();
        os.write(bytes);
        os.close();
    }

    private static void mostrarAyuda() {
        System.out.println("Uso: java Tarea3 [opciones]");
        System.out.println("-h, --hostname  IP del servidor (default: localhost)");
        System.out.println("-p, --port      Puerto (default: 8080)");
        System.out.println("-v, --version   Muestra versión");
    }
}

class GreetingHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange ex) throws IOException {
        JSONObject body = new JSONObject().put("message", "Hola desde la API refactorizada");
        Tarea03.sendResponse(ex, 200, "OK", body);
    }
}

class ByeHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange ex) throws IOException {
        JSONObject body = new JSONObject().put("message", "Adiós desde la API refactorizada");
        Tarea03.sendResponse(ex, 200, "OK", body);
    }
}

class PersonHandler implements HttpHandler {
    private Repository repo;
    public PersonHandler(Repository repo) { this.repo = repo; }

    @Override
    public void handle(HttpExchange ex) throws IOException {
        String method = ex.getRequestMethod();
        Map<String, String> params = parseQueryParams(ex.getRequestURI().getQuery());
        String name = params.get("name");

        try {
            if (name == null) {
                Tarea03.sendResponse(ex, 400, "Bad Request", new JSONObject().put("error", "Falta el nombre"));
                return;
            }

            switch (method) {
                case "GET":
                    Persona p = repo.findByName(name);
                    if (p != null) {
                        JSONObject body = new JSONObject()
                            .put("name", p.name)
                            .put("about", p.about)
                            .put("birthYear", p.birthYear);
                        Tarea03.sendResponse(ex, 200, "OK", body);
                    } else {
                        Tarea03.sendResponse(ex, 404, "Not Found", null);
                    }
                    break;
                case "POST":
                    repo.save(new Persona(name, params.get("about"), Integer.parseInt(params.get("birthYear"))));
                    Tarea03.sendResponse(ex, 200, "Persona Creada", null);
                    break;
                case "PUT":
                    if (repo.update(new Persona(name, params.get("about"), Integer.parseInt(params.get("birthYear"))))) {
                        Tarea03.sendResponse(ex, 200, "Persona Actualizada", null);
                    } else {
                        Tarea03.sendResponse(ex, 404, "Not Found", null);
                    }
                    break;
                case "DELETE":
                    if (repo.delete(name)) {
                        Tarea03.sendResponse(ex, 200, "Persona Eliminada", null);
                    } else {
                        Tarea03.sendResponse(ex, 404, "Not Found", null);
                    }
                    break;
                default:
                    Tarea03.sendResponse(ex, 405, "Method Not Allowed", null);
            }
        } catch (Exception e) {
            Tarea03.sendResponse(ex, 400, "Bad Request", new JSONObject().put("error", e.getMessage()));
        }
    }

    private Map<String, String> parseQueryParams(String query) {
        Map<String, String> map = new HashMap<>();
        if (query == null) return map;
        for (String pair : query.split("&")) {
            String[] kv = pair.split("=");
            map.put(kv[0], kv.length > 1 ? kv[1].replace("+", " ") : "");
        }
        return map;
    }
}
