package com.javastarterkit.patterns.theory.apigateway;

/**
 * System Design Theory: Api Gateway
 * 
 * This module covers the fundamental concepts of Api Gateway.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class ApiGatewayExample {
    
    private final String description;
    
    public ApiGatewayExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Api Gateway ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        ApiGatewayExample example = new ApiGatewayExample(
            "Practical example of Api Gateway concepts in system design"
        );
        example.demonstrate();
    }
}
