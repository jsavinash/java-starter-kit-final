package com.javastarterkit.patterns.theory.normalizationdenormalization;

/**
 * System Design Theory: Normalization Denormalization
 * 
 * This module covers the fundamental concepts of Normalization Denormalization.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class NormalizationDenormalizationExample {
    
    private final String description;
    
    public NormalizationDenormalizationExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Normalization Denormalization ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        NormalizationDenormalizationExample example = new NormalizationDenormalizationExample(
            "Practical example of Normalization Denormalization concepts in system design"
        );
        example.demonstrate();
    }
}
