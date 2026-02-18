package org.api;

import java.io.OutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Date;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.sun.net.httpserver.HttpExchange;

class MD5Handler extends BasicHandler {

    public MD5Handler(DataStore store) {
        super(store);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        long startTime = System.nanoTime();

        String remoteAddress = exchange.getRemoteAddress().getHostString();
        System.out.println("[" + new Date() + "] Received "
                + exchange.getRequestMethod() + " "
                + exchange.getRequestURI()
                + " from client: " + remoteAddress);

        if (!"GET".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            exchange.close();
            return;
        }

        Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());
        String text = params.get("text");
        String algorithm = "md5";

        if (text == null || text.isEmpty()) {

            String errorResponse = "{ \"error\": \"Missing 'text' parameter\" }";

            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(400, errorResponse.getBytes().length);

            OutputStream output = exchange.getResponseBody();
            output.write(errorResponse.getBytes());
            output.close();
            exchange.close();
            return;
        }

        String md5Hash = calculateMD5(text);

        long endTime = System.nanoTime();
        long responseTime = endTime - startTime;

        String responseString = "{";
        responseString += "\"header\": {";
        responseString += "\"api_name\": \"My API Name\",";
        responseString += "\"api_version\": \"1.0.0\",";
        responseString += "\"api_user\": \"guest\",";
        responseString += "\"api_endpoint\": \"api/md5\",";
        responseString += "\"http_request_method\": \"" + exchange.getRequestMethod() + "\",";
        responseString += "\"http_request_parameters\": {";
        responseString += "\"algorithm\": \"" + algorithm + "\",";
        responseString += "\"text\": \"" + text + "\"";
        responseString += "},";
        responseString += "\"http_response_status\": 200,";
        responseString += "\"http_response_message\": \"OK\",";
        responseString += "\"response_time\": " + responseTime;
        responseString += "},";
        responseString += "\"body\": {";
        responseString += "\"algorithm\": \"" + algorithm + "\",";
        responseString += "\"text\": \"" + text + "\",";
        responseString += "\"hash\": \"" + md5Hash + "\"";
        responseString += "}";
        responseString += "}";

        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, responseString.getBytes().length);

        OutputStream output = exchange.getResponseBody();
        output.write(responseString.getBytes());
        output.close();

        exchange.close();
    }

    private String calculateMD5(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(text.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            return "error";
        }
    }
}
