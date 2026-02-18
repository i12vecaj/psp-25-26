import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HashAPI {

    // ==================== CLASE PRINCIPAL ====================
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);

        server.createContext("/api/hash/", new HashHandler());

        server.setExecutor(null);
        server.start();
        System.out.println("[" + new Date() + "] Hash API server is listening on " +
                server.getAddress().getAddress() + ":" + server.getAddress().getPort());
    }

    // ==================== HANDLER BASE ====================
    static abstract class BasicHandler implements HttpHandler {
        public static Map<String, String> queryToMap(String query) {
            Map<String, String> result = new HashMap<>();

            if (query != null) {
                for (String param : query.split("&")) {
                    String pair[] = param.split("=");
                    if (pair.length > 1) {
                        result.put(pair[0], pair[1]);
                    } else {
                        result.put(pair[0], "");
                    }
                }
            }
            return result;
        }
    }

    // ==================== HANDLER DE HASH ====================
    static class HashHandler extends BasicHandler {
        private Gson gson;

        public HashHandler() {
            super();
            this.gson = new GsonBuilder().setPrettyPrinting().create();
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String remoteAddress = exchange.getRemoteAddress().getHostString();
            System.out.println("[" + new Date() + "] Received " + exchange.getRequestMethod() + " " +
                    exchange.getRequestURI() + " from client: " + remoteAddress);

            if ("GET".equals(exchange.getRequestMethod())) {
                String queryString = exchange.getRequestURI().getQuery();
                Map<String, String> params = queryToMap(queryString);

                String text = decodeValue(params.getOrDefault("text", ""));
                String algorithm = params.getOrDefault("algorithm", "md5").toLowerCase();

                long startTime = System.currentTimeMillis();
                ApiResponse response = generateHashResponse(text, algorithm);
                long endTime = System.currentTimeMillis();
                response.getHeader().setResponseTime(endTime - startTime);

                String responseJson = gson.toJson(response);

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, responseJson.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(responseJson.getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);
            }

            exchange.close();
        }

        private ApiResponse generateHashResponse(String text, String algorithm) {
            ApiResponse response = new ApiResponse();

            ApiHeader header = new ApiHeader();
            header.setApiName("Hash API");
            header.setApiVersion("1.0.0");
            header.setApiUser("guest");
            header.setApiEndpoint("api/hash/");
            header.setHttpRequestMethod("GET");
            header.setHttpResponseStatus(200);
            header.setHttpResponseMessage("OK");

            Map<String, String> params = new HashMap<>();
            params.put("algorithm", algorithm);
            params.put("text", text);
            header.setHttpRequestParameters(params);

            response.setHeader(header);

            try {
                String hash = calculateHash(text, algorithm);

                HashBody body = new HashBody();
                body.setAlgorithm(algorithm);
                body.setText(text);
                body.setHash(hash);

                response.setBody(body);
            } catch (NoSuchAlgorithmException e) {
                header.setHttpResponseStatus(400);
                header.setHttpResponseMessage("Bad Request");
                HashBody body = new HashBody();
                body.setAlgorithm(algorithm);
                body.setText(text);
                body.setHash("Error: " + e.getMessage());
                response.setBody(body);
            }

            return response;
        }

        private String calculateHash(String text, String algorithm) throws NoSuchAlgorithmException {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm.toUpperCase());
            byte[] messageBytes = text.getBytes(StandardCharsets.UTF_8);
            byte[] digestBytes = messageDigest.digest(messageBytes);

            StringBuilder hexString = new StringBuilder();
            for (byte b : digestBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        }

        private String decodeValue(String value) {
            try {
                return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
            } catch (Exception e) {
                return value;
            }
        }
    }

    // ==================== MODELOS DE DATOS ====================
    static class ApiHeader {
        private String api_name;
        private String api_version;
        private String api_user;
        private String api_endpoint;
        private String http_request_method;
        private Map<String, String> http_request_parameters;
        private int http_response_status;
        private String http_response_message;
        private long response_time;

        public String getApiName() {
            return api_name;
        }

        public void setApiName(String api_name) {
            this.api_name = api_name;
        }

        public String getApiVersion() {
            return api_version;
        }

        public void setApiVersion(String api_version) {
            this.api_version = api_version;
        }

        public String getApiUser() {
            return api_user;
        }

        public void setApiUser(String api_user) {
            this.api_user = api_user;
        }

        public String getApiEndpoint() {
            return api_endpoint;
        }

        public void setApiEndpoint(String api_endpoint) {
            this.api_endpoint = api_endpoint;
        }

        public String getHttpRequestMethod() {
            return http_request_method;
        }

        public void setHttpRequestMethod(String http_request_method) {
            this.http_request_method = http_request_method;
        }

        public Map<String, String> getHttpRequestParameters() {
            return http_request_parameters;
        }

        public void setHttpRequestParameters(Map<String, String> http_request_parameters) {
            this.http_request_parameters = http_request_parameters;
        }

        public int getHttpResponseStatus() {
            return http_response_status;
        }

        public void setHttpResponseStatus(int http_response_status) {
            this.http_response_status = http_response_status;
        }

        public String getHttpResponseMessage() {
            return http_response_message;
        }

        public void setHttpResponseMessage(String http_response_message) {
            this.http_response_message = http_response_message;
        }

        public long getResponseTime() {
            return response_time;
        }

        public void setResponseTime(long response_time) {
            this.response_time = response_time;
        }
    }

    static class HashBody {
        private String algorithm;
        private String text;
        private String hash;

        public String getAlgorithm() {
            return algorithm;
        }

        public void setAlgorithm(String algorithm) {
            this.algorithm = algorithm;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }
    }

    static class ApiResponse {
        private ApiHeader header;
        private HashBody body;

        public ApiHeader getHeader() {
            return header;
        }

        public void setHeader(ApiHeader header) {
            this.header = header;
        }

        public HashBody getBody() {
            return body;
        }

        public void setBody(HashBody body) {
            this.body = body;
        }
    }
}
