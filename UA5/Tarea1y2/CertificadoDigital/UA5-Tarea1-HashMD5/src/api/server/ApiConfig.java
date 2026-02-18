package api.server;

/**
 * Configuración global de la API.
 * Centraliza las constantes utilizadas en todos los controladores.
 */
public class ApiConfig {

    // Metadatos de la API
    public static final String API_NAME    = "My API Name";
    public static final String API_VERSION = "1.0.0";
    public static final String API_USER    = "guest";

    // Servidor HTTP
    public static final String HOST = "localhost";
    public static final int    PORT = 8080;

    // Endpoints
    public static final String ENDPOINT_HASH = "/api/hash/";

    private ApiConfig() {}
}