package com.javastarterkit.patterns.theory.sqlvsnosql;

/**
 * System Design Theory: Sql Vs Nosql
 * 
 * This module covers the fundamental concepts of Sql Vs Nosql.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class SqlVsNosqlExample {
    
    private final String description;
    
    public SqlVsNosqlExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Sql Vs Nosql ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        SqlVsNosqlExample example = new SqlVsNosqlExample(
            "Practical example of Sql Vs Nosql concepts in system design"
        );
        example.demonstrate();
    }
}
