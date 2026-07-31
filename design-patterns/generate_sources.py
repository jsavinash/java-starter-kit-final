#!/usr/bin/env python3
"""
Generate Java source code examples for all system design patterns.
This script creates proper Java source files for each pattern module.
"""
import os
import shutil

BASE_DIR = os.path.dirname(os.path.abspath(__file__))

def write_java_file(module_path, package, class_name, content):
    """Write a Java source file to the proper location."""
    dir_path = os.path.join(BASE_DIR, module_path, "src/main/java", package.replace('.', '/'))
    os.makedirs(dir_path, exist_ok=True)
    file_path = os.path.join(dir_path, f"{class_name}.java")
    
    full_content = f"package {package};\n\n{content}"
    
    with open(file_path, 'w') as f:
        f.write(full_content)
    print(f"  Created: {file_path}")

def generate_creational():
    """Generate Creational pattern examples."""
    base = "system-design-pattern/creational"
    pkg = "com.javastarterkit.patterns.creational"
    
    # Abstract Factory
    write_java_file(f"{base}/abstract-factory", f"{pkg}.abstractfactory", "AbstractFactoryExample", '''
/**
 * Abstract Factory Pattern
 * Provides an interface for creating families of related objects without specifying their concrete classes.
 */
public class AbstractFactoryExample {
    // Product interfaces
    public interface Button { void render(); }
    public interface Checkbox { void render(); }
    
    // Concrete products for Windows
    public static class WindowsButton implements Button {
        public void render() { System.out.println("Rendering Windows Button"); }
    }
    public static class WindowsCheckbox implements Checkbox {
        public void render() { System.out.println("Rendering Windows Checkbox"); }
    }
    
    // Concrete products for Mac
    public static class MacButton implements Button {
        public void render() { System.out.println("Rendering Mac Button"); }
    }
    public static class MacCheckbox implements Checkbox {
        public void render() { System.out.println("Rendering Mac Checkbox"); }
    }
    
    // Abstract Factory
    public interface GUIFactory {
        Button createButton();
        Checkbox createCheckbox();
    }
    
    public static class WindowsFactory implements GUIFactory {
        public Button createButton() { return new WindowsButton(); }
        public Checkbox createCheckbox() { return new WindowsCheckbox(); }
    }
    
    public static class MacFactory implements GUIFactory {
        public Button createButton() { return new MacButton(); }
        public Checkbox createCheckbox() { return new MacCheckbox(); }
    }
    
    public static void main(String[] args) {
        GUIFactory factory = new WindowsFactory();
        Button button = factory.createButton();
        Checkbox checkbox = factory.createCheckbox();
        button.render();
        checkbox.render();
        
        factory = new MacFactory();
        button = factory.createButton();
        checkbox = factory.createCheckbox();
        button.render();
        checkbox.render();
    }
}
''')
    
    # Builder
    write_java_file(f"{base}/builder", f"{pkg}.builder", "BuilderExample", '''
/**
 * Builder Pattern
 * Separates the construction of a complex object from its representation.
 */
public class BuilderExample {
    public static class Pizza {
        private final String dough;
        private final String sauce;
        private final String topping;
        
        private Pizza(Builder builder) {
            this.dough = builder.dough;
            this.sauce = builder.sauce;
            this.topping = builder.topping;
        }
        
        public static class Builder {
            private String dough = "thin";
            private String sauce = "tomato";
            private String topping = "cheese";
            
            public Builder dough(String dough) { this.dough = dough; return this; }
            public Builder sauce(String sauce) { this.sauce = sauce; return this; }
            public Builder topping(String topping) { this.topping = topping; return this; }
            public Pizza build() { return new Pizza(this); }
        }
        
        @Override
        public String toString() {
            return "Pizza{dough='" + dough + "', sauce='" + sauce + "', topping='" + topping + "'}";
        }
    }
    
    public static void main(String[] args) {
        Pizza pizza = new Pizza.Builder()
            .dough("thick")
            .sauce("bbq")
            .topping("pepperoni")
            .build();
        System.out.println(pizza);
    }
}
''')
    
    # Factory Method
    write_java_file(f"{base}/factory-method", f"{pkg}.factorymethod", "FactoryMethodExample", '''
/**
 * Factory Method Pattern
 * Defines an interface for creating an object, but lets subclasses decide which class to instantiate.
 */
public class FactoryMethodExample {
    public interface Transport {
        void deliver();
    }
    
    public static class Truck implements Transport {
        public void deliver() { System.out.println("Delivering by land in a truck"); }
    }
    
    public static class Ship implements Transport {
        public void deliver() { System.out.println("Delivering by sea in a ship"); }
    }
    
    public abstract static class Logistics {
        public abstract Transport createTransport();
        
        public void planDelivery() {
            Transport transport = createTransport();
            transport.deliver();
        }
    }
    
    public static class RoadLogistics extends Logistics {
        public Transport createTransport() { return new Truck(); }
    }
    
    public static class SeaLogistics extends Logistics {
        public Transport createTransport() { return new Ship(); }
    }
    
    public static void main(String[] args) {
        Logistics logistics = new RoadLogistics();
        logistics.planDelivery();
        
        logistics = new SeaLogistics();
        logistics.planDelivery();
    }
}
''')
    
    # Prototype
    write_java_file(f"{base}/prototype", f"{pkg}.prototype", "PrototypeExample", '''
/**
 * Prototype Pattern
 * Creates new objects by copying an existing object (prototype).
 */
public class PrototypeExample implements Cloneable {
    private String name;
    private int value;
    
    public PrototypeExample(String name, int value) {
        this.name = name;
        this.value = value;
    }
    
    public PrototypeExample clone() {
        try {
            return (PrototypeExample) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void setName(String name) { this.name = name; }
    public void setValue(int value) { this.value = value; }
    
    @Override
    public String toString() {
        return "PrototypeExample{name='" + name + "', value=" + value + "}";
    }
    
    public static void main(String[] args) {
        PrototypeExample original = new PrototypeExample("Original", 100);
        PrototypeExample clone = original.clone();
        clone.setName("Clone");
        clone.setValue(200);
        
        System.out.println("Original: " + original);
        System.out.println("Clone: " + clone);
    }
}
''')
    
    # Singleton (already exists, but let's update it)
    write_java_file(f"{base}/singleton", f"{pkg}.singleton", "SingletonPattern", '''
/**
 * Singleton Pattern
 * Ensures a class has only one instance and provides a global point of access to it.
 * This implementation uses the Bill Pugh Singleton pattern (thread-safe without synchronization).
 */
public class SingletonPattern {
    private SingletonPattern() {}
    
    private static class SingletonHolder {
        private static final SingletonPattern INSTANCE = new SingletonPattern();
    }
    
    public static SingletonPattern getInstance() {
        return SingletonHolder.INSTANCE;
    }
    
    public void doSomething() {
        System.out.println("Singleton instance performing action...");
    }
    
    public static void main(String[] args) {
        SingletonPattern instance = SingletonPattern.getInstance();
        instance.doSomething();
        System.out.println("Instance hash: " + instance.hashCode());
        System.out.println("Same instance? " + (instance == SingletonPattern.getInstance()));
    }
}
''')

def generate_structural():
    """Generate Structural pattern examples."""
    base = "system-design-pattern/structural"
    pkg = "com.javastarterkit.patterns.structural"
    
    # Adapter
    write_java_file(f"{base}/adapter", f"{pkg}.adapter", "AdapterExample", '''
/**
 * Adapter Pattern
 * Allows incompatible interfaces to work together by converting one interface to another.
 */
public class AdapterExample {
    // Target interface
    public interface MediaPlayer {
        void play(String audioType, String fileName);
    }
    
    // Adaptee
    public static class AdvancedMediaPlayer {
        public void playVlc(String fileName) {
            System.out.println("Playing vlc file: " + fileName);
        }
        public void playMp4(String fileName) {
            System.out.println("Playing mp4 file: " + fileName);
        }
    }
    
    // Adapter
    public static class MediaAdapter implements MediaPlayer {
        private AdvancedMediaPlayer advancedPlayer;
        
        public MediaAdapter() {
            advancedPlayer = new AdvancedMediaPlayer();
        }
        
        public void play(String audioType, String fileName) {
            if (audioType.equalsIgnoreCase("vlc")) {
                advancedPlayer.playVlc(fileName);
            } else if (audioType.equalsIgnoreCase("mp4")) {
                advancedPlayer.playMp4(fileName);
            }
        }
    }
    
    // Client
    public static class AudioPlayer implements MediaPlayer {
        private MediaAdapter adapter;
        
        public void play(String audioType, String fileName) {
            if (audioType.equalsIgnoreCase("mp3")) {
                System.out.println("Playing mp3 file: " + fileName);
            } else if (audioType.equalsIgnoreCase("vlc") || audioType.equalsIgnoreCase("mp4")) {
                adapter = new MediaAdapter();
                adapter.play(audioType, fileName);
            } else {
                System.out.println("Invalid media type: " + audioType);
            }
        }
    }
    
    public static void main(String[] args) {
        AudioPlayer player = new AudioPlayer();
        player.play("mp3", "song.mp3");
        player.play("mp4", "video.mp4");
        player.play("vlc", "movie.vlc");
    }
}
''')
    
    # Bridge
    write_java_file(f"{base}/bridge", f"{pkg}.bridge", "BridgeExample", '''
/**
 * Bridge Pattern
 * Decouples an abstraction from its implementation so that the two can vary independently.
 */
public class BridgeExample {
    // Implementor
    public interface Device {
        void turnOn();
        void turnOff();
        void setVolume(int volume);
    }
    
    // Concrete Implementors
    public static class TV implements Device {
        public void turnOn() { System.out.println("TV is ON"); }
        public void turnOff() { System.out.println("TV is OFF"); }
        public void setVolume(int volume) { System.out.println("TV volume set to " + volume); }
    }
    
    public static class Radio implements Device {
        public void turnOn() { System.out.println("Radio is ON"); }
        public void turnOff() { System.out.println("Radio is OFF"); }
        public void setVolume(int volume) { System.out.println("Radio volume set to " + volume); }
    }
    
    // Abstraction
    public static class RemoteControl {
        protected Device device;
        
        public RemoteControl(Device device) { this.device = device; }
        
        public void togglePower() {
            System.out.println("Remote: toggle power");
            device.turnOn();
        }
        
        public void volumeUp() {
            device.setVolume(10);
        }
    }
    
    public static void main(String[] args) {
        RemoteControl remote = new RemoteControl(new TV());
        remote.togglePower();
        remote.volumeUp();
        
        remote = new RemoteControl(new Radio());
        remote.togglePower();
        remote.volumeUp();
    }
}
''')
    
    # Composite
    write_java_file(f"{base}/composite", f"{pkg}.composite", "CompositeExample", '''
/**
 * Composite Pattern
 * Composes objects into tree structures to represent part-whole hierarchies.
 */
public class CompositeExample {
    // Component
    public interface Employee {
        void showDetails();
    }
    
    // Leaf
    public static class Developer implements Employee {
        private String name;
        public Developer(String name) { this.name = name; }
        public void showDetails() { System.out.println("  Developer: " + name); }
    }
    
    // Leaf
    public static class Designer implements Employee {
        private String name;
        public Designer(String name) { this.name = name; }
        public void showDetails() { System.out.println("  Designer: " + name); }
    }
    
    // Composite
    public static class Manager implements Employee {
        private String name;
        private java.util.List<Employee> subordinates = new java.util.ArrayList<>();
        
        public Manager(String name) { this.name = name; }
        public void add(Employee emp) { subordinates.add(emp); }
        public void remove(Employee emp) { subordinates.remove(emp); }
        
        public void showDetails() {
            System.out.println("Manager: " + name);
            for (Employee emp : subordinates) {
                emp.showDetails();
            }
        }
    }
    
    public static void main(String[] args) {
        Manager ceo = new Manager("John (CEO)");
        Manager devManager = new Manager("Alice (Dev Manager)");
        Manager designManager = new Manager("Bob (Design Manager)");
        
        devManager.add(new Developer("Charlie"));
        devManager.add(new Developer("Diana"));
        designManager.add(new Designer("Eve"));
        
        ceo.add(devManager);
        ceo.add(designManager);
        
        ceo.showDetails();
    }
}
''')
    
    # Decorator
    write_java_file(f"{base}/decorator", f"{pkg}.decorator", "DecoratorExample", '''
/**
 * Decorator Pattern
 * Attaches additional responsibilities to an object dynamically.
 */
public class DecoratorExample {
    // Component
    public interface Coffee {
        String getDescription();
        double getCost();
    }
    
    // Concrete Component
    public static class SimpleCoffee implements Coffee {
        public String getDescription() { return "Simple coffee"; }
        public double getCost() { return 2.0; }
    }
    
    // Decorator
    public static abstract class CoffeeDecorator implements Coffee {
        protected Coffee decoratedCoffee;
        public CoffeeDecorator(Coffee coffee) { this.decoratedCoffee = coffee; }
        public String getDescription() { return decoratedCoffee.getDescription(); }
        public double getCost() { return decoratedCoffee.getCost(); }
    }
    
    // Concrete Decorators
    public static class MilkDecorator extends CoffeeDecorator {
        public MilkDecorator(Coffee coffee) { super(coffee); }
        public String getDescription() { return decoratedCoffee.getDescription() + ", milk"; }
        public double getCost() { return decoratedCoffee.getCost() + 0.5; }
    }
    
    public static class SugarDecorator extends CoffeeDecorator {
        public SugarDecorator(Coffee coffee) { super(coffee); }
        public String getDescription() { return decoratedCoffee.getDescription() + ", sugar"; }
        public double getCost() { return decoratedCoffee.getCost() + 0.2; }
    }
    
    public static void main(String[] args) {
        Coffee coffee = new SimpleCoffee();
        System.out.println(coffee.getDescription() + " -> $" + coffee.getCost());
        
        coffee = new MilkDecorator(coffee);
        System.out.println(coffee.getDescription() + " -> $" + coffee.getCost());
        
        coffee = new SugarDecorator(coffee);
        System.out.println(coffee.getDescription() + " -> $" + coffee.getCost());
    }
}
''')
    
    # Facade
    write_java_file(f"{base}/facade", f"{pkg}.facade", "FacadeExample", '''
/**
 * Facade Pattern
 * Provides a simplified interface to a complex subsystem.
 */
public class FacadeExample {
    // Complex subsystem classes
    public static class CPU {
        public void freeze() { System.out.println("CPU: freezing"); }
        public void jump(long position) { System.out.println("CPU: jumping to " + position); }
        public void execute() { System.out.println("CPU: executing"); }
    }
    
    public static class Memory {
        public void load(long position, byte[] data) {
            System.out.println("Memory: loading data at " + position);
        }
    }
    
    public static class HardDrive {
        public byte[] read(long lba, int size) {
            System.out.println("HardDrive: reading " + size + " bytes from " + lba);
            return new byte[size];
        }
    }
    
    // Facade
    public static class ComputerFacade {
        private CPU cpu;
        private Memory memory;
        private HardDrive hardDrive;
        
        public ComputerFacade() {
            this.cpu = new CPU();
            this.memory = new Memory();
            this.hardDrive = new HardDrive();
        }
        
        public void start() {
            System.out.println("=== Computer Starting ===");
            cpu.freeze();
            memory.load(0, hardDrive.read(0, 1024));
            cpu.jump(0);
            cpu.execute();
            System.out.println("=== Computer Started ===");
        }
    }
    
    public static void main(String[] args) {
        ComputerFacade computer = new ComputerFacade();
        computer.start();
    }
}
''')
    
    # Proxy
    write_java_file(f"{base}/proxy", f"{pkg}.proxy", "ProxyExample", '''
/**
 * Proxy Pattern
 * Provides a surrogate or placeholder for another object to control access to it.
 */
public class ProxyExample {
    // Subject interface
    public interface Image {
        void display();
    }
    
    // Real Subject
    public static class RealImage implements Image {
        private String fileName;
        
        public RealImage(String fileName) {
            this.fileName = fileName;
            loadFromDisk();
        }
        
        private void loadFromDisk() {
            System.out.println("Loading " + fileName + " from disk");
        }
        
        public void display() {
            System.out.println("Displaying " + fileName);
        }
    }
    
    // Proxy
    public static class ProxyImage implements Image {
        private RealImage realImage;
        private String fileName;
        
        public ProxyImage(String fileName) { this.fileName = fileName; }
        
        public void display() {
            if (realImage == null) {
                realImage = new RealImage(fileName);
            }
            realImage.display();
        }
    }
    
    public static void main(String[] args) {
        Image image = new ProxyImage("photo.jpg");
        // Image will be loaded from disk only when display() is called
        System.out.println("Image object created, not yet loaded");
        image.display();
        System.out.println("Second call - no loading needed:");
        image.display();
    }
}
''')

def generate_behavioral():
    """Generate Behavioral pattern examples."""
    base = "system-design-pattern/behavioral"
    pkg = "com.javastarterkit.patterns.behavioral"
    
    # Chain of Responsibility
    write_java_file(f"{base}/chain-of-responsibility", f"{pkg}.chainofresponsibility", "ChainOfResponsibilityExample", '''
/**
 * Chain of Responsibility Pattern
 * Passes a request along a chain of handlers until one handles it.
 */
public class ChainOfResponsibilityExample {
    public static abstract class Logger {
        public static final int INFO = 1;
        public static final int DEBUG = 2;
        public static final int ERROR = 3;
        
        protected int level;
        protected Logger nextLogger;
        
        public void setNext(Logger nextLogger) { this.nextLogger = nextLogger; }
        
        public void logMessage(int level, String message) {
            if (this.level <= level) {
                write(message);
            }
            if (nextLogger != null) {
                nextLogger.logMessage(level, message);
            }
        }
        
        protected abstract void write(String message);
    }
    
    public static class ConsoleLogger extends Logger {
        public ConsoleLogger(int level) { this.level = level; }
        protected void write(String message) { System.out.println("Console: " + message); }
    }
    
    public static class FileLogger extends Logger {
        public FileLogger(int level) { this.level = level; }
        protected void write(String message) { System.out.println("File: " + message); }
    }
    
    public static class ErrorLogger extends Logger {
        public ErrorLogger(int level) { this.level = level; }
        protected void write(String message) { System.out.println("Error: " + message); }
    }
    
    public static void main(String[] args) {
        Logger errorLogger = new ErrorLogger(Logger.ERROR);
        Logger fileLogger = new FileLogger(Logger.DEBUG);
        Logger consoleLogger = new ConsoleLogger(Logger.INFO);
        
        errorLogger.setNext(fileLogger);
        fileLogger.setNext(consoleLogger);
        
        errorLogger.logMessage(Logger.INFO, "This is an info message");
        System.out.println("---");
        errorLogger.logMessage(Logger.ERROR, "This is an error message");
    }
}
''')
    
    # Command
    write_java_file(f"{base}/command", f"{pkg}.command", "CommandExample", '''
/**
 * Command Pattern
 * Encapsulates a request as an object, allowing parameterization and queuing of requests.
 */
public class CommandExample {
    // Receiver
    public static class Light {
        public void turnOn() { System.out.println("Light is ON"); }
        public void turnOff() { System.out.println("Light is OFF"); }
    }
    
    // Command interface
    public interface Command {
        void execute();
        void undo();
    }
    
    // Concrete Commands
    public static class LightOnCommand implements Command {
        private Light light;
        public LightOnCommand(Light light) { this.light = light; }
        public void execute() { light.turnOn(); }
        public void undo() { light.turnOff(); }
    }
    
    public static class LightOffCommand implements Command {
        private Light light;
        public LightOffCommand(Light light) { this.light = light; }
        public void execute() { light.turnOff(); }
        public void undo() { light.turnOn(); }
    }
    
    // Invoker
    public static class RemoteControl {
        private Command command;
        private Command lastCommand;
        
        public void setCommand(Command command) { this.command = command; }
        
        public void pressButton() {
            command.execute();
            lastCommand = command;
        }
        
        public void pressUndo() {
            if (lastCommand != null) {
                lastCommand.undo();
            }
        }
    }
    
    public static void main(String[] args) {
        Light light = new Light();
        RemoteControl remote = new RemoteControl();
        
        remote.setCommand(new LightOnCommand(light));
        remote.pressButton();
        
        remote.setCommand(new LightOffCommand(light));
        remote.pressButton();
        
        remote.pressUndo();
    }
}
''')
    
    # Iterator
    write_java_file(f"{base}/iterator", f"{pkg}.iterator", "IteratorExample", '''
/**
 * Iterator Pattern
 * Provides a way to access elements of a collection sequentially without exposing its underlying representation.
 */
public class IteratorExample {
    public interface Iterator<T> {
        boolean hasNext();
        T next();
    }
    
    public interface Container<T> {
        Iterator<T> getIterator();
    }
    
    public static class NameRepository implements Container<String> {
        private String[] names = {"John", "Jane", "Bob", "Alice"};
        
        public Iterator<String> getIterator() {
            return new NameIterator();
        }
        
        private class NameIterator implements Iterator<String> {
            int index;
            
            public boolean hasNext() { return index < names.length; }
            
            public String next() {
                if (hasNext()) {
                    return names[index++];
                }
                return null;
            }
        }
    }
    
    public static void main(String[] args) {
        NameRepository repo = new NameRepository();
        Iterator<String> iterator = repo.getIterator();
        
        while (iterator.hasNext()) {
            System.out.println("Name: " + iterator.next());
        }
    }
}
''')
    
    # Mediator
    write_java_file(f"{base}/mediator", f"{pkg}.mediator", "MediatorExample", '''
/**
 * Mediator Pattern
 * Defines an object that encapsulates how a set of objects interact, promoting loose coupling.
 */
public class MediatorExample {
    // Mediator interface
    public interface ChatMediator {
        void sendMessage(String message, User user);
        void addUser(User user);
    }
    
    // Concrete Mediator
    public static class ChatRoom implements ChatMediator {
        private java.util.List<User> users = new java.util.ArrayList<>();
        
        public void addUser(User user) {
            users.add(user);
        }
        
        public void sendMessage(String message, User user) {
            for (User u : users) {
                if (u != user) {
                    u.receive(message);
                }
            }
        }
    }
    
    // Colleague
    public static abstract class User {
        protected String name;
        protected ChatMediator mediator;
        
        public User(String name, ChatMediator mediator) {
            this.name = name;
            this.mediator = mediator;
        }
        
        public abstract void send(String message);
        public abstract void receive(String message);
    }
    
    public static class ConcreteUser extends User {
        public ConcreteUser(String name, ChatMediator mediator) {
            super(name, mediator);
        }
        
        public void send(String message) {
            System.out.println(name + " sends: " + message);
            mediator.sendMessage(message, this);
        }
        
        public void receive(String message) {
            System.out.println(name + " receives: " + message);
        }
    }
    
    public static void main(String[] args) {
        ChatMediator mediator = new ChatRoom();
        
        User john = new ConcreteUser("John", mediator);
        User jane = new ConcreteUser("Jane", mediator);
        User bob = new ConcreteUser("Bob", mediator);
        
        mediator.addUser(john);
        mediator.addUser(jane);
        mediator.addUser(bob);
        
        john.send("Hello everyone!");
    }
}
''')
    
    # Observer
    write_java_file(f"{base}/observer", f"{pkg}.observer", "ObserverExample", '''
/**
 * Observer Pattern
 * Defines a one-to-many dependency between objects so that when one object changes state,
 * all its dependents are notified and updated automatically.
 */
public class ObserverExample {
    // Observer interface
    public interface Observer {
        void update(String message);
    }
    
    // Subject
    public static class NewsAgency {
        private String news;
        private java.util.List<Observer> observers = new java.util.ArrayList<>();
        
        public void addObserver(Observer observer) { observers.add(observer); }
        public void removeObserver(Observer observer) { observers.remove(observer); }
        
        public void setNews(String news) {
            this.news = news;
            notifyObservers();
        }
        
        private void notifyObservers() {
            for (Observer observer : observers) {
                observer.update(news);
            }
        }
    }
    
    // Concrete Observers
    public static class NewsChannel implements Observer {
        private String name;
        public NewsChannel(String name) { this.name = name; }
        public void update(String message) {
            System.out.println(name + " received news: " + message);
        }
    }
    
    public static void main(String[] args) {
        NewsAgency agency = new NewsAgency();
        
        agency.addObserver(new NewsChannel("CNN"));
        agency.addObserver(new NewsChannel("BBC"));
        agency.addObserver(new NewsChannel("Al Jazeera"));
        
        agency.setNews("Breaking: Java 25 released!");
        System.out.println("---");
        agency.setNews("Update: Gradle 9.0 is now available");
    }
}
''')
    
    # State
    write_java_file(f"{base}/state", f"{pkg}.state", "StateExample", '''
/**
 * State Pattern
 * Allows an object to alter its behavior when its internal state changes.
 */
public class StateExample {
    // State interface
    public interface State {
        void doAction(Context context);
    }
    
    // Context
    public static class Context {
        private State state;
        
        public Context() { state = null; }
        public void setState(State state) { this.state = state; }
        public State getState() { return state; }
    }
    
    // Concrete States
    public static class StartState implements State {
        public void doAction(Context context) {
            System.out.println("Player is in START state");
            context.setState(this);
        }
        public String toString() { return "Start State"; }
    }
    
    public static class StopState implements State {
        public void doAction(Context context) {
            System.out.println("Player is in STOP state");
            context.setState(this);
        }
        public String toString() { return "Stop State"; }
    }
    
    public static void main(String[] args) {
        Context context = new Context();
        
        StartState startState = new StartState();
        startState.doAction(context);
        System.out.println("Current state: " + context.getState());
        
        StopState stopState = new StopState();
        stopState.doAction(context);
        System.out.println("Current state: " + context.getState());
    }
}
''')
    
    # Strategy
    write_java_file(f"{base}/strategy", f"{pkg}.strategy", "StrategyExample", '''
/**
 * Strategy Pattern
 * Defines a family of algorithms, encapsulates each one, and makes them interchangeable.
 */
public class StrategyExample {
    // Strategy interface
    public interface PaymentStrategy {
        void pay(int amount);
    }
    
    // Concrete Strategies
    public static class CreditCardPayment implements PaymentStrategy {
        private String cardNumber;
        public CreditCardPayment(String cardNumber) { this.cardNumber = cardNumber; }
        public void pay(int amount) {
            System.out.println("Paid $" + amount + " using Credit Card " + cardNumber);
        }
    }
    
    public static class PayPalPayment implements PaymentStrategy {
        private String email;
        public PayPalPayment(String email) { this.email = email; }
        public void pay(int amount) {
            System.out.println("Paid $" + amount + " using PayPal (" + email + ")");
        }
    }
    
    public static class CryptoPayment implements PaymentStrategy {
        private String walletAddress;
        public CryptoPayment(String walletAddress) { this.walletAddress = walletAddress; }
        public void pay(int amount) {
            System.out.println("Paid $" + amount + " using Crypto wallet " + walletAddress);
        }
    }
    
    // Context
    public static class ShoppingCart {
        private java.util.List<String> items = new java.util.ArrayList<>();
        
        public void addItem(String item) { items.add(item); }
        public void checkout(PaymentStrategy paymentStrategy) {
            int total = items.size() * 10; // simplified pricing
            paymentStrategy.pay(total);
        }
    }
    
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Book");
        cart.addItem("Laptop");
        
        cart.checkout(new CreditCardPayment("1234-5678-9012-3456"));
        cart.checkout(new PayPalPayment("user@example.com"));
        cart.checkout(new CryptoPayment("0xABC123"));
    }
}
''')
    
    # Template Method
    write_java_file(f"{base}/template-method", f"{pkg}.templatemethod", "TemplateMethodExample", '''
/**
 * Template Method Pattern
 * Defines the skeleton of an algorithm in a method, deferring some steps to subclasses.
 */
public class TemplateMethodExample {
    public static abstract class DataProcessor {
        // Template method
        public final void process() {
            loadData();
            processData();
            saveData();
            if (shouldValidate()) {
                validateData();
            }
        }
        
        protected abstract void loadData();
        protected abstract void processData();
        protected abstract void saveData();
        
        // Hook method - subclasses can override
        protected boolean shouldValidate() { return false; }
        protected void validateData() {}
    }
    
    public static class CSVProcessor extends DataProcessor {
        protected void loadData() { System.out.println("Loading CSV file"); }
        protected void processData() { System.out.println("Processing CSV data"); }
        protected void saveData() { System.out.println("Saving processed CSV data"); }
    }
    
    public static class JSONProcessor extends DataProcessor {
        protected void loadData() { System.out.println("Loading JSON file"); }
        protected void processData() { System.out.println("Processing JSON data"); }
        protected void saveData() { System.out.println("Saving processed JSON data"); }
        protected boolean shouldValidate() { return true; }
        protected void validateData() { System.out.println("Validating JSON schema"); }
    }
    
    public static void main(String[] args) {
        System.out.println("=== CSV Processing ===");
        DataProcessor csvProcessor = new CSVProcessor();
        csvProcessor.process();
        
        System.out.println("\\n=== JSON Processing ===");
        DataProcessor jsonProcessor = new JSONProcessor();
        jsonProcessor.process();
    }
}
''')
    
    # Visitor
    write_java_file(f"{base}/visitor", f"{pkg}.visitor", "VisitorExample", '''
/**
 * Visitor Pattern
 * Represents an operation to be performed on elements of an object structure.
 * Lets you define a new operation without changing the classes of the elements.
 */
public class VisitorExample {
    // Element interface
    public interface Shape {
        void accept(ShapeVisitor visitor);
    }
    
    // Concrete Elements
    public static class Circle implements Shape {
        private double radius;
        public Circle(double radius) { this.radius = radius; }
        public double getRadius() { return radius; }
        public void accept(ShapeVisitor visitor) { visitor.visit(this); }
    }
    
    public static class Rectangle implements Shape {
        private double width, height;
        public Rectangle(double width, double height) { this.width = width; this.height = height; }
        public double getWidth() { return width; }
        public double getHeight() { return height; }
        public void accept(ShapeVisitor visitor) { visitor.visit(this); }
    }
    
    // Visitor interface
    public interface ShapeVisitor {
        void visit(Circle circle);
        void visit(Rectangle rectangle);
    }
    
    // Concrete Visitors
    public static class AreaCalculator implements ShapeVisitor {
        private double totalArea;
        
        public void visit(Circle circle) {
            double area = Math.PI * circle.getRadius() * circle.getRadius();
            totalArea += area;
            System.out.println("Circle area: " + area);
        }
        
        public void visit(Rectangle rectangle) {
            double area = rectangle.getWidth() * rectangle.getHeight();
            totalArea += area;
            System.out.println("Rectangle area: " + area);
        }
        
        public double getTotalArea() { return totalArea; }
    }
    
    public static void main(String[] args) {
        Shape[] shapes = {new Circle(5), new Rectangle(4, 6)};
        
        AreaCalculator calculator = new AreaCalculator();
        for (Shape shape : shapes) {
            shape.accept(calculator);
        }
        System.out.println("Total area: " + calculator.getTotalArea());
    }
}
''')

def generate_concurrency():
    """Generate Concurrency pattern examples."""
    base = "system-design-pattern/concurrency"
    pkg = "com.javastarterkit.patterns.concurrency"
    
    # Producer-Consumer
    write_java_file(f"{base}/producer-consumer", f"{pkg}.producerconsumer", "ProducerConsumerExample", '''
/**
 * Producer-Consumer Pattern
 * Decouples producers and consumers by using a shared buffer/queue.
 */
public class ProducerConsumerExample {
    public static class SharedQueue {
        private java.util.Queue<Integer> queue = new java.util.LinkedList<>();
        private final int capacity;
        
        public SharedQueue(int capacity) { this.capacity = capacity; }
        
        public synchronized void produce(int value) throws InterruptedException {
            while (queue.size() == capacity) {
                wait();
            }
            queue.add(value);
            System.out.println("Produced: " + value + " (size: " + queue.size() + ")");
            notifyAll();
        }
        
        public synchronized int consume() throws InterruptedException {
            while (queue.isEmpty()) {
                wait();
            }
            int value = queue.poll();
            System.out.println("Consumed: " + value + " (size: " + queue.size() + ")");
            notifyAll();
            return value;
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        SharedQueue queue = new SharedQueue(5);
        
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try { queue.produce(i); Thread.sleep(100); }
                catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        });
        
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try { queue.consume(); Thread.sleep(200); }
                catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        });
        
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        System.out.println("Producer-Consumer completed");
    }
}
''')
    
    # Read-Write Lock
    write_java_file(f"{base}/read-write-lock", f"{pkg}.readwritelock", "ReadWriteLockExample", '''
/**
 * Read-Write Lock Pattern
 * Allows concurrent read access but exclusive write access to shared resources.
 */
public class ReadWriteLockExample {
    public static class ReadWriteLock {
        private int readers = 0;
        private int writers = 0;
        private int writeRequests = 0;
        
        public synchronized void lockRead() throws InterruptedException {
            while (writers > 0 || writeRequests > 0) {
                wait();
            }
            readers++;
        }
        
        public synchronized void unlockRead() {
            readers--;
            notifyAll();
        }
        
        public synchronized void lockWrite() throws InterruptedException {
            writeRequests++;
            while (readers > 0 || writers > 0) {
                wait();
            }
            writeRequests--;
            writers++;
        }
        
        public synchronized void unlockWrite() {
            writers--;
            notifyAll();
        }
    }
    
    public static class SharedData {
        private int data = 0;
        private ReadWriteLock lock = new ReadWriteLock();
        
        public void read() throws InterruptedException {
            lock.lockRead();
            System.out.println(Thread.currentThread().getName() + " read: " + data);
            Thread.sleep(100);
            lock.unlockRead();
        }
        
        public void write(int value) throws InterruptedException {
            lock.lockWrite();
            data = value;
            System.out.println(Thread.currentThread().getName() + " wrote: " + value);
            Thread.sleep(200);
            lock.unlockWrite();
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        SharedData data = new SharedData();
        
        Thread[] readers = new Thread[3];
        for (int i = 0; i < 3; i++) {
            readers[i] = new Thread(() -> {
                for (int j = 0; j < 2; j++) {
                    try { data.read(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                }
            }, "Reader-" + i);
        }
        
        Thread writer = new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                try { data.write(i * 100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        }, "Writer");
        
        for (Thread r : readers) r.start();
        writer.start();
        
        for (Thread r : readers) r.join();
        writer.join();
    }
}
''')
    
    # Thread Pool
    write_java_file(f"{base}/thread-pool", f"{pkg}.threadpool", "ThreadPoolExample", '''
/**
 * Thread Pool Pattern
 * Manages a pool of worker threads to execute tasks concurrently.
 */
public class ThreadPoolExample {
    public static class ThreadPool {
        private final java.util.concurrent.BlockingQueue<Runnable> taskQueue;
        private final java.util.List<WorkerThread> workers;
        private volatile boolean isStopped = false;
        
        public ThreadPool(int numThreads) {
            taskQueue = new java.util.concurrent.LinkedBlockingQueue<>();
            workers = new java.util.ArrayList<>();
            
            for (int i = 0; i < numThreads; i++) {
                WorkerThread worker = new WorkerThread("Worker-" + i);
                worker.start();
                workers.add(worker);
            }
        }
        
        public void execute(Runnable task) {
            if (isStopped) throw new IllegalStateException("ThreadPool is stopped");
            taskQueue.offer(task);
        }
        
        public void shutdown() {
            isStopped = true;
            for (WorkerThread worker : workers) {
                worker.interrupt();
            }
        }
        
        private class WorkerThread extends Thread {
            public WorkerThread(String name) { super(name); }
            
            public void run() {
                while (!isStopped) {
                    try {
                        Runnable task = taskQueue.poll(100, java.util.concurrent.TimeUnit.MILLISECONDS);
                        if (task != null) {
                            System.out.println(getName() + " executing task");
                            task.run();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool(3);
        
        for (int i = 0; i < 6; i++) {
            final int taskId = i;
            pool.execute(() -> {
                System.out.println("Task " + taskId + " completed by " + Thread.currentThread().getName());
                try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            });
        }
        
        Thread.sleep(3000);
        pool.shutdown();
        System.out.println("ThreadPool shutdown complete");
    }
}
''')

def generate_resilience():
    """Generate Resilience pattern examples."""
    base = "system-design-pattern/resilience"
    pkg = "com.javastarterkit.patterns.resilience"
    
    # Circuit Breaker
    write_java_file(f"{base}/circuit-breaker", f"{pkg}.circuitbreaker", "CircuitBreakerExample", '''
/**
 * Circuit Breaker Pattern
 * Prevents cascading failures by detecting failures and preventing calls to a failing service.
 */
public class CircuitBreakerExample {
    public enum State { CLOSED, OPEN, HALF_OPEN }
    
    public static class CircuitBreaker {
        private State state = State.CLOSED;
        private int failureCount = 0;
        private final int threshold;
        private final long timeout;
        private long lastFailureTime;
        
        public CircuitBreaker(int threshold, long timeout) {
            this.threshold = threshold;
            this.timeout = timeout;
        }
        
        public synchronized boolean allowRequest() {
            if (state == State.OPEN) {
                if (System.currentTimeMillis() - lastFailureTime > timeout) {
                    state = State.HALF_OPEN;
                    System.out.println("Circuit: HALF_OPEN - trying again");
                    return true;
                }
                System.out.println("Circuit: OPEN - request blocked");
                return false;
            }
            return true;
        }
        
        public synchronized void onSuccess() {
            failureCount = 0;
            if (state == State.HALF_OPEN) {
                state = State.CLOSED;
                System.out.println("Circuit: CLOSED - service recovered");
            }
        }
        
        public synchronized void onFailure() {
            failureCount++;
            lastFailureTime = System.currentTimeMillis();
            if (failureCount >= threshold) {
                state = State.OPEN;
                System.out.println("Circuit: OPEN - threshold reached (" + failureCount + " failures)");
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        CircuitBreaker cb = new CircuitBreaker(3, 1000);
        
        // Simulate failures
        for (int i = 0; i < 5; i++) {
            if (cb.allowRequest()) {
                System.out.println("Request " + i + " allowed");
                cb.onFailure();
            } else {
                System.out.println("Request " + i + " blocked by circuit breaker");
            }
        }
        
        // Wait for timeout
        System.out.println("\\nWaiting for circuit breaker timeout...");
        Thread.sleep(1100);
        
        // Try again
        if (cb.allowRequest()) {
            System.out.println("Request after timeout allowed");
            cb.onSuccess();
        }
        
        System.out.println("\\nFinal circuit state: " + (cb.allowRequest() ? "CLOSED" : "OPEN"));
    }
}
''')

def generate_microservices():
    """Generate Microservices pattern examples."""
    base = "system-design-pattern/microservices"
    pkg = "com.javastarterkit.patterns.microservices"
    
    # API Gateway
    write_java_file(f"{base}/api-gateway", f"{pkg}.apigateway", "ApiGatewayExample", '''
/**
 * API Gateway Pattern
 * Provides a single entry point for all client requests, routing them to appropriate services.
 */
public class ApiGatewayExample {
    // Microservices
    public static class UserService {
        public String getUser(String userId) {
            return "User{id='" + userId + "', name='John Doe'}";
        }
    }
    
    public static class OrderService {
        public String getOrders(String userId) {
            return "[Order{id='1', total=100}, Order{id='2', total=200}]";
        }
    }
    
    public static class ProductService {
        public String getProduct(String productId) {
            return "Product{id='" + productId + "', name='Laptop', price=999}";
        }
    }
    
    // API Gateway
    public static class ApiGateway {
        private UserService userService = new UserService();
        private OrderService orderService = new OrderService();
        private ProductService productService = new ProductService();
        
        public String getUserProfile(String userId) {
            return "{\\n  user: " + userService.getUser(userId) + 
                   ",\\n  orders: " + orderService.getOrders(userId) + "\\n}";
        }
        
        public String getProductDetails(String productId) {
            return productService.getProduct(productId);
        }
    }
    
    public static void main(String[] args) {
        ApiGateway gateway = new ApiGateway();
        System.out.println("=== User Profile (Aggregated) ===");
        System.out.println(gateway.getUserProfile("user123"));
        System.out.println("\\n=== Product Details ===");
        System.out.println(gateway.getProductDetails("prod456"));
    }
}
''')

def generate_architectural():
    """Generate Architectural pattern examples."""
    base = "system-design-pattern/architectural"
    pkg = "com.javastarterkit.patterns.architectural"
    
    # MVC
    write_java_file(f"{base}/model-view-controller", f"{pkg}.mvc", "MVCExample", '''
/**
 * Model-View-Controller (MVC) Pattern
 * Separates application into three interconnected components: Model, View, and Controller.
 */
public class MVCExample {
    // Model
    public static class Student {
        private String name;
        private int id;
        
        public Student(String name, int id) { this.name = name; this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
    }
    
    // View
    public static class StudentView {
        public void displayStudent(Student student) {
            System.out.println("Student: [ID=" + student.getId() + ", Name=" + student.getName() + "]");
        }
    }
    
    // Controller
    public static class StudentController {
        private Student model;
        private StudentView view;
        
        public StudentController(Student model, StudentView view) {
            this.model = model;
            this.view = view;
        }
        
        public void setStudentName(String name) { model.setName(name); }
        public String getStudentName() { return model.getName(); }
        public void setStudentId(int id) { model.setId(id); }
        public int getStudentId() { return model.getId(); }
        
        public void updateView() { view.displayStudent(model); }
    }
    
    public static void main(String[] args) {
        Student model = new Student("John Doe", 1);
        StudentView view = new StudentView();
        StudentController controller = new StudentController(model, view);
        
        controller.updateView();
        controller.setStudentName("Jane Smith");
        controller.updateView();
    }
}
''')

def generate_data_access():
    """Generate Data Access pattern examples."""
    base = "system-design-pattern/data-access"
    pkg = "com.javastarterkit.patterns.dataaccess"
    
    # Repository
    write_java_file(f"{base}/repository", f"{pkg}.repository", "RepositoryExample", '''
/**
 * Repository Pattern
 * Mediates between the domain and data mapping layers, acting like an in-memory collection.
 */
public class RepositoryExample {
    // Entity
    public static class User {
        private Long id;
        private String name;
        private String email;
        
        public User(Long id, String name, String email) {
            this.id = id; this.name = name; this.email = email;
        }
        
        public Long getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        
        @Override
        public String toString() {
            return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
        }
    }
    
    // Repository interface
    public interface UserRepository {
        User findById(Long id);
        java.util.List<User> findAll();
        void save(User user);
        void delete(Long id);
    }
    
    // In-memory implementation
    public static class InMemoryUserRepository implements UserRepository {
        private java.util.Map<Long, User> database = new java.util.HashMap<>();
        private long nextId = 1;
        
        public User findById(Long id) { return database.get(id); }
        
        public java.util.List<User> findAll() {
            return new java.util.ArrayList<>(database.values());
        }
        
        public void save(User user) {
            if (user.getId() == null) {
                database.put(nextId++, user);
            } else {
                database.put(user.getId(), user);
            }
        }
        
        public void delete(Long id) { database.remove(id); }
    }
    
    public static void main(String[] args) {
        UserRepository repo = new InMemoryUserRepository();
        
        repo.save(new User(null, "Alice", "alice@example.com"));
        repo.save(new User(null, "Bob", "bob@example.com"));
        
        System.out.println("All users:");
        for (User user : repo.findAll()) {
            System.out.println("  " + user);
        }
        
        System.out.println("\\nFind by ID 1: " + repo.findById(1L));
    }
}
''')

def generate_functional():
    """Generate Functional pattern examples."""
    base = "system-design-pattern/functional"
    pkg = "com.javastarterkit.patterns.functional"
    
    # Monad
    write_java_file(f"{base}/monad", f"{pkg}.monad", "MonadExample", '''
/**
 * Monad Pattern (Optional/Maybe Monad)
 * Wraps a value and provides operations to transform it while handling null/empty cases.
 */
public class MonadExample {
    public static class Optional<T> {
        private final T value;
        
        private Optional(T value) { this.value = value; }
        
        public static <T> Optional<T> of(T value) {
            return new Optional<>(java.util.Objects.requireNonNull(value));
        }
        
        public static <T> Optional<T> ofNullable(T value) {
            return new Optional<>(value);
        }
        
        public static <T> Optional<T> empty() {
            return new Optional<>(null);
        }
        
        public boolean isPresent() { return value != null; }
        
        public <R> Optional<R> map(java.util.function.Function<T, R> mapper) {
            if (value == null) return empty();
            return ofNullable(mapper.apply(value));
        }
        
        public <R> Optional<R> flatMap(java.util.function.Function<T, Optional<R>> mapper) {
            if (value == null) return empty();
            return mapper.apply(value);
        }
        
        public T orElse(T defaultValue) {
            return value != null ? value : defaultValue;
        }
        
        @Override
        public String toString() {
            return value != null ? "Optional[" + value + "]" : "Optional.empty";
        }
    }
    
    public static void main(String[] args) {
        Optional<String> name = Optional.ofNullable("John");
        Optional<String> empty = Optional.empty();
        
        System.out.println("Name: " + name.map(String::toUpperCase).orElse("Unknown"));
        System.out.println("Empty: " + empty.map(String::toUpperCase).orElse("Unknown"));
        
        // Chaining
        String result = Optional.ofNullable("hello")
            .map(String::toUpperCase)
            .map(s -> s + " WORLD")
            .orElse("default");
        System.out.println("Chained result: " + result);
    }
}
''')

def generate_solid():
    """Generate SOLID Principles examples."""
    base = "system-design-pattern/solid-principles"
    pkg = "com.javastarterkit.patterns.solid"
    
    # Single Responsibility
    write_java_file(f"{base}/single-responsibility-principle", f"{pkg}.singleresponsibility", "SingleResponsibilityExample", '''
/**
 * Single Responsibility Principle (SRP)
 * A class should have only one reason to change.
 * 
 * This example separates concerns: EmailContent for content, EmailSender for sending.
 */
public class SingleResponsibilityExample {
    // Class with single responsibility: hold email content
    public static class EmailContent {
        private String to;
        private String subject;
        private String body;
        
        public EmailContent(String to, String subject, String body) {
            this.to = to; this.subject = subject; this.body = body;
        }
        
        public String getTo() { return to; }
        public String getSubject() { return subject; }
        public String getBody() { return body; }
    }
    
    // Class with single responsibility: send emails
    public static class EmailSender {
        public void send(EmailContent content) {
            System.out.println("Sending email to: " + content.getTo());
            System.out.println("Subject: " + content.getSubject());
            System.out.println("Body: " + content.getBody());
            System.out.println("Email sent successfully!");
        }
    }
    
    public static void main(String[] args) {
        EmailContent content = new EmailContent("user@example.com", "Hello", "This is a test email");
        EmailSender sender = new EmailSender();
        sender.send(content);
    }
}
''')
    
    # Open-Closed
    write_java_file(f"{base}/open-close-principle", f"{pkg}.openclosed", "OpenClosedExample", '''
/**
 * Open-Closed Principle (OCP)
 * Classes should be open for extension but closed for modification.
 * 
 * New shapes can be added without modifying the AreaCalculator.
 */
public class OpenClosedExample {
    public interface Shape {
        double calculateArea();
    }
    
    public static class Circle implements Shape {
        private double radius;
        public Circle(double radius) { this.radius = radius; }
        public double calculateArea() { return Math.PI * radius * radius; }
    }
    
    public static class Rectangle implements Shape {
        private double width, height;
        public Rectangle(double width, double height) { this.width = width; this.height = height; }
        public double calculateArea() { return width * height; }
    }
    
    // New shape - no modification needed to AreaCalculator
    public static class Triangle implements Shape {
        private double base, height;
        public Triangle(double base, double height) { this.base = base; this.height = height; }
        public double calculateArea() { return 0.5 * base * height; }
    }
    
    // Closed for modification, open for extension
    public static class AreaCalculator {
        public double calculateTotalArea(Shape[] shapes) {
            double total = 0;
            for (Shape shape : shapes) {
                total += shape.calculateArea();
            }
            return total;
        }
    }
    
    public static void main(String[] args) {
        Shape[] shapes = {new Circle(5), new Rectangle(4, 6), new Triangle(3, 8)};
        AreaCalculator calculator = new AreaCalculator();
        System.out.println("Total area: " + calculator.calculateTotalArea(shapes));
    }
}
''')
    
    # Liskov Substitution
    write_java_file(f"{base}/liskov-substitution-principle", f"{pkg}.liskovsubstitution", "LiskovSubstitutionExample", '''
/**
 * Liskov Substitution Principle (LSP)
 * Subtypes must be substitutable for their base types.
 * 
 * All bird subtypes can be used where Bird is expected.
 */
public class LiskovSubstitutionExample {
    public static abstract class Bird {
        protected String name;
        public Bird(String name) { this.name = name; }
        public abstract void makeSound();
    }
    
    public static class Sparrow extends Bird {
        public Sparrow() { super("Sparrow"); }
        public void makeSound() { System.out.println("Chirp chirp!"); }
    }
    
    public static class Penguin extends Bird {
        public Penguin() { super("Penguin"); }
        public void makeSound() { System.out.println("Honk honk!"); }
    }
    
    public static class Parrot extends Bird {
        public Parrot() { super("Parrot"); }
        public void makeSound() { System.out.println("Hello! Hello!"); }
    }
    
    public static void main(String[] args) {
        Bird[] birds = {new Sparrow(), new Penguin(), new Parrot()};
        
        for (Bird bird : birds) {
            System.out.print(bird.name + " says: ");
            bird.makeSound(); // All subtypes work correctly
        }
    }
}
''')
    
    # Interface Segregation
    write_java_file(f"{base}/interface-segregation-principle", f"{pkg}.interfacesegregation", "InterfaceSegregationExample", '''
/**
 * Interface Segregation Principle (ISP)
 * Clients should not be forced to depend on interfaces they do not use.
 * 
 * Instead of one large interface, we have smaller, specific interfaces.
 */
public class InterfaceSegregationExample {
    // Segregated interfaces
    public interface Workable {
        void work();
    }
    
    public interface Eatable {
        void eat();
    }
    
    public interface Sleepable {
        void sleep();
    }
    
    // Robot only implements Workable
    public static class Robot implements Workable {
        public void work() { System.out.println("Robot is working"); }
    }
    
    // Human implements all three
    public static class Human implements Workable, Eatable, Sleepable {
        public void work() { System.out.println("Human is working"); }
        public void eat() { System.out.println("Human is eating"); }
        public void sleep() { System.out.println("Human is sleeping"); }
    }
    
    public static void main(String[] args) {
        Robot robot = new Robot();
        Human human = new Human();
        
        robot.work();
        human.work();
        human.eat();
        human.sleep();
    }
}
''')
    
    # Dependency Inversion
    write_java_file(f"{base}/dependency-inversion-principle", f"{pkg}.dependencyinversion", "DependencyInversionExample", '''
/**
 * Dependency Inversion Principle (DIP)
 * Depend on abstractions, not on concretions.
 * 
 * High-level modules should not depend on low-level modules. Both should depend on abstractions.
 */
public class DependencyInversionExample {
    // Abstraction
    public interface MessageService {
        void sendMessage(String message, String recipient);
    }
    
    // Low-level modules
    public static class EmailService implements MessageService {
        public void sendMessage(String message, String recipient) {
            System.out.println("Email sent to " + recipient + ": " + message);
        }
    }
    
    public static class SMSService implements MessageService {
        public void sendMessage(String message, String recipient) {
            System.out.println("SMS sent to " + recipient + ": " + message);
        }
    }
    
    // High-level module depends on abstraction
    public static class NotificationService {
        private MessageService messageService;
        
        public NotificationService(MessageService messageService) {
            this.messageService = messageService;
        }
        
        public void notify(String message, String recipient) {
            messageService.sendMessage(message, recipient);
        }
    }
    
    public static void main(String[] args) {
        NotificationService emailNotifier = new NotificationService(new EmailService());
        NotificationService smsNotifier = new NotificationService(new SMSService());
        
        emailNotifier.notify("Hello via email", "user@example.com");
        smsNotifier.notify("Hello via SMS", "+1234567890");
    }
}
''')

def generate_integration():
    """Generate Integration pattern examples."""
    base = "system-design-pattern/integration"
    pkg = "com.javastarterkit.patterns.integration"
    
    # Ambassador
    write_java_file(f"{base}/ambassador", f"{pkg}.ambassador", "AmbassadorExample", '''
/**
 * Ambassador Pattern
 * Creates a helper service that offloads common client connectivity tasks.
 */
public class AmbassadorExample {
    // Remote Service
    public static class RemoteService {
        public String processRequest(String request) {
            return "Processed: " + request;
        }
    }
    
    // Ambassador (proxy with retry, logging, etc.)
    public static class ServiceAmbassador {
        private RemoteService service = new RemoteService();
        private static final int MAX_RETRIES = 3;
        
        public String request(String request) {
            System.out.println("Ambassador: forwarding request '" + request + "'");
            
            for (int i = 0; i < MAX_RETRIES; i++) {
                try {
                    String response = service.processRequest(request);
                    System.out.println("Ambassador: received response");
                    return response;
                } catch (Exception e) {
                    System.out.println("Ambassador: attempt " + (i+1) + " failed, retrying...");
                }
            }
            return "Ambassador: all retries exhausted";
        }
    }
    
    public static void main(String[] args) {
        ServiceAmbassador ambassador = new ServiceAmbassador();
        String result = ambassador.request("Hello World");
        System.out.println("Client received: " + result);
    }
}
''')

def generate_testing():
    """Generate Testing pattern examples."""
    base = "system-design-pattern/testing"
    pkg = "com.javastarterkit.patterns.testing"
    
    # Arrange-Act-Assert
    write_java_file(f"{base}/arrange-act-assert", f"{pkg}.arrangeactassert", "ArrangeActAssertExample", '''
/**
 * Arrange-Act-Assert (AAA) Pattern
 * Structures test cases into three clear sections: setup, execution, and verification.
 */
public class ArrangeActAssertExample {
    public static class Calculator {
        public int add(int a, int b) { return a + b; }
        public int subtract(int a, int b) { return a - b; }
        public int multiply(int a, int b) { return a * b; }
        public int divide(int a, int b) {
            if (b == 0) throw new IllegalArgumentException("Cannot divide by zero");
            return a / b;
        }
    }
    
    public static void main(String[] args) {
        // Arrange
        Calculator calculator = new Calculator();
        int expected = 15;
        
        // Act
        int result = calculator.add(10, 5);
        
        // Assert
        assert result == expected : "Expected " + expected + " but got " + result;
        System.out.println("Test passed: " + result + " == " + expected);
        
        // More tests
        assert calculator.subtract(10, 3) == 7 : "Subtract failed";
        assert calculator.multiply(4, 5) == 20 : "Multiply failed";
        assert calculator.divide(10, 2) == 5 : "Divide failed";
        
        System.out.println("All tests passed!");
    }
}
''')

def generate_performance():
    """Generate Performance Optimization pattern examples."""
    base = "system-design-pattern/performance-optimization"
    pkg = "com.javastarterkit.patterns.performance"
    
    # Lazy Loading
    write_java_file(f"{base}/lazy-loading", f"{pkg}.lazyloading", "LazyLoadingExample", '''
/**
 * Lazy Loading Pattern
 * Defers initialization of an object until it is actually needed.
 */
public class LazyLoadingExample {
    public static class HeavyResource {
        public HeavyResource() {
            System.out.println("HeavyResource: Loading expensive resource...");
            try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            System.out.println("HeavyResource: Loaded!");
        }
        
        public void process() {
            System.out.println("HeavyResource: Processing data...");
        }
    }
    
    public static class ResourceWrapper {
        private HeavyResource resource;
        
        public HeavyResource getResource() {
            if (resource == null) {
                resource = new HeavyResource(); // Lazy initialization
            }
            return resource;
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Creating wrapper (no loading yet)...");
        ResourceWrapper wrapper = new ResourceWrapper();
        
        System.out.println("First access - resource will be loaded:");
        wrapper.getResource().process();
        
        System.out.println("\\nSecond access - resource already loaded:");
        wrapper.getResource().process();
    }
}
''')

def generate_messaging():
    """Generate Messaging pattern examples."""
    base = "system-design-pattern/messaging"
    pkg = "com.javastarterkit.patterns.messaging"
    
    # Event Aggregator
    write_java_file(f"{base}/event-aggregator", f"{pkg}.eventaggregator", "EventAggregatorExample", '''
/**
 * Event Aggregator Pattern
 * Provides a centralized channel for events, decoupling publishers from subscribers.
 */
public class EventAggregatorExample {
    public static class EventAggregator {
        private java.util.Map<String, java.util.List<java.util.function.Consumer<String>>> listeners = new java.util.HashMap<>();
        
        public void subscribe(String eventType, java.util.function.Consumer<String> handler) {
            listeners.computeIfAbsent(eventType, k -> new java.util.ArrayList<>()).add(handler);
        }
        
        public void publish(String eventType, String data) {
            java.util.List<java.util.function.Consumer<String>> handlers = listeners.get(eventType);
            if (handlers != null) {
                for (java.util.function.Consumer<String> handler : handlers) {
                    handler.accept(data);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        EventAggregator aggregator = new EventAggregator();
        
        // Subscribe to events
        aggregator.subscribe("user.login", data -> 
            System.out.println("Logging: User logged in - " + data));
        aggregator.subscribe("user.login", data -> 
            System.out.println("Email: Send welcome email to " + data));
        aggregator.subscribe("user.logout", data -> 
            System.out.println("Logging: User logged out - " + data));
        
        // Publish events
        System.out.println("=== User Login Event ===");
        aggregator.publish("user.login", "john@example.com");
        
        System.out.println("\\n=== User Logout Event ===");
        aggregator.publish("user.logout", "john@example.com");
    }
}
''')

def generate_resource_management():
    """Generate Resource Management pattern examples."""
    base = "system-design-pattern/resource-management"
    pkg = "com.javastarterkit.patterns.resource"
    
    # RAII
    write_java_file(f"{base}/resource-acquisition-is-initialization", f"{pkg}.raii", "RAIIExample", '''
/**
 * Resource Acquisition Is Initialization (RAII) Pattern
 * Binds resource lifecycle to object lifetime - resource is acquired in constructor and released in destructor.
 * In Java, this is implemented using try-with-resources and AutoCloseable.
 */
public class RAIIExample {
    public static class DatabaseConnection implements AutoCloseable {
        private final String name;
        
        public DatabaseConnection(String name) {
            this.name = name;
            System.out.println("Acquiring connection: " + name);
        }
        
        public void query(String sql) {
            System.out.println("Executing query on " + name + ": " + sql);
        }
        
        @Override
        public void close() {
            System.out.println("Releasing connection: " + name);
        }
    }
    
    public static void main(String[] args) {
        // try-with-resources ensures close() is called automatically
        try (DatabaseConnection conn = new DatabaseConnection("PrimaryDB")) {
            conn.query("SELECT * FROM users");
            conn.query("INSERT INTO logs VALUES ('test')");
            System.out.println("Operations completed, closing connection...");
        } // close() called automatically here
        
        System.out.println("\\nConnection properly released.");
    }
}
''')

if __name__ == "__main__":
    print("Generating Creational patterns...")
    generate_creational()
    
    print("Generating Structural patterns...")
    generate_structural()
    
    print("Generating Behavioral patterns...")
    generate_behavioral()
    
    print("Generating Concurrency patterns...")
    generate_concurrency()
    
    print("Generating Resilience patterns...")
    generate_resilience()
    
    print("Generating Microservices patterns...")
    generate_microservices()
    
    print("Generating Architectural patterns...")
    generate_architectural()
    
    print("Generating Data Access patterns...")
    generate_data_access()
    
    print("Generating Functional patterns...")
    generate_functional()
    
    print("Generating SOLID Principles...")
    generate_solid()
    
    print("Generating Integration patterns...")
    generate_integration()
    
    print("Generating Testing patterns...")
    generate_testing()
    
    print("Generating Performance Optimization patterns...")
    generate_performance()
    
    print("Generating Messaging patterns...")
    generate_messaging()
    
    print("Generating Resource Management patterns...")
    generate_resource_management()
    
    print("\\nAll source files generated successfully!")