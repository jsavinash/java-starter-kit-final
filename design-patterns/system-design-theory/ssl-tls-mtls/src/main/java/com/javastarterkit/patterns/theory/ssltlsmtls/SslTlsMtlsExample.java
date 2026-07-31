package com.javastarterkit.patterns.theory.ssltlsmtls;

/**
 * System Design Theory: Ssl Tls Mtls
 * 
 * This module covers the fundamental concepts of Ssl Tls Mtls.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class SslTlsMtlsExample {
    
    private final String description;
    
    public SslTlsMtlsExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Ssl Tls Mtls ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        SslTlsMtlsExample example = new SslTlsMtlsExample(
            "Practical example of Ssl Tls Mtls concepts in system design"
        );
        example.demonstrate();
    }
}
