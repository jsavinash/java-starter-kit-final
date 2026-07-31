package com.javastarterkit.patterns.theory.slaslosli;

/**
 * System Design Theory: Sla Slo Sli
 * 
 * This module covers the fundamental concepts of Sla Slo Sli.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class SlaSloSliExample {
    
    private final String description;
    
    public SlaSloSliExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Sla Slo Sli ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        SlaSloSliExample example = new SlaSloSliExample(
            "Practical example of Sla Slo Sli concepts in system design"
        );
        example.demonstrate();
    }
}
