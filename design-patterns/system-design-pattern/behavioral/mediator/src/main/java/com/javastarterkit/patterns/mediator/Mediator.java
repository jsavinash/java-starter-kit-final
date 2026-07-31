package com.javastarterkit.patterns.mediator;

/**
 * Mediator Pattern
 * 
 * Reduces coupling between objects by making them communicate through a mediator.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Mediator {
    
    // Mediator interface
    interface ChatMediator {
        void sendMessage(String message, User user);
    }
    
    // Colleague
    static abstract class User {
        protected String name;
        protected ChatMediator mediator;
        public User(String name, ChatMediator mediator) {
            this.name = name; this.mediator = mediator;
        }
        public abstract void send(String message);
        public abstract void receive(String message);
    }
    
    // Concrete Mediator
    static class ChatRoom implements ChatMediator {
        private java.util.List<User> users = new java.util.ArrayList<>();
        public void addUser(User user) { users.add(user); }
        
        @Override
        public void sendMessage(String message, User sender) {
            for (User user : users) {
                if (user != sender) { user.receive(message); }
            }
        }
    }
    
    // Concrete Colleague
    static class ChatUser extends User {
        public ChatUser(String name, ChatMediator mediator) { super(name, mediator); }
        
        @Override
        public void send(String message) {
            System.out.println(this.name + " sends: " + message);
            mediator.sendMessage(message, this);
        }
        
        @Override
        public void receive(String message) {
            System.out.println(this.name + " receives: " + message);
        }
    }
    
    public static void demonstrate() {
        System.out.println("=== Mediator Pattern ===");
        System.out.println("Reduces coupling between objects by making them communicate through a mediator.\n");
        
        ChatRoom chatRoom = new ChatRoom();
        User alice = new ChatUser("Alice", chatRoom);
        User bob = new ChatUser("Bob", chatRoom);
        User charlie = new ChatUser("Charlie", chatRoom);
        
        chatRoom.addUser(alice);
        chatRoom.addUser(bob);
        chatRoom.addUser(charlie);
        
        alice.send("Hello everyone!");
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}
