package com.javastarterkit.patterns.theory.databasereplication;

/**
 * System Design Theory: Database Replication
 * 
 * This module covers the fundamental concepts of Database Replication.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class DatabaseReplicationExample {
    
    private final String description;
    
    public DatabaseReplicationExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Database Replication ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        DatabaseReplicationExample example = new DatabaseReplicationExample(
            "Practical example of Database Replication concepts in system design"
        );
        example.demonstrate();
    }
}
