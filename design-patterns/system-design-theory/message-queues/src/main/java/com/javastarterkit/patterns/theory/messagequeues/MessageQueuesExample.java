package com.javastarterkit.patterns.theory.messagequeues;

/**
 * System Design Theory: Message Queues
 * 
 * This module covers the fundamental concepts of Message Queues.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class MessageQueuesExample {
    
    private final String description;
    
    public MessageQueuesExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Message Queues ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        MessageQueuesExample example = new MessageQueuesExample(
            "Practical example of Message Queues concepts in system design"
        );
        example.demonstrate();
    }
}
