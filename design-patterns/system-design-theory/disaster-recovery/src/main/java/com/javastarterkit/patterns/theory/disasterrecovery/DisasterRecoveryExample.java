package com.javastarterkit.patterns.theory.disasterrecovery;

/**
 * System Design Theory: Disaster Recovery
 * 
 * This module covers the fundamental concepts of Disaster Recovery.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class DisasterRecoveryExample {
    
    private final String description;
    
    public DisasterRecoveryExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Disaster Recovery ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        DisasterRecoveryExample example = new DisasterRecoveryExample(
            "Practical example of Disaster Recovery concepts in system design"
        );
        example.demonstrate();
    }
}
