package com.javastarterkit.patterns.theory.consistenthashing;

/**
 * System Design Theory: Consistent Hashing
 * 
 * This module covers the fundamental concepts of Consistent Hashing.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class ConsistentHashingExample {
    
    private final String description;
    
    public ConsistentHashingExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Consistent Hashing ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        ConsistentHashingExample example = new ConsistentHashingExample(
            "Practical example of Consistent Hashing concepts in system design"
        );
        example.demonstrate();
    }
}
