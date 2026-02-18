package com.myapi.tests;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ByeHandler {

    @GetMapping("/bye")
    public String bye() {
        long startTime = System.nanoTime();

        JSONObject body = new JSONObject();
        body.put("message", "Bye bye!");

        JSONObject header = new JSONObject();
        header.put("api_name", "My API Name");
        header.put("api_version", "1.0.0");
        header.put("api_user", "guest");
        header.put("api_endpoint", "/api/bye");
        header.put("http_request_method", "GET");
        header.put("http_request_parameters", JSONObject.NULL);
        header.put("http_response_status", 200);
        header.put("http_response_message", "OK");
        header.put("response_time", System.nanoTime() - startTime);

        JSONObject response = new JSONObject();
        response.put("header", header);
        response.put("body", body);

        return response.toString();
    }
}
