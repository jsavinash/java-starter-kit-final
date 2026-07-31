package com.javastarterkit.patterns.ambassador;

/**
 * Ambassador Pattern
 * 
 * Helper service that handles retries, logging, and latency.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Ambassador {
    
    static class RemoteService {
        public String call(boolean fail) {
            if (fail) { throw new RuntimeException("Remote service error"); }
            return "Remote service response";
        }
    }
    
    static class ServiceAmbassador {
        private RemoteService service = new RemoteService();
        private int retries = 3;
        
        public String callService(boolean fail) {
            System.out.println("  Ambassador: intercepting request");
            for (int i = 1; i <= retries; i++) {
                try {
                    String result = service.call(fail);
                    System.out.println("  Ambassador: request succeeded");
                    return result;
                } catch (Exception e) {
                    System.out.println("  Ambassador: attempt " + i + " failed: " + e.getMessage());
                    if (i == retries) {
                        System.out.println("  Ambassador: all retries exhausted");
                        return "Fallback response";
                    }
                }
            }
            return "Fallback response";
        }
    }
    
    public static void demonstrate() {
        System.out.println("=== Ambassador Pattern ===");
        System.out.println("Helper service that handles retries, logging, and latency.\n");
        
        ServiceAmbassador ambassador = new ServiceAmbassador();
        
        System.out.println("Calling healthy service:");
        System.out.println("  Response: " + ambassador.callService(false));
        
        System.out.println("\nCalling failing service (with retry):");
        System.out.println("  Response: " + ambassador.callService(true));
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}
