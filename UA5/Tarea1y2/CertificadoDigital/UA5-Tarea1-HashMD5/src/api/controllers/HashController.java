package api.controllers;

import api.models.ApiBody;
import api.models.ApiHeader;
import api.models.ApiResponse;
import api.server.ApiConfig;
import api.utils.HashUtils;
import api.utils.QueryStringParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Controlador para el endpoint GET /api/hash/
 *
 * Parámetros de la petición:
 *   - algorithm : Algoritmo de hash (por defecto "md5"). Soportados: md5, sha1, sha256.
 *   - text      : Texto del que se calculará el hash.
 *
 * Ejemplo de uso:
 *   GET http://localhost:8080/api/hash/?algorithm=md5&text=Hola+mundo
 */
public class HashController implements HttpHandler {

    private static final String ENDPOINT = ApiConfig.ENDPOINT_HASH;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        long startTime = System.currentTimeMillis();
        String method  = exchange.getRequestMethod();

        // Sólo se acepta GET
        if (!"GET".equalsIgnoreCase(method)) {
            sendResponse(exchange, 405, buildErrorResponse(
                    method, new LinkedHashMap<>(), 405, "Method Not Allowed",
                    "Sólo se admite el método GET en este endpoint.", startTime));
            return;
        }

        // Parsear parámetros de la query string
        String rawQuery = exchange.getRequestURI().getRawQuery();
        Map<String, String> params = QueryStringParser.parse(rawQuery);

        // Parámetro "algorithm" (opcional, por defecto md5)
        String algorithm = params.getOrDefault("algorithm", "md5").toLowerCase();

        // Parámetro "text" (obligatorio)
        String text = params.get("text");
        if (text == null || text.isBlank()) {
            Map<String, String> reqParams = new LinkedHashMap<>();
            reqParams.put("algorithm", algorithm);
            sendResponse(exchange, 400, buildErrorResponse(
                    method, reqParams, 400, "Bad Request",
                    "El parámetro 'text' es obligatorio y no puede estar vacío.", startTime));
            return;
        }

        // Validar algoritmo
        if (!algorithm.equals("md5") && !algorithm.equals("sha1") && !algorithm.equals("sha256")) {
            Map<String, String> reqParams = new LinkedHashMap<>();
            reqParams.put("algorithm", algorithm);
            reqParams.put("text", text);
            sendResponse(exchange, 400, buildErrorResponse(
                    method, reqParams, 400, "Bad Request",
                    "Algoritmo no soportado. Use: md5, sha1, sha256.", startTime));
            return;
        }

        // Calcular hash
        String hashResult = HashUtils.hash(algorithm, text);

        // Construir parámetros para la cabecera
        Map<String, String> reqParams = new LinkedHashMap<>();
        reqParams.put("algorithm", algorithm);
        reqParams.put("text", text);

        long responseTime = System.currentTimeMillis() - startTime;

        // Construir respuesta exitosa
        ApiHeader header = new ApiHeader(
                ApiConfig.API_NAME,
                ApiConfig.API_VERSION,
                ApiConfig.API_USER,
                ENDPOINT,
                method,
                reqParams,
                200,
                "OK",
                responseTime
        );
        ApiBody body = new ApiBody(algorithm, text, hashResult);
        ApiResponse response = new ApiResponse(header, body);

        sendResponse(exchange, 200, response.toJson());
    }

    /**
     * Construye una respuesta de error con la estructura estándar de la API.
     */
    private String buildErrorResponse(String method, Map<String, String> params,
                                      int status, String message,
                                      String errorDetail, long startTime) {
        long responseTime = System.currentTimeMillis() - startTime;
        ApiHeader header = new ApiHeader(
                ApiConfig.API_NAME,
                ApiConfig.API_VERSION,
                ApiConfig.API_USER,
                ENDPOINT,
                method,
                params,
                status,
                message,
                responseTime
        );
        ApiBody body = new ApiBody(errorDetail);
        return new ApiResponse(header, body).toJson();
    }

    /**
     * Envía la respuesta HTTP con el JSON generado.
     */
    private void sendResponse(HttpExchange exchange, int statusCode, String json) throws IOException {
        byte[] responseBytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}