package com.javastarterkit.patterns.theory.eventdrivenarchitecture;

/**
 * System Design Theory: Event Driven Architecture
 * 
 * This module covers the fundamental concepts of Event Driven Architecture.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class EventDrivenArchitectureExample {
    
    private final String description;
    
    public EventDrivenArchitectureExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Event Driven Architecture ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        EventDrivenArchitectureExample example = new EventDrivenArchitectureExample(
            "Practical example of Event Driven Architecture concepts in system design"
        );
        example.demonstrate();
    }
}
