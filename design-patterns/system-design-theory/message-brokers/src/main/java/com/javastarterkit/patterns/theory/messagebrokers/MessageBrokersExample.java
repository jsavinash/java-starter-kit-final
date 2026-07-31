package com.javastarterkit.patterns.theory.messagebrokers;

/**
 * System Design Theory: Message Brokers
 * 
 * This module covers the fundamental concepts of Message Brokers.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class MessageBrokersExample {
    
    private final String description;
    
    public MessageBrokersExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Message Brokers ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        MessageBrokersExample example = new MessageBrokersExample(
            "Practical example of Message Brokers concepts in system design"
        );
        example.demonstrate();
    }
}
