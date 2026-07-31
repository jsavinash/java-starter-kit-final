package com.javastarterkit.patterns.theory.circuitbreaker;

/**
 * System Design Theory: Circuit Breaker
 * 
 * This module covers the fundamental concepts of Circuit Breaker.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class CircuitBreakerExample {
    
    private final String description;
    
    public CircuitBreakerExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Circuit Breaker ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        CircuitBreakerExample example = new CircuitBreakerExample(
            "Practical example of Circuit Breaker concepts in system design"
        );
        example.demonstrate();
    }
}
