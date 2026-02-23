import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class HashHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        long startTime = System.currentTimeMillis();
        String endpoint = "api/hash/";
        String method = exchange.getRequestMethod();

        if (!method.equalsIgnoreCase("GET")) {
            sendResponse(exchange, 405, "Method Not Allowed", null, null, endpoint, method, startTime);
            return;
        }

        String query = exchange.getRequestURI().getQuery();
        Map<String, String> params = parseQuery(query);

        if (!params.containsKey("text") || !params.containsKey("algorithm")) {
            sendResponse(exchange, 400, "Bad Request", null, params, endpoint, method, startTime);
            return;
        }

        String algorithm = params.get("algorithm").toLowerCase();
        String text = params.get("text");

        if (!algorithm.equals("md5")) {
            sendResponse(exchange, 400, "Bad Request - Unsupported algorithm", null, params, endpoint, method, startTime);
            return;
        }

        String hash = generateMD5(text);

        if (hash == null) {
            sendResponse(exchange, 500, "Internal Server Error", null, params, endpoint, method, startTime);
            return;
        }

        JSONObject body = new JSONObject();
        body.put("algorithm", algorithm);
        body.put("text", text);
        body.put("hash", hash);

        sendResponse(exchange, 200, "OK", body, params, endpoint, method, startTime);
    }

    private String generateMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Map<String, String> parseQuery(String query) {
        Map<String, String> params = new HashMap<>();
        if (query == null || query.isEmpty()) return params;
        for (String pair : query.split("&")) {
            String[] kv = pair.split("=", 2);
            if (kv.length == 2) {
                params.put(
                        URLDecoder.decode(kv[0], StandardCharsets.UTF_8),
                        URLDecoder.decode(kv[1], StandardCharsets.UTF_8)
                );
            }
        }
        return params;
    }

    private void sendResponse(HttpExchange exchange, int status, String message,
                              JSONObject body, Map<String, String> params,
                              String endpoint, String method, long startTime) throws IOException {

        JSONObject header = new JSONObject();
        header.put("api_name", "My API Name");
        header.put("api_version", "1.0.0");
        header.put("api_user", "guest");
        header.put("api_endpoint", endpoint);
        header.put("http_request_method", method);
        header.put("http_request_parameters", params != null ? new JSONObject(params) : new JSONObject());
        header.put("http_response_status", status);
        header.put("http_response_message", message);
        header.put("response_time", System.currentTimeMillis() - startTime);

        JSONObject response = new JSONObject();
        response.put("header", header);
        response.put("body", body != null ? body : new JSONObject());

        byte[] responseBytes = response.toString(2).getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(status, responseBytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}