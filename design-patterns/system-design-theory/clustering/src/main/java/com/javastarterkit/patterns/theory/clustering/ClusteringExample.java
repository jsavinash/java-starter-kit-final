package com.javastarterkit.patterns.theory.clustering;

/**
 * System Design Theory: Clustering
 * 
 * This module covers the fundamental concepts of Clustering.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class ClusteringExample {
    
    private final String description;
    
    public ClusteringExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Clustering ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        ClusteringExample example = new ClusteringExample(
            "Practical example of Clustering concepts in system design"
        );
        example.demonstrate();
    }
}
