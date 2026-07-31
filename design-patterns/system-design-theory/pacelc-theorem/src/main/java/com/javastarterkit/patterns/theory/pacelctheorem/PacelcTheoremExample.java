package com.javastarterkit.patterns.theory.pacelctheorem;

/**
 * System Design Theory: Pacelc Theorem
 * 
 * This module covers the fundamental concepts of Pacelc Theorem.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class PacelcTheoremExample {
    
    private final String description;
    
    public PacelcTheoremExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Pacelc Theorem ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        PacelcTheoremExample example = new PacelcTheoremExample(
            "Practical example of Pacelc Theorem concepts in system design"
        );
        example.demonstrate();
    }
}
