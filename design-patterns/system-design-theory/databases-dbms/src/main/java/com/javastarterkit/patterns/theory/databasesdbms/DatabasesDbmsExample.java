package com.javastarterkit.patterns.theory.databasesdbms;

/**
 * System Design Theory: Databases Dbms
 * 
 * This module covers the fundamental concepts of Databases Dbms.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class DatabasesDbmsExample {
    
    private final String description;
    
    public DatabasesDbmsExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Databases Dbms ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        DatabasesDbmsExample example = new DatabasesDbmsExample(
            "Practical example of Databases Dbms concepts in system design"
        );
        example.demonstrate();
    }
}
