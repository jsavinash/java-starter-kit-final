package com.javastarterkit.patterns.singleton;

/**
 * Singleton Pattern Example
 * 
 * Ensures a class has only one instance with global access point.
 * Like a database connection pool manager.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Singleton {
    
    public static void demonstrate() {
        System.out.println("\n=== Singleton Pattern ===");
        System.out.println("Ensures only one instance exists\n");
        
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        
        System.out.println("Connection 1: " + System.identityHashCode(db1));
        System.out.println("Connection 2: " + System.identityHashCode(db2));
        System.out.println("Same instance? " + (db1 == db2));
        
        db1.connect();
        
        System.out.println("\nBenefits:");
        System.out.println("- Controls access to shared resource");
        System.out.println("- Reduces memory footprint");
        System.out.println("- Global point of access");
    }
    
    static class DatabaseConnection {
        private static volatile DatabaseConnection instance;
        private String url;
        
        private DatabaseConnection() {
            this.url = "jdbc:mysql://localhost:3306/mydb";
        }
        
        public static DatabaseConnection getInstance() {
            if (instance == null) {
                synchronized (DatabaseConnection.class) {
                    if (instance == null) {
                        instance = new DatabaseConnection();
                    }
                }
            }
            return instance;
        }
        
        public void connect() {
            System.out.println("  Connected to: " + url);
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}