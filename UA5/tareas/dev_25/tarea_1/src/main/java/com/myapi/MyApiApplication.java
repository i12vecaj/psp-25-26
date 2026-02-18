package com.myapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Map;

@SpringBootApplication
public class MyApiApplication {

    public static void main(String[] args) {

        String hostname = "localhost";
        int port = 8080;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-h":
                case "--hostname":
                    hostname = args[++i];
                    break;
                case "-p":
                case "--port":
                    port = Integer.parseInt(args[++i]);
                    break;
                case "--help":
                    System.out.println("Uso: java -jar api.jar [-h hostname] [-p port]");
                    return;
            }
        }

        SpringApplication app = new SpringApplication(MyApiApplication.class);
        app.setDefaultProperties(Map.of(
                "server.port", port,
                "server.address", hostname
        ));
        app.run(args);
    }
}
