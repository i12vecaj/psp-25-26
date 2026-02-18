package main.java.com.psp.ua5;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HashHandler implements HttpHandler {
    
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String remoteAddress = exchange.getRemoteAddress().getHostString();
        System.out.println(new Date() + ": Recibido " + 
                         exchange.getRequestMethod() + " " + 
                         exchange.getRequestURI() + " del cliente: " + remoteAddress);
        
        if ("GET".equals(exchange.getRequestMethod())) {
            handleGetRequest(exchange);
        } else {
            exchange.sendResponseHeaders(405, -1); // 405 Método No Permitido
        }
        exchange.close();
    }
    
    private void handleGetRequest(HttpExchange exchange) throws IOException {
        long startTime = System.currentTimeMillis();
        
        // Analizar parámetros de consulta
        Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());
        
        // Obtener parámetros
        String algorithm = params.get("algorithm");
        String text = params.get("text");
        
        // Validar parámetros
        if (algorithm == null || text == null) {
            sendErrorResponse(exchange, 400, "Bad Request", 
                            "Faltan parámetros requeridos: algorithm y text", startTime);
            return;
        }
        
        // Solo MD5 está soportado por ahora
        if (!"md5".equalsIgnoreCase(algorithm)) {
            sendErrorResponse(exchange, 400, "Bad Request", 
                            "Algoritmo no soportado. Solo 'md5' está soportado.", startTime);
            return;
        }
        
        try {
            // Generar hash MD5
            String hash = generateMD5(text);
            
            // Construir respuesta JSON
            String jsonResponse = buildSuccessResponse(algorithm, text, hash, startTime);
            
            // Enviar respuesta
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, jsonResponse.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(jsonResponse.getBytes());
            output.flush();
            
        } catch (NoSuchAlgorithmException e) {
            sendErrorResponse(exchange, 500, "Internal Server Error", 
                            "Error generando hash: " + e.getMessage(), startTime);
        }
    }
    
    private String generateMD5(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashBytes = md.digest(text.getBytes());
        
        // Convertir array de bytes a string hexadecimal
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    private String buildSuccessResponse(String algorithm, String text, String hash, long startTime) {
        long responseTime = System.currentTimeMillis() - startTime;
        
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"header\": {\n");
        json.append("    \"api_name\": \"").append(api.API_NAME).append("\",\n");
        json.append("    \"api_version\": \"").append(api.API_VERSION).append("\",\n");
        json.append("    \"api_user\": \"guest\",\n");
        json.append("    \"api_endpoint\": \"api/hash/\",\n");
        json.append("    \"http_request_method\": \"GET\",\n");
        json.append("    \"http_request_parameters\": {\n");
        json.append("      \"algorithm\": \"").append(algorithm).append("\",\n");
        json.append("      \"text\": \"").append(escapeJson(text)).append("\"\n");
        json.append("    },\n");
        json.append("    \"http_response_status\": 200,\n");
        json.append("    \"http_response_message\": \"OK\",\n");
        json.append("    \"response_time\": ").append(responseTime).append("\n");
        json.append("  },\n");
        json.append("  \"body\": {\n");
        json.append("    \"algorithm\": \"").append(algorithm).append("\",\n");
        json.append("    \"text\": \"").append(escapeJson(text)).append("\",\n");
        json.append("    \"hash\": \"").append(hash).append("\"\n");
        json.append("  }\n");
        json.append("}\n");
        
        return json.toString();
    }
    
    private void sendErrorResponse(HttpExchange exchange, int statusCode, 
                                   String statusMessage, String errorDetail, 
                                   long startTime) throws IOException {
        long responseTime = System.currentTimeMillis() - startTime;
        
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"header\": {\n");
        json.append("    \"api_name\": \"").append(api.API_NAME).append("\",\n");
        json.append("    \"api_version\": \"").append(api.API_VERSION).append("\",\n");
        json.append("    \"api_user\": \"guest\",\n");
        json.append("    \"api_endpoint\": \"api/hash/\",\n");
        json.append("    \"http_request_method\": \"GET\",\n");
        json.append("    \"http_request_parameters\": {},\n");
        json.append("    \"http_response_status\": ").append(statusCode).append(",\n");
        json.append("    \"http_response_message\": \"").append(statusMessage).append("\",\n");
        json.append("    \"response_time\": ").append(responseTime).append("\n");
        json.append("  },\n");
        json.append("  \"body\": {\n");
        json.append("    \"error\": \"").append(escapeJson(errorDetail)).append("\"\n");
        json.append("  }\n");
        json.append("}\n");
        
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, json.toString().getBytes().length);
        OutputStream output = exchange.getResponseBody();
        output.write(json.toString().getBytes());
        output.flush();
    }
    
    private Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();
        
        if (query == null || query.isEmpty()) {
            return result;
        }
        
        for (String param : query.split("&")) {
            String[] pair = param.split("=");
            if (pair.length > 1) {
                result.put(pair[0], java.net.URLDecoder.decode(pair[1], java.nio.charset.StandardCharsets.UTF_8));
            } else {
                result.put(pair[0], "");
            }
        }
        return result;
    }
}