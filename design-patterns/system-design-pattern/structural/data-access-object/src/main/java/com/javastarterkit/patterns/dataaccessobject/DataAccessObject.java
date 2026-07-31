package com.javastarterkit.patterns.dataaccessobject;

/**
 * Data Access Object (DAO) Pattern Example
 * 
 * Separates data access logic from business logic.
 * Like a repository that handles all database operations.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class DataAccessObject {
    
    public static void demonstrate() {
        System.out.println("\n=== Data Access Object Pattern ===");
        System.out.println("Separates data access from business logic\n");
        
        // Create DAO
        UserDao userDao = new UserDaoImpl();
        
        // Perform CRUD operations
        System.out.println("Creating users...");
        userDao.create(new User(1, "Alice", "alice@example.com"));
        userDao.create(new User(2, "Bob", "bob@example.com"));
        
        System.out.println("\nReading user with ID 1:");
        User user = userDao.read(1);
        System.out.println("  User: " + user.getName() + " - " + user.getEmail());
        
        System.out.println("\nUpdating user with ID 1...");
        user.setEmail("alice.new@example.com");
        userDao.update(user);
        
        System.out.println("\nReading all users:");
        for (User u : userDao.getAll()) {
            System.out.println("  " + u);
        }
        
        System.out.println("\nDeleting user with ID 2...");
        userDao.delete(2);
        
        System.out.println("\nRemaining users:");
        for (User u : userDao.getAll()) {
            System.out.println("  " + u);
        }
        
        System.out.println("\nBenefits:");
        System.out.println("- Separates data access from business logic");
        System.out.println("- Makes code more maintainable");
        System.out.println("- Easy to switch data sources");
    }
    
    // Entity class
    static class User {
        private int id;
        private String name;
        private String email;
        
        public User(int id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }
        
        // Getters and setters
        public int getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String toString() {
            return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
        }
    }
    
    // DAO interface
    interface UserDao {
        void create(User user);
        User read(int id);
        void update(User user);
        void delete(int id);
        java.util.List<User> getAll();
    }
    
    // DAO Implementation
    static class UserDaoImpl implements UserDao {
        private java.util.Map<Integer, User> users = new java.util.HashMap<>();
        
        @Override
        public void create(User user) {
            users.put(user.getId(), user);
            System.out.println("  Created user: " + user.getName());
        }
        
        @Override
        public User read(int id) {
            return users.get(id);
        }
        
        @Override
        public void update(User user) {
            users.put(user.getId(), user);
            System.out.println("  Updated user: " + user.getName());
        }
        
        @Override
        public void delete(int id) {
            User user = users.remove(id);
            System.out.println("  Deleted user: " + (user != null ? user.getName() : id));
        }
        
        @Override
        public java.util.List<User> getAll() {
            return new java.util.ArrayList<>(users.values());
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}