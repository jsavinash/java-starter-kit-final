package com.javastarterkit.patterns.strategy;

/**
 * Strategy Pattern
 * 
 * Interchangeable algorithms that can be selected at runtime.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Strategy {
    
    // Strategy interface
    interface PaymentStrategy {
        void pay(double amount);
    }
    
    // Concrete Strategies
    static class CreditCardPayment implements PaymentStrategy {
        private String cardNumber;
        public CreditCardPayment(String cardNumber) { this.cardNumber = cardNumber; }
        @Override
        public void pay(double amount) {
            System.out.println("Paid $" + amount + " using Credit Card " + cardNumber);
        }
    }
    
    static class PayPalPayment implements PaymentStrategy {
        private String email;
        public PayPalPayment(String email) { this.email = email; }
        @Override
        public void pay(double amount) {
            System.out.println("Paid $" + amount + " using PayPal (" + email + ")");
        }
    }
    
    // Context
    static class ShoppingCart {
        private PaymentStrategy paymentStrategy;
        public void setPaymentStrategy(PaymentStrategy strategy) { this.paymentStrategy = strategy; }
        public void checkout(double amount) { paymentStrategy.pay(amount); }
    }
    
    public static void demonstrate() {
        System.out.println("=== Strategy Pattern ===");
        System.out.println("Interchangeable algorithms that can be selected at runtime.\n");
        
        ShoppingCart cart = new ShoppingCart();
        cart.setPaymentStrategy(new CreditCardPayment("1234-5678-9012-3456"));
        cart.checkout(100.0);
        
        cart.setPaymentStrategy(new PayPalPayment("user@example.com"));
        cart.checkout(50.0);
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}
