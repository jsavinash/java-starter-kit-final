package com.javastarterkit.patterns.theory.vmscontainers;

/**
 * System Design Theory: Vms Containers
 * 
 * This module covers the fundamental concepts of Vms Containers.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class VmsContainersExample {
    
    private final String description;
    
    public VmsContainersExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Vms Containers ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        VmsContainersExample example = new VmsContainersExample(
            "Practical example of Vms Containers concepts in system design"
        );
        example.demonstrate();
    }
}
