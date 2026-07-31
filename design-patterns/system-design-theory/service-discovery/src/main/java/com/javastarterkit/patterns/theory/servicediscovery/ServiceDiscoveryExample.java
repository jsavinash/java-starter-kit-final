package com.javastarterkit.patterns.theory.servicediscovery;

/**
 * System Design Theory: Service Discovery
 * 
 * This module covers the fundamental concepts of Service Discovery.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class ServiceDiscoveryExample {
    
    private final String description;
    
    public ServiceDiscoveryExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Service Discovery ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        ServiceDiscoveryExample example = new ServiceDiscoveryExample(
            "Practical example of Service Discovery concepts in system design"
        );
        example.demonstrate();
    }
}
