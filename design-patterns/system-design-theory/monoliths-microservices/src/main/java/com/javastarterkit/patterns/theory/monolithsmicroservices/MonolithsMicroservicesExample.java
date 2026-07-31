package com.javastarterkit.patterns.theory.monolithsmicroservices;

/**
 * System Design Theory: Monoliths Microservices
 * 
 * This module covers the fundamental concepts of Monoliths Microservices.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class MonolithsMicroservicesExample {
    
    private final String description;
    
    public MonolithsMicroservicesExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Monoliths Microservices ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        MonolithsMicroservicesExample example = new MonolithsMicroservicesExample(
            "Practical example of Monoliths Microservices concepts in system design"
        );
        example.demonstrate();
    }
}
