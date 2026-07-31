package com.javastarterkit.patterns.strategy;

/**
 * Strategy Pattern Example
 * 
 * Defines a family of algorithms, encapsulates each one, and makes them interchangeable.
 * Like different sorting algorithms that can be used interchangeably.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Strategy {
    
    public static void demonstrate() {
        System.out.println("\n=== Strategy Pattern ===");
        System.out.println("Encapsulates interchangeable algorithms\n");
        
        // Create shopping cart with different payment strategies
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Laptop", 1200.0);
        cart.addItem("Mouse", 25.0);
        cart.addItem("Keyboard", 75.0);
        
        System.out.println("Total amount: $" + cart.getTotal());
        System.out.println();
        
        // Pay with credit card
        System.out.println("Paying with Credit Card:");
        cart.setPaymentStrategy(new CreditCardPayment("1234-5678-9012-3456", "John Doe"));
        cart.checkout();
        
        System.out.println();
        
        // Pay with PayPal
        System.out.println("Paying with PayPal:");
        cart.setPaymentStrategy(new PayPalPayment("john@example.com"));
        cart.checkout();
        
        System.out.println();
        
        // Pay with cryptocurrency
        System.out.println("Paying with Bitcoin:");
        cart.setPaymentStrategy(new BitcoinPayment("1A2b3C4d5E6f7G8h9I0j"));
        cart.checkout();
        
        System.out.println("\nBenefits:");
        System.out.println("- Algorithms are interchangeable");
        System.out.println("- Eliminates conditional statements");
        System.out.println("- Easy to add new strategies");
    }
    
    // Strategy interface
    interface PaymentStrategy {
        void pay(double amount);
    }
    
    // Concrete strategies
    static class CreditCardPayment implements PaymentStrategy {
        private String cardNumber;
        private String cardHolder;
        
        public CreditCardPayment(String cardNumber, String cardHolder) {
            this.cardNumber = cardNumber;
            this.cardHolder = cardHolder;
        }
        
        @Override
        public void pay(double amount) {
            System.out.println("  Paid $" + amount + " using Credit Card ending in " + 
                             cardNumber.substring(cardNumber.length() - 4));
        }
    }
    
    static class PayPalPayment implements PaymentStrategy {
        private String email;
        
        public PayPalPayment(String email) {
            this.email = email;
        }
        
        @Override
        public void pay(double amount) {
            System.out.println("  Paid $" + amount + " using PayPal account: " + email);
        }
    }
    
    static class BitcoinPayment implements PaymentStrategy {
        private String walletAddress;
        
        public BitcoinPayment(String walletAddress) {
            this.walletAddress = walletAddress;
        }
        
        @Override
        public void pay(double amount) {
            System.out.println("  Paid $" + amount + " using Bitcoin wallet: " + 
                             walletAddress.substring(0, 10) + "...");
        }
    }
    
    // Context class
    static class ShoppingCart {
        private java.util.Map<String, Double> items = new java.util.HashMap<>();
        private PaymentStrategy paymentStrategy;
        
        public void addItem(String item, double price) {
            items.put(item, price);
            System.out.println("  Added to cart: " + item + " - $" + price);
        }
        
        public double getTotal() {
            double total = 0;
            for (double price : items.values()) {
                total += price;
            }
            return total;
        }
        
        public void setPaymentStrategy(PaymentStrategy strategy) {
            this.paymentStrategy = strategy;
        }
        
        public void checkout() {
            if (paymentStrategy == null) {
                System.out.println("  Error: No payment strategy selected");
                return;
            }
            paymentStrategy.pay(getTotal());
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}