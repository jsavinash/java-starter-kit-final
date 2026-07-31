package com.javastarterkit.patterns.theory.dns;

/**
 * System Design Theory: Dns
 * 
 * This module covers the fundamental concepts of Dns.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class DnsExample {
    
    private final String description;
    
    public DnsExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Dns ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        DnsExample example = new DnsExample(
            "Practical example of Dns concepts in system design"
        );
        example.demonstrate();
    }
}
