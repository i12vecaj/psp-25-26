package com.myapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/hash")
public class HashController {

    private static final String API_NAME     = "My API Name";
    private static final String API_VERSION  = "1.0.0";
    private static final String API_ENDPOINT = "api/hash/";

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getHash(
            @RequestParam(value = "text",      required = false) String text,
            @RequestParam(value = "algorithm", defaultValue = "md5") String algorithm,
            jakarta.servlet.http.HttpServletRequest httpRequest) {

        long startTime = System.currentTimeMillis();
        String apiUser = (httpRequest.getUserPrincipal() != null)
                ? httpRequest.getUserPrincipal().getName()
                : "guest";

        Map<String, String> requestParams = new LinkedHashMap<>();
        requestParams.put("algorithm", algorithm.toLowerCase());
        requestParams.put("text", text != null ? text : "");

        if (text == null || text.isEmpty()) {
            return buildResponse(apiUser, requestParams, HttpStatus.BAD_REQUEST,
                    "Bad Request - Missing required parameter: text",
                    null, algorithm, text, startTime);
        }

        if (!"md5".equalsIgnoreCase(algorithm)) {
            return buildResponse(apiUser, requestParams, HttpStatus.BAD_REQUEST,
                    "Bad Request - Unsupported algorithm: " + algorithm + ". Supported: md5",
                    null, algorithm, text, startTime);
        }

        try {
            String hash = calculateMD5(text);
            return buildResponse(apiUser, requestParams, HttpStatus.OK, "OK",
                    hash, algorithm.toLowerCase(), text, startTime);
        } catch (NoSuchAlgorithmException e) {
            return buildResponse(apiUser, requestParams, HttpStatus.INTERNAL_SERVER_ERROR,
                    "Internal Server Error - " + e.getMessage(),
                    null, algorithm, text, startTime);
        }
    }

    private String calculateMD5(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(text.getBytes(java.nio.charset.StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private ResponseEntity<Map<String, Object>> buildResponse(
            String apiUser,
            Map<String, String> requestParams,
            HttpStatus status,
            String statusMessage,
            String hash,
            String algorithm,
            String text,
            long startTime) {

        Map<String, Object> header = new LinkedHashMap<>();
        header.put("api_name",                API_NAME);
        header.put("api_version",             API_VERSION);
        header.put("api_user",                apiUser);
        header.put("api_endpoint",            API_ENDPOINT);
        header.put("http_request_method",     "GET");
        header.put("http_request_parameters", requestParams);
        header.put("http_response_status",    status.value());
        header.put("http_response_message",   statusMessage);
        header.put("response_time",           System.currentTimeMillis() - startTime);

        Map<String, Object> body = null;
        if (hash != null) {
            body = new LinkedHashMap<>();
            body.put("algorithm", algorithm.toLowerCase());
            body.put("text",      text);
            body.put("hash",      hash);
        }

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("header", header);
        response.put("body",   body);

        return new ResponseEntity<>(response, status);
    }
}
