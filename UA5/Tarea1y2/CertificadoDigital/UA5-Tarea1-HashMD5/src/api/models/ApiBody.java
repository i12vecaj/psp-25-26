package api.models;

/**
 * Cuerpo de la respuesta para el endpoint de hash.
 */
public class ApiBody {

    private final String algorithm;
    private final String text;
    private final String hash;

    // Constructor para respuesta exitosa
    public ApiBody(String algorithm, String text, String hash) {
        this.algorithm = algorithm;
        this.text      = text;
        this.hash      = hash;
    }

    // Constructor para respuesta de error (sin hash)
    public ApiBody(String errorMessage) {
        this.algorithm = null;
        this.text      = errorMessage;
        this.hash      = null;
    }

    public String toJson() {
        if (algorithm == null) {
            // Respuesta de error
            return "{\n" +
                   "    \"error\": \"" + escapeJson(text) + "\"\n" +
                   "  }";
        }
        return "{\n" +
               "    \"algorithm\": \"" + escapeJson(algorithm) + "\",\n" +
               "    \"text\": \""      + escapeJson(text)      + "\",\n" +
               "    \"hash\": \""      + escapeJson(hash)      + "\"\n" +
               "  }";
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}