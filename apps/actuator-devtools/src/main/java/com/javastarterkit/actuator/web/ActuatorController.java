// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.actuator.web;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller demonstrating Actuator endpoints.
 */
@RestController
@RequestMapping("/api/monitoring")
public class ActuatorController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("application", "Actuator & DevTools Example");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> appInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("name", "Spring Boot Actuator Example");
        info.put("version", "1.0.0-SNAPSHOT");
        info.put("description", "Demonstrates Actuator and DevTools features");
        info.put("endpoints", "/actuator/health, /actuator/info, /actuator/metrics");
        return ResponseEntity.ok(info);
    }
}
