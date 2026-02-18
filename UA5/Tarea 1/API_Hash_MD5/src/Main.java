import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

// ============================================================
//  ARRANQUE DE SPRING BOOT
// ============================================================
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // ============================================================
    //  CONSTANTES DE LA API
    // ============================================================
    static final String API_NAME    = "My API Name";
    static final String API_VERSION = "1.0.0";
    static final String API_USER    = "guest";
    static final String API_ENDPOINT = "api/hash/";

    // ============================================================
    //  MODELO: Header de respuesta
    // ============================================================
    static class ApiHeader {

        @JsonProperty("api_name")
        public String apiName;

        @JsonProperty("api_version")
        public String apiVersion;

        @JsonProperty("api_user")
        public String apiUser;

        @JsonProperty("api_endpoint")
        public String apiEndpoint;

        @JsonProperty("http_request_method")
        public String httpRequestMethod;

        @JsonProperty("http_request_parameters")
        public Map<String, String> httpRequestParameters;

        @JsonProperty("http_response_status")
        public int httpResponseStatus;

        @JsonProperty("http_response_message")
        public String httpResponseMessage;

        @JsonProperty("response_time")
        public long responseTime;

        public ApiHeader(Map<String, String> params, int status, String message, long responseTime) {
            this.apiName              = API_NAME;
            this.apiVersion           = API_VERSION;
            this.apiUser              = API_USER;
            this.apiEndpoint          = API_ENDPOINT;
            this.httpRequestMethod    = "GET";
            this.httpRequestParameters = params;
            this.httpResponseStatus   = status;
            this.httpResponseMessage  = message;
            this.responseTime         = responseTime;
        }
    }

    // ============================================================
    //  MODELO: Body de respuesta (éxito)
    // ============================================================
    static class HashBody {
        public String algorithm;
        public String text;
        public String hash;

        public HashBody(String algorithm, String text, String hash) {
            this.algorithm = algorithm;
            this.text      = text;
            this.hash      = hash;
        }
    }

    // ============================================================
    //  MODELO: Respuesta completa { header + body }
    // ============================================================
    static class ApiResponse {
        public ApiHeader header;
        public Object body;

        public ApiResponse(ApiHeader header, Object body) {
            this.header = header;
            this.body   = body;
        }
    }

    // ============================================================
    //  LÓGICA: Generar hash MD5
    // ============================================================
    static String generateMD5(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashBytes = md.digest(text.getBytes());

        StringBuilder hex = new StringBuilder();
        for (byte b : hashBytes) {
            String h = Integer.toHexString(0xff & b);
            if (h.length() == 1) hex.append('0');
            hex.append(h);
        }
        return hex.toString();
    }

    // ============================================================
    //  CONTROLADOR: GET /api/hash
    // ============================================================
    @RestController
    @RequestMapping("/api/hash")
    static class HashController {

        @GetMapping
        public ResponseEntity<ApiResponse> getHash(
                @RequestParam(value = "algorithm", defaultValue = "md5") String algorithm,
                @RequestParam(value = "text") String text) throws NoSuchAlgorithmException {

            long start = System.currentTimeMillis();

            // Parámetros recibidos
            Map<String, String> params = new LinkedHashMap<>();
            params.put("algorithm", algorithm.toLowerCase());
            params.put("text", text);

            // Validar algoritmo
            if (!algorithm.equalsIgnoreCase("md5")) {
                ApiHeader header = new ApiHeader(params,
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request: algoritmo '" + algorithm + "' no soportado. Use 'md5'.",
                        System.currentTimeMillis() - start);

                Map<String, String> errorBody = new LinkedHashMap<>();
                errorBody.put("error", "Algoritmo no soportado. Use algorithm=md5");

                return ResponseEntity.badRequest().body(new ApiResponse(header, errorBody));
            }

            // Generar MD5
            String hash = generateMD5(text);

            ApiHeader header = new ApiHeader(params, 200, "OK", System.currentTimeMillis() - start);
            HashBody  body   = new HashBody(algorithm.toLowerCase(), text, hash);

            return ResponseEntity.ok(new ApiResponse(header, body));
        }
    }

    // ============================================================
    //  MANEJO DE ERRORES GLOBALES
    // ============================================================
    @RestControllerAdvice
    static class GlobalExceptionHandler {

        // Falta el parámetro ?text=
        @ExceptionHandler(MissingServletRequestParameterException.class)
        public ResponseEntity<ApiResponse> missingParam(MissingServletRequestParameterException ex) {
            Map<String, String> params = new LinkedHashMap<>();
            params.put("algorithm", "md5");
            params.put("text", "");

            ApiHeader header = new ApiHeader(params,
                    HttpStatus.BAD_REQUEST.value(),
                    "Bad Request: falta el parámetro '" + ex.getParameterName() + "'.",
                    0L);

            Map<String, String> errorBody = new LinkedHashMap<>();
            errorBody.put("error", "El parámetro '" + ex.getParameterName() + "' es obligatorio.");
            errorBody.put("uso",   "GET /api/hash?algorithm=md5&text=TuTexto");

            return ResponseEntity.badRequest().body(new ApiResponse(header, errorBody));
        }

        // Cualquier otro error interno
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiResponse> genericError(Exception ex) {
            Map<String, String> params = new LinkedHashMap<>();

            ApiHeader header = new ApiHeader(params,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    0L);

            Map<String, String> errorBody = new LinkedHashMap<>();
            errorBody.put("error", ex.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(header, errorBody));
        }
    }
}
