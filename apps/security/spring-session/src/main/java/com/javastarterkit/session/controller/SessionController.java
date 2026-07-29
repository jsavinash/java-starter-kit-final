package com.javastarterkit.session.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/session")
public class SessionController {

    @PostMapping("/set")
    public ResponseEntity<String> setSessionAttribute(HttpServletRequest request, @RequestBody Map<String, String> data) {
        HttpSession session = request.getSession();
        data.forEach(session::setAttribute);
        return ResponseEntity.ok("Session attributes set");
    }

    @GetMapping("/get")
    public ResponseEntity<Map<String, String>> getSessionAttribute(HttpServletRequest request, String key) {
        HttpSession session = request.getSession();
        Map<String, String> response = new HashMap<>();
        if (key != null) {
            Object value = session.getAttribute(key);
            response.put(key, value != null ? value.toString() : null);
        } else {
            // Return all session attributes
            request.getSession().getAttributeNames().asIterator()
                    .forEachRemaining(attr -> response.put(attr, session.getAttribute(attr).toString()));
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/invalidate")
    public ResponseEntity<String> invalidateSession(HttpServletRequest request) {
        request.getSession().invalidate();
        return ResponseEntity.ok("Session invalidated");
    }
}