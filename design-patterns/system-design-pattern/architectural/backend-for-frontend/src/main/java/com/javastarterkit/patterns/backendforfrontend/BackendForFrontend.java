package com.javastarterkit.patterns.backendforfrontend;

/**
 * Backend For Frontend (BFF) Pattern Example
 * 
 * Creates separate backend services for different client types (web, mobile, desktop).
 * Optimizes data delivery based on client needs.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class BackendForFrontend {
    
    public static void demonstrate() {
        System.out.println("\n=== Backend For Frontend Pattern ===");
        System.out.println("Optimizes backends for different clients\n");
        
        // Simulate different backend services
        Backend mobileBackend = new MobileBackend();
        Backend webBackend = new WebBackend();
        
        System.out.println("Mobile Request:");
        System.out.println("  Response: " + mobileBackend.getUserData(1));
        
        System.out.println("\nWeb Request:");
        System.out.println("  Response: " + webBackend.getUserData(1));
        
        System.out.println("\nBenefits:");
        System.out.println("- Reduces over-fetching/under-fetching");
        System.out.println("- Optimizes payload per client");
        System.out.println("- Independent scaling");
    }
    
    interface Backend {
        String getUserData(long userId);
    }
    
    static class MobileBackend implements Backend {
        @Override
        public String getUserData(long userId) {
            return "{'id':" + userId + ",'name':'John','mobile_view':true}";
        }
    }
    
    static class WebBackend implements Backend {
        @Override
        public String getUserData(long userId) {
            return "{'id':" + userId + ",'name':'John','email':'john@example.com','dashboard':true}";
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}