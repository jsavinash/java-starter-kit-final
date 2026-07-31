package com.javastarterkit.patterns.theory.cqrs;

/**
 * System Design Theory: Cqrs
 * 
 * This module covers the fundamental concepts of Cqrs.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class CqrsExample {
    
    private final String description;
    
    public CqrsExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Cqrs ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        CqrsExample example = new CqrsExample(
            "Practical example of Cqrs concepts in system design"
        );
        example.demonstrate();
    }
}
