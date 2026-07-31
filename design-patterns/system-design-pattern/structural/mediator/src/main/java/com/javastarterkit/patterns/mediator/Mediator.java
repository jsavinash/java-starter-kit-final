package com.javastarterkit.patterns.mediator;

/**
 * Mediator Pattern Example
 * 
 * Defines an object that encapsulates how objects interact, promoting loose coupling.
 * Like an air traffic control tower coordinating flights.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Mediator {
    
    public static void demonstrate() {
        System.out.println("\n=== Mediator Pattern ===");
        System.out.println("Centralizes communication between objects\n");
        
        // Create mediator
        ChatRoom chatRoom = new ChatRoom();
        
        // Create users
        User alice = new ChatUser("Alice");
        User bob = new ChatUser("Bob");
        User charlie = new ChatUser("Charlie");
        
        // Register users with mediator
        chatRoom.registerUser(alice);
        chatRoom.registerUser(bob);
        chatRoom.registerUser(charlie);
        
        // Users communicate through mediator
        System.out.println("\nAlice sends a message:");
        alice.send("Hello everyone!");
        
        System.out.println("\nBob sends a message:");
        bob.send("Hi Alice!");
        
        System.out.println("\nBenefits:");
        System.out.println("- Centralizes complex communication");
        System.out.println("- Promotes loose coupling");
        System.out.println("- Easier to maintain");
    }
    
    // Mediator interface
    interface ChatMediator {
        void registerUser(User user);
        void sendMessage(String message, User sender);
    }
    
    // Concrete mediator
    static class ChatRoom implements ChatMediator {
        private java.util.List<User> users = new java.util.ArrayList<>();
        
        @Override
        public void registerUser(User user) {
            users.add(user);
            System.out.println("  " + user.getName() + " joined the chat");
        }
        
        @Override
        public void sendMessage(String message, User sender) {
            for (User user : users) {
                if (user != sender) {
                    user.receive(sender.getName(), message);
                }
            }
        }
    }
    
    // Colleague interface
    interface User {
        String getName();
        void send(String message);
        void receive(String sender, String message);
    }
    
    // Concrete colleague
    static class ChatUser implements User {
        private String name;
        private ChatMediator mediator;
        
        public ChatUser(String name) {
            this.name = name;
        }
        
        public void setMediator(ChatMediator mediator) {
            this.mediator = mediator;
        }
        
        @Override
        public String getName() {
            return name;
        }
        
        @Override
        public void send(String message) {
            System.out.println("  " + name + " is sending: " + message);
            mediator.sendMessage(message, this);
        }
        
        @Override
        public void receive(String sender, String message) {
            System.out.println("  " + name + " received from " + sender + ": " + message);
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}