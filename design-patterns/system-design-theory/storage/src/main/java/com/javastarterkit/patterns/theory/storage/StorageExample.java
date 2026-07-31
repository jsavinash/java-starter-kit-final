package com.javastarterkit.patterns.theory.storage;

/**
 * System Design Theory: Storage
 * 
 * This module covers the fundamental concepts of Storage.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class StorageExample {
    
    private final String description;
    
    public StorageExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Storage ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        StorageExample example = new StorageExample(
            "Practical example of Storage concepts in system design"
        );
        example.demonstrate();
    }
}
