// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.starters.web;

import com.javastarterkit.starters.service.DatabaseService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller demonstrating Spring Boot starters and auto-configuration.
 */
@RestController
@RequestMapping("/api/starters")
public class StarterController {

    private final DatabaseService databaseService;

    public StarterController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @GetMapping("/test-db")
    public ResponseEntity<Map<String, String>> testDatabaseConnection() {
        Map<String, String> response = new HashMap<>();
        try {
            databaseService.testConnection();
            response.put("status", "success");
            response.put("message", "Database connection test completed. Check logs for details.");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Database connection failed: " + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> getInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("application", "Spring Boot Starters & Auto-configuration Example");
        info.put("features", "Starters, Auto-configuration, Manual Configuration");
        info.put("endpoints", "/api/starters/test-db, /api/starters/info");
        return ResponseEntity.ok(info);
    }
}
