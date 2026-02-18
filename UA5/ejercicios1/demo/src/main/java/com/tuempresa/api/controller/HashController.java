package com.tuempresa.api.controller;

import com.tuempresa.api.dto.ApiResponse;
import com.tuempresa.api.dto.Body;
import com.tuempresa.api.dto.Header;
import com.tuempresa.api.service.HashService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            @RequestParam String text) throws NoSuchAlgorithmException {

        long startTime = System.nanoTime();

        if (!algorithm.equalsIgnoreCase("md5")) {
            return ResponseEntity.badRequest().build();
        }

        String hash = hashService.generateMD5(text);
        long responseTime = System.nanoTime() - startTime;

        Map<String, String> params = new HashMap<>();
        params.put("algorithm", algorithm);
        params.put("text", text);

        Header header = new Header(
                "My API Name",
                "1.0.0",
                "guest",
                "api/hash/",
                "GET",
                params,
                200,
                "OK",
                responseTime
        );

        Body body = new Body(algorithm, text, hash);

        ApiResponse response = new ApiResponse(header, body);

        return ResponseEntity.ok(response);
    }
}
