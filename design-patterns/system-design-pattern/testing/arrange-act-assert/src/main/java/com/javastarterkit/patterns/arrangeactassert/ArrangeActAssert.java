package com.javastarterkit.patterns.arrangeactassert;

/**
 * Arrange-Act-Assert Pattern
 * 
 * Structures tests into three clear phases.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class ArrangeActAssert {
    
    static class Calculator {
        public int add(int a, int b) { return a + b; }
        public int divide(int a, int b) {
            if (b == 0) throw new IllegalArgumentException("Cannot divide by zero");
            return a / b;
        }
    }
    
    public static void demonstrate() {
        System.out.println("=== Arrange-Act-Assert Pattern ===");
        System.out.println("Structures tests into three clear phases.\n");
        
        // Test 1: Addition
        System.out.println("Test: Calculator.add()");
        // Arrange
        Calculator calc = new Calculator();
        int a = 10, b = 5;
        // Act
        int result = calc.add(a, b);
        // Assert
        assert result == 15 : "Expected 15 but got " + result;
        System.out.println("  PASS: " + a + " + " + b + " = " + result);
        
        // Test 2: Division by zero
        System.out.println("\nTest: Calculator.divide() by zero");
        boolean exceptionThrown = false;
        try {
            calc.divide(10, 0);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected exception was not thrown";
        System.out.println("  PASS: Division by zero throws exception");
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}
