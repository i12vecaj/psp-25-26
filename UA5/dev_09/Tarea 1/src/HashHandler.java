import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashHandler implements HttpHandler {

    private static final String API_NAME    = "david alberto cruz barranco";
    private static final String API_VERSION = "1.0.0";
    private static final String API_USER    = "david alberto cruz barranco";
    private static final String ALGORITHM   = "md5";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            exchange.close();
            return;
        }

        long   startTime = System.nanoTime();
        String text      = extractTextParam(exchange.getRequestURI().getQuery());
        String hash      = generateMD5(text);
        long   elapsed   = System.nanoTime() - startTime;

        String json = buildJsonResponse(text, hash, elapsed);

        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        Api.sendResponse(exchange, 200, json);
    }

    private String extractTextParam(String query) {
        if (query == null) return "";
        for (String pair : query.split("&")) {
            if (pair.startsWith("text=")) {
                return URLDecoder.decode(pair.substring(5), StandardCharsets.UTF_8);
            }
        }
        return "";
    }

    private String buildJsonResponse(String text, String hash, long responseTime) {
        return "{\n" +
                "  \"header\": {\n" +
                "    \"api_name\": \""      + API_NAME    + "\",\n" +
                "    \"api_version\": \""   + API_VERSION + "\",\n" +
                "    \"api_user\": \""      + API_USER    + "\",\n" +
                "    \"api_endpoint\": \"api/hash/\",\n" +
                "    \"http_request_method\": \"GET\",\n" +
                "    \"http_request_parameters\": {\n" +
                "      \"algorithm\": \""   + ALGORITHM   + "\",\n" +
                "      \"text\": \""        + text        + "\"\n" +
                "    },\n" +
                "    \"http_response_status\": 200,\n" +
                "    \"http_response_message\": \"OK\",\n" +
                "    \"response_time\": "   + responseTime + "\n" +
                "  },\n" +
                "  \"body\": {\n" +
                "    \"algorithm\": \""     + ALGORITHM   + "\",\n" +
                "    \"text\": \""          + text        + "\",\n" +
                "    \"hash\": \""          + hash        + "\"\n" +
                "  }\n" +
                "}";
    }

    private String generateMD5(String text) {
        try {
            MessageDigest md     = MessageDigest.getInstance("MD5");
            byte[]        digest = md.digest(text.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb     = new StringBuilder(32);
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }
}