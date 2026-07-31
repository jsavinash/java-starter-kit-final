package com.javastarterkit.patterns.stepbuilder;

/**
 * Step Builder Pattern
 * 
 * Guides object construction through predefined steps.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class StepBuilder {
    
    static class Pizza {
        private String dough, sauce, cheese, topping;
        private Pizza() {}
        
        interface DoughStep { SauceStep withDough(String dough); }
        interface SauceStep { CheeseStep withSauce(String sauce); }
        interface CheeseStep { ToppingStep withCheese(String cheese); }
        interface ToppingStep { BuildStep withTopping(String topping); }
        interface BuildStep { Pizza build(); }
        
        static class PizzaBuilder implements DoughStep, SauceStep, CheeseStep, ToppingStep, BuildStep {
            private Pizza pizza = new Pizza();
            public SauceStep withDough(String d) { pizza.dough = d; return this; }
            public CheeseStep withSauce(String s) { pizza.sauce = s; return this; }
            public ToppingStep withCheese(String c) { pizza.cheese = c; return this; }
            public BuildStep withTopping(String t) { pizza.topping = t; return this; }
            public Pizza build() { return pizza; }
        }
        
        static DoughStep start() { return new PizzaBuilder(); }
        
        @Override
        public String toString() { return "Pizza{" + dough + ", " + sauce + ", " + cheese + ", " + topping + "}"; }
    }
    
    public static void demonstrate() {
        System.out.println("=== Step Builder Pattern ===");
        System.out.println("Guides object construction through predefined steps.\n");
        
        Pizza pizza = Pizza.start()
            .withDough("Thin Crust")
            .withSauce("Tomato")
            .withCheese("Mozzarella")
            .withTopping("Pepperoni")
            .build();
        System.out.println("  " + pizza);
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}
