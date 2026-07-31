package com.javastarterkit.patterns.factory.factory.Factory.java;

/**
 * Factory Pattern
 * 
 * System design pattern example demonstrating the Factory pattern.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Factory {
    
    public static void demonstrate() {

        // Product interface
        interface Payment {
            void process(double amount);
        }
        
        // Concrete Products
        static class CreditCardPayment implements Payment {
            @Override
            public void process(double amount) {
                System.out.println("Processing credit card payment: $" + amount);
            }
        }
        
        static class PayPalPayment implements Payment {
            @Override
            public void process(double amount) {
                System.out.println("Processing PayPal payment: $" + amount);
            }
        }
        
        static class CryptoPayment implements Payment {
            @Override
            public void process(double amount) {
                System.out.println("Processing cryptocurrency payment: $" + amount);
            }
        }
        
        // Factory
        static class PaymentFactory {
            public static Payment createPayment(String type) {
                switch (type.toLowerCase()) {
                    case "credit": return new CreditCardPayment();
                    case "paypal": return new PayPalPayment();
                    case "crypto": return new CryptoPayment();
                    default: throw new IllegalArgumentException("Unknown payment type: " + type);
                }
            }
        }
        
        System.out.println("=== Factory Pattern ===");
        System.out.println("Creates objects without specifying exact class.\n");
        
        Payment payment1 = PaymentFactory.createPayment("credit");
        payment1.process(100.0);
        
        Payment payment2 = PaymentFactory.createPayment("paypal");
        payment2.process(50.0);
        
        Payment payment3 = PaymentFactory.createPayment("crypto");
        payment3.process(200.0);
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}
