package com.javastarterkit.patterns.theory.availabilityconsistency;

/**
 * System Design Theory: Availability Consistency
 * 
 * This module covers the fundamental concepts of Availability Consistency.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class AvailabilityConsistencyExample {
    
    private final String description;
    
    public AvailabilityConsistencyExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Availability Consistency ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        AvailabilityConsistencyExample example = new AvailabilityConsistencyExample(
            "Practical example of Availability Consistency concepts in system design"
        );
        example.demonstrate();
    }
}
