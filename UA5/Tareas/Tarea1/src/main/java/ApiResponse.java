package main.java;

public class ApiResponse {
    private ApiHeader header;
    private HashBody body;

    // Getters y Setters
    public ApiHeader getHeader() {
        return header;
    }

    public void setHeader(ApiHeader header) {
        this.header = header;
    }

    public HashBody getBody() {
        return body;
    }

    public void setBody(HashBody body) {
        this.body = body;
    }
}
