package com.myapi.handler;

import com.myapi.model.Person;
import com.myapi.repository.InMemoryPersonRepository;
import com.myapi.repository.PersonRepository;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PersonHandler {

    private final PersonRepository repo = new InMemoryPersonRepository();

    @GetMapping("/person")
    public String getPerson(@RequestParam String name) {
        long startTime = System.nanoTime();

        Person person = repo.getPerson(name);
        JSONObject header = new JSONObject();
        header.put("api_name", "My API Name");
        header.put("api_version", "1.0.0");
        header.put("api_user", "guest");
        header.put("api_endpoint", "/api/person");
        header.put("http_request_method", "GET");
        header.put("http_request_parameters", "name=" + name);

        JSONObject body = new JSONObject();
        if (person != null) {
            body.put("name", person.getName());
            body.put("about", person.getAbout());
            body.put("birthYear", person.getBirthYear());
            header.put("http_response_status", 200);
            header.put("http_response_message", "OK");
        } else {
            header.put("http_response_status", 404);
            header.put("http_response_message", "Not Found");
        }

        header.put("response_time", System.nanoTime() - startTime);
        JSONObject response = new JSONObject();
        response.put("header", header);
        response.put("body", body);

        return response.toString();
    }

    @PostMapping("/person")
    public String createPerson(@RequestParam String name, @RequestParam String about, @RequestParam int birthYear) {
        long startTime = System.nanoTime();

        Person person = new Person(name, about, birthYear);
        repo.addPerson(person);

        JSONObject body = new JSONObject();
        body.put("name", person.getName());

        JSONObject header = new JSONObject();
        header.put("api_name", "My API Name");
        header.put("api_version", "1.0.0");
        header.put("api_user", "guest");
        header.put("api_endpoint", "/api/person");
        header.put("http_request_method", "POST");
        header.put("http_request_parameters", "name=" + name + "&about=" + about + "&birthYear=" + birthYear);
        header.put("http_response_status", 200);
        header.put("http_response_message", "OK");
        header.put("response_time", System.nanoTime() - startTime);

        JSONObject response = new JSONObject();
        response.put("header", header);
        response.put("body", body);
        return response.toString();
    }
}
