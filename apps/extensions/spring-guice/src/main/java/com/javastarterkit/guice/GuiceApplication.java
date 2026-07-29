package com.javastarterkit.guice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class GuiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuiceApplication.class, args);
    }
}

@RestController
@RequestMapping("/api/guice")
class GuiceController {

    @GetMapping
    public String guiceExample() {
        return "Spring Guice integration example";
    }
}