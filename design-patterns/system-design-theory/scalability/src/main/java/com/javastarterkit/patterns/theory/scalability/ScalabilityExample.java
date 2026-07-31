package com.javastarterkit.patterns.theory.scalability;

/**
 * System Design Theory: Scalability
 * 
 * This module covers the fundamental concepts of Scalability.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class ScalabilityExample {
    
    private final String description;
    
    public ScalabilityExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Scalability ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        ScalabilityExample example = new ScalabilityExample(
            "Practical example of Scalability concepts in system design"
        );
        example.demonstrate();
    }
}
