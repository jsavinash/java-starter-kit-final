// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.webapp.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

/**
 * Service layer component that provides greeting functionality.
 *
 * Demonstrates dependency injection and service-layer patterns
 * within the monorepo's Spring Boot application structure.
 */
@Service
public class GreetingService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * Generates a personalized greeting message with a timestamp.
     *
     * @param name the name of the person to greet
     * @return a formatted greeting string
     */
    public String greet(String name) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        return "Hello, %s! The current server time is %s.".formatted(name, timestamp);
    }

    /**
     * Returns the current health status of the service.
     *
     * @return a status indicator string
     */
    public String health() {
        return "UP";
    }
}
