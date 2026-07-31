package com.javastarterkit.patterns.theory.availability;

/**
 * System Design Theory: Availability
 * 
 * This module covers the fundamental concepts of Availability.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class AvailabilityExample {
    
    private final String description;
    
    public AvailabilityExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Availability ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        AvailabilityExample example = new AvailabilityExample(
            "Practical example of Availability concepts in system design"
        );
        example.demonstrate();
    }
}
