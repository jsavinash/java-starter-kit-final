package com.javastarterkit.patterns.theory.oauth2oidc;

/**
 * System Design Theory: Oauth2 Oidc
 * 
 * This module covers the fundamental concepts of Oauth2 Oidc.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class Oauth2OidcExample {
    
    private final String description;
    
    public Oauth2OidcExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Oauth2 Oidc ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        Oauth2OidcExample example = new Oauth2OidcExample(
            "Practical example of Oauth2 Oidc concepts in system design"
        );
        example.demonstrate();
    }
}
