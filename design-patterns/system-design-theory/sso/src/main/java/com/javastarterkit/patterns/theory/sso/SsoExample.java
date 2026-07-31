package com.javastarterkit.patterns.theory.sso;

/**
 * System Design Theory: Sso
 * 
 * This module covers the fundamental concepts of Sso.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class SsoExample {
    
    private final String description;
    
    public SsoExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Sso ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        SsoExample example = new SsoExample(
            "Practical example of Sso concepts in system design"
        );
        example.demonstrate();
    }
}
