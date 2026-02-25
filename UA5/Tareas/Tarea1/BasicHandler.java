import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

abstract class BasicHandler implements HttpHandler {

    protected static Map<String, String> queryToMap(String query) {
        Map<String, String> result = new LinkedHashMap<>();
        if (query == null || query.isBlank()) {
            return result;
        }

        for (String param : query.split("&")) {
            if (param.isEmpty()) {
                continue;
            }

            String[] pair = param.split("=", 2);
            String key = urlDecode(pair[0]);
            String value = pair.length > 1 ? urlDecode(pair[1]) : "";
            result.put(key, value);
        }

        return result;
    }

    protected static void sendJsonResponse(HttpExchange exchange, int statusCode, String jsonBody) throws IOException {
        byte[] payload = jsonBody.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, payload.length);
        try (OutputStream output = exchange.getResponseBody()) {
            output.write(payload);
            output.flush();
        }
    }

    protected static String jsonEscape(String value) {
        if (value == null) {
            return "";
        }

        String escaped = value;
        escaped = escaped.replace("\\", "\\\\");
        escaped = escaped.replace("\"", "\\\"");
        escaped = escaped.replace("\b", "\\b");
        escaped = escaped.replace("\f", "\\f");
        escaped = escaped.replace("\n", "\\n");
        escaped = escaped.replace("\r", "\\r");
        escaped = escaped.replace("\t", "\\t");
        return escaped;
    }

    private static String urlDecode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }
}
