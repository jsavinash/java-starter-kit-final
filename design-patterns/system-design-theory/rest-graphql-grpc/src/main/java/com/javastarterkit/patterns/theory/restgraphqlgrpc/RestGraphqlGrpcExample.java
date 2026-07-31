package com.javastarterkit.patterns.theory.restgraphqlgrpc;

/**
 * System Design Theory: Rest Graphql Grpc
 * 
 * This module covers the fundamental concepts of Rest Graphql Grpc.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class RestGraphqlGrpcExample {
    
    private final String description;
    
    public RestGraphqlGrpcExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Rest Graphql Grpc ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        RestGraphqlGrpcExample example = new RestGraphqlGrpcExample(
            "Practical example of Rest Graphql Grpc concepts in system design"
        );
        example.demonstrate();
    }
}
