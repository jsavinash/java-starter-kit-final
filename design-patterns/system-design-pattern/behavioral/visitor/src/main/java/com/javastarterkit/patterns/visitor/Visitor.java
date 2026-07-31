package com.javastarterkit.patterns.visitor;

/**
 * Visitor Pattern
 * 
 * Separates algorithms from the objects they operate on.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Visitor {
    
    // Element interface
    interface Shape {
        void accept(VisitorInterface visitor);
    }
    
    // Concrete Elements
    static class Circle implements Shape {
        private double radius;
        public Circle(double radius) { this.radius = radius; }
        public double getRadius() { return radius; }
        @Override
        public void accept(VisitorInterface visitor) { visitor.visit(this); }
    }
    
    static class Rectangle implements Shape {
        private double width, height;
        public Rectangle(double width, double height) { this.width = width; this.height = height; }
        public double getWidth() { return width; }
        public double getHeight() { return height; }
        @Override
        public void accept(VisitorInterface visitor) { visitor.visit(this); }
    }
    
    // Visitor interface
    interface VisitorInterface {
        void visit(Circle circle);
        void visit(Rectangle rectangle);
    }
    
    // Concrete Visitor
    static class AreaCalculator implements VisitorInterface {
        @Override
        public void visit(Circle circle) {
            double area = Math.PI * circle.getRadius() * circle.getRadius();
            System.out.println("  Circle area: " + String.format("%.2f", area));
        }
        @Override
        public void visit(Rectangle rectangle) {
            double area = rectangle.getWidth() * rectangle.getHeight();
            System.out.println("  Rectangle area: " + area);
        }
    }
    
    public static void demonstrate() {
        System.out.println("=== Visitor Pattern ===");
        System.out.println("Separates algorithms from the objects they operate on.\n");
        
        Shape[] shapes = {new Circle(5.0), new Rectangle(4.0, 6.0)};
        AreaCalculator areaCalc = new AreaCalculator();
        for (Shape shape : shapes) {
            shape.accept(areaCalc);
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}
