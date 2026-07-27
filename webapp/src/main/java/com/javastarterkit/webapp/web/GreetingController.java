// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.webapp.web;

import com.javastarterkit.webapp.service.GreetingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that exposes greeting endpoints.
 *
 * Maps to {@code /api/greeting} and demonstrates:
 * - {@code @RestController} for RESTful endpoints
 * - {@code @GetMapping} for HTTP GET requests
 * - Service injection via constructor-based DI
 * - Query parameter handling
 */
@RestController
@RequestMapping("/api")
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    /**
     * Returns a personalized greeting.
     *
     * @param name the name to greet (defaults to "World")
     * @return greeting message with server timestamp
     */
    @GetMapping("/greeting")
    public String greet(@RequestParam(name = "name", defaultValue = "World") String name) {
        return greetingService.greet(name);
    }

    /**
     * Health check endpoint.
     *
     * @return service status
     */
    @GetMapping("/health")
    public String health() {
        return greetingService.health();
    }
}
