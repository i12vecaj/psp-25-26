import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.OutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

class HashHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String remoteAddress = exchange.getRemoteAddress().getHostString();
        System.out.println("[" + new Date() + "] Received GET " + exchange.getRequestURI() + " from client: " + remoteAddress);

        if (!"GET".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            exchange.close();
            return;
        }

        long startTime = System.currentTimeMillis();

        Map<String, String> params = BasicHandler.queryToMap(exchange.getRequestURI().getQuery());
        String text = params.get("text");
        String algorithm = params.getOrDefault("algorithm", "md5").toLowerCase();

        if (text == null || text.isEmpty()) {
            exchange.sendResponseHeaders(400, -1); // Bad Request
            exchange.close();
            return;
        }

        String hash = "";

        try {
            MessageDigest md;
            switch (algorithm) {

                case "md5":
                    md = MessageDigest.getInstance("MD5");
                    break;

                case "sha1":
                    md = MessageDigest.getInstance("SHA-1");
                    break;

                case "sha256":
                    md = MessageDigest.getInstance("SHA-256");
                    break;

                default:
                    exchange.sendResponseHeaders(400, -1); // Unsupported algorithm
                    exchange.close();
                    return;
            }

            byte[] digest = md.digest(text.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            hash = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            exchange.sendResponseHeaders(500, -1);
            exchange.close();
            return;
        }

        long responseTime = System.currentTimeMillis() - startTime;

        String jsonResponse = "{\n" +
                "  \"header\": {\n" +
                "    \"api_name\": \"My API Name\",\n" +
                "    \"api_version\": \"1.0.0\",\n" +
                "    \"api_user\": \"guest\",\n" +
                "    \"api_endpoint\": \"api/hash/\",\n" +
                "    \"http_request_method\": \"GET\",\n" +
                "    \"http_request_parameters\": {\n" +
                "      \"algorithm\": \"" + algorithm + "\",\n" +
                "      \"text\": \"" + text + "\"\n" +
                "    },\n" +
                "    \"http_response_status\": 200,\n" +
                "    \"http_response_message\": \"OK\",\n" +
                "    \"response_time\": " + responseTime + "\n" +
                "  },\n" +
                "  \"body\": {\n" +
                "    \"algorithm\": \"" + algorithm + "\",\n" +
                "    \"text\": \"" + text + "\",\n" +
                "    \"hash\": \"" + hash + "\"\n" +
                "  }\n" +
                "}";

        // Crear carpeta "resultado" si no existe
        File folder = new File("resultado");
        if (!folder.exists()) {
            folder.mkdir();
        }

        // Guardar JSON en resultado/hash_output.json
        try (FileWriter file = new FileWriter(new File(folder, "hash_output.json"))) {
            file.write(jsonResponse);
            file.flush();
        } catch (IOException e) {
            System.err.println("Error al guardar el JSON: " + e.getMessage());
        }

        // Enviar JSON al cliente
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, jsonResponse.getBytes().length);
        OutputStream output = exchange.getResponseBody();
        output.write(jsonResponse.getBytes());
        output.flush();
        exchange.close();
    }

}
