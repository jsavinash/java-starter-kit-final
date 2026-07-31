package com.javastarterkit.patterns.circuitbreaker;

/**
 * Circuit Breaker Pattern Example
 * 
 * Prevents cascading failures by stopping requests to failing services.
 * Like an electrical circuit breaker that stops power flow on overload.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class CircuitBreaker {
    
    public static void demonstrate() {
        System.out.println("\n=== Circuit Breaker Pattern ===");
        System.out.println("Prevents cascading failures\n");
        
        CircuitBreaker breaker = new CircuitBreaker(3, 2000);
        ExternalService service = new ExternalService();
        
        System.out.println("Attempting service calls:");
        for (int i = 1; i <= 5; i++) {
            try {
                breaker.execute(service::call);
            } catch (Exception e) {
                System.out.println("  Call " + i + " failed: " + e.getMessage());
            }
        }
        
        System.out.println("\nBenefits:");
        System.out.println("- Prevents cascading failures");
        System.out.println("- Fast failure detection");
        System.out.println("- Automatic recovery");
    }
    
    interface Service {
        void call() throws Exception;
    }
    
    static class ExternalService {
        private int failureCount = 0;
        
        public void call() throws Exception {
            failureCount++;
            if (failureCount <= 2) {
                throw new Exception("Service unavailable");
            }
            System.out.println("  Service call succeeded");
            failureCount = 0;
        }
    }
    
    static class CircuitBreaker {
        private int threshold;
        private long timeout;
        private int failureCount = 0;
        private long lastFailureTime = 0;
        private String state = "CLOSED";
        
        public CircuitBreaker(int threshold, long timeout) {
            this.threshold = threshold;
            this.timeout = timeout;
        }
        
        public void execute(Service service) throws Exception {
            if (state.equals("OPEN")) {
                if (System.currentTimeMillis() - lastFailureTime > timeout) {
                    state = "HALF-OPEN";
                    System.out.println("  Circuit breaker: HALF-OPEN");
                } else {
                    throw new Exception("Circuit breaker is OPEN");
                }
            }
            
            try {
                service.call();
                failureCount = 0;
                state = "CLOSED";
            } catch (Exception e) {
                failureCount++;
                lastFailureTime = System.currentTimeMillis();
                
                if (failureCount >= threshold) {
                    state = "OPEN";
                    System.out.println("  Circuit breaker: OPEN");
                }
                throw e;
            }
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}