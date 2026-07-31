package com.javastarterkit.patterns.templatemethod;

/**
 * Template Method Pattern
 * 
 * Defines algorithm skeleton, letting subclasses fill in steps.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class TemplateMethod {
    
    // Abstract class with template method
    static abstract class DataProcessor {
        public final void process() {
            loadData();
            processData();
            saveData();
            if (shouldValidate()) { validate(); }
        }
        abstract void loadData();
        abstract void processData();
        abstract void saveData();
        boolean shouldValidate() { return false; }
        void validate() {}
    }
    
    // Concrete implementations
    static class CsvProcessor extends DataProcessor {
        @Override void loadData() { System.out.println("  Loading CSV file"); }
        @Override void processData() { System.out.println("  Processing CSV data"); }
        @Override void saveData() { System.out.println("  Saving processed CSV data"); }
    }
    
    static class JsonProcessor extends DataProcessor {
        @Override void loadData() { System.out.println("  Loading JSON file"); }
        @Override void processData() { System.out.println("  Processing JSON data"); }
        @Override void saveData() { System.out.println("  Saving processed JSON data"); }
        @Override boolean shouldValidate() { return true; }
        @Override void validate() { System.out.println("  Validating JSON structure"); }
    }
    
    public static void demonstrate() {
        System.out.println("=== Template Method Pattern ===");
        System.out.println("Defines algorithm skeleton, letting subclasses fill in steps.\n");
        
        System.out.println("Processing CSV:");
        new CsvProcessor().process();
        
        System.out.println("\nProcessing JSON:");
        new JsonProcessor().process();
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}
