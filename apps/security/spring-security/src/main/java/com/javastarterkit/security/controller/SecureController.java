// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.security.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class SecureController {

    @GetMapping("/profile")
    public ResponseEntity<Map<String, String>> getProfile(Authentication authentication) {
        return ResponseEntity.ok(
                Map.of("username", authentication.getName(), "message", "This is a protected endpoint"));
    }

    @GetMapping("/admin")
    public ResponseEntity<List<String>> adminEndpoint() {
        return ResponseEntity.ok(List.of("Admin data", "Sensitive information"));
    }
}
