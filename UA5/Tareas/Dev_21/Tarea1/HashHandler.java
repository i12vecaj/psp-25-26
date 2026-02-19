import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.Map;
import com.sun.net.httpserver.HttpExchange;

class HashHandler extends BasicHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());

        String text = params.get("text");

        if (text == null || text.isEmpty()) {
            exchange.sendResponseHeaders(400, -1);
            return;
        }

        String hash = generateMD5(text);

        String response = "{"
                + "\"algorithm\":\"md5\","
                + "\"text\":\"" + text + "\","
                + "\"hash\":\"" + hash + "\""
                + "}";

        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, response.length());

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String generateMD5(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(text.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            return "error";
        }
    }
}
