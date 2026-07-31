package com.javastarterkit.patterns.theory.ratelimiting;

/**
 * System Design Theory: Rate Limiting
 * 
 * This module covers the fundamental concepts of Rate Limiting.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class RateLimitingExample {
    
    private final String description;
    
    public RateLimitingExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Rate Limiting ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        RateLimitingExample example = new RateLimitingExample(
            "Practical example of Rate Limiting concepts in system design"
        );
        example.demonstrate();
    }
}
