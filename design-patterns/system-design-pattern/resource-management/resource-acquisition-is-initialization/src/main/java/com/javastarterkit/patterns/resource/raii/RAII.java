package com.javastarterkit.patterns.resource.raii.RAII.java;

/**
 * Resource Acquisition Is Initialization Pattern
 * 
 * System design pattern example demonstrating the Resource Acquisition Is Initialization pattern.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class RAII {
    
    public static void demonstrate() {

        // RAII - Resource management through object lifecycle
        static class FileHandler implements AutoCloseable {
            private String filename;
            private boolean opened = false;
            
            public FileHandler(String filename) {
                this.filename = filename;
                open();
            }
            
            private void open() {
                opened = true;
                System.out.println("  Resource acquired: " + filename);
            }
            
            public void write(String data) {
                if (!opened) throw new RuntimeException("File not opened");
                System.out.println("  Writing to " + filename + ": " + data);
            }
            
            @Override
            public void close() {
                if (opened) {
                    opened = false;
                    System.out.println("  Resource released: " + filename);
                }
            }
        }
        
        System.out.println("=== Resource Acquisition Is Initialization ===");
        System.out.println("Ties resource lifecycle to object lifetime.\n");
        
        System.out.println("Using FileHandler with try-with-resources:");
        try (FileHandler file = new FileHandler("test.txt")) {
            file.write("Hello, World!");
            file.write("Second line");
        }
        
        System.out.println("\nResource automatically released after try block.");
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}
