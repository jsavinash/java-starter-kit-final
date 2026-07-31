package com.javastarterkit.patterns.factory;

/**
 * Factory Pattern Example
 * 
 * Creates objects without specifying the exact class to instantiate.
 * Like a payment processor creating different payment method objects.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Factory {
    
    public static void demonstrate() {
        System.out.println("\n=== Factory Pattern ===");
        System.out.println("Creates objects without specifying exact class\n");
        
        // Create different payment methods using factory
        PaymentMethod creditCard = PaymentFactory.createPayment("credit");
        PaymentMethod paypal = PaymentFactory.createPayment("paypal");
        PaymentMethod crypto = PaymentFactory.createPayment("crypto");
        
        // Process payments
        System.out.println("Processing payments:");
        creditCard.processPayment(100.0);
        paypal.processPayment(50.0);
        crypto.processPayment(75.0);
        
        System.out.println("\nBenefits:");
        System.out.println("- Encapsulates object creation logic");
        System.out.println("- Makes code more flexible and maintainable");
        System.out.println("- Follows Open/Closed Principle");
    }
    
    // Product interface
    interface PaymentMethod {
        void processPayment(double amount);
    }
    
    // Concrete products
    static class CreditCardPayment implements PaymentMethod {
        @Override
        public void processPayment(double amount) {
            System.out.println("  Processing credit card payment: $" + amount);
        }
    }
    
    static class PayPalPayment implements PaymentMethod {
        @Override
        public void processPayment(double amount) {
            System.out.println("  Processing PayPal payment: $" + amount);
        }
    }
    
    static class CryptoPayment implements PaymentMethod {
        @Override
        public void processPayment(double amount) {
            System.out.println("  Processing cryptocurrency payment: $" + amount);
        }
    }
    
    // Factory class
    static class PaymentFactory {
        public static PaymentMethod createPayment(String type) {
            switch (type.toLowerCase()) {
                case "credit":
                    return new CreditCardPayment();
                case "paypal":
                    return new PayPalPayment();
                case "crypto":
                    return new CryptoPayment();
                default:
                    throw new IllegalArgumentException("Unknown payment type: " + type);
            }
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}