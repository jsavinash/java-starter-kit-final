package com.javastarterkit.patterns.state;

/**
 * State Pattern Example
 * 
 * Allows an object to change behavior when internal state changes.
 * Like a TCP connection that behaves differently based on connection state.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class State {
    
    public static void demonstrate() {
        System.out.println("\n=== State Pattern ===");
        System.out.println("Changes behavior based on internal state\n");
        
        // Create a TCP connection
        TCPConnection connection = new TCPConnection();
        
        System.out.println("Opening connection:");
        connection.open();
        
        System.out.println("\nSending data:");
        connection.send("Hello World");
        
        System.out.println("\nClosing connection:");
        connection.close();
        
        System.out.println("\nTrying to send data on closed connection:");
        connection.send("This should fail");
        
        System.out.println("\nBenefits:");
        System.out.println("- Eliminates conditional state logic");
        System.out.println("- Makes state transitions explicit");
        System.out.println("- Single Responsibility Principle");
    }
    
    // State interface
    interface ConnectionState {
        void open(TCPConnection connection);
        void close(TCPConnection connection);
        void send(TCPConnection connection, String data);
    }
    
    // Concrete states
    static class ClosedState implements ConnectionState {
        @Override
        public void open(TCPConnection connection) {
            System.out.println("  Connection opened");
            connection.setState(new OpenState());
        }
        
        @Override
        public void close(TCPConnection connection) {
            System.out.println("  Connection is already closed");
        }
        
        @Override
        public void send(TCPConnection connection, String data) {
            System.out.println("  Error: Cannot send data on closed connection");
        }
    }
    
    static class OpenState implements ConnectionState {
        @Override
        public void open(TCPConnection connection) {
            System.out.println("  Connection is already open");
        }
        
        @Override
        public void close(TCPConnection connection) {
            System.out.println("  Connection closed");
            connection.setState(new ClosedState());
        }
        
        @Override
        public void send(TCPConnection connection, String data) {
            System.out.println("  Sending data: " + data);
        }
    }
    
    // Context class
    static class TCPConnection {
        private ConnectionState state = new ClosedState();
        
        public void setState(ConnectionState state) {
            this.state = state;
        }
        
        public void open() {
            state.open(this);
        }
        
        public void close() {
            state.close(this);
        }
        
        public void send(String data) {
            state.send(this, data);
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}