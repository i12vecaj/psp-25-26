package com.marissa.hash_api.Models;


public class ApiResponse {

    private ApiHeader header;
    private ApiBody body;

    // Getters y setters

    public ApiHeader getHeader() {
        return header;
    }

    public void setHeader(ApiHeader header) {
        this.header = header;
    }

    public ApiBody getBody() {
        return body;
    }

    public void setBody(ApiBody body) {
        this.body = body;
    }
}

