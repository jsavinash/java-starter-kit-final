package com.javastarterkit.patterns.theory.ip;

/**
 * System Design Theory: Ip
 * 
 * This module covers the fundamental concepts of Ip.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class IpExample {
    
    private final String description;
    
    public IpExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Ip ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        IpExample example = new IpExample(
            "Practical example of Ip concepts in system design"
        );
        example.demonstrate();
    }
}
