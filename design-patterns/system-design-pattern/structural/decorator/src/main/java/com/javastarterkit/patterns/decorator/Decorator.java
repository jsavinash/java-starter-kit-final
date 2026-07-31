package com.javastarterkit.patterns.decorator;

/**
 * Decorator Pattern Example
 * 
 * Adds responsibilities to objects dynamically without modifying their structure.
 * Like wrapping a coffee with milk, sugar, or whipped cream.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Decorator {
    
    public static void demonstrate() {
        System.out.println("\n=== Decorator Pattern ===");
        System.out.println("Adds responsibilities to objects dynamically\n");
        
        // Start with a simple coffee
        Coffee coffee = new SimpleCoffee();
        System.out.println("Base coffee: " + coffee.getDescription() + " - $" + coffee.getCost());
        
        // Add milk
        coffee = new MilkDecorator(coffee);
        System.out.println("After adding milk: " + coffee.getDescription() + " - $" + coffee.getCost());
        
        // Add sugar
        coffee = new SugarDecorator(coffee);
        System.out.println("After adding sugar: " + coffee.getDescription() + " - $" + coffee.getCost());
        
        // Add whipped cream
        coffee = new WhippedCreamDecorator(coffee);
        System.out.println("Final coffee: " + coffee.getDescription() + " - $" + coffee.getCost());
        
        System.out.println("\nBenefits:");
        System.out.println("- More flexible than inheritance");
        System.out.println("- Can add/remove responsibilities at runtime");
        System.out.println("- Follows Open/Closed Principle");
    }
    
    // Component interface
    interface Coffee {
        String getDescription();
        double getCost();
    }
    
    // Concrete component
    static class SimpleCoffee implements Coffee {
        @Override
        public String getDescription() {
            return "Simple Coffee";
        }
        
        @Override
        public double getCost() {
            return 2.0;
        }
    }
    
    // Decorator base class
    abstract static class CoffeeDecorator implements Coffee {
        protected Coffee coffee;
        
        public CoffeeDecorator(Coffee coffee) {
            this.coffee = coffee;
        }
    }
    
    // Concrete decorators
    static class MilkDecorator extends CoffeeDecorator {
        public MilkDecorator(Coffee coffee) {
            super(coffee);
        }
        
        @Override
        public String getDescription() {
            return coffee.getDescription() + ", Milk";
        }
        
        @Override
        public double getCost() {
            return coffee.getCost() + 0.5;
        }
    }
    
    static class SugarDecorator extends CoffeeDecorator {
        public SugarDecorator(Coffee coffee) {
            super(coffee);
        }
        
        @Override
        public String getDescription() {
            return coffee.getDescription() + ", Sugar";
        }
        
        @Override
        public double getCost() {
            return coffee.getCost() + 0.2;
        }
    }
    
    static class WhippedCreamDecorator extends CoffeeDecorator {
        public WhippedCreamDecorator(Coffee coffee) {
            super(coffee);
        }
        
        @Override
        public String getDescription() {
            return coffee.getDescription() + ", Whipped Cream";
        }
        
        @Override
        public double getCost() {
            return coffee.getCost() + 0.7;
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}