import java.util.Map;

public class ApiHeader {
    private String api_name;
    private String api_version;
    private String api_user;
    private String api_endpoint;
    private String http_request_method;
    private Map<String, String> http_request_parameters;
    private int http_response_status;
    private String http_response_message;
    private long response_time;

    // Getters y Setters
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
