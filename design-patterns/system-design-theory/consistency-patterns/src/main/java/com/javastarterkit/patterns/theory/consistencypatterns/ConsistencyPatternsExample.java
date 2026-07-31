package com.javastarterkit.patterns.theory.consistencypatterns;

/**
 * System Design Theory: Consistency Patterns
 * 
 * This module covers the fundamental concepts of Consistency Patterns.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class ConsistencyPatternsExample {
    
    private final String description;
    
    public ConsistencyPatternsExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Consistency Patterns ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        ConsistencyPatternsExample example = new ConsistencyPatternsExample(
            "Practical example of Consistency Patterns concepts in system design"
        );
        example.demonstrate();
    }
}
