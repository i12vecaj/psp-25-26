import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class Md5Endpoint implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        long startTime = System.currentTimeMillis();

        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            send(exchange, 405, buildError("Método no permitido", startTime));
            return;
        }

        Map<String, String> params = parseQuery(exchange.getRequestURI().getQuery());

        String algorithm = params.getOrDefault("algorithm", "md5");
        String text = params.get("text");

        if (text == null || text.isEmpty()) {
            send(exchange, 400, buildError("Falta el parámetro 'text'", startTime));
            return;
        }

        String hash = generateMD5(text);

        String json = buildSuccess(exchange, algorithm, text, hash, startTime);
        send(exchange, 200, json);
    }

    private String generateMD5(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(text.getBytes());

            StringBuilder hex = new StringBuilder();
            for (byte b : digest) {
                String h = Integer.toHexString(0xff & b);
                if (h.length() == 1) hex.append('0');
                hex.append(h);
            }
            return hex.toString();

        } catch (Exception e) {
            return "error";
        }
    }


    private String buildSuccess(HttpExchange exchange, String algorithm, String text, String hash, long start) {

        long responseTime = System.currentTimeMillis() - start;

        return "{\n" +
                "  \"header\": {\n" +
                "    \"api_name\": \"" + ApiServer.API_NAME + "\",\n" +
                "    \"api_version\": \"" + ApiServer.API_VERSION + "\",\n" +
                "    \"api_user\": \"" + ApiServer.API_USER + "\",\n" +
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
    }


    private String buildError(String msg, long start) {

        long responseTime = System.currentTimeMillis() - start;

        return "{\n" +
                "  \"header\": {\n" +
                "    \"http_response_status\": 400,\n" +
                "    \"http_response_message\": \"" + msg + "\",\n" +
                "    \"response_time\": " + responseTime + "\n" +
                "  }\n" +
                "}";
    }


    private void send(HttpExchange exchange, int status, String json) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, json.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(json.getBytes());
        os.close();
    }

    private Map<String, String> parseQuery(String query) {
        Map<String, String> map = new HashMap<>();

        if (query == null) return map;

        for (String param : query.split("&")) {
            String[] pair = param.split("=");
            if (pair.length == 2) map.put(pair[0], pair[1]);
        }
        return map;
    }
}
