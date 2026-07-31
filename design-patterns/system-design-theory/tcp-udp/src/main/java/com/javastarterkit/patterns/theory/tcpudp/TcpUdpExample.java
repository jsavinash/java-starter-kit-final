package com.javastarterkit.patterns.theory.tcpudp;

/**
 * System Design Theory: Tcp Udp
 * 
 * This module covers the fundamental concepts of Tcp Udp.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class TcpUdpExample {
    
    private final String description;
    
    public TcpUdpExample(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void demonstrate() {
        System.out.println("=== Tcp Udp ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }
    
    public static void main(String[] args) {
        TcpUdpExample example = new TcpUdpExample(
            "Practical example of Tcp Udp concepts in system design"
        );
        example.demonstrate();
    }
}
