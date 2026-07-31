package com.javastarterkit.patterns.theory.caching;

/**
 * System Design Theory: Caching
 * 
 * This module covers the fundamental concepts of Caching.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class CachingExample {
    
    private final String description;
    
    public CachingExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Caching ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        CachingExample example = new CachingExample(
            "Practical example of Caching concepts in system design"
        );
        example.demonstrate();
    }
}
