package com.javastarterkit.patterns.theory.sqldatabases;

/**
 * System Design Theory: Sql Databases
 * 
 * This module covers the fundamental concepts of Sql Databases.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class SqlDatabasesExample {
    
    private final String description;
    
    public SqlDatabasesExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Sql Databases ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        SqlDatabasesExample example = new SqlDatabasesExample(
            "Practical example of Sql Databases concepts in system design"
        );
        example.demonstrate();
    }
}
