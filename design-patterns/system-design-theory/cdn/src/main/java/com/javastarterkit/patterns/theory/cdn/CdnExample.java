package com.javastarterkit.patterns.theory.cdn;

/**
 * System Design Theory: Cdn
 * 
 * This module covers the fundamental concepts of Cdn.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class CdnExample {
    
    private final String description;
    
    public CdnExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Cdn ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        CdnExample example = new CdnExample(
            "Practical example of Cdn concepts in system design"
        );
        example.demonstrate();
    }
}
