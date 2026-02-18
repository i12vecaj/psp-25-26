import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class HashHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            exchange.close();
            return;
        }

        long startTime = System.nanoTime();

        String query = exchange.getRequestURI().getQuery();
        String text = "";

        if (query != null && query.startsWith("text=")) {
            text = URLDecoder.decode(query.substring(5), StandardCharsets.UTF_8);
        }

        String hash = generateMD5(text);
        long responseTime = System.nanoTime() - startTime;

        String responseText = "{\n" +
                "  \"header\": {\n" +
                "    \"api_name\": \"My API Name\",\n" +
                "    \"api_version\": \"1.0.0\",\n" +
                "    \"api_user\": \"guest\",\n" +
                "    \"api_endpoint\": \"api/hash/\",\n" +
                "    \"http_request_method\": \"GET\",\n" +
                "    \"http_request_parameters\": {\n" +
                "      \"algorithm\": \"md5\",\n" +
                "      \"text\": \"" + text + "\"\n" +
                "    },\n" +
                "    \"http_response_status\": 200,\n" +
                "    \"http_response_message\": \"OK\",\n" +
                "    \"response_time\": " + responseTime + "\n" +
                "  },\n" +
                "  \"body\": {\n" +
                "    \"algorithm\": \"md5\",\n" +
                "    \"text\": \"" + text + "\",\n" +
                "    \"hash\": \"" + hash + "\"\n" +
                "  }\n" +
                "}";

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, responseText.getBytes().length);

        OutputStream output = exchange.getResponseBody();
        output.write(responseText.getBytes());
        output.close();
        exchange.close();
    }

    private String generateMD5(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(text.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (Exception e) {
            return "";
        }
    }
}
