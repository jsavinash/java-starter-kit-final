package com.javastarterkit.patterns.command;

/**
 * Command Pattern Example
 * 
 * Encapsulates a request as an object, allowing parameterization and queuing.
 * Like a remote control that can execute various commands.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Command {
    
    public static void demonstrate() {
        System.out.println("\n=== Command Pattern ===");
        System.out.println("Encapsulates requests as objects\n");
        
        // Create receiver (the actual device)
        Light livingRoomLight = new Light();
        
        // Create commands
        Command turnOn = new LightOnCommand(livingRoomLight);
        Command turnOff = new LightOffCommand(livingRoomLight);
        Command dim = new LightDimCommand(livingRoomLight, 50);
        
        // Create invoker (remote control)
        RemoteControl remote = new RemoteControl();
        
        // Execute commands
        System.out.println("Using remote control:");
        remote.setCommand(turnOn);
        remote.pressButton();
        
        remote.setCommand(dim);
        remote.pressButton();
        
        remote.setCommand(turnOff);
        remote.pressButton();
        
        // Queue commands
        System.out.println("\nQueuing commands:");
        MacroCommand macro = new MacroCommand(new Command[]{turnOn, dim, turnOff});
        remote.setCommand(macro);
        remote.pressButton();
        
        System.out.println("\nBenefits:");
        System.out.println("- Decouples invoker from receiver");
        System.out.println("- Commands can be queued and logged");
        System.out.println("- Supports undo/redo operations");
    }
    
    // Command interface
    interface Command {
        void execute();
    }
    
    // Receiver
    static class Light {
        private boolean isOn = false;
        private int brightness = 100;
        
        public void turnOn() {
            isOn = true;
            System.out.println("  Light turned ON");
        }
        
        public void turnOff() {
            isOn = false;
            System.out.println("  Light turned OFF");
        }
        
        public void setBrightness(int level) {
            brightness = level;
            System.out.println("  Brightness set to " + level + "%");
        }
        
        public boolean isOn() {
            return isOn;
        }
    }
    
    // Concrete commands
    static class LightOnCommand implements Command {
        private Light light;
        
        public LightOnCommand(Light light) {
            this.light = light;
        }
        
        @Override
        public void execute() {
            light.turnOn();
        }
    }
    
    static class LightOffCommand implements Command {
        private Light light;
        
        public LightOffCommand(Light light) {
            this.light = light;
        }
        
        @Override
        public void execute() {
            light.turnOff();
        }
    }
    
    static class LightDimCommand implements Command {
        private Light light;
        private int level;
        
        public LightDimCommand(Light light, int level) {
            this.light = light;
            this.level = level;
        }
        
        @Override
        public void execute() {
            light.setBrightness(level);
        }
    }
    
    // Macro command (composite command)
    static class MacroCommand implements Command {
        private Command[] commands;
        
        public MacroCommand(Command[] commands) {
            this.commands = commands;
        }
        
        @Override
        public void execute() {
            System.out.println("  Executing macro command...");
            for (Command command : commands) {
                command.execute();
            }
        }
    }
    
    // Invoker
    static class RemoteControl {
        private Command command;
        
        public void setCommand(Command command) {
            this.command = command;
        }
        
        public void pressButton() {
            if (command != null) {
                command.execute();
            }
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}