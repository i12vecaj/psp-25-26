package api.models;

import java.util.Map;

/**
 * Cabecera estándar de la API con metadatos de la petición y la respuesta.
 */
public class ApiHeader {

    private final String apiName;
    private final String apiVersion;
    private final String apiUser;
    private final String apiEndpoint;
    private final String httpRequestMethod;
    private final Map<String, String> httpRequestParameters;
    private final int httpResponseStatus;
    private final String httpResponseMessage;
    private final long responseTime;

    public ApiHeader(String apiName, String apiVersion, String apiUser,
                     String apiEndpoint, String httpRequestMethod,
                     Map<String, String> httpRequestParameters,
                     int httpResponseStatus, String httpResponseMessage,
                     long responseTime) {
        this.apiName               = apiName;
        this.apiVersion            = apiVersion;
        this.apiUser               = apiUser;
        this.apiEndpoint           = apiEndpoint;
        this.httpRequestMethod     = httpRequestMethod;
        this.httpRequestParameters = httpRequestParameters;
        this.httpResponseStatus    = httpResponseStatus;
        this.httpResponseMessage   = httpResponseMessage;
        this.responseTime          = responseTime;
    }

    /** Serialización manual a JSON */
    public String toJson() {
        StringBuilder params = new StringBuilder("{\n");
        int i = 0;
        for (Map.Entry<String, String> entry : httpRequestParameters.entrySet()) {
            params.append("        \"").append(entry.getKey()).append("\": \"")
                  .append(escapeJson(entry.getValue())).append("\"");
            if (i < httpRequestParameters.size() - 1) params.append(",");
            params.append("\n");
            i++;
        }
        params.append("      }");

        return "{\n" +
               "    \"api_name\": \""           + escapeJson(apiName)           + "\",\n" +
               "    \"api_version\": \""        + escapeJson(apiVersion)        + "\",\n" +
               "    \"api_user\": \""           + escapeJson(apiUser)           + "\",\n" +
               "    \"api_endpoint\": \""       + escapeJson(apiEndpoint)       + "\",\n" +
               "    \"http_request_method\": \"" + escapeJson(httpRequestMethod) + "\",\n" +
               "    \"http_request_parameters\": " + params + ",\n" +
               "    \"http_response_status\": " + httpResponseStatus            + ",\n" +
               "    \"http_response_message\": \"" + escapeJson(httpResponseMessage) + "\",\n" +
               "    \"response_time\": "        + responseTime                  + "\n" +
               "  }";
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}