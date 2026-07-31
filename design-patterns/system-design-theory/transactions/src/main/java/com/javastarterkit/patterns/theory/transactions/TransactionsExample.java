package com.javastarterkit.patterns.theory.transactions;

/**
 * System Design Theory: Transactions
 * 
 * This module covers the fundamental concepts of Transactions.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class TransactionsExample {
    
    private final String description;
    
    public TransactionsExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Transactions ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        TransactionsExample example = new TransactionsExample(
            "Practical example of Transactions concepts in system design"
        );
        example.demonstrate();
    }
}
