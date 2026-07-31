package com.javastarterkit.patterns.theory.performancethroughput;

/**
 * System Design Theory: Performance Throughput
 * 
 * This module covers the fundamental concepts of Performance Throughput.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class PerformanceThroughputExample {
    
    private final String description;
    
    public PerformanceThroughputExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Performance Throughput ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        PerformanceThroughputExample example = new PerformanceThroughputExample(
            "Practical example of Performance Throughput concepts in system design"
        );
        example.demonstrate();
    }
}
