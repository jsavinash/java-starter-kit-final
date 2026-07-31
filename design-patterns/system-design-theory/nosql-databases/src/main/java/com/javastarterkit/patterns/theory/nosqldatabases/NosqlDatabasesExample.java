package com.javastarterkit.patterns.theory.nosqldatabases;

/**
 * System Design Theory: Nosql Databases
 * 
 * This module covers the fundamental concepts of Nosql Databases.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class NosqlDatabasesExample {
    
    private final String description;
    
    public NosqlDatabasesExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Nosql Databases ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        NosqlDatabasesExample example = new NosqlDatabasesExample(
            "Practical example of Nosql Databases concepts in system design"
        );
        example.demonstrate();
    }
}
