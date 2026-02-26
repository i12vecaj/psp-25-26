package com.myapi.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HashControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testMD5HashCorrecto() throws Exception {
        mockMvc.perform(get("/api/hash/")
                .param("text",      "Generando un MD5 de un texto")
                .param("algorithm", "md5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.http_response_status").value(200))
                .andExpect(jsonPath("$.header.http_response_message").value("OK"))
                .andExpect(jsonPath("$.header.http_request_parameters.algorithm").value("md5"))
                .andExpect(jsonPath("$.header.http_request_parameters.text").value("Generando un MD5 de un texto"))
                .andExpect(jsonPath("$.body.hash").value("5df9f63916ebf8528697b629022993e8"))
                .andExpect(jsonPath("$.body.algorithm").value("md5"))
                .andExpect(jsonPath("$.body.text").value("Generando un MD5 de un texto"));
    }

    @Test
    public void testSinParametroText() throws Exception {
        mockMvc.perform(get("/api/hash/"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.header.http_response_status").value(400));
    }

    @Test
    public void testAlgoritmoNoSoportado() throws Exception {
        mockMvc.perform(get("/api/hash/")
                .param("text",      "Hola")
                .param("algorithm", "sha256"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.header.http_response_status").value(400));
    }

    @Test
    public void testAlgorithmPorDefecto() throws Exception {
        mockMvc.perform(get("/api/hash/")
                .param("text", "Hola mundo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.algorithm").value("md5"))
                .andExpect(jsonPath("$.body.hash").value("a887aaee2c2a3e2460a24d0a4c86cf62"));
    }

    @Test
    public void testEstructuraHeaderCompleta() throws Exception {
        mockMvc.perform(get("/api/hash/")
                .param("text", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.api_name").exists())
                .andExpect(jsonPath("$.header.api_version").exists())
                .andExpect(jsonPath("$.header.api_user").value("guest"))
                .andExpect(jsonPath("$.header.api_endpoint").value("api/hash/"))
                .andExpect(jsonPath("$.header.http_request_method").value("GET"))
                .andExpect(jsonPath("$.header.response_time").isNumber());
    }
}
