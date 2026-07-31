package com.javastarterkit.patterns.theory.acidbase;

/**
 * System Design Theory: Acid Base
 * 
 * This module covers the fundamental concepts of Acid Base.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class AcidBaseExample {
    
    private final String description;
    
    public AcidBaseExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Acid Base ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        AcidBaseExample example = new AcidBaseExample(
            "Practical example of Acid Base concepts in system design"
        );
        example.demonstrate();
    }
}
