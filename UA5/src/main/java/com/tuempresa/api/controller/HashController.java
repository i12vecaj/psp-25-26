package com.tuempresa.api.controller;

import com.tuempresa.api.dto.*;
import com.tuempresa.api.service.HashService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HashController {

    @Autowired
    private HashService hashService;

    @GetMapping("/hash")
    public ResponseEntity<ApiResponse> generateHash(
            @RequestParam String algorithm,
            @RequestParam String text) {

        long startTime = System.nanoTime();

        if (algorithm == null || text == null || text.isBlank()) {
            long responseTime = System.nanoTime() - startTime;
            ApiResponse response = buildResponse(
                    400,
                    "Bad Request",
                    algorithm,
                    text,
                    null,
                    responseTime
            );
            return ResponseEntity.badRequest().body(response);
        }

        if (!"md5".equalsIgnoreCase(algorithm)) {
            long responseTime = System.nanoTime() - startTime;
            ApiResponse response = buildResponse(
                    400,
                    "Unsupported algorithm",
                    algorithm,
                    text,
                    null,
                    responseTime
            );
            return ResponseEntity.badRequest().body(response);
        }

        String hash;
        try {
            hash = hashService.generateMD5(text);
        } catch (NoSuchAlgorithmException ex) {
            long responseTime = System.nanoTime() - startTime;
            ApiResponse response = buildResponse(
                    500,
                    "Hash algorithm not available",
                    algorithm,
                    text,
                    null,
                    responseTime
            );
            return ResponseEntity.status(500).body(response);
        }
        long responseTime = System.nanoTime() - startTime;

        ApiResponse response = buildResponse(200, "OK", algorithm, text, hash, responseTime);
        return ResponseEntity.ok(response);
    }

    private ApiResponse buildResponse(
            int status,
            String message,
            String algorithm,
            String text,
            String hash,
            long responseTime) {

        Map<String, String> params = new HashMap<>();
        if (algorithm != null) {
            params.put("algorithm", algorithm);
        }
        if (text != null) {
            params.put("text", text);
        }

        Header header = new Header(
                "My API Name",
                "1.0.0",
                "guest",
                "/api/hash",
                "GET",
                params,
                status,
                message,
                responseTime
        );

        Body body = new Body(algorithm, text, hash);
        return new ApiResponse(header, body);
    }
}
