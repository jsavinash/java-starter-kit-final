// Copyright © 2012-2024 Java Starter Kit. All rights reserved.
package com.javastarterkit.helloworld.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple REST controller demonstrating basic Spring Boot web concepts.
 *
 * @author Spring Boot Engineering
 * @since 3.3.0
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(@RequestParam(defaultValue = "World") String name) {
        return "Hello, " + name + "!";
    }

    @GetMapping("/greet")
    public String greet() {
        return "Welcome to Spring Boot Study Material!";
    }
}
