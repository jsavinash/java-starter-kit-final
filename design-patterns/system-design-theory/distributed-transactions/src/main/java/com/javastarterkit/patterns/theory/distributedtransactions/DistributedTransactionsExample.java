package com.javastarterkit.patterns.theory.distributedtransactions;

/**
 * System Design Theory: Distributed Transactions
 * 
 * This module covers the fundamental concepts of Distributed Transactions.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class DistributedTransactionsExample {
    
    private final String description;
    
    public DistributedTransactionsExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Distributed Transactions ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        DistributedTransactionsExample example = new DistributedTransactionsExample(
            "Practical example of Distributed Transactions concepts in system design"
        );
        example.demonstrate();
    }
}
