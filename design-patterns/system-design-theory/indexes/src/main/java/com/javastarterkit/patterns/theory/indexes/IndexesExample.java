package com.javastarterkit.patterns.theory.indexes;

/**
 * System Design Theory: Indexes
 * 
 * This module covers the fundamental concepts of Indexes.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class IndexesExample {
    
    private final String description;
    
    public IndexesExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Indexes ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        IndexesExample example = new IndexesExample(
            "Practical example of Indexes concepts in system design"
        );
        example.demonstrate();
    }
}
