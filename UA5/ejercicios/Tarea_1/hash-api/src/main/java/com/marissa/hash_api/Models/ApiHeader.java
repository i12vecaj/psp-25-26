package com.marissa.hash_api.Models;

import java.util.Map;

public class ApiHeader {

    private String api_name;
    private String api_version;
    private String api_user;
    private String api_endpoint;
    private String http_request_method;
    private Map<String, Object> http_request_parameters;
    private int http_response_status;
    private String http_response_message;
    private long response_time;

    // Getters y setters

    public String getApi_name() {
        return api_name;
    }

    public void setApi_name(String api_name) {
        this.api_name = api_name;
    }

    public String getApi_version() {
        return api_version;
    }

    public void setApi_version(String api_version) {
        this.api_version = api_version;
    }

    public String getApi_user() {
        return api_user;
    }

    public void setApi_user(String api_user) {
        this.api_user = api_user;
    }

    public String getApi_endpoint() {
        return api_endpoint;
    }

    public void setApi_endpoint(String api_endpoint) {
        this.api_endpoint = api_endpoint;
    }

    public String getHttp_request_method() {
        return http_request_method;
    }

    public void setHttp_request_method(String http_request_method) {
        this.http_request_method = http_request_method;
    }

    public Map<String, Object> getHttp_request_parameters() {
        return http_request_parameters;
    }

    public void setHttp_request_parameters(Map<String, Object> http_request_parameters) {
        this.http_request_parameters = http_request_parameters;
    }

    public int getHttp_response_status() {
        return http_response_status;
    }

    public void setHttp_response_status(int http_response_status) {
        this.http_response_status = http_response_status;
    }

    public String getHttp_response_message() {
        return http_response_message;
    }

    public void setHttp_response_message(String http_response_message) {
        this.http_response_message = http_response_message;
    }

    public long getResponse_time() {
        return response_time;
    }

    public void setResponse_time(long response_time) {
        this.response_time = response_time;
    }
}

