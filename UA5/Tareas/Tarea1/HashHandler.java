import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

class HashHandler extends BasicHandler {
    private static final String API_NAME = "My API Name";
    private static final String API_VERSION = "1.0.0";
    private static final String API_USER = "guest";
    private static final String API_ENDPOINT = "api/hash/";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        long startTime = System.nanoTime();

        String requestMethod = exchange.getRequestMethod();
        Map<String, String> parameters = queryToMap(exchange.getRequestURI().getRawQuery());
        String algorithm = parameters.getOrDefault("algorithm", "md5");
        String text = parameters.get("text");

        int statusCode;
        String responseMessage;
        String bodyJson;

        if (!"GET".equalsIgnoreCase(requestMethod)) {
            statusCode = 405;
            responseMessage = "Method Not Allowed";
            bodyJson = "{\"error\":\"Only GET is supported\"}";
        } else if (text == null) {
            statusCode = 400;
            responseMessage = "Bad Request";
            bodyJson = "{\"error\":\"Missing required parameter: text\"}";
        } else if (!"md5".equalsIgnoreCase(algorithm)) {
            statusCode = 400;
            responseMessage = "Bad Request";
            bodyJson = "{\"error\":\"Unsupported algorithm. Use md5\"}";
        } else {
            algorithm = "md5";
            String hash = md5(text);
            statusCode = 200;
            responseMessage = "OK";
            bodyJson = "{"
                    + "\"algorithm\":\"" + jsonEscape(algorithm) + "\","
                    + "\"text\":\"" + jsonEscape(text) + "\","
                    + "\"hash\":\"" + hash + "\""
                    + "}";
        }

        long responseTime = System.nanoTime() - startTime;
        String responseJson = buildResponseJson(
                requestMethod,
                algorithm.toLowerCase(),
                text,
                statusCode,
                responseMessage,
                responseTime,
                bodyJson
        );

        sendJsonResponse(exchange, statusCode, responseJson);
    }

    private String buildResponseJson(
            String requestMethod,
            String algorithm,
            String text,
            int statusCode,
            String responseMessage,
            long responseTime,
            String bodyJson
    ) {
        return "{"
                + "\"header\":{"
                + "\"api_name\":\"" + jsonEscape(API_NAME) + "\","
                + "\"api_version\":\"" + jsonEscape(API_VERSION) + "\","
                + "\"api_user\":\"" + jsonEscape(API_USER) + "\","
                + "\"api_endpoint\":\"" + jsonEscape(API_ENDPOINT) + "\","
                + "\"http_request_method\":\"" + jsonEscape(requestMethod) + "\","
                + "\"http_request_parameters\":{"
                + "\"algorithm\":\"" + jsonEscape(algorithm) + "\","
                + "\"text\":\"" + jsonEscape(text) + "\""
                + "},"
                + "\"http_response_status\":" + statusCode + ","
                + "\"http_response_message\":\"" + jsonEscape(responseMessage) + "\","
                + "\"response_time\":" + responseTime
                + "},"
                + "\"body\":" + bodyJson
                + "}";
    }

    private String md5(String text) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] digest = messageDigest.digest(text.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 is not available in this JVM", e);
        }
    }
}
