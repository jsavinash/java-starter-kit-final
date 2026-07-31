package com.javastarterkit.patterns.theory.enterpriseservicebus;

/**
 * System Design Theory: Enterprise Service Bus
 * 
 * This module covers the fundamental concepts of Enterprise Service Bus.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class EnterpriseServiceBusExample {
    
    private final String description;
    
    public EnterpriseServiceBusExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Enterprise Service Bus ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        EnterpriseServiceBusExample example = new EnterpriseServiceBusExample(
            "Practical example of Enterprise Service Bus concepts in system design"
        );
        example.demonstrate();
    }
}
