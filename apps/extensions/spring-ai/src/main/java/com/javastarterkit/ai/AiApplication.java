package com.javastarterkit.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class AiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiApplication.class, args);
    }
}

@RestController
@RequestMapping("/api/ai")
class AiController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring AI!";
    }
}