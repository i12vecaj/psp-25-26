package com.myapi.tests;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;

@RestController
@RequestMapping("/api")
public class HashHandler {

    @GetMapping("/hash")
    public String generateHash(@RequestParam String text) {
        long startTime = System.nanoTime();

        String hash = md5(text);

        JSONObject body = new JSONObject();
        body.put("algorithm", "md5");
        body.put("text", text);
        body.put("hash", hash);

        JSONObject header = new JSONObject();
        header.put("api_name", "My API Name");
        header.put("api_version", "1.0.0");
        header.put("api_user", "guest");
        header.put("api_endpoint", "/api/hash");
        header.put("http_request_method", "GET");
        header.put("http_request_parameters", "text=" + text);
        header.put("http_response_status", 200);
        header.put("http_response_message", "OK");
        header.put("response_time", System.nanoTime() - startTime);

        JSONObject response = new JSONObject();
        response.put("header", header);
        response.put("body", body);

        return response.toString();
    }

    private String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
