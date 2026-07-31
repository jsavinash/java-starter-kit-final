package com.javastarterkit.patterns.lazyloading;

/**
 * Lazy Loading Pattern
 * 
 * Defers object creation until it's actually needed.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class LazyLoading {
    
    static class HeavyResource {
        private String name;
        public HeavyResource(String name) {
            this.name = name;
            System.out.println("  Loading expensive resource: " + name);
            try { Thread.sleep(100); } catch (InterruptedException e) {}
        }
        public String getName() { return name; }
        public void use() { System.out.println("  Using: " + name); }
    }
    
    static class LazyLoader {
        private HeavyResource resource;
        private String resourceName;
        
        public LazyLoader(String resourceName) { this.resourceName = resourceName; }
        
        public HeavyResource getResource() {
            if (resource == null) { resource = new HeavyResource(resourceName); }
            return resource;
        }
    }
    
    public static void demonstrate() {
        System.out.println("=== Lazy Loading Pattern ===");
        System.out.println("Defers object creation until it's actually needed.\n");
        
        LazyLoader loader = new LazyLoader("Database Connection");
        System.out.println("Loader created (resource not yet loaded)");
        System.out.println("...doing other work...");
        
        System.out.println("\nFirst access to resource:");
        loader.getResource().use();
        
        System.out.println("\nSecond access (already loaded):");
        loader.getResource().use();
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}
