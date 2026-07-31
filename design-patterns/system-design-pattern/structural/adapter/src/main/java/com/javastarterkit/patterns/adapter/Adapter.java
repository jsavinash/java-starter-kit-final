package com.javastarterkit.patterns.adapter;

/**
 * Adapter Pattern Example
 * 
 * Converts one interface to another so incompatible interfaces can work together.
 * Like a power adapter that converts between different plug types.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Adapter {
    
    public static void demonstrate() {
        System.out.println("\n=== Adapter Pattern ===");
        System.out.println("Converts incompatible interfaces\n");
        
        // Create a modern payment processor
        ModernPaymentProcessor processor = new ModernPaymentProcessor();
        
        // Use adapter to integrate legacy payment system
        PaymentGateway legacyGateway = new LegacyPaymentGatewayAdapter(new LegacyPaymentSystem());
        
        System.out.println("Processing payments:");
        processor.processPayment(legacyGateway, 100.0);
        processor.processPayment(legacyGateway, 250.0);
        
        System.out.println("\nBenefits:");
        System.out.println("- Enables incompatible interfaces to work together");
        System.out.println("- Promotes code reuse");
        System.out.println("- Decouples client from adaptee");
    }
    
    // Target interface
    interface PaymentGateway {
        void processPayment(double amount);
    }
    
    // Adaptee (legacy system)
    static class LegacyPaymentSystem {
        public void makePayment(int cents) {
            System.out.println("  Legacy system processed: " + cents + " cents");
        }
    }
    
    // Adapter
    static class LegacyPaymentGatewayAdapter implements PaymentGateway {
        private LegacyPaymentSystem legacySystem;
        
        public LegacyPaymentGatewayAdapter(LegacyPaymentSystem legacySystem) {
            this.legacySystem = legacySystem;
        }
        
        @Override
        public void processPayment(double amount) {
            int cents = (int) (amount * 100);
            legacySystem.makePayment(cents);
        }
    }
    
    // Client
    static class ModernPaymentProcessor {
        public void processPayment(PaymentGateway gateway, double amount) {
            gateway.processPayment(amount);
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}