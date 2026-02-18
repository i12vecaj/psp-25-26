package com.tuempresa.api.dto;

import java.util.Map;

public class Header {

    private String api_name;
    private String api_version;
    private String api_user;
    private String api_endpoint;
    private String http_request_method;
    private Map<String, String> http_request_parameters;
    private int http_response_status;
    private String http_response_message;
    private long response_time;

    public Header(String api_name, String api_version, String api_user,
                  String api_endpoint, String http_request_method,
                  Map<String, String> http_request_parameters,
                  int http_response_status, String http_response_message,
                  long response_time) {

        this.api_name = api_name;
        this.api_version = api_version;
        this.api_user = api_user;
        this.api_endpoint = api_endpoint;
        this.http_request_method = http_request_method;
        this.http_request_parameters = http_request_parameters;
        this.http_response_status = http_response_status;
        this.http_response_message = http_response_message;
        this.response_time = response_time;
    }

    public String getApi_name() { return api_name; }
    public String getApi_version() { return api_version; }
    public String getApi_user() { return api_user; }
    public String getApi_endpoint() { return api_endpoint; }
    public String getHttp_request_method() { return http_request_method; }
    public Map<String, String> getHttp_request_parameters() { return http_request_parameters; }
    public int getHttp_response_status() { return http_response_status; }
    public String getHttp_response_message() { return http_response_message; }
    public long getResponse_time() { return response_time; }
}
