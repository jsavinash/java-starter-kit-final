package com.javastarterkit.patterns.templatemethod;

/**
 * Template Method Pattern Example
 * 
 * Defines the skeleton of an algorithm in a base class, letting subclasses override steps.
 * Like a data processing pipeline with fixed steps but variable implementations.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class TemplateMethod {
    
    public static void demonstrate() {
        System.out.println("\n=== Template Method Pattern ===");
        System.out.println("Defines algorithm skeleton with overridable steps\n");
        
        // Create data processors
        DataProcessor csvProcessor = new CSVProcessor();
        DataProcessor jsonProcessor = new JSONProcessor();
        
        System.out.println("Processing CSV file:");
        csvProcessor.process();
        
        System.out.println("\nProcessing JSON file:");
        jsonProcessor.process();
        
        System.out.println("\nBenefits:");
        System.out.println("- Code reuse in base class");
        System.out.println("- Controls inversion of variation");
        System.out.println("- Follows Hollywood Principle");
    }
    
    // Abstract base class with template method
    abstract static class DataProcessor {
        // Template method - defines algorithm structure
        public void process() {
            readData();
            processData();
            writeData();
            System.out.println("  Processing complete!");
        }
        
        protected abstract void readData();
        protected abstract void processData();
        protected abstract void writeData();
    }
    
    // Concrete implementation for CSV
    static class CSVProcessor extends DataProcessor {
        @Override
        protected void readData() {
            System.out.println("  Reading CSV file");
        }
        
        @Override
        protected void processData() {
            System.out.println("  Parsing CSV columns and rows");
        }
        
        @Override
        protected void writeData() {
            System.out.println("  Writing to database");
        }
    }
    
    // Concrete implementation for JSON
    static class JSONProcessor extends DataProcessor {
        @Override
        protected void readData() {
            System.out.println("  Reading JSON file");
        }
        
        @Override
        protected void processData() {
            System.out.println("  Parsing JSON objects and arrays");
        }
        
        @Override
        protected void writeData() {
            System.out.println("  Writing to API endpoint");
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}