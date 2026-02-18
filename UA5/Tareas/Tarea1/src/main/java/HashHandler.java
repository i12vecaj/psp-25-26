package main.java;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class HashHandler extends BasicHandler {

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

            // Obtener los parámetros
            String text = decodeValue(params.getOrDefault("text", ""));
            String algorithm = params.getOrDefault("algorithm", "md5").toLowerCase();

            long startTime = System.currentTimeMillis();

            // Generar la respuesta
            ApiResponse response = generateHashResponse(text, algorithm);

            long endTime = System.currentTimeMillis();
            response.getHeader().setResponseTime(endTime - startTime);

            // Convertir a JSON
            String responseJson = gson.toJson(response);

            // Enviar respuesta
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, responseJson.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(responseJson.getBytes());
            output.flush();
        } else {
            exchange.sendResponseHeaders(405, -1); // 405 Method Not Allowed
        }

        exchange.close();
    }

    /**
     * Genera la respuesta con el hash calculado
     */
    private ApiResponse generateHashResponse(String text, String algorithm) {
        ApiResponse response = new ApiResponse();

        // Configurar header
        ApiHeader header = new ApiHeader();
        header.setApiName("Hash API");
        header.setApiVersion("1.0.0");
        header.setApiUser("guest");
        header.setApiEndpoint("api/hash/");
        header.setHttpRequestMethod("GET");
        header.setHttpResponseStatus(200);
        header.setHttpResponseMessage("OK");

        // Crear parámetros de la solicitud
        Map<String, String> params = new java.util.HashMap<>();
        params.put("algorithm", algorithm);
        params.put("text", text);
        header.setHttpRequestParameters(params);

        response.setHeader(header);

        // Calcular hash
        try {
            String hash = calculateHash(text, algorithm);

            // Configurar body de respuesta
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

    /**
     * Calcula el hash del texto utilizando el algoritmo especificado
     */
    private String calculateHash(String text, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm.toUpperCase());
        byte[] messageBytes = text.getBytes(StandardCharsets.UTF_8);
        byte[] digestBytes = messageDigest.digest(messageBytes);

        // Convertir a hexadecimal
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

    /**
     * Decodifica un valor URL-encoded
     */
    private String decodeValue(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            return value;
        }
    }
}
