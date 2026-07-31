package com.javastarterkit.patterns.bridge;

/**
 * Bridge Pattern Example
 * 
 * Decouples abstraction from implementation, allowing them to vary independently.
 * Like different shapes rendered with different graphics APIs.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Bridge {
    
    public static void demonstrate() {
        System.out.println("\n=== Bridge Pattern ===");
        System.out.println("Decouples abstraction from implementation\n");
        
        // Create shapes with different renderers
        Shape circle1 = new Circle(new VectorRenderer(), 5.0);
        Shape circle2 = new Circle(new RasterRenderer(), 3.0);
        Shape square1 = new Square(new VectorRenderer(), 4.0);
        Shape square2 = new Square(new RasterRenderer(), 6.0);
        
        System.out.println("Drawing shapes with different renderers:");
        System.out.println();
        
        circle1.draw();
        circle2.draw();
        square1.draw();
        square2.draw();
        
        System.out.println("\nBenefits:");
        System.out.println("- Abstraction and implementation can vary independently");
        System.out.println("- Reduces compile-time dependencies");
        System.out.println("- Better code organization and maintainability");
    }
    
    // Implementor interface
    interface Renderer {
        String renderShape(String shape);
        String renderColor(String color);
    }
    
    // Concrete Implementors
    static class VectorRenderer implements Renderer {
        @Override
        public String renderShape(String shape) {
            return "Drawing " + shape + " as vector graphics";
        }
        
        @Override
        public String renderColor(String color) {
            return " with " + color + " fill";
        }
    }
    
    static class RasterRenderer implements Renderer {
        @Override
        public String renderShape(String shape) {
            return "Rendering " + shape + " as pixels";
        }
        
        @Override
        public String renderColor(String color) {
            return " with " + color + " palette";
        }
    }
    
    // Abstraction
    abstract static class Shape {
        protected Renderer renderer;
        protected String color;
        
        protected Shape(Renderer renderer, String color) {
            this.renderer = renderer;
            this.color = color;
        }
        
        public abstract void draw();
    }
    
    // Refined Abstractions
    static class Circle extends Shape {
        private double radius;
        
        public Circle(Renderer renderer, double radius) {
            super(renderer, "Red");
            this.radius = radius;
        }
        
        @Override
        public void draw() {
            System.out.println(renderer.renderShape("Circle(radius=" + radius + ")") + 
                             renderer.renderColor(color));
        }
    }
    
    static class Square extends Shape {
        private double side;
        
        public Square(Renderer renderer, double side) {
            super(renderer, "Blue");
            this.side = side;
        }
        
        @Override
        public void draw() {
            System.out.println(renderer.renderShape("Square(side=" + side + ")") + 
                             renderer.renderColor(color));
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}