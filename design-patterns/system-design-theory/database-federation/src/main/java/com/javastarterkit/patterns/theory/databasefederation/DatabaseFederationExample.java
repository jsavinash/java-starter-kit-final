package com.javastarterkit.patterns.theory.databasefederation;

/**
 * System Design Theory: Database Federation
 * 
 * This module covers the fundamental concepts of Database Federation.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class DatabaseFederationExample {
    
    private final String description;
    
    public DatabaseFederationExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Database Federation ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        DatabaseFederationExample example = new DatabaseFederationExample(
            "Practical example of Database Federation concepts in system design"
        );
        example.demonstrate();
    }
}
