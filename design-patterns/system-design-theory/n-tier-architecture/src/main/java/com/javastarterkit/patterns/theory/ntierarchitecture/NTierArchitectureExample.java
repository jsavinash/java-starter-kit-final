package com.javastarterkit.patterns.theory.ntierarchitecture;

/**
 * System Design Theory: N Tier Architecture
 * 
 * This module covers the fundamental concepts of N Tier Architecture.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class NTierArchitectureExample {
    
    private final String description;
    
    public NTierArchitectureExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== N Tier Architecture ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        NTierArchitectureExample example = new NTierArchitectureExample(
            "Practical example of N Tier Architecture concepts in system design"
        );
        example.demonstrate();
    }
}
