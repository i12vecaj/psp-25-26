package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

public class HashHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Log de auditoría simple
        String clientIp = exchange.getRemoteAddress().getAddress().getHostAddress();
        System.out.println(LocalDateTime.now() + " | Request: " + exchange.getRequestMethod() + " de " + clientIp);

        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            exchange.close();
            return;
        }

        processGet(exchange);
    }

    private void processGet(HttpExchange exchange) throws IOException {
        long startTimer = System.currentTimeMillis();
        Map<String, String> queryParams = parseQuery(exchange.getRequestURI().getQuery());

        String algo = queryParams.get("algorithm");
        String input = queryParams.get("text");

        // Validación de entrada
        if (algo == null || input == null) {
            dispatchResponse(exchange, 400, buildJsonResponse(400, "Parámetros insuficientes", null, startTimer));
            return;
        }

        if (!"md5".equalsIgnoreCase(algo)) {
            dispatchResponse(exchange, 400, buildJsonResponse(400, "Algoritmo '" + algo + "' no soportado", null, startTimer));
            return;
        }

        try {
            String resultHash = computeMd5(input);
            String json = buildJsonResponse(200, "OK", resultHash, startTimer, algo, input);
            dispatchResponse(exchange, 200, json);
        } catch (Exception e) {
            dispatchResponse(exchange, 500, buildJsonResponse(500, "Error interno: " + e.getMessage(), null, startTimer));
        } finally {
            exchange.close();
        }
    }

    private String computeMd5(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(data.getBytes(StandardCharsets.UTF_8));

        // Conversión a Hexadecimal usando un estilo más moderno
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private void dispatchResponse(HttpExchange exchange, int code, String body) throws IOException {
        byte[] responseBytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(code, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }

    private String buildJsonResponse(int status, String msg, String hash, long start, String... extras) {
        long elapsed = System.currentTimeMillis() - start;

        // Estructura simplificada pero compatible con tu esquema anterior
        StringBuilder json = new StringBuilder("{\n");
        json.append("  \"meta\": {\n");
        json.append("    \"service\": \"").append(api.APP_ID).append("\",\n");
        json.append("    \"status\": ").append(status).append(",\n");
        json.append("    \"ms\": ").append(elapsed).append("\n");
        json.append("  },\n");
        json.append("  \"data\": {\n");
        json.append("    \"message\": \"").append(msg).append("\",\n");
        if (hash != null) {
            json.append("    \"algorithm\": \"").append(extras[0]).append("\",\n");
            json.append("    \"input\": \"").append(sanitize(extras[1])).append("\",\n");
            json.append("    \"hash\": \"").append(hash).append("\"\n");
        }
        json.append("  }\n}");
        return json.toString();
    }

    private Map<String, String> parseQuery(String query) {
        Map<String, String> map = new HashMap<>();
        if (query == null || query.isEmpty()) return map;

        for (String entry : query.split("&")) {
            String[] parts = entry.split("=", 2);
            String key = parts[0];
            String value = parts.length > 1 ? java.net.URLDecoder.decode(parts[1], StandardCharsets.UTF_8) : "";
            map.put(key, value);
        }
        return map;
    }

    private String sanitize(String val) {
        return val.replace("\"", "\\\"");
    }
}