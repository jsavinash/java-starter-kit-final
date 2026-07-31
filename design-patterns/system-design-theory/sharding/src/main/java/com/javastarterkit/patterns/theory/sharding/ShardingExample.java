package com.javastarterkit.patterns.theory.sharding;

/**
 * System Design Theory: Sharding
 * 
 * This module covers the fundamental concepts of Sharding.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class ShardingExample {
    
    private final String description;
    
    public ShardingExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Sharding ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        ShardingExample example = new ShardingExample(
            "Practical example of Sharding concepts in system design"
        );
        example.demonstrate();
    }
}
