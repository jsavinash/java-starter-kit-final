package com.javastarterkit.patterns.factorymethod;

/**
 * Factory Method Pattern Example
 * 
 * Creates objects without specifying exact class, using factory methods.
 * Like a payment factory creating different payment types.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class FactoryMethod {
    
    public static void demonstrate() {
        System.out.println("\n=== Factory Method Pattern ===");
        System.out.println("Creates objects using factory methods\n");
        
        PaymentProcessor creditProcessor = new CreditCardProcessor();
        PaymentProcessor paypalProcessor = new PayPalProcessor();
        
        System.out.println("Processing credit card payment:");
        creditProcessor.process(150.0);
        
        System.out.println("\nProcessing PayPal payment:");
        paypalProcessor.process(200.0);
        
        System.out.println("\nBenefits:");
        System.out.println("- Encapsulates object creation");
        System.out.println("- Promotes loose coupling");
        System.out.println("- Open/Closed Principle");
    }
    
    interface Payment {
        void pay(double amount);
    }
    
    static class CreditCardPayment implements Payment {
        @Override
        public void pay(double amount) {
            System.out.println("  Paid $" + amount + " via Credit Card");
        }
    }
    
    static class PayPalPayment implements Payment {
        @Override
        public void pay(double amount) {
            System.out.println("  Paid $" + amount + " via PayPal");
        }
    }
    
    abstract static class PaymentProcessor {
        public void process(double amount) {
            Payment payment = createPayment();
            payment.pay(amount);
        }
        
        protected abstract Payment createPayment();
    }
    
    static class CreditCardProcessor extends PaymentProcessor {
        @Override
        protected Payment createPayment() {
            return new CreditCardPayment();
        }
    }
    
    static class PayPalProcessor extends PaymentProcessor {
        @Override
        protected Payment createPayment() {
            return new PayPalPayment();
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}