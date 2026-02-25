import java.io.OutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import com.sun.net.httpserver.HttpExchange;

class HashHandler extends BasicHandler {

    public HashHandler(DataStore store) {
        super(store);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            String query = exchange.getRequestURI().getQuery();
            Map<String, String> params = queryToMap(query);
            String text = params.get("text");

            if (text == null) {
                exchange.sendResponseHeaders(400, -1); // Bad Request
            } else {
                String hash = calculateMD5(text);
                String responseString = "{\"text\": \"" + text + "\", \"hash\": \"" + hash + "\"}";
                
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, responseString.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(responseString.getBytes());
                output.flush();
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
        exchange.close();
    }

    private String calculateMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
