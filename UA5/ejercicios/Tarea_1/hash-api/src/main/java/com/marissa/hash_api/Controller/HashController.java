package com.marissa.hash_api.Controller;

import com.marissa.hash_api.Models.ApiBody;
import com.marissa.hash_api.Models.ApiHeader;
import com.marissa.hash_api.Models.ApiResponse;
import com.marissa.hash_api.Utils.HashUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController

@RequestMapping("/api/hash") // Define la ruta base del controlador
public class HashController {

    @GetMapping //metodo responde a peticiones GET
    public ResponseEntity<ApiResponse> getHash( // Devuelve un objeto JSON
                                               // Recoge parámetros de la URL
            @RequestParam(name = "algorithm", defaultValue = "md5") String algorithm,
            @RequestParam(name = "text") String text) {

        long startTime = System.currentTimeMillis();

        // Solo aceptamos md5 (según enunciado)
        if (!"md5".equalsIgnoreCase(algorithm)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String hash = HashUtils.md5(text);

        // Construir body
        ApiBody body = new ApiBody();
        body.setAlgorithm("md5");
        body.setText(text);
        body.setHash(hash);

        // Construir header
        ApiHeader header = new ApiHeader();
        header.setApi_name("My API Name");
        header.setApi_version("1.0.0");
        header.setApi_user("guest");
        header.setApi_endpoint("api/hash/");
        header.setHttp_request_method("GET");

        Map<String, Object> params = new HashMap<>();
        params.put("algorithm", "md5");
        params.put("text", text);
        header.setHttp_request_parameters(params);

        header.setHttp_response_status(200);
        header.setHttp_response_message("OK");

        long endTime = System.currentTimeMillis();
        header.setResponse_time(endTime - startTime);

        // Construir respuesta completa
        ApiResponse response = new ApiResponse();
        response.setHeader(header);
        response.setBody(body);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

