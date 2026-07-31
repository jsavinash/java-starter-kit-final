package com.javastarterkit.patterns.theory.captheorem;

/**
 * System Design Theory: Cap Theorem
 * 
 * This module covers the fundamental concepts of Cap Theorem.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class CapTheoremExample {
    
    private final String description;
    
    public CapTheoremExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Cap Theorem ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        CapTheoremExample example = new CapTheoremExample(
            "Practical example of Cap Theorem concepts in system design"
        );
        example.demonstrate();
    }
}
