package com.javastarterkit.patterns.theory.proxy;

/**
 * System Design Theory: Proxy
 * 
 * This module covers the fundamental concepts of Proxy.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class ProxyExample {
    
    private final String description;
    
    public ProxyExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Proxy ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        ProxyExample example = new ProxyExample(
            "Practical example of Proxy concepts in system design"
        );
        example.demonstrate();
    }
}
