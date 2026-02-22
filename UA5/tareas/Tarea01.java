import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Tarea01 {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/api/hash", new HashHandler());
        server.setExecutor(null);
        System.out.println("Servidor de Hash iniciado en el puerto 8081...");
        server.start();
    }

    static class HashHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange ex) throws IOException {
            Map<String, String> params = parseQueryParams(ex.getRequestURI().getQuery());
            String text = params.get("text");

            if (text == null) {
                sendResponse(ex, 400, "Bad Request", null, params);
                return;
            }

            String hash = calcularMD5(text);

            Map<String, Object> body = new LinkedHashMap<>();
            body.put("algorithm", "md5");
            body.put("text", text);
            body.put("hash", hash);

            sendResponse(ex, 200, "OK", body, params);
        }

        private String calcularMD5(String input) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                for (byte b : messageDigest) {
                    sb.append(String.format("%02x", b));
                }
                return sb.toString();
            } catch (Exception e) { return null; }
        }

        private void sendResponse(HttpExchange ex, int status, String msg, Map<String, Object> body, Map<String, String> params) throws IOException {
            Map<String, Object> header = new LinkedHashMap<>();
            header.put("api_name", "Hash API");
            header.put("api_endpoint", "/api/hash");
            header.put("http_request_method", ex.getRequestMethod());
            header.put("http_request_parameters", params);
            header.put("http_response_status", status);
            header.put("http_response_message", msg);
            header.put("response_time", System.currentTimeMillis());

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("header", header);
            response.put("body", body != null ? body : new LinkedHashMap<>());

            byte[] bytes = toJson(response).getBytes(StandardCharsets.UTF_8);
            ex.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
            ex.sendResponseHeaders(status, bytes.length);
            ex.getResponseBody().write(bytes);
            ex.getResponseBody().close();
        }

        private Map<String, String> parseQueryParams(String query) {
            Map<String, String> map = new HashMap<>();
            if (query == null) return map;
            for (String pair : query.split("&")) {
                String[] kv = pair.split("=", 2);
                map.put(kv[0], kv.length > 1 ? kv[1].replace("+", " ") : "");
            }
            return map;
        }

        private String toJson(Object obj) {
            if (obj == null) return "null";
            if (obj instanceof String s) return "\"" + escapeJson(s) + "\"";
            if (obj instanceof Number || obj instanceof Boolean) return obj.toString();
            if (obj instanceof Map<?, ?> m) {
                StringBuilder sb = new StringBuilder("{");
                boolean first = true;
                for (Map.Entry<?, ?> e : m.entrySet()) {
                    if (!first) sb.append(",");
                    first = false;
                    sb.append(toJson(String.valueOf(e.getKey())));
                    sb.append(":");
                    sb.append(toJson(e.getValue()));
                }
                sb.append("}");
                return sb.toString();
            }
            if (obj instanceof Collection<?> c) {
                StringBuilder sb = new StringBuilder("[");
                boolean first = true;
                for (Object item : c) {
                    if (!first) sb.append(",");
                    first = false;
                    sb.append(toJson(item));
                }
                sb.append("]");
                return sb.toString();
            }
            return "\"" + escapeJson(String.valueOf(obj)) + "\"";
        }

        private String escapeJson(String s) {
            return s
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
        }
    }
}