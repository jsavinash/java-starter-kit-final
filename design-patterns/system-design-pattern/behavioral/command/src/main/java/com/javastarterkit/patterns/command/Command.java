package com.javastarterkit.patterns.command;

/**
 * Command Pattern
 * 
 * Encapsulates requests as objects, enabling undo/redo.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Command {
    
    // Command interface
    interface CommandInterface {
        void execute();
        void undo();
    }
    
    // Receiver
    static class Light {
        public void turnOn() { System.out.println("  Light is ON"); }
        public void turnOff() { System.out.println("  Light is OFF"); }
    }
    
    // Concrete Commands
    static class LightOnCommand implements CommandInterface {
        private Light light;
        public LightOnCommand(Light light) { this.light = light; }
        @Override
        public void execute() { light.turnOn(); }
        @Override
        public void undo() { light.turnOff(); }
    }
    
    static class LightOffCommand implements CommandInterface {
        private Light light;
        public LightOffCommand(Light light) { this.light = light; }
        @Override
        public void execute() { light.turnOff(); }
        @Override
        public void undo() { light.turnOn(); }
    }
    
    // Invoker
    static class RemoteControl {
        private CommandInterface command;
        private CommandInterface lastCommand;
        
        public void setCommand(CommandInterface command) { this.command = command; }
        
        public void pressButton() {
            command.execute();
            lastCommand = command;
        }
        
        public void pressUndo() {
            if (lastCommand != null) {
                System.out.println("  Undoing...");
                lastCommand.undo();
            }
        }
    }
    
    public static void demonstrate() {
        System.out.println("=== Command Pattern ===");
        System.out.println("Encapsulates requests as objects, enabling undo/redo.\n");
        
        Light light = new Light();
        RemoteControl remote = new RemoteControl();
        
        remote.setCommand(new LightOnCommand(light));
        remote.pressButton();
        
        remote.setCommand(new LightOffCommand(light));
        remote.pressButton();
        
        remote.pressUndo();
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}
