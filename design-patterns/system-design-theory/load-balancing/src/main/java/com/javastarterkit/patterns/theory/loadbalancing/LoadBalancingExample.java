package com.javastarterkit.patterns.theory.loadbalancing;

/**
 * System Design Theory: Load Balancing
 * 
 * This module covers the fundamental concepts of Load Balancing.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class LoadBalancingExample {
    
    private final String description;
    
    public LoadBalancingExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Load Balancing ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        LoadBalancingExample example = new LoadBalancingExample(
            "Practical example of Load Balancing concepts in system design"
        );
        example.demonstrate();
    }
}
