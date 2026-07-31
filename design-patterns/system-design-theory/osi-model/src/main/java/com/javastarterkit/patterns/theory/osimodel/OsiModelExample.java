package com.javastarterkit.patterns.theory.osimodel;

/**
 * System Design Theory: Osi Model
 * 
 * This module covers the fundamental concepts of Osi Model.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class OsiModelExample {
    
    private final String description;
    
    public OsiModelExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Osi Model ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        OsiModelExample example = new OsiModelExample(
            "Practical example of Osi Model concepts in system design"
        );
        example.demonstrate();
    }
}
