package com.javastarterkit.patterns.theory.eventsourcing;

/**
 * System Design Theory: Event Sourcing
 * 
 * This module covers the fundamental concepts of Event Sourcing.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class EventSourcingExample {
    
    private final String description;
    
    public EventSourcingExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Event Sourcing ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        EventSourcingExample example = new EventSourcingExample(
            "Practical example of Event Sourcing concepts in system design"
        );
        example.demonstrate();
    }
}
