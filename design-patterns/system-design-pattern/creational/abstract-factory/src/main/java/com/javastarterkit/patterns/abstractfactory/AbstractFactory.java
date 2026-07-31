package com.javastarterkit.patterns.abstractfactory;

/**
 * Abstract Factory Pattern
 * 
 * Creates families of related objects without specifying concrete classes.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class AbstractFactory {
    
    interface Button { void render(); }
    interface Checkbox { void render(); }
    
    interface GUIFactory {
        Button createButton();
        Checkbox createCheckbox();
    }
    
    static class WindowsButton implements Button {
        @Override public void render() { System.out.println("  Rendering Windows button"); }
    }
    static class WindowsCheckbox implements Checkbox {
        @Override public void render() { System.out.println("  Rendering Windows checkbox"); }
    }
    static class MacButton implements Button {
        @Override public void render() { System.out.println("  Rendering Mac button"); }
    }
    static class MacCheckbox implements Checkbox {
        @Override public void render() { System.out.println("  Rendering Mac checkbox"); }
    }
    
    static class WindowsFactory implements GUIFactory {
        @Override public Button createButton() { return new WindowsButton(); }
        @Override public Checkbox createCheckbox() { return new WindowsCheckbox(); }
    }
    static class MacFactory implements GUIFactory {
        @Override public Button createButton() { return new MacButton(); }
        @Override public Checkbox createCheckbox() { return new MacCheckbox(); }
    }
    
    public static void demonstrate() {
        System.out.println("=== Abstract Factory Pattern ===");
        System.out.println("Creates families of related objects without specifying concrete classes.\n");
        
        System.out.println("Windows UI:");
        GUIFactory winFactory = new WindowsFactory();
        winFactory.createButton().render();
        winFactory.createCheckbox().render();
        
        System.out.println("\nMac UI:");
        GUIFactory macFactory = new MacFactory();
        macFactory.createButton().render();
        macFactory.createCheckbox().render();
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}
