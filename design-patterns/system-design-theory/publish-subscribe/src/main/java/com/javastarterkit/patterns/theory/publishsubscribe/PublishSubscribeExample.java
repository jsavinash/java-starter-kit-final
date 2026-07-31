package com.javastarterkit.patterns.theory.publishsubscribe;

/**
 * System Design Theory: Publish Subscribe
 * 
 * This module covers the fundamental concepts of Publish Subscribe.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class PublishSubscribeExample {
    
    private final String description;
    
    public PublishSubscribeExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Publish Subscribe ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        PublishSubscribeExample example = new PublishSubscribeExample(
            "Practical example of Publish Subscribe concepts in system design"
        );
        example.demonstrate();
    }
}
