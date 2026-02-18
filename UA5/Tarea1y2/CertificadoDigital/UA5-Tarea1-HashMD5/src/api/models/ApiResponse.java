package api.models;

/**
 * Modelo de respuesta estándar de la API.
 * Contiene header con metadatos y body con el resultado.
 */
public class ApiResponse {

    private ApiHeader header;
    private ApiBody body;

    public ApiResponse(ApiHeader header, ApiBody body) {
        this.header = header;
        this.body = body;
    }

    public ApiHeader getHeader() { return header; }
    public ApiBody getBody()     { return body; }

    /**
     * Serializa la respuesta completa a JSON manualmente (sin librerías externas).
     */
    public String toJson() {
        return "{\n" +
               "  \"header\": " + header.toJson() + ",\n" +
               "  \"body\": " + body.toJson() + "\n" +
               "}";
    }
}