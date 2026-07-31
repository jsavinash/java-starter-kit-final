package com.javastarterkit.patterns.singleton;

/**
 * Singleton Pattern Example
 * 
 * Ensures a class has only one instance and provides a global point of access to it.
 * Like a database connection pool or logging service.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Singleton {
    
    public static void demonstrate() {
        System.out.println("\n=== Singleton Pattern ===");
        System.out.println("Ensures only one instance exists\n");
        
        // Get singleton instances
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        
        System.out.println("Creating database connections...");
        db1.connect();
        db2.connect();
        
        System.out.println("\nAre they the same instance? " + (db1 == db2));
        System.out.println("Instance hashcode: " + System.identityHashCode(db1));
        
        System.out.println("\nBenefits:");
        System.out.println("- Ensures only one instance exists");
        System.out.println("- Provides global access point");
        System.out.println("- Lazy initialization possible");
    }
    
    // Singleton class
    static class DatabaseConnection {
        // Thread-safe singleton with double-checked locking
        private static volatile DatabaseConnection instance;
        private String connectionString;
        
        // Private constructor prevents instantiation
        private DatabaseConnection() {
            this.connectionString = "jdbc:mysql://localhost:3306/mydb";
            System.out.println("  DatabaseConnection instance created");
        }
        
        // Public method to get the singleton instance
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
            System.out.println("  Connected to database: " + connectionString);
        }
        
        public void disconnect() {
            System.out.println("  Disconnected from database");
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}