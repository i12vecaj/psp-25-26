import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

class HashHandler extends BasicHandler {
    private final String apiName;
    private final String apiVersion;

    public HashHandler(String apiName, String apiVersion) {
        this.apiName = apiName;
        this.apiVersion = apiVersion;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        long start = System.nanoTime();

        if (!"GET".equals(exchange.getRequestMethod())) {
            String response = buildErrorResponse(start, exchange, 405, "Method Not Allowed");
            writeJson(exchange, 405, response);
            return;
        }

        Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());
        String algorithm = params.getOrDefault("algorithm", "md5").toLowerCase();
        String text = params.getOrDefault("text", "");

        if (!"md5".equals(algorithm)) {
            String response = buildValidationResponse(start, exchange, algorithm, text, 400, "Bad Request", "Only md5 is supported");
            writeJson(exchange, 400, response);
            return;
        }

        String hash = md5(text);
        long responseTime = System.nanoTime() - start;

        StringBuilder response = new StringBuilder();
        response.append("{")
                .append("\"header\":{")
                .append("\"api_name\":\"").append(escape(apiName)).append("\",")
                .append("\"api_version\":\"").append(escape(apiVersion)).append("\",")
                .append("\"api_user\":\"guest\",")
                .append("\"api_endpoint\":\"api/hash/\",")
                .append("\"http_request_method\":\"GET\",")
                .append("\"http_request_parameters\":{")
                .append("\"algorithm\":\"").append(escape(algorithm)).append("\",")
                .append("\"text\":\"").append(escape(text)).append("\"},")
                .append("\"http_response_status\":200,")
                .append("\"http_response_message\":\"OK\",")
                .append("\"response_time\":").append(responseTime)
                .append("},")
                .append("\"body\":{")
                .append("\"algorithm\":\"").append(escape(algorithm)).append("\",")
                .append("\"text\":\"").append(escape(text)).append("\",")
                .append("\"hash\":\"").append(hash).append("\"}")
                .append("}");

        writeJson(exchange, 200, response.toString());
    }

    private String buildErrorResponse(long start, HttpExchange exchange, int status, String message) {
        long responseTime = System.nanoTime() - start;
        String method = exchange.getRequestMethod();
        StringBuilder response = new StringBuilder();
        response.append("{")
                .append("\"header\":{")
                .append("\"api_name\":\"").append(escape(apiName)).append("\",")
                .append("\"api_version\":\"").append(escape(apiVersion)).append("\",")
                .append("\"api_user\":\"guest\",")
                .append("\"api_endpoint\":\"api/hash/\",")
                .append("\"http_request_method\":\"").append(escape(method)).append("\",")
                .append("\"http_request_parameters\":{},")
                .append("\"http_response_status\":").append(status).append(",")
                .append("\"http_response_message\":\"").append(escape(message)).append("\",")
                .append("\"response_time\":").append(responseTime)
                .append("},")
                .append("\"body\":{")
                .append("\"error\":\"").append(escape(message)).append("\"}")
                .append("}");
        return response.toString();
    }

    private String buildValidationResponse(long start, HttpExchange exchange, String algorithm, String text, int status, String message, String detail) {
        long responseTime = System.nanoTime() - start;
        StringBuilder response = new StringBuilder();
        response.append("{")
                .append("\"header\":{")
                .append("\"api_name\":\"").append(escape(apiName)).append("\",")
                .append("\"api_version\":\"").append(escape(apiVersion)).append("\",")
                .append("\"api_user\":\"guest\",")
                .append("\"api_endpoint\":\"api/hash/\",")
                .append("\"http_request_method\":\"").append(escape(exchange.getRequestMethod())).append("\",")
                .append("\"http_request_parameters\":{")
                .append("\"algorithm\":\"").append(escape(algorithm)).append("\",")
                .append("\"text\":\"").append(escape(text)).append("\"},")
                .append("\"http_response_status\":").append(status).append(",")
                .append("\"http_response_message\":\"").append(escape(message)).append("\",")
                .append("\"response_time\":").append(responseTime)
                .append("},")
                .append("\"body\":{")
                .append("\"error\":\"").append(escape(detail)).append("\"}")
                .append("}");
        return response.toString();
    }

    private String md5(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(text.getBytes(StandardCharsets.UTF_8));
            return toHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm not available", e);
        }
    }

    private String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private void writeJson(HttpExchange exchange, int status, String payload) throws IOException {
        byte[] body = payload.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(status, body.length);
        OutputStream output = exchange.getResponseBody();
        output.write(body);
        output.flush();
        exchange.close();
    }

    private String escape(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
