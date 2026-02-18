package com.tuempresa.api.dto;

public class ApiResponse {

    private Header header;
    private Body body;

    public ApiResponse(Header header, Body body) {
        this.header = header;
        this.body = body;
    }

    public Header getHeader() { return header; }
    public Body getBody() { return body; }
}
