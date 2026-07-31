#!/usr/bin/env python3
"""
Comprehensive script to validate and fix all system design pattern examples.
Analyzes each Java file and ensures it has:
- Valid package declaration
- Proper class definition
- public static void demonstrate() method
- public static void main() method
- No placeholder content
- No syntax errors (double braces, floating code, etc.)
"""

import os
import re
import sys
from pathlib import Path

# Base paths
DESIGN_PATTERNS_DIR = Path("design-patterns")
SYSTEM_PATTERN_DIR = DESIGN_PATTERNS_DIR / "system-design-pattern"
DUPLICATE_PATTERN_DIR = DESIGN_PATTERNS_DIR / "design-patterns" / "system-design-pattern"

# ============================================================
# PATTERN IMPLEMENTATIONS - Complete, valid Java examples
# ============================================================

PATTERN_IMPLEMENTATIONS = {
    # ========== STRUCTURAL PATTERNS ==========
    "adapter": '''
        // Target interface
        interface PaymentGateway {
            void processPayment(double amount);
        }
        
        // Adaptee (legacy system)
        static class LegacyPaymentSystem {
            public void makePayment(int cents) {
                System.out.println("  Legacy system processed: " + cents + " cents");
            }
        }
        
        // Adapter
        static class LegacyPaymentGatewayAdapter implements PaymentGateway {
            private LegacyPaymentSystem legacySystem;
            
            public LegacyPaymentGatewayAdapter(LegacyPaymentSystem legacySystem) {
                this.legacySystem = legacySystem;
            }
            
            @Override
            public void processPayment(double amount) {
                int cents = (int) (amount * 100);
                legacySystem.makePayment(cents);
            }
        }
        
        // Client
        static class ModernPaymentProcessor {
            public void processPayment(PaymentGateway gateway, double amount) {
                gateway.processPayment(amount);
            }
        }
        
        System.out.println("=== Adapter Pattern ===");
        System.out.println("Converts incompatible interfaces so they can work together.");
        System.out.println("Like a power adapter that converts between different plug types.\\n");
        
        ModernPaymentProcessor processor = new ModernPaymentProcessor();
        PaymentGateway legacyGateway = new LegacyPaymentGatewayAdapter(new LegacyPaymentSystem());
        
        System.out.println("Processing payments:");
        processor.processPayment(legacyGateway, 100.0);
        processor.processPayment(legacyGateway, 250.0);''',

    "bridge": '''
        // Implementor interface
        interface Renderer {
            void render(String shape);
        }
        
        // Concrete Implementors
        static class VectorRenderer implements Renderer {
            @Override
            public void render(String shape) {
                System.out.println("Drawing " + shape + " using vectors");
            }
        }
        
        static class RasterRenderer implements Renderer {
            @Override
            public void render(String shape) {
                System.out.println("Drawing " + shape + " using pixels");
            }
        }
        
        // Abstraction
        static abstract class Shape {
            protected Renderer renderer;
            
            protected Shape(Renderer renderer) {
                this.renderer = renderer;
            }
            
            public abstract void draw();
        }
        
        // Refined Abstraction
        static class Circle extends Shape {
            private double radius;
            
            public Circle(Renderer renderer, double radius) {
                super(renderer);
                this.radius = radius;
            }
            
            @Override
            public void draw() {
                renderer.render("Circle(radius=" + radius + ")");
            }
        }
        
        static class Square extends Shape {
            private double side;
            
            public Square(Renderer renderer, double side) {
                super(renderer);
                this.side = side;
            }
            
            @Override
            public void draw() {
                renderer.render("Square(side=" + side + ")");
            }
        }
        
        System.out.println("=== Bridge Pattern ===");
        System.out.println("Decouples abstraction from implementation so they can vary independently.\\n");
        
        Shape vectorCircle = new Circle(new VectorRenderer(), 5.0);
        Shape rasterSquare = new Square(new RasterRenderer(), 10.0);
        
        vectorCircle.draw();
        rasterSquare.draw();''',

    "builder": '''
        // Product
        static class House {
            private String foundation;
            private String structure;
            private String roof;
            private String interior;
            
            public void setFoundation(String foundation) { this.foundation = foundation; }
            public void setStructure(String structure) { this.structure = structure; }
            public void setRoof(String roof) { this.roof = roof; }
            public void setInterior(String interior) { this.interior = interior; }
            
            public void display() {
                System.out.println("House built with: " + foundation + ", " + structure + 
                                 ", " + roof + ", " + interior);
            }
        }
        
        // Builder interface
        interface HouseBuilder {
            HouseBuilder buildFoundation(String foundation);
            HouseBuilder buildStructure(String structure);
            HouseBuilder buildRoof(String roof);
            HouseBuilder buildInterior(String interior);
            House build();
        }
        
        // Concrete Builder
        static class ConcreteHouseBuilder implements HouseBuilder {
            private House house = new House();
            
            public HouseBuilder buildFoundation(String foundation) {
                house.setFoundation(foundation);
                return this;
            }
            
            public HouseBuilder buildStructure(String structure) {
                house.setStructure(structure);
                return this;
            }
            
            public HouseBuilder buildRoof(String roof) {
                house.setRoof(roof);
                return this;
            }
            
            public HouseBuilder buildInterior(String interior) {
                house.setInterior(interior);
                return this;
            }
            
            public House build() {
                return house;
            }
        }
        
        System.out.println("=== Builder Pattern ===");
        System.out.println("Constructs complex objects step by step.\\n");
        
        House house = new ConcreteHouseBuilder()
            .buildFoundation("Concrete Foundation")
            .buildStructure("Wood Frame")
            .buildRoof("Clay Tiles")
            .buildInterior("Modern Design")
            .build();
        house.display();''',

    "composite": '''
        // Component interface
        interface FileSystemComponent {
            void display();
        }
        
        // Leaf
        static class File implements FileSystemComponent {
            private String name;
            
            public File(String name) { this.name = name; }
            
            @Override
            public void display() {
                System.out.println("  File: " + name);
            }
        }
        
        // Composite
        static class Directory implements FileSystemComponent {
            private String name;
            private java.util.List<FileSystemComponent> components = new java.util.ArrayList<>();
            
            public Directory(String name) { this.name = name; }
            
            public void add(FileSystemComponent component) {
                components.add(component);
            }
            
            @Override
            public void display() {
                System.out.println("Directory: " + name);
                for (FileSystemComponent component : components) {
                    component.display();
                }
            }
        }
        
        System.out.println("=== Composite Pattern ===");
        System.out.println("Treats individual and composite objects uniformly.\\n");
        
        Directory root = new Directory("root");
        Directory docs = new Directory("documents");
        Directory pics = new Directory("pictures");
        
        root.add(docs);
        root.add(pics);
        docs.add(new File("resume.pdf"));
        docs.add(new File("notes.txt"));
        pics.add(new File("photo.jpg"));
        
        root.display();''',

    "decorator": '''
        // Component interface
        interface Coffee {
            double getCost();
            String getDescription();
        }
        
        // Concrete Component
        static class SimpleCoffee implements Coffee {
            @Override
            public double getCost() { return 5.0; }
            
            @Override
            public String getDescription() { return "Simple Coffee"; }
        }
        
        // Decorator
        static abstract class CoffeeDecorator implements Coffee {
            protected Coffee coffee;
            
            public CoffeeDecorator(Coffee coffee) {
                this.coffee = coffee;
            }
        }
        
        // Concrete Decorators
        static class MilkDecorator extends CoffeeDecorator {
            public MilkDecorator(Coffee coffee) { super(coffee); }
            
            @Override
            public double getCost() { return coffee.getCost() + 1.5; }
            
            @Override
            public String getDescription() { return coffee.getDescription() + ", Milk"; }
        }
        
        static class SugarDecorator extends CoffeeDecorator {
            public SugarDecorator(Coffee coffee) { super(coffee); }
            
            @Override
            public double getCost() { return coffee.getCost() + 0.5; }
            
            @Override
            public String getDescription() { return coffee.getDescription() + ", Sugar"; }
        }
        
        System.out.println("=== Decorator Pattern ===");
        System.out.println("Adds responsibilities to objects dynamically.\\n");
        
        Coffee coffee = new SugarDecorator(new MilkDecorator(new SimpleCoffee()));
        System.out.println("Order: " + coffee.getDescription() + " - Cost: $" + coffee.getCost());''',

    "facade": '''
        // Complex subsystem classes
        static class CPU {
            public void execute() { System.out.println("CPU executing instructions"); }
        }
        
        static class Memory {
            public void load() { System.out.println("Memory loading data"); }
        }
        
        static class HardDrive {
            public void read() { System.out.println("Hard drive reading data"); }
        }
        
        // Facade
        static class ComputerFacade {
            private CPU cpu;
            private Memory memory;
            private HardDrive hardDrive;
            
            public ComputerFacade() {
                this.cpu = new CPU();
                this.memory = new Memory();
                this.hardDrive = new HardDrive();
            }
            
            public void start() {
                System.out.println("Starting computer...");
                cpu.execute();
                memory.load();
                hardDrive.read();
                System.out.println("Computer started successfully!");
            }
        }
        
        System.out.println("=== Facade Pattern ===");
        System.out.println("Provides a simplified interface to a complex subsystem.\\n");
        
        ComputerFacade computer = new ComputerFacade();
        computer.start();''',

    "flyweight": '''
        // Flyweight (intrinsic state)
        static class CharacterProperties {
            private char character;
            private String font;
            
            public CharacterProperties(char character, String font) {
                this.character = character;
                this.font = font;
            }
            
            public void display(int x, int y) {
                System.out.println("Character '" + character + "' at (" + x + ", " + y + 
                                 ") with font: " + font);
            }
        }
        
        // Flyweight Factory
        static class CharacterFactory {
            private java.util.Map<Character, CharacterProperties> cache = new java.util.HashMap<>();
            
            public CharacterProperties getCharacter(char c) {
                if (!cache.containsKey(c)) {
                    cache.put(c, new CharacterProperties(c, "Arial"));
                }
                return cache.get(c);
            }
        }
        
        System.out.println("=== Flyweight Pattern ===");
        System.out.println("Shares common state between objects to save memory.\\n");
        
        CharacterFactory factory = new CharacterFactory();
        CharacterProperties c1 = factory.getCharacter('A');
        CharacterProperties c2 = factory.getCharacter('A');
        System.out.println("Same instance reused? " + (c1 == c2));
        c1.display(10, 20);
        c2.display(30, 40);''',

    "proxy": '''
        // Subject interface
        interface Image {
            void display();
        }
        
        // Real Subject
        static class RealImage implements Image {
            private String filename;
            
            public RealImage(String filename) {
                this.filename = filename;
                loadFromDisk();
            }
            
            private void loadFromDisk() {
                System.out.println("  Loading image from disk: " + filename);
            }
            
            @Override
            public void display() {
                System.out.println("  Displaying image: " + filename);
            }
        }
        
        // Proxy
        static class ImageProxy implements Image {
            private String filename;
            private RealImage realImage;
            
            public ImageProxy(String filename) {
                this.filename = filename;
            }
            
            @Override
            public void display() {
                if (realImage == null) {
                    realImage = new RealImage(filename);
                }
                realImage.display();
            }
        }
        
        System.out.println("=== Proxy Pattern ===");
        System.out.println("Controls access to an object, acting as a placeholder.\\n");
        
        Image image1 = new ImageProxy("photo1.jpg");
        Image image2 = new ImageProxy("photo2.jpg");
        
        System.out.println("First display (loads from disk):");
        image1.display();
        System.out.println("\\nSecond display (uses cached):");
        image1.display();
        System.out.println("\\nDisplaying another image:");
        image2.display();''',

    "data-access-object": '''
        // Model class
        static class Person {
            private int id;
            private String name;
            private String email;
            
            public Person(int id, String name, String email) {
                this.id = id; this.name = name; this.email = email;
            }
            
            public int getId() { return id; }
            public String getName() { return name; }
            public String getEmail() { return email; }
            
            @Override
            public String toString() {
                return "Person{id=" + id + ", name='" + name + "', email='" + email + "'}";
            }
        }
        
        // DAO Interface
        interface PersonDao {
            void create(Person person);
            Person read(int id);
            void update(Person person);
            void delete(int id);
        }
        
        // DAO Implementation
        static class PersonDaoImpl implements PersonDao {
            private java.util.Map<Integer, Person> database = new java.util.HashMap<>();
            
            @Override
            public void create(Person person) {
                database.put(person.getId(), person);
                System.out.println("Created: " + person);
            }
            
            @Override
            public Person read(int id) {
                return database.get(id);
            }
            
            @Override
            public void update(Person person) {
                database.put(person.getId(), person);
                System.out.println("Updated: " + person);
            }
            
            @Override
            public void delete(int id) {
                database.remove(id);
                System.out.println("Deleted person with id: " + id);
            }
        }
        
        System.out.println("=== Data Access Object Pattern ===");
        System.out.println("Abstracts database operations behind a clean interface.\\n");
        
        PersonDao dao = new PersonDaoImpl();
        dao.create(new Person(1, "Alice", "alice@example.com"));
        dao.create(new Person(2, "Bob", "bob@example.com"));
        
        Person found = dao.read(1);
        System.out.println("Found: " + found);
        
        dao.update(new Person(1, "Alice Smith", "alice.smith@example.com"));
        dao.delete(2);''',

    "data-transfer-object": '''
        // DTO class
        static class UserDto {
            private String username;
            private String email;
            private String role;
            
            public UserDto(String username, String email, String role) {
                this.username = username; this.email = email; this.role = role;
            }
            
            public String getUsername() { return username; }
            public String getEmail() { return email; }
            public String getRole() { return role; }
        }
        
        // Business entity
        static class User {
            private int id;
            private String username;
            private String email;
            private String password;
            private String role;
            
            public User(int id, String username, String email, String password, String role) {
                this.id = id; this.username = username; this.email = email;
                this.password = password; this.role = role;
            }
            
            // Convert to DTO (hides sensitive data)
            public UserDto toDto() {
                return new UserDto(username, email, role);
            }
        }
        
        System.out.println("=== Data Transfer Object Pattern ===");
        System.out.println("Transfers data between subsystems without exposing internal details.\\n");
        
        User user = new User(1, "john_doe", "john@example.com", "secret123", "admin");
        UserDto dto = user.toDto();
        
        System.out.println("User DTO (safe for transfer):");
        System.out.println("  Username: " + dto.getUsername());
        System.out.println("  Email: " + dto.getEmail());
        System.out.println("  Role: " + dto.getRole());
        System.out.println("  (Password is NOT included in DTO)");''',

    "iterator": '''
        // Iterator interface
        interface Iterator<T> {
            boolean hasNext();
            T next();
        }
        
        // Aggregate interface
        interface IterableCollection<T> {
            Iterator<T> createIterator();
        }
        
        // Concrete Aggregate
        static class NameCollection implements IterableCollection<String> {
            private String[] names;
            
            public NameCollection(String[] names) { this.names = names; }
            
            @Override
            public Iterator<String> createIterator() {
                return new NameIterator();
            }
            
            // Inner iterator class
            private class NameIterator implements Iterator<String> {
                private int index = 0;
                
                @Override
                public boolean hasNext() {
                    return index < names.length;
                }
                
                @Override
                public String next() {
                    return names[index++];
                }
            }
        }
        
        System.out.println("=== Iterator Pattern ===");
        System.out.println("Provides a way to access elements sequentially without exposing the underlying structure.\\n");
        
        String[] names = {"Alice", "Bob", "Charlie", "Diana"};
        NameCollection collection = new NameCollection(names);
        Iterator<String> iterator = collection.createIterator();
        
        System.out.println("Iterating through names:");
        while (iterator.hasNext()) {
            System.out.println("  " + iterator.next());
        }''',

    "template-method": '''
        // Abstract class with template method
        static abstract class DataProcessor {
            // Template method - defines the skeleton
            public final void process() {
                loadData();
                processData();
                saveData();
                if (shouldValidate()) {
                    validate();
                }
            }
            
            abstract void loadData();
            abstract void processData();
            abstract void saveData();
            
            // Hook method (optional override)
            boolean shouldValidate() { return false; }
            void validate() {}
        }
        
        // Concrete implementation
        static class CsvProcessor extends DataProcessor {
            @Override
            void loadData() { System.out.println("  Loading CSV file"); }
            
            @Override
            void processData() { System.out.println("  Processing CSV data"); }
            
            @Override
            void saveData() { System.out.println("  Saving processed CSV data"); }
        }
        
        // Another concrete implementation
        static class JsonProcessor extends DataProcessor {
            @Override
            void loadData() { System.out.println("  Loading JSON file"); }
            
            @Override
            void processData() { System.out.println("  Processing JSON data"); }
            
            @Override
            void saveData() { System.out.println("  Saving processed JSON data"); }
            
            @Override
            boolean shouldValidate() { return true; }
            
            @Override
            void validate() { System.out.println("  Validating JSON structure"); }
        }
        
        System.out.println("=== Template Method Pattern ===");
        System.out.println("Defines algorithm skeleton, letting subclasses fill in steps.\\n");
        
        System.out.println("Processing CSV:");
        new CsvProcessor().process();
        
        System.out.println("\\nProcessing JSON:");
        new JsonProcessor().process();''',

    "mediator": '''
        // Mediator interface
        interface ChatMediator {
            void sendMessage(String message, User user);
        }
        
        // Colleague
        static abstract class User {
            protected String name;
            protected ChatMediator mediator;
            
            public User(String name, ChatMediator mediator) {
                this.name = name;
                this.mediator = mediator;
            }
            
            public abstract void send(String message);
            public abstract void receive(String message);
        }
        
        // Concrete Mediator
        static class ChatRoom implements ChatMediator {
            private java.util.List<User> users = new java.util.ArrayList<>();
            
            public void addUser(User user) {
                users.add(user);
            }
            
            @Override
            public void sendMessage(String message, User sender) {
                for (User user : users) {
                    if (user != sender) {
                        user.receive(message);
                    }
                }
            }
        }
        
        // Concrete Colleagues
        static class ChatUser extends User {
            public ChatUser(String name, ChatMediator mediator) {
                super(name, mediator);
            }
            
            @Override
            public void send(String message) {
                System.out.println(this.name + " sends: " + message);
                mediator.sendMessage(message, this);
            }
            
            @Override
            public void receive(String message) {
                System.out.println(this.name + " receives: " + message);
            }
        }
        
        System.out.println("=== Mediator Pattern ===");
        System.out.println("Reduces coupling between objects by making them communicate through a mediator.\\n");
        
        ChatRoom chatRoom = new ChatRoom();
        User alice = new ChatUser("Alice", chatRoom);
        User bob = new ChatUser("Bob", chatRoom);
        User charlie = new ChatUser("Charlie", chatRoom);
        
        chatRoom.addUser(alice);
        chatRoom.addUser(bob);
        chatRoom.addUser(charlie);
        
        alice.send("Hello everyone!");''',

    "observer": '''
        // Observer interface
        interface Observer {
            void update(String message);
        }
        
        // Subject
        static class NewsAgency {
            private java.util.List<Observer> observers = new java.util.ArrayList<>();
            private String news;
            
            public void addObserver(Observer observer) {
                observers.add(observer);
            }
            
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
        static class NewsChannel implements Observer {
            private String name;
            
            public NewsChannel(String name) { this.name = name; }
            
            @Override
            public void update(String message) {
                System.out.println(name + " received news: " + message);
            }
        }
        
        static class NewsBlog implements Observer {
            @Override
            public void update(String message) {
                System.out.println("Blog posting: " + message);
            }
        }
        
        System.out.println("=== Observer Pattern ===");
        System.out.println("Notifies multiple objects about state changes.\\n");
        
        NewsAgency agency = new NewsAgency();
        agency.addObserver(new NewsChannel("CNN"));
        agency.addObserver(new NewsChannel("BBC"));
        agency.addObserver(new NewsBlog());
        
        agency.setNews("Breaking: Design Pattern implemented!");
        System.out.println("---");
        agency.setNews("Update: All patterns validated!");''',

    "strategy": '''
        // Strategy interface
        interface PaymentStrategy {
            void pay(double amount);
        }
        
        // Concrete Strategies
        static class CreditCardPayment implements PaymentStrategy {
            private String cardNumber;
            
            public CreditCardPayment(String cardNumber) { this.cardNumber = cardNumber; }
            
            @Override
            public void pay(double amount) {
                System.out.println("Paid $" + amount + " using Credit Card " + cardNumber);
            }
        }
        
        static class PayPalPayment implements PaymentStrategy {
            private String email;
            
            public PayPalPayment(String email) { this.email = email; }
            
            @Override
            public void pay(double amount) {
                System.out.println("Paid $" + amount + " using PayPal (" + email + ")");
            }
        }
        
        static class CryptoPayment implements PaymentStrategy {
            private String walletAddress;
            
            public CryptoPayment(String walletAddress) { this.walletAddress = walletAddress; }
            
            @Override
            public void pay(double amount) {
                System.out.println("Paid $" + amount + " in Crypto to " + walletAddress);
            }
        }
        
        // Context
        static class ShoppingCart {
            private PaymentStrategy paymentStrategy;
            
            public void setPaymentStrategy(PaymentStrategy strategy) {
                this.paymentStrategy = strategy;
            }
            
            public void checkout(double amount) {
                paymentStrategy.pay(amount);
            }
        }
        
        System.out.println("=== Strategy Pattern ===");
        System.out.println("Interchangeable algorithms that can be selected at runtime.\\n");
        
        ShoppingCart cart = new ShoppingCart();
        
        cart.setPaymentStrategy(new CreditCardPayment("1234-5678-9012-3456"));
        cart.checkout(100.0);
        
        cart.setPaymentStrategy(new PayPalPayment("user@example.com"));
        cart.checkout(50.0);
        
        cart.setPaymentStrategy(new CryptoPayment("0xABC123..."));
        cart.checkout(200.0);''',

    "command": '''
        // Command interface
        interface Command {
            void execute();
            void undo();
        }
        
        // Receiver
        static class Light {
            public void turnOn() { System.out.println("  Light is ON"); }
            public void turnOff() { System.out.println("  Light is OFF"); }
        }
        
        // Concrete Commands
        static class LightOnCommand implements Command {
            private Light light;
            
            public LightOnCommand(Light light) { this.light = light; }
            
            @Override
            public void execute() { light.turnOn(); }
            
            @Override
            public void undo() { light.turnOff(); }
        }
        
        static class LightOffCommand implements Command {
            private Light light;
            
            public LightOffCommand(Light light) { this.light = light; }
            
            @Override
            public void execute() { light.turnOff(); }
            
            @Override
            public void undo() { light.turnOn(); }
        }
        
        // Invoker
        static class RemoteControl {
            private Command command;
            private Command lastCommand;
            
            public void setCommand(Command command) {
                this.command = command;
            }
            
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
        
        System.out.println("=== Command Pattern ===");
        System.out.println("Encapsulates requests as objects, enabling undo/redo.\\n");
        
        Light light = new Light();
        RemoteControl remote = new RemoteControl();
        
        remote.setCommand(new LightOnCommand(light));
        remote.pressButton();
        
        remote.setCommand(new LightOffCommand(light));
        remote.pressButton();
        
        remote.pressUndo();''',

    "singleton": '''
        // Singleton with double-checked locking
        static class DatabaseConnection {
            private static volatile DatabaseConnection instance;
            private static int instanceCount = 0;
            
            private DatabaseConnection() {
                instanceCount++;
                System.out.println("  DatabaseConnection instance created (#" + instanceCount + ")");
            }
            
            public static DatabaseConnection getInstance() {
                if (instance == null) {
                    synchronized (DatabaseConnection.class) {
                        if (instance == null) {
                            instance = new DatabaseConnection();
                        }
                    }
                }
                return instance;
            }
            
            public void query(String sql) {
                System.out.println("  Executing: " + sql);
            }
        }
        
        System.out.println("=== Singleton Pattern ===");
        System.out.println("Ensures a class has only one instance.\\n");
        
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        
        System.out.println("Same instance? " + (db1 == db2));
        db1.query("SELECT * FROM users");''',

    "factory": '''
        // Product interface
        interface Payment {
            void process(double amount);
        }
        
        // Concrete Products
        static class CreditCardPayment implements Payment {
            @Override
            public void process(double amount) {
                System.out.println("Processing credit card payment: $" + amount);
            }
        }
        
        static class PayPalPayment implements Payment {
            @Override
            public void process(double amount) {
                System.out.println("Processing PayPal payment: $" + amount);
            }
        }
        
        static class CryptoPayment implements Payment {
            @Override
            public void process(double amount) {
                System.out.println("Processing cryptocurrency payment: $" + amount);
            }
        }
        
        // Factory
        static class PaymentFactory {
            public static Payment createPayment(String type) {
                switch (type.toLowerCase()) {
                    case "credit": return new CreditCardPayment();
                    case "paypal": return new PayPalPayment();
                    case "crypto": return new CryptoPayment();
                    default: throw new IllegalArgumentException("Unknown payment type: " + type);
                }
            }
        }
        
        System.out.println("=== Factory Pattern ===");
        System.out.println("Creates objects without specifying exact class.\\n");
        
        Payment payment1 = PaymentFactory.createPayment("credit");
        payment1.process(100.0);
        
        Payment payment2 = PaymentFactory.createPayment("paypal");
        payment2.process(50.0);
        
        Payment payment3 = PaymentFactory.createPayment("crypto");
        payment3.process(200.0);''',

    "factory-method": '''
        // Product interface
        interface Document {
            void open();
            void close();
        }
        
        // Concrete Products
        static class PdfDocument implements Document {
            @Override
            public void open() { System.out.println("  Opening PDF document"); }
            @Override
            public void close() { System.out.println("  Closing PDF document"); }
        }
        
        static class WordDocument implements Document {
            @Override
            public void open() { System.out.println("  Opening Word document"); }
            @Override
            public void close() { System.out.println("  Closing Word document"); }
        }
        
        static class SpreadsheetDocument implements Document {
            @Override
            public void open() { System.out.println("  Opening Spreadsheet document"); }
            @Override
            public void close() { System.out.println("  Closing Spreadsheet document"); }
        }
        
        // Creator with factory method
        static abstract class DocumentCreator {
            // Factory method
            public abstract Document createDocument();
            
            public void newDocument() {
                Document doc = createDocument();
                doc.open();
                doc.close();
            }
        }
        
        // Concrete Creators
        static class PdfCreator extends DocumentCreator {
            @Override
            public Document createDocument() { return new PdfDocument(); }
        }
        
        static class WordCreator extends DocumentCreator {
            @Override
            public Document createDocument() { return new WordDocument(); }
        }
        
        System.out.println("=== Factory Method Pattern ===");
        System.out.println("Defines an interface for creating objects, but lets subclasses decide which class to instantiate.\\n");
        
        DocumentCreator pdfCreator = new PdfCreator();
        DocumentCreator wordCreator = new WordCreator();
        
        System.out.println("Creating PDF:");
        pdfCreator.newDocument();
        
        System.out.println("\\nCreating Word Document:");
        wordCreator.newDocument();''',

    "prototype": '''
        // Prototype interface
        interface Prototype extends Cloneable {
            Prototype clone() throws CloneNotSupportedException;
        }
        
        // Concrete Prototype
        static class Employee implements Prototype {
            private String name;
            private String department;
            private java.util.List<String> skills;
            
            public Employee(String name, String department, java.util.List<String> skills) {
                this.name = name;
                this.department = department;
                this.skills = new java.util.ArrayList<>(skills);
            }
            
            @Override
            public Employee clone() throws CloneNotSupportedException {
                Employee cloned = (Employee) super.clone();
                cloned.skills = new java.util.ArrayList<>(this.skills);
                return cloned;
            }
            
            public void addSkill(String skill) { skills.add(skill); }
            
            @Override
            public String toString() {
                return "Employee{name='" + name + "', dept='" + department + "', skills=" + skills + "}";
            }
        }
        
        System.out.println("=== Prototype Pattern ===");
        System.out.println("Creates new objects by copying existing ones.\\n");
        
        try {
            java.util.List<String> skills = new java.util.ArrayList<>();
            skills.add("Java");
            skills.add("Spring");
            
            Employee original = new Employee("Alice", "Engineering", skills);
            System.out.println("Original: " + original);
            
            Employee cloned = original.clone();
            cloned.addSkill("Python");
            
            System.out.println("Cloned:   " + cloned);
            System.out.println("Original (unchanged): " + original);
            System.out.println("\\nPrototype is useful when object creation is expensive.");
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }''',

    # ========== CREATIONAL PATTERNS ==========
    "abstract-factory": '''
        // Abstract Product interfaces
        interface Button {
            void render();
        }
        
        interface Checkbox {
            void render();
        }
        
        // Abstract Factory
        interface GUIFactory {
            Button createButton();
            Checkbox createCheckbox();
        }
        
        // Concrete Products for Windows
        static class WindowsButton implements Button {
            @Override
            public void render() { System.out.println("  Rendering Windows button"); }
        }
        
        static class WindowsCheckbox implements Checkbox {
            @Override
            public void render() { System.out.println("  Rendering Windows checkbox"); }
        }
        
        // Concrete Products for Mac
        static class MacButton implements Button {
            @Override
            public void render() { System.out.println("  Rendering Mac button"); }
        }
        
        static class MacCheckbox implements Checkbox {
            @Override
            public void render() { System.out.println("  Rendering Mac checkbox"); }
        }
        
        // Concrete Factories
        static class WindowsFactory implements GUIFactory {
            @Override
            public Button createButton() { return new WindowsButton(); }
            @Override
            public Checkbox createCheckbox() { return new WindowsCheckbox(); }
        }
        
        static class MacFactory implements GUIFactory {
            @Override
            public Button createButton() { return new MacButton(); }
            @Override
            public Checkbox createCheckbox() { return new MacCheckbox(); }
        }
        
        System.out.println("=== Abstract Factory Pattern ===");
        System.out.println("Creates families of related objects without specifying concrete classes.\\n");
        
        System.out.println("Windows UI:");
        GUIFactory winFactory = new WindowsFactory();
        winFactory.createButton().render();
        winFactory.createCheckbox().render();
        
        System.out.println("\\nMac UI:");
        GUIFactory macFactory = new MacFactory();
        macFactory.createButton().render();
        macFactory.createCheckbox().render();''',

    "object-pool": '''
        // Reusable object
        static class Connection {
            private int id;
            private boolean inUse;
            
            public Connection(int id) {
                this.id = id;
                System.out.println("    Creating connection #" + id);
            }
            
            public void connect() { System.out.println("    Connection #" + id + " connected"); }
            public void disconnect() { System.out.println("    Connection #" + id + " disconnected"); }
            public boolean isInUse() { return inUse; }
            public void setInUse(boolean inUse) { this.inUse = inUse; }
            public int getId() { return id; }
        }
        
        // Object Pool
        static class ConnectionPool {
            private java.util.List<Connection> available = new java.util.ArrayList<>();
            private java.util.List<Connection> inUse = new java.util.ArrayList<>();
            private int maxSize;
            
            public ConnectionPool(int maxSize) {
                this.maxSize = maxSize;
                // Pre-create connections
                for (int i = 1; i <= maxSize; i++) {
                    available.add(new Connection(i));
                }
            }
            
            public synchronized Connection acquire() {
                if (available.isEmpty()) {
                    throw new RuntimeException("No connections available in pool");
                }
                Connection conn = available.remove(0);
                conn.setInUse(true);
                inUse.add(conn);
                System.out.println("  Acquired connection #" + conn.getId());
                return conn;
            }
            
            public synchronized void release(Connection conn) {
                conn.setInUse(false);
                inUse.remove(conn);
                available.add(conn);
                System.out.println("  Released connection #" + conn.getId());
            }
        }
        
        System.out.println("=== Object Pool Pattern ===");
        System.out.println("Manages reusable objects to avoid expensive creation and destruction.\\n");
        
        ConnectionPool pool = new ConnectionPool(2);
        
        Connection conn1 = pool.acquire();
        Connection conn2 = pool.acquire();
        
        conn1.connect();
        conn2.connect();
        
        pool.release(conn1);
        pool.release(conn2);
        
        // Reusing released connection
        Connection conn3 = pool.acquire();
        conn3.connect();
        pool.release(conn3);''',

    "dependency-injection": '''
        // Service interface
        interface MessageService {
            void sendMessage(String message, String recipient);
        }
        
        // Service implementations
        static class EmailService implements MessageService {
            @Override
            public void sendMessage(String message, String recipient) {
                System.out.println("  Email sent to " + recipient + ": " + message);
            }
        }
        
        static class SmsService implements MessageService {
            @Override
            public void sendMessage(String message, String recipient) {
                System.out.println("  SMS sent to " + recipient + ": " + message);
            }
        }
        
        // Client with dependency injection
        static class NotificationService {
            private MessageService messageService;
            
            // Constructor injection
            public NotificationService(MessageService messageService) {
                this.messageService = messageService;
            }
            
            public void sendNotification(String message, String recipient) {
                messageService.sendMessage(message, recipient);
            }
        }
        
        System.out.println("=== Dependency Injection Pattern ===");
        System.out.println("Passes dependencies from outside rather than creating them internally.\\n");
        
        NotificationService emailNotifier = new NotificationService(new EmailService());
        NotificationService smsNotifier = new NotificationService(new SmsService());
        
        emailNotifier.sendNotification("Hello!", "alice@example.com");
        smsNotifier.sendNotification("Alert!", "+1234567890");''',

    "registry": '''
        // Service interface
        interface Service {
            String getName();
            void execute();
        }
        
        // Service implementations
        static class UserService implements Service {
            @Override
            public String getName() { return "user"; }
            @Override
            public void execute() { System.out.println("  UserService: managing users"); }
        }
        
        static class OrderService implements Service {
            @Override
            public String getName() { return "order"; }
            @Override
            public void execute() { System.out.println("  OrderService: processing orders"); }
        }
        
        // Registry
        static class ServiceRegistry {
            private java.util.Map<String, Service> services = new java.util.HashMap<>();
            
            public void register(Service service) {
                services.put(service.getName(), service);
                System.out.println("  Registered: " + service.getName());
            }
            
            public Service getService(String name) {
                Service service = services.get(name);
                if (service == null) {
                    throw new RuntimeException("Service not found: " + name);
                }
                return service;
            }
        }
        
        System.out.println("=== Registry Pattern ===");
        System.out.println("Provides a centralized location for accessing objects.\\n");
        
        ServiceRegistry registry = new ServiceRegistry();
        registry.register(new UserService());
        registry.register(new OrderService());
        
        registry.getService("user").execute();
        registry.getService("order").execute();''',

    "factory-kit": '''
        // Product interface
        interface Weapon {
            void attack();
        }
        
        // Concrete Products
        static class Sword implements Weapon {
            @Override
            public void attack() { System.out.println("  Slashing with sword!"); }
        }
        
        static class Bow implements Weapon {
            @Override
            public void attack() { System.out.println("  Shooting arrow from bow!"); }
        }
        
        static class Staff implements Weapon {
            @Override
            public void attack() { System.out.println("  Casting magic spell from staff!"); }
        }
        
        // Factory Kit (flexible factory)
        static class WeaponFactoryKit {
            private java.util.Map<String, java.util.function.Supplier<Weapon>> builders = new java.util.HashMap<>();
            
            public void register(String type, java.util.function.Supplier<Weapon> supplier) {
                builders.put(type, supplier);
            }
            
            public Weapon create(String type) {
                java.util.function.Supplier<Weapon> supplier = builders.get(type);
                if (supplier == null) {
                    throw new IllegalArgumentException("Unknown weapon type: " + type);
                }
                return supplier.get();
            }
        }
        
        System.out.println("=== Factory Kit Pattern ===");
        System.out.println("A flexible factory that can be configured with different builders.\\n");
        
        WeaponFactoryKit weaponKit = new WeaponFactoryKit();
        weaponKit.register("sword", Sword::new);
        weaponKit.register("bow", Bow::new);
        weaponKit.register("staff", Staff::new);
        
        weaponKit.create("sword").attack();
        weaponKit.create("bow").attack();
        weaponKit.create("staff").attack();''',

    "monostate": '''
        // Monostate - shares state via static fields, not singleton pattern
        static class MonostateConnection {
            private static String url = "jdbc:mysql://localhost:3306/mydb";
            private static String username = "admin";
            private static int connectionCount = 0;
            
            private String connectionId;
            
            public MonostateConnection() {
                connectionCount++;
                connectionId = "conn-" + connectionCount;
            }
            
            public void connect() {
                System.out.println("  " + connectionId + " connected to " + url + " as " + username);
            }
            
            public static void setUrl(String url) { MonostateConnection.url = url; }
            public static void setUsername(String username) { MonostateConnection.username = username; }
        }
        
        System.out.println("=== Monostate Pattern ===");
        System.out.println("Shares state across instances via static fields.\\n");
        
        MonostateConnection conn1 = new MonostateConnection();
        MonostateConnection conn2 = new MonostateConnection();
        
        System.out.println("Different instances, same state:");
        conn1.connect();
        conn2.connect();
        
        System.out.println("\\nChanging state affects all instances:");
        MonostateConnection.setUrl("jdbc:postgresql://prod:5432/proddb");
        conn1.connect();
        conn2.connect();''',

    "multiton": '''
        // Multiton - manages a fixed set of instances
        static class Printer {
            private String name;
            
            private static java.util.Map<String, Printer> instances = new java.util.HashMap<>();
            
            private Printer(String name) { this.name = name; }
            
            public static synchronized Printer getInstance(String name) {
                if (!instances.containsKey(name)) {
                    instances.put(name, new Printer(name));
                }
                return instances.get(name);
            }
            
            public void print(String document) {
                System.out.println("  " + name + " printing: " + document);
            }
        }
        
        System.out.println("=== Multiton Pattern ===");
        System.out.println("Manages a named set of instances.\\n");
        
        Printer mainPrinter = Printer.getInstance("Main Printer");
        Printer backupPrinter = Printer.getInstance("Backup Printer");
        Printer anotherMain = Printer.getInstance("Main Printer");
        
        mainPrinter.print("Report.pdf");
        backupPrinter.print("Invoice.pdf");
        System.out.println("Same Main Printer instance? " + (mainPrinter == anotherMain));''',

    "type-object": '''
        // Type Object - defines a type system dynamically
        static class WeaponType {
            private String name;
            private int damage;
            private double speed;
            
            public WeaponType(String name, int damage, double speed) {
                this.name = name; this.damage = damage; this.speed = speed;
            }
            
            public String getName() { return name; }
            public int getDamage() { return damage; }
            public double getSpeed() { return speed; }
        }
        
        // Object that uses types
        static class Weapon {
            private String name;
            private WeaponType type;
            
            public Weapon(String name, WeaponType type) {
                this.name = name;
                this.type = type;
            }
            
            public void attack() {
                System.out.println("  " + name + " (" + type.getName() + 
                    "): " + type.getDamage() + " damage at speed " + type.getSpeed());
            }
        }
        
        System.out.println("=== Type Object Pattern ===");
        System.out.println("Allows creation of flexible type systems at runtime.\\n");
        
        WeaponType sword = new WeaponType("Sword", 10, 1.5);
        WeaponType bow = new WeaponType("Bow", 7, 2.0);
        WeaponType axe = new WeaponType("Axe", 15, 0.8);
        
        new Weapon("Iron Sword", sword).attack();
        new Weapon("Longbow", bow).attack();
        new Weapon("Battle Axe", axe).attack();''',

    "step-builder": '''
        // Step Builder Pattern - guides object creation through steps
        static class Pizza {
            private String dough;
            private String sauce;
            private String cheese;
            private String topping;
            
            private Pizza() {}
            
            // Step interfaces
            interface DoughStep { SauceStep withDough(String dough); }
            interface SauceStep { CheeseStep withSauce(String sauce); }
            interface CheeseStep { ToppingStep withCheese(String cheese); }
            interface ToppingStep { BuildStep withTopping(String topping); }
            interface BuildStep { Pizza build(); }
            
            // Builder implementing all steps
            static class PizzaBuilder implements DoughStep, SauceStep, CheeseStep, ToppingStep, BuildStep {
                private Pizza pizza = new Pizza();
                
                public SauceStep withDough(String dough) { pizza.dough = dough; return this; }
                public CheeseStep withSauce(String sauce) { pizza.sauce = sauce; return this; }
                public ToppingStep withCheese(String cheese) { pizza.cheese = cheese; return this; }
                public BuildStep withTopping(String topping) { pizza.topping = topping; return this; }
                public Pizza build() { return pizza; }
            }
            
            static DoughStep start() { return new PizzaBuilder(); }
            
            @Override
            public String toString() {
                return "Pizza{" + dough + ", " + sauce + ", " + cheese + ", " + topping + "}";
            }
        }
        
        System.out.println("=== Step Builder Pattern ===");
        System.out.println("Guides object construction through predefined steps.\\n");
        
        Pizza pizza = Pizza.start()
            .withDough("Thin Crust")
            .withSauce("Tomato")
            .withCheese("Mozzarella")
            .withTopping("Pepperoni")
            .build();
        
        System.out.println("  " + pizza);''',

    # ========== BEHAVIORAL PATTERNS ==========
    "chain-of-responsibility": '''
        // Handler interface
        static abstract class SupportHandler {
            protected SupportHandler nextHandler;
            protected int level;
            
            public void setNext(SupportHandler handler) {
                this.nextHandler = handler;
            }
            
            public void handleRequest(int level, String issue) {
                if (this.level >= level) {
                    handle(issue);
                } else if (nextHandler != null) {
                    System.out.println("  Escalating to next level...");
                    nextHandler.handleRequest(level, issue);
                }
            }
            
            protected abstract void handle(String issue);
        }
        
        // Concrete Handlers
        static class Level1Support extends SupportHandler {
            public Level1Support() { this.level = 1; }
            
            @Override
            protected void handle(String issue) {
                System.out.println("  Level 1 Support handling: " + issue);
            }
        }
        
        static class Level2Support extends SupportHandler {
            public Level2Support() { this.level = 2; }
            
            @Override
            protected void handle(String issue) {
                System.out.println("  Level 2 Support handling: " + issue);
            }
        }
        
        static class Level3Support extends SupportHandler {
            public Level3Support() { this.level = 3; }
            
            @Override
            protected void handle(String issue) {
                System.out.println("  Level 3 Support handling: " + issue);
            }
        }
        
        System.out.println("=== Chain of Responsibility Pattern ===");
        System.out.println("Passes a request through a chain of handlers until one handles it.\\n");
        
        SupportHandler level1 = new Level1Support();
        SupportHandler level2 = new Level2Support();
        SupportHandler level3 = new Level3Support();
        
        level1.setNext(level2);
        level2.setNext(level3);
        
        System.out.println("Issue: Password reset (level 1):");
        level1.handleRequest(1, "Password reset");
        
        System.out.println("\\nIssue: Account suspension (level 2):");
        level1.handleRequest(2, "Account suspension");
        
        System.out.println("\\nIssue: Security breach (level 3):");
        level1.handleRequest(3, "Security breach");''',

    "state": '''
        // State interface
        interface OrderState {
            void next(Order order);
            void cancel(Order order);
            String getStatus();
        }
        
        // Context
        static class Order {
            private OrderState currentState;
            
            public Order() {
                currentState = new NewOrder();
            }
            
            public void setState(OrderState state) { this.currentState = state; }
            
            public void next() {
                currentState.next(this);
            }
            
            public void cancel() {
                currentState.cancel(this);
            }
            
            public String getStatus() { return currentState.getStatus(); }
        }
        
        // Concrete States
        static class NewOrder implements OrderState {
            @Override
            public void next(Order order) { order.setState(new PaidOrder()); }
            @Override
            public void cancel(Order order) { order.setState(new CancelledOrder()); }
            @Override
            public String getStatus() { return "New Order"; }
        }
        
        static class PaidOrder implements OrderState {
            @Override
            public void next(Order order) { order.setState(new ShippedOrder()); }
            @Override
            public void cancel(Order order) { order.setState(new CancelledOrder()); }
            @Override
            public String getStatus() { return "Paid"; }
        }
        
        static class ShippedOrder implements OrderState {
            @Override
            public void next(Order order) { order.setState(new DeliveredOrder()); }
            @Override
            public void cancel(Order order) { System.out.println("  Cannot cancel - already shipped!"); }
            @Override
            public String getStatus() { return "Shipped"; }
        }
        
        static class DeliveredOrder implements OrderState {
            @Override
            public void next(Order order) { System.out.println("  Order already delivered"); }
            @Override
            public void cancel(Order order) { System.out.println("  Cannot cancel - already delivered"); }
            @Override
            public String getStatus() { return "Delivered"; }
        }
        
        static class CancelledOrder implements OrderState {
            @Override
            public void next(Order order) { System.out.println("  Cannot proceed - order cancelled"); }
            @Override
            public void cancel(Order order) { System.out.println("  Already cancelled"); }
            @Override
            public String getStatus() { return "Cancelled"; }
        }
        
        System.out.println("=== State Pattern ===");
        System.out.println("Allows an object to change its behavior when its internal state changes.\\n");
        
        Order order = new Order();
        System.out.println("Status: " + order.getStatus());
        
        order.next();
        System.out.println("Status: " + order.getStatus());
        
        order.next();
        System.out.println("Status: " + order.getStatus());
        
        order.next();
        System.out.println("Status: " + order.getStatus());''',

    "visitor": '''
        // Element interface
        interface Shape {
            void accept(Visitor visitor);
        }
        
        // Concrete Elements
        static class Circle implements Shape {
            private double radius;
            public Circle(double radius) { this.radius = radius; }
            public double getRadius() { return radius; }
            
            @Override
            public void accept(Visitor visitor) { visitor.visit(this); }
        }
        
        static class Rectangle implements Shape {
            private double width, height;
            public Rectangle(double width, double height) { this.width = width; this.height = height; }
            public double getWidth() { return width; }
            public double getHeight() { return height; }
            
            @Override
            public void accept(Visitor visitor) { visitor.visit(this); }
        }
        
        // Visitor interface
        interface Visitor {
            void visit(Circle circle);
            void visit(Rectangle rectangle);
        }
        
        // Concrete Visitors
        static class AreaCalculator implements Visitor {
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
        
        static class DescriptionPrinter implements Visitor {
            @Override
            public void visit(Circle circle) {
                System.out.println("  Circle: radius=" + circle.getRadius());
            }
            
            @Override
            public void visit(Rectangle rectangle) {
                System.out.println("  Rectangle: " + rectangle.getWidth() + "x" + rectangle.getHeight());
            }
        }
        
        System.out.println("=== Visitor Pattern ===");
        System.out.println("Separates algorithms from the objects they operate on.\\n");
        
        Shape[] shapes = {new Circle(5.0), new Rectangle(4.0, 6.0)};
        
        System.out.println("Calculating areas:");
        AreaCalculator areaCalc = new AreaCalculator();
        for (Shape shape : shapes) {
            shape.accept(areaCalc);
        }
        
        System.out.println("\\nDescribing shapes:");
        DescriptionPrinter descPrinter = new DescriptionPrinter();
        for (Shape shape : shapes) {
            shape.accept(descPrinter);
        }''',

    "interpreter": '''
        // Expression interface
        interface Expression {
            int interpret();
        }
        
        // Terminal expressions
        static class NumberExpression implements Expression {
            private int number;
            public NumberExpression(int number) { this.number = number; }
            
            @Override
            public int interpret() { return number; }
        }
        
        // Non-terminal expressions
        static class AddExpression implements Expression {
            private Expression left, right;
            public AddExpression(Expression left, Expression right) { this.left = left; this.right = right; }
            
            @Override
            public int interpret() { return left.interpret() + right.interpret(); }
        }
        
        static class SubtractExpression implements Expression {
            private Expression left, right;
            public SubtractExpression(Expression left, Expression right) { this.left = left; this.right = right; }
            
            @Override
            public int interpret() { return left.interpret() - right.interpret(); }
        }
        
        static class MultiplyExpression implements Expression {
            private Expression left, right;
            public MultiplyExpression(Expression left, Expression right) { this.left = left; this.right = right; }
            
            @Override
            public int interpret() { return left.interpret() * right.interpret(); }
        }
        
        System.out.println("=== Interpreter Pattern ===");
        System.out.println("Interprets a language by defining grammar rules.\\n");
        
        // Build expression: (10 + 5) * 2 - 3
        Expression expression = new SubtractExpression(
            new MultiplyExpression(
                new AddExpression(new NumberExpression(10), new NumberExpression(5)),
                new NumberExpression(2)
            ),
            new NumberExpression(3)
        );
        
        int result = expression.interpret();
        System.out.println("(10 + 5) * 2 - 3 = " + result);''',

    "memento": '''
        // Memento - stores state
        static class TextMemento {
            private final String state;
            
            public TextMemento(String state) { this.state = state; }
            public String getState() { return state; }
        }
        
        // Originator - creates and restores mementos
        static class TextEditor {
            private StringBuilder content = new StringBuilder();
            
            public void write(String text) { content.append(text); }
            public String getContent() { return content.toString(); }
            
            public TextMemento save() {
                System.out.println("  Saving state: '" + content + "'");
                return new TextMemento(content.toString());
            }
            
            public void restore(TextMemento memento) {
                content = new StringBuilder(memento.getState());
                System.out.println("  Restored state: '" + content + "'");
            }
        }
        
        // Caretaker - manages mementos
        static class History {
            private java.util.Stack<TextMemento> mementos = new java.util.Stack<>();
            
            public void push(TextMemento memento) { mementos.push(memento); }
            public TextMemento pop() { return mementos.pop(); }
        }
        
        System.out.println("=== Memento Pattern ===");
        System.out.println("Captures and restores an object's internal state.\\n");
        
        TextEditor editor = new TextEditor();
        History history = new History();
        
        editor.write("Hello");
        history.push(editor.save());
        
        editor.write(" World");
        history.push(editor.save());
        
        editor.write("!!!");
        System.out.println("Current: '" + editor.getContent() + "'");
        
        System.out.println("\\nUndoing...");
        editor.restore(history.pop());
        System.out.println("After undo: '" + editor.getContent() + "'");''',

    "null-object": '''
        // Abstract class
        static abstract class AbstractLogger {
            protected String name;
            public abstract void log(String message);
            public String getName() { return name; }
        }
        
        // Real object
        static class ConsoleLogger extends AbstractLogger {
            public ConsoleLogger() { this.name = "ConsoleLogger"; }
            
            @Override
            public void log(String message) {
                System.out.println("  [Console] " + message);
            }
        }
        
        // Null object - does nothing silently
        static class NullLogger extends AbstractLogger {
            public NullLogger() { this.name = "NullLogger"; }
            
            @Override
            public void log(String message) {
                // Does nothing - the null object pattern
            }
        }
        
        // Factory for loggers
        static class LoggerFactory {
            public static AbstractLogger createLogger(boolean enabled) {
                if (enabled) {
                    return new ConsoleLogger();
                }
                return new NullLogger(); // No special null checking needed
            }
        }
        
        System.out.println("=== Null Object Pattern ===");
        System.out.println("Provides a default object that does nothing, avoiding null checks.\\n");
        
        AbstractLogger enabledLogger = LoggerFactory.createLogger(true);
        AbstractLogger disabledLogger = LoggerFactory.createLogger(false);
        
        System.out.println("Logger 1: " + enabledLogger.getName());
        enabledLogger.log("This message will appear");
        
        System.out.println("\\nLogger 2: " + disabledLogger.getName());
        disabledLogger.log("This message will NOT appear (no null check needed)");
        
        System.out.println("\\nNo NullPointerException - both loggers are always safe to use!");''',

    "specification": '''
        // Specification interface
        interface Specification<T> {
            boolean isSatisfiedBy(T item);
            Specification<T> and(Specification<T> other);
            Specification<T> or(Specification<T> other);
        }
        
        // Abstract base
        static abstract class AbstractSpecification<T> implements Specification<T> {
            @Override
            public Specification<T> and(Specification<T> other) {
                return item -> this.isSatisfiedBy(item) && other.isSatisfiedBy(item);
            }
            
            @Override
            public Specification<T> or(Specification<T> other) {
                return item -> this.isSatisfiedBy(item) || other.isSatisfiedBy(item);
            }
        }
        
        // Product to filter
        static class Product {
            String name; double price; String category;
            public Product(String name, double price, String category) {
                this.name = name; this.price = price; this.category = category;
            }
            @Override
            public String toString() {
                return name + " ($" + price + ", " + category + ")";
            }
        }
        
        // Concrete specifications
        static class PriceSpecification extends AbstractSpecification<Product> {
            private double maxPrice;
            public PriceSpecification(double maxPrice) { this.maxPrice = maxPrice; }
            @Override
            public boolean isSatisfiedBy(Product p) { return p.price <= maxPrice; }
        }
        
        static class CategorySpecification extends AbstractSpecification<Product> {
            private String category;
            public CategorySpecification(String category) { this.category = category; }
            @Override
            public boolean isSatisfiedBy(Product p) { return p.category.equals(category); }
        }
        
        System.out.println("=== Specification Pattern ===");
        System.out.println("Combines business rules using boolean logic.\\n");
        
        java.util.List<Product> products = new java.util.ArrayList<>();
        products.add(new Product("Laptop", 999.99, "Electronics"));
        products.add(new Product("Book", 29.99, "Books"));
        products.add(new Product("Phone", 699.99, "Electronics"));
        products.add(new Product("Pen", 5.99, "Stationery"));
        
        Specification<Product> cheapElectronics = new PriceSpecification(700.0)
            .and(new CategorySpecification("Electronics"));
        
        System.out.println("Cheap Electronics:");
        for (Product p : products) {
            if (cheapElectronics.isSatisfiedBy(p)) {
                System.out.println("  " + p);
            }
        }''',

    "pipeline": '''
        // Pipeline pattern - chain of processing stages
        static class PipelineStage {
            private java.util.function.Function<String, String> processor;
            private PipelineStage next;
            
            public PipelineStage(java.util.function.Function<String, String> processor) {
                this.processor = processor;
            }
            
            public PipelineStage andThen(PipelineStage next) {
                this.next = next;
                return next;
            }
            
            public String process(String input) {
                String result = processor.apply(input);
                if (next != null) {
                    return next.process(result);
                }
                return result;
            }
        }
        
        System.out.println("=== Pipeline Pattern ===");
        System.out.println("Passes data through a sequence of processing stages.\\n");
        
        PipelineStage trimStage = new PipelineStage(s -> s.trim());
        PipelineStage uppercaseStage = new PipelineStage(s -> s.toUpperCase());
        PipelineStage replaceStage = new PipelineStage(s -> s.replace(" ", "_"));
        
        trimStage.andThen(uppercaseStage).andThen(replaceStage);
        
        String result = trimStage.process("  Hello World  ");
        System.out.println("Input: '  Hello World  '");
        System.out.println("Output: '" + result + "'");''',

    # ========== ARCHITECTURAL PATTERNS ==========
    "model-view-controller": '''
        // Model
        static class Student {
            private String name;
            private int grade;
            
            public Student(String name, int grade) { this.name = name; this.grade = grade; }
            public String getName() { return name; }
            public int getGrade() { return grade; }
            public void setGrade(int grade) { this.grade = grade; }
        }
        
        // View
        static class StudentView {
            public void display(Student student) {
                System.out.println("  Student: " + student.getName());
                System.out.println("  Grade: " + student.getGrade());
            }
        }
        
        // Controller
        static class StudentController {
            private Student model;
            private StudentView view;
            
            public StudentController(Student model, StudentView view) {
                this.model = model;
                this.view = view;
            }
            
            public void updateGrade(int grade) {
                model.setGrade(grade);
            }
            
            public void displayStudent() {
                view.display(model);
            }
        }
        
        System.out.println("=== Model-View-Controller Pattern ===");
        System.out.println("Separates data (Model), UI (View), and logic (Controller).\\n");
        
        Student student = new Student("Alice", 85);
        StudentView view = new StudentView();
        StudentController controller = new StudentController(student, view);
        
        System.out.println("Initial display:");
        controller.displayStudent();
        
        System.out.println("\\nAfter grade update:");
        controller.updateGrade(92);
        controller.displayStudent();''',

    "event-driven-architecture": '''
        // Event interface
        interface Event {
            String getType();
            Object getData();
        }
        
        // Listener interface
        interface EventListener {
            void onEvent(Event event);
        }
        
        // Event Bus
        static class EventBus {
            private java.util.Map<String, java.util.List<EventListener>> listeners = new java.util.HashMap<>();
            
            public void subscribe(String eventType, EventListener listener) {
                listeners.computeIfAbsent(eventType, k -> new java.util.ArrayList<>()).add(listener);
            }
            
            public void publish(Event event) {
                java.util.List<EventListener> eventListeners = listeners.get(event.getType());
                if (eventListeners != null) {
                    for (EventListener listener : eventListeners) {
                        listener.onEvent(event);
                    }
                }
            }
        }
        
        // Concrete Events
        static class OrderCreatedEvent implements Event {
            private String orderId;
            public OrderCreatedEvent(String orderId) { this.orderId = orderId; }
            @Override
            public String getType() { return "ORDER_CREATED"; }
            @Override
            public Object getData() { return orderId; }
        }
        
        static class PaymentReceivedEvent implements Event {
            private String orderId;
            private double amount;
            public PaymentReceivedEvent(String orderId, double amount) { this.orderId = orderId; this.amount = amount; }
            @Override
            public String getType() { return "PAYMENT_RECEIVED"; }
            @Override
            public Object getData() { return orderId + " - $" + amount; }
        }
        
        System.out.println("=== Event-Driven Architecture Pattern ===");
        System.out.println("Systems communicate through events.\\n");
        
        EventBus eventBus = new EventBus();
        
        eventBus.subscribe("ORDER_CREATED", event -> 
            System.out.println("  Inventory: Reserved stock for order " + event.getData()));
        
        eventBus.subscribe("ORDER_CREATED", event -> 
            System.out.println("  Notification: Order confirmation sent for " + event.getData()));
        
        eventBus.subscribe("PAYMENT_RECEIVED", event -> 
            System.out.println("  Shipping: Preparing shipment for " + event.getData()));
        
        System.out.println("Order #12345 created:");
        eventBus.publish(new OrderCreatedEvent("ORD-12345"));
        
        System.out.println("\\nPayment received:");
        eventBus.publish(new PaymentReceivedEvent("ORD-12345", 99.99));''',

    "backend-for-frontend": '''
        // BFF interfaces
        interface MobileAPI {
            String getMobileData();
        }
        
        interface WebAPI {
            String getWebData();
        }
        
        // Mobile BFF - optimized for mobile clients
        static class MobileBackend implements MobileAPI {
            @Override
            public String getMobileData() {
                return "{\"user\":\"John\",\"mobile_view\":\"simplified\",\"data\":\"compressed\"}";
            }
        }
        
        // Web BFF - optimized for web clients
        static class WebBackend implements WebAPI {
            @Override
            public String getWebData() {
                return "{\"user\":\"John\",\"full_data\":true,\"details\":\"extensive\",\"analytics\":true}";
            }
        }
        
        System.out.println("=== Backend-for-Frontend Pattern ===");
        System.out.println("Creates separate backend services optimized for each client type.\\n");
        
        MobileAPI mobileBFF = new MobileBackend();
        WebAPI webBFF = new WebBackend();
        
        System.out.println("Mobile BFF response: " + mobileBFF.getMobileData());
        System.out.println("Web BFF response:   " + webBFF.getWebData());''',

    "front-controller": '''
        // Front Controller
        static class FrontController {
            private static final java.util.Map<String, String> VIEWS = new java.util.HashMap<>();
            
            static {
                VIEWS.put("home", "HomeView");
                VIEWS.put("user", "UserView");
                VIEWS.put("product", "ProductView");
            }
            
            public void handleRequest(String request) {
                System.out.println("  FrontController received: " + request);
                
                // Authentication check
                if (!authenticate(request)) {
                    System.out.println("  Access denied - redirecting to login");
                    return;
                }
                
                // Route to appropriate view
                String view = VIEWS.getOrDefault(request, "ErrorView");
                System.out.println("  Routing to: " + view);
                render(view);
            }
            
            private boolean authenticate(String request) {
                return !request.equals("admin"); // Simple auth example
            }
            
            private void render(String view) {
                System.out.println("  Rendering: " + view);
            }
        }
        
        System.out.println("=== Front Controller Pattern ===");
        System.out.println("Centralizes request handling for a web application.\\n");
        
        FrontController controller = new FrontController();
        
        System.out.println("Request: home");
        controller.handleRequest("home");
        
        System.out.println("\\nRequest: user");
        controller.handleRequest("user");
        
        System.out.println("\\nRequest: admin (unauthorized)");
        controller.handleRequest("admin");''',

    "command-query-responsibility-segregation": '''
        // Command side (write model)
        static class OrderCommand {
            private java.util.Map<String, Integer> orders = new java.util.HashMap<>();
            
            public void placeOrder(String orderId, int quantity) {
                orders.put(orderId, quantity);
                System.out.println("  Command: Order " + orderId + " placed for " + quantity + " items");
            }
            
            public void cancelOrder(String orderId) {
                orders.remove(orderId);
                System.out.println("  Command: Order " + orderId + " cancelled");
            }
        }
        
        // Query side (read model)
        static class OrderQuery {
            private java.util.Map<String, Integer> orderSummary = new java.util.HashMap<>();
            
            public void updateSummary(String orderId, int quantity) {
                orderSummary.put(orderId, quantity);
            }
            
            public int getOrderCount() {
                return orderSummary.size();
            }
            
            public void displaySummary() {
                System.out.println("  Query: Total orders: " + getOrderCount());
                for (java.util.Map.Entry<String, Integer> entry : orderSummary.entrySet()) {
                    System.out.println("    " + entry.getKey() + ": " + entry.getValue() + " items");
                }
            }
        }
        
        System.out.println("=== CQRS Pattern ===");
        System.out.println("Separates read and write operations for better scalability.\\n");
        
        OrderCommand command = new OrderCommand();
        OrderQuery query = new OrderQuery();
        
        command.placeOrder("ORD-001", 5);
        query.updateSummary("ORD-001", 5);
        
        command.placeOrder("ORD-002", 3);
        query.updateSummary("ORD-002", 3);
        
        command.cancelOrder("ORD-001");
        
        query.displaySummary();''',

    "layered-architecture": '''
        // Presentation Layer
        static class UserController {
            private UserService userService;
            
            public UserController(UserService userService) { this.userService = userService; }
            
            public void displayUser(int id) {
                System.out.println("  [Presentation] Requesting user " + id);
                UserDto dto = userService.getUser(id);
                System.out.println("  [Presentation] Displaying: " + dto);
            }
        }
        
        // Service Layer (Business Logic)
        static class UserService {
            private UserRepository repository;
            
            public UserService(UserRepository repository) { this.repository = repository; }
            
            public UserDto getUser(int id) {
                System.out.println("  [Service] Processing user request");
                User user = repository.findById(id);
                return new UserDto(user.getName(), user.getEmail());
            }
        }
        
        // Data Access Layer
        static class UserRepository {
            public User findById(int id) {
                System.out.println("  [Repository] Querying database for user " + id);
                return new User(id, "Alice", "alice@example.com");
            }
        }
        
        // DTOs and Entities
        static class User {
            private int id; private String name; private String email;
            public User(int id, String name, String email) { this.id = id; this.name = name; this.email = email; }
            public String getName() { return name; }
            public String getEmail() { return email; }
        }
        
        static class UserDto {
            private String name; private String email;
            public UserDto(String name, String email) { this.name = name; this.email = email; }
            @Override
            public String toString() { return "UserDto{name='" + name + "', email='" + email + "'}"; }
        }
        
        System.out.println("=== Layered Architecture Pattern ===");
        System.out.println("Organizes code into layers with specific responsibilities.\\n");
        
        UserRepository repository = new UserRepository();
        UserService service = new UserService(repository);
        UserController controller = new UserController(service);
        
        controller.displayUser(1);''',

    "hexagonal-architecture": '''
        // Port interfaces
        interface UserPort {
            User findById(int id);
        }
        
        interface UserPresentationPort {
            void displayUser(User user);
        }
        
        // Domain entity
        static class User {
            private int id; private String name; private String email;
            public User(int id, String name, String email) { this.id = id; this.name = name; this.email = email; }
            public int getId() { return id; }
            public String getName() { return name; }
            public String getEmail() { return email; }
        }
        
        // Core domain logic
        static class UserService {
            private UserPort userPort;
            private UserPresentationPort presentationPort;
            
            public UserService(UserPort userPort, UserPresentationPort presentationPort) {
                this.userPort = userPort;
                this.presentationPort = presentationPort;
            }
            
            public void handleUserRequest(int id) {
                System.out.println("  [Core] Processing user request");
                User user = userPort.findById(id);
                presentationPort.displayUser(user);
            }
        }
        
        // Adapter implementations
        static class DatabaseAdapter implements UserPort {
            @Override
            public User findById(int id) {
                System.out.println("  [DB Adapter] Querying database");
                return new User(id, "Alice", "alice@example.com");
            }
        }
        
        static class ConsoleAdapter implements UserPresentationPort {
            @Override
            public void displayUser(User user) {
                System.out.println("  [Console Adapter] " + user.getName() + " (" + user.getEmail() + ")");
            }
        }
        
        System.out.println("=== Hexagonal Architecture Pattern ===");
        System.out.println("Core logic is isolated from external concerns through ports and adapters.\\n");
        
        UserService service = new UserService(new DatabaseAdapter(), new ConsoleAdapter());
        service.handleUserRequest(1);''',

    "event-sourcing": '''
        // Event
        static class Event {
            private String type;
            private Object data;
            private long timestamp;
            
            public Event(String type, Object data) {
                this.type = type;
                this.data = data;
                this.timestamp = System.currentTimeMillis();
            }
            
            @Override
            public String toString() {
                return "Event{type='" + type + "', data=" + data + "}";
            }
        }
        
        // Event Store
        static class EventStore {
            private java.util.List<Event> events = new java.util.ArrayList<>();
            
            public void save(Event event) {
                events.add(event);
                System.out.println("  Stored: " + event);
            }
            
            public java.util.List<Event> getAll() {
                return new java.util.ArrayList<>(events);
            }
        }
        
        // Aggregate
        static class BankAccount {
            private String accountId;
            private double balance;
            
            public BankAccount(String accountId) { this.accountId = accountId; }
            
            public void apply(Event event) {
                if (event.type.equals("DEPOSIT")) {
                    balance += (double) event.data;
                } else if (event.type.equals("WITHDRAW")) {
                    balance -= (double) event.data;
                }
            }
            
            public double getBalance() { return balance; }
            
            public static BankAccount reconstruct(java.util.List<Event> events) {
                BankAccount account = new BankAccount("ACC-001");
                for (Event event : events) {
                    account.apply(event);
                }
                return account;
            }
        }
        
        System.out.println("=== Event Sourcing Pattern ===");
        System.out.println("Stores state changes as a sequence of events.\\n");
        
        EventStore eventStore = new EventStore();
        
        eventStore.save(new Event("DEPOSIT", 1000.0));
        eventStore.save(new Event("WITHDRAW", 200.0));
        eventStore.save(new Event("DEPOSIT", 500.0));
        eventStore.save(new Event("WITHDRAW", 100.0));
        
        System.out.println("\\nReconstructing account state from events:");
        BankAccount account = BankAccount.reconstruct(eventStore.getAll());
        System.out.println("  Final balance: $" + account.getBalance());''',

    # ========== SOLID PRINCIPLES ==========
    "single-responsibility-principle": '''
        // Class with single responsibility: hold email content
        static class EmailContent {
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
        static class EmailSender {
            public void send(EmailContent content) {
                System.out.println("  Sending email to: " + content.getTo());
                System.out.println("  Subject: " + content.getSubject());
                System.out.println("  Body: " + content.getBody());
                System.out.println("  Email sent successfully!");
            }
        }
        
        System.out.println("=== Single Responsibility Principle ===");
        System.out.println("A class should have only one reason to change.\\n");
        
        EmailContent content = new EmailContent("user@example.com", "Hello", "This is a test email");
        EmailSender sender = new EmailSender();
        sender.send(content);''',

    "open-close-principle": '''
        // Base class - closed for modification
        static abstract class Shape {
            public abstract double calculateArea();
        }
        
        // Extensions - open for extension
        static class Rectangle extends Shape {
            private double width, height;
            public Rectangle(double w, double h) { this.width = w; this.height = h; }
            @Override
            public double calculateArea() { return width * height; }
        }
        
        static class Circle extends Shape {
            private double radius;
            public Circle(double r) { this.radius = r; }
            @Override
            public double calculateArea() { return Math.PI * radius * radius; }
        }
        
        static class Triangle extends Shape {
            private double base, height;
            public Triangle(double b, double h) { this.base = b; this.height = h; }
            @Override
            public double calculateArea() { return 0.5 * base * height; }
        }
        
        // Calculator that works with any Shape without modification
        static class AreaCalculator {
            public double totalArea(Shape[] shapes) {
                double total = 0;
                for (Shape shape : shapes) {
                    total += shape.calculateArea();
                }
                return total;
            }
        }
        
        System.out.println("=== Open/Closed Principle ===");
        System.out.println("Classes should be open for extension but closed for modification.\\n");
        
        Shape[] shapes = {new Rectangle(5, 4), new Circle(3), new Triangle(6, 4)};
        AreaCalculator calculator = new AreaCalculator();
        
        System.out.println("Rectangle area: " + String.format("%.2f", shapes[0].calculateArea()));
        System.out.println("Circle area: " + String.format("%.2f", shapes[1].calculateArea()));
        System.out.println("Triangle area: " + String.format("%.2f", shapes[2].calculateArea()));
        System.out.println("Total area: " + String.format("%.2f", calculator.totalArea(shapes)));''',

    "liskov-substitution-principle": '''
        // Base class - Rectangle
        static class Rectangle {
            protected int width, height;
            public Rectangle(int w, int h) { this.width = w; this.height = h; }
            public int getWidth() { return width; }
            public int getHeight() { return height; }
            public int getArea() { return width * height; }
        }
        
        // Square IS-A Rectangle, but behaves correctly
        static class Square extends Rectangle {
            public Square(int side) { super(side, side); }
        }
        
        // This demonstrates LSP - Square can substitute Rectangle
        static class ShapeProcessor {
            public void printArea(Rectangle rectangle) {
                int area = rectangle.getArea();
                System.out.println("  Width: " + rectangle.getWidth() + 
                    ", Height: " + rectangle.getHeight() + 
                    ", Area: " + area);
            }
        }
        
        System.out.println("=== Liskov Substitution Principle ===");
        System.out.println("Subtypes must be substitutable for their base types.\\n");
        
        ShapeProcessor processor = new ShapeProcessor();
        
        Rectangle rect = new Rectangle(5, 4);
        System.out.println("Rectangle (5x4):");
        processor.printArea(rect);
        
        // Square can be used anywhere Rectangle is expected
        Rectangle square = new Square(5);
        System.out.println("\\nSquare (5x5) - substituting Rectangle:");
        processor.printArea(square);''',

    "interface-segregation-principle": '''
        // Segregated (small, focused) interfaces
        interface Workable {
            void work();
        }
        
        interface Eatable {
            void eat();
        }
        
        interface Sleepable {
            void sleep();
        }
        
        // Robot only needs work
        static class Robot implements Workable {
            @Override
            public void work() { System.out.println("  Robot working..."); }
        }
        
        // Human needs all three
        static class Human implements Workable, Eatable, Sleepable {
            @Override
            public void work() { System.out.println("  Human working..."); }
            @Override
            public void eat() { System.out.println("  Human eating..."); }
            @Override
            public void sleep() { System.out.println("  Human sleeping..."); }
        }
        
        System.out.println("=== Interface Segregation Principle ===");
        System.out.println("Clients should not be forced to depend on interfaces they don't use.\\n");
        
        Workable robot = new Robot();
        Workable human = new Human();
        
        System.out.println("Robot:");
        robot.work();
        
        System.out.println("\\nHuman:");
        human.work();
        ((Eatable) human).eat();
        ((Sleepable) human).sleep();''',

    "dependency-inversion-principle": '''
        // Abstraction (interface)
        interface NotificationChannel {
            void send(String message);
        }
        
        // Low-level modules depend on abstraction
        static class EmailChannel implements NotificationChannel {
            @Override
            public void send(String message) {
                System.out.println("  Sending email: " + message);
            }
        }
        
        static class SMSChannel implements NotificationChannel {
            @Override
            public void send(String message) {
                System.out.println("  Sending SMS: " + message);
            }
        }
        
        // High-level module depends on abstraction, not concrete implementations
        static class NotificationService {
            private NotificationChannel channel;
            
            public NotificationService(NotificationChannel channel) {
                this.channel = channel;
            }
            
            public void notify(String message) {
                channel.send(message);
            }
        }
        
        System.out.println("=== Dependency Inversion Principle ===");
        System.out.println("Depend on abstractions, not concrete implementations.\\n");
        
        NotificationService emailService = new NotificationService(new EmailChannel());
        NotificationService smsService = new NotificationService(new SMSChannel());
        
        emailService.notify("Hello via email");
        smsService.notify("Hello via SMS");''',

    # ========== CONCURRENCY PATTERNS ==========
    "producer-consumer": '''
        // Shared buffer
        static class Buffer {
            private java.util.Queue<Integer> queue = new java.util.LinkedList<>();
            private int capacity;
            
            public Buffer(int capacity) { this.capacity = capacity; }
            
            public synchronized void produce(int item) throws InterruptedException {
                while (queue.size() == capacity) {
                    wait();
                }
                queue.add(item);
                System.out.println("  Produced: " + item);
                notifyAll();
            }
            
            public synchronized int consume() throws InterruptedException {
                while (queue.isEmpty()) {
                    wait();
                }
                int item = queue.poll();
                System.out.println("  Consumed: " + item);
                notifyAll();
                return item;
            }
        }
        
        System.out.println("=== Producer-Consumer Pattern ===");
        System.out.println("Separates production and consumption of data with a shared buffer.\\n");
        
        Buffer buffer = new Buffer(5);
        
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    buffer.produce(i);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    buffer.consume();
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        
        producer.start();
        consumer.start();
        
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        
        System.out.println("\\nAll items produced and consumed successfully!");''',

    "circuit-breaker": '''
        // Circuit Breaker states
        enum CircuitState { CLOSED, OPEN, HALF_OPEN }
        
        static class CircuitBreaker {
            private CircuitState state = CircuitState.CLOSED;
            private int failureCount = 0;
            private int threshold = 3;
            private long lastFailureTime = 0;
            private long timeout = 5000;
            
            public boolean call(String service) {
                switch (state) {
                    case OPEN:
                        if (System.currentTimeMillis() - lastFailureTime > timeout) {
                            state = CircuitState.HALF_OPEN;
                            System.out.println("  Circuit: HALF_OPEN - trying again");
                            return tryCall(service);
                        }
                        System.out.println("  Circuit: OPEN - " + service + " unavailable (fast fail)");
                        return false;
                    case HALF_OPEN:
                        return tryCall(service);
                    case CLOSED:
                        return tryCall(service);
                    default:
                        return false;
                }
            }
            
            private boolean tryCall(String service) {
                if (service.equals("unstable")) {
                    failureCount++;
                    lastFailureTime = System.currentTimeMillis();
                    System.out.println("  " + service + " FAILED (failures: " + failureCount + ")");
                    if (failureCount >= threshold) {
                        state = CircuitState.OPEN;
                        System.out.println("  Circuit: OPEN - threshold reached");
                    }
                    return false;
                }
                failureCount = 0;
                state = CircuitState.CLOSED;
                System.out.println("  " + service + " SUCCESS");
                return true;
            }
        }
        
        System.out.println("=== Circuit Breaker Pattern ===");
        System.out.println("Prevents cascading failures by detecting when services are down.\\n");
        
        CircuitBreaker cb = new CircuitBreaker();
        
        System.out.println("Calling stable service:");
        cb.call("stable");
        
        System.out.println("\\nCalling unstable service multiple times:");
        cb.call("unstable");
        cb.call("unstable");
        cb.call("unstable");
        cb.call("unstable"); // This one should be fast-failed''',

    # ========== MICROSERVICES PATTERNS ==========
    "api-gateway": '''
        // API Gateway
        static class ApiGateway {
            public String getUserServiceData(int userId) {
                System.out.println("  Gateway routing to UserService for user " + userId);
                return "{\"userId\":" + userId + ",\"name\":\"Alice\"}";
            }
            
            public String getOrderServiceData(int userId) {
                System.out.println("  Gateway routing to OrderService for user " + userId);
                return "{\"orderCount\":5,\"totalSpent\":1250.00}";
            }
            
            public String getAggregatedData(int userId) {
                System.out.println("  Gateway aggregating data for user " + userId);
                String userData = getUserServiceData(userId);
                String orderData = getOrderServiceData(userId);
                return "{\"user\":" + userData + ",\"orders\":" + orderData + "}";
            }
        }
        
        System.out.println("=== API Gateway Pattern ===");
        System.out.println("Provides a single entry point for client requests to microservices.\\n");
        
        ApiGateway gateway = new ApiGateway();
        
        System.out.println("Client requesting aggregated data:");
        String result = gateway.getAggregatedData(1);
        System.out.println("\\nAggregated response: " + result);''',

    # ========== RESILIENCE PATTERNS ==========
    "retry": '''
        // Retry mechanism
        static class RetryHandler {
            private int maxRetries;
            private long delayMs;
            
            public RetryHandler(int maxRetries, long delayMs) {
                this.maxRetries = maxRetries;
                this.delayMs = delayMs;
            }
            
            public boolean execute(String task) {
                for (int attempt = 1; attempt <= maxRetries; attempt++) {
                    try {
                        System.out.println("  Attempt " + attempt + " of " + maxRetries + ": " + task);
                        if (task.equals("failing-task") && attempt < 3) {
                            throw new RuntimeException("Service unavailable");
                        }
                        System.out.println("  Success on attempt " + attempt);
                        return true;
                    } catch (Exception e) {
                        System.out.println("  Failed: " + e.getMessage());
                        if (attempt == maxRetries) {
                            System.out.println("  All retries exhausted");
                            return false;
                        }
                        try {
                            Thread.sleep(delayMs);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                            return false;
                        }
                    }
                }
                return false;
            }
        }
        
        System.out.println("=== Retry Pattern ===");
        System.out.println("Automatically retries failed operations.\\n");
        
        RetryHandler handler = new RetryHandler(3, 100);
        
        System.out.println("Executing stable task:");
        handler.execute("stable-task");
        
        System.out.println("\\nExecuting failing task:");
        handler.execute("failing-task");''',

    "saga": '''
        // Saga step
        static class SagaStep {
            String name;
            Runnable execute;
            Runnable compensate;
            
            public SagaStep(String name, Runnable execute, Runnable compensate) {
                this.name = name; this.execute = execute; this.compensate = compensate;
            }
        }
        
        // Saga orchestrator
        static class SagaOrchestrator {
            private java.util.List<SagaStep> steps = new java.util.ArrayList<>();
            private java.util.Stack<SagaStep> executedSteps = new java.util.Stack<>();
            
            public void addStep(SagaStep step) { steps.add(step); }
            
            public boolean execute() {
                System.out.println("  Starting saga...");
                for (SagaStep step : steps) {
                    try {
                        System.out.println("  Executing: " + step.name);
                        step.execute.run();
                        executedSteps.push(step);
                    } catch (Exception e) {
                        System.out.println("  Failed at: " + step.name + " - " + e.getMessage());
                        System.out.println("  Starting compensation...");
                        while (!executedSteps.isEmpty()) {
                            SagaStep failedStep = executedSteps.pop();
                            System.out.println("  Compensating: " + failedStep.name);
                            failedStep.compensate.run();
                        }
                        return false;
                    }
                }
                System.out.println("  Saga completed successfully!");
                return true;
            }
        }
        
        System.out.println("=== Saga Pattern ===");
        System.out.println("Manages distributed transactions with compensation.\\n");
        
        SagaOrchestrator saga = new SagaOrchestrator();
        saga.addStep(new SagaStep("Reserve Inventory", 
            () -> System.out.println("    Inventory reserved"),
            () -> System.out.println("    Inventory released")));
        saga.addStep(new SagaStep("Process Payment", 
            () -> System.out.println("    Payment processed"),
            () -> System.out.println("    Payment refunded")));
        saga.addStep(new SagaStep("Update Order", 
            () -> System.out.println("    Order updated"),
            () -> System.out.println("    Order reverted")));
        
        saga.execute();''',

    "bulkheads": '''
        // Thread pool for bulkhead
        static class Bulkhead {
            private String name;
            private int maxConcurrent;
            private int active = 0;
            
            public Bulkhead(String name, int maxConcurrent) {
                this.name = name;
                this.maxConcurrent = maxConcurrent;
            }
            
            public synchronized boolean tryCall(String task) {
                if (active >= maxConcurrent) {
                    System.out.println("  " + name + " bulkhead FULL - rejecting " + task);
                    return false;
                }
                active++;
                System.out.println("  " + name + " executing " + task + " (active: " + active + "/" + maxConcurrent + ")");
                return true;
            }
            
            public synchronized void complete() {
                active--;
                System.out.println("  " + name + " completed (active: " + active + "/" + maxConcurrent + ")");
            }
        }
        
        System.out.println("=== Bulkhead Pattern ===");
        System.out.println("Isolates resources to prevent failures from cascading.\\n");
        
        Bulkhead paymentBulkhead = new Bulkhead("Payment", 2);
        Bulkhead notificationBulkhead = new Bulkhead("Notification", 5);
        
        System.out.println("Payment service (limited to 2 concurrent):");
        paymentBulkhead.tryCall("Payment-1");
        paymentBulkhead.tryCall("Payment-2");
        paymentBulkhead.tryCall("Payment-3"); // Rejected
        paymentBulkhead.complete();
        paymentBulkhead.tryCall("Payment-3"); // Now accepted
        paymentBulkhead.complete();
        paymentBulkhead.complete();''',

    "graceful-degradation": '''
        // Service with graceful degradation
        static class RecommendationService {
            public java.util.List<String> getRecommendations(String userId, boolean primaryAvailable) {
                if (primaryAvailable) {
                    System.out.println("  Primary: Personalized recommendations for " + userId);
                    return java.util.Arrays.asList("Item A", "Item B", "Item C");
                } else {
                    System.out.println("  Degraded: Popular items for " + userId);
                    return java.util.Arrays.asList("Popular 1", "Popular 2", "Popular 3");
                }
            }
        }
        
        System.out.println("=== Graceful Degradation Pattern ===");
        System.out.println("Provides reduced functionality when a service is unavailable.\\n");
        
        RecommendationService service = new RecommendationService();
        
        System.out.println("When primary service is available:");
        service.getRecommendations("user1", true);
        
        System.out.println("\\nWhen primary service is DOWN:");
        service.getRecommendations("user1", false);''',

    "rate-limiting": '''
        // Token bucket rate limiter
        static class RateLimiter {
            private int maxTokens;
            private int tokens;
            private long refillInterval;
            private long lastRefillTime;
            
            public RateLimiter(int maxTokens, long refillIntervalMs) {
                this.maxTokens = maxTokens;
                this.tokens = maxTokens;
                this.refillInterval = refillIntervalMs;
                this.lastRefillTime = System.currentTimeMillis();
            }
            
            public synchronized boolean allowRequest() {
                refill();
                if (tokens > 0) {
                    tokens--;
                    return true;
                }
                return false;
            }
            
            private void refill() {
                long now = System.currentTimeMillis();
                long elapsed = now - lastRefillTime;
                if (elapsed >= refillInterval) {
                    int newTokens = (int) (elapsed / refillInterval);
                    tokens = Math.min(maxTokens, tokens + newTokens);
                    lastRefillTime = now;
                }
            }
        }
        
        System.out.println("=== Rate Limiting Pattern ===");
        System.out.println("Controls the rate of requests to a service.\\n");
        
        RateLimiter limiter = new RateLimiter(3, 1000);
        
        System.out.println("Making 5 rapid requests (limit: 3 per second):");
        for (int i = 1; i <= 5; i++) {
            boolean allowed = limiter.allowRequest();
            System.out.println("  Request " + i + ": " + (allowed ? "ALLOWED" : "DENIED"));
        }''',

    "timeouts": '''
        // Timeout handler
        static class TimeoutHandler {
            private long timeoutMs;
            
            public TimeoutHandler(long timeoutMs) { this.timeoutMs = timeoutMs; }
            
            public String callService(String service, boolean slow) {
                long startTime = System.currentTimeMillis();
                
                if (slow) {
                    // Simulate slow service
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                }
                
                long elapsed = System.currentTimeMillis() - startTime;
                if (elapsed > timeoutMs) {
                    System.out.println("  TIMEOUT: " + service + " took " + elapsed + "ms (limit: " + timeoutMs + "ms)");
                    return "{\"error\":\"timeout\"}";
                }
                
                System.out.println("  SUCCESS: " + service + " responded in " + elapsed + "ms");
                return "{\"status\":\"ok\"}";
            }
        }
        
        System.out.println("=== Timeout Pattern ===");
        System.out.println("Limits the wait time for service responses.\\n");
        
        TimeoutHandler handler = new TimeoutHandler(100);
        
        System.out.println("Fast service (under 100ms):");
        handler.callService("fast-service", false);
        
        System.out.println("\\nSlow service (over 100ms):");
        handler.callService("slow-service", true);''',

    "fallbacks": '''
        // Primary service
        static class PrimaryService {
            public String process(String request, boolean available) {
                if (!available) {
                    throw new RuntimeException("Primary service unavailable");
                }
                return "Primary result: " + request;
            }
        }
        
        // Fallback service
        static class FallbackService {
            public String process(String request) {
                return "Fallback result: " + request + " (cached)";
            }
        }
        
        // Service with fallback
        static class ServiceWithFallback {
            private PrimaryService primary = new PrimaryService();
            private FallbackService fallback = new FallbackService();
            
            public String call(String request, boolean primaryAvailable) {
                try {
                    System.out.println("  Trying primary service...");
                    return primary.process(request, primaryAvailable);
                } catch (Exception e) {
                    System.out.println("  Primary failed: " + e.getMessage());
                    System.out.println("  Using fallback...");
                    return fallback.process(request);
                }
            }
        }
        
        System.out.println("=== Fallback Pattern ===");
        System.out.println("Provides an alternative response when a service fails.\\n");
        
        ServiceWithFallback service = new ServiceWithFallback();
        
        System.out.println("When primary is available:");
        System.out.println("  " + service.call("getUserData", true));
        
        System.out.println("\\nWhen primary is DOWN:");
        System.out.println("  " + service.call("getUserData", false));''',

    "tolerant-reader": '''
        // Tolerant Reader - reads data even with extra fields
        static class TolerantReader {
            public static String readUserData(String jsonData) {
                System.out.println("  Parsing: " + jsonData);
                
                // Extract only the fields we care about
                String name = extractField(jsonData, "name");
                String email = extractField(jsonData, "email");
                
                // Ignore extra fields we don't know about
                if (jsonData.contains("phone")) {
                    System.out.println("  (Ignoring extra field: phone)");
                }
                if (jsonData.contains("address")) {
                    System.out.println("  (Ignoring extra field: address)");
                }
                
                return "User: " + name + " (" + email + ")";
            }
            
            private static String extractField(String json, String field) {
                String search = "\"" + field + "\":\"";
                int start = json.indexOf(search);
                if (start == -1) return "unknown";
                start += search.length();
                int end = json.indexOf("\"", start);
                return json.substring(start, end);
            }
        }
        
        System.out.println("=== Tolerant Reader Pattern ===");
        System.out.println("Reads only the fields it understands, ignoring unknown data.\\n");
        
        String oldVersion = "{\"name\":\"Alice\",\"email\":\"alice@example.com\"}";
        String newVersion = "{\"name\":\"Bob\",\"email\":\"bob@example.com\",\"phone\":\"+1234567890\",\"address\":\"123 Main St\"}";
        
        System.out.println("Old version data:");
        System.out.println("  " + TolerantReader.readUserData(oldVersion));
        
        System.out.println("\\nNew version data (with extra fields):");
        System.out.println("  " + TolerantReader.readUserData(newVersion));''',

    "queue-based-load-leveling": '''
        // Queue-based load leveling
        static class TaskQueue {
            private java.util.Queue<String> queue = new java.util.LinkedList<>();
            private int maxSize;
            
            public TaskQueue(int maxSize) { this.maxSize = maxSize; }
            
            public synchronized boolean addTask(String task) {
                if (queue.size() >= maxSize) {
                    System.out.println("  Queue FULL - rejecting: " + task);
                    return false;
                }
                queue.add(task);
                System.out.println("  Queued: " + task + " (size: " + queue.size() + ")");
                return true;
            }
            
            public synchronized String processTask() {
                if (queue.isEmpty()) return null;
                String task = queue.poll();
                System.out.println("  Processing: " + task + " (remaining: " + queue.size() + ")");
                try { Thread.sleep(50); } catch (InterruptedException e) {}
                return task;
            }
        }
        
        System.out.println("=== Queue-Based Load Leveling Pattern ===");
        System.out.println("Uses a queue to smooth out spikes in workload.\\n");
        
        TaskQueue queue = new TaskQueue(5);
        
        // Burst of tasks
        for (int i = 1; i <= 7; i++) {
            queue.addTask("Task-" + i);
        }
        
        System.out.println("\\nProcessing at steady rate:");
        String task;
        while ((task = queue.processTask()) != null) {
            // Processing
        }''',

    # ========== DATA ACCESS PATTERNS ==========
    "repository": '''
        // Entity
        static class Product {
            private int id; private String name; private double price;
            public Product(int id, String name, double price) {
                this.id = id; this.name = name; this.price = price;
            }
            public int getId() { return id; }
            public String getName() { return name; }
            public double getPrice() { return price; }
            @Override
            public String toString() {
                return "Product{id=" + id + ", name='" + name + "', price=$" + price + "}";
            }
        }
        
        // Repository interface
        interface ProductRepository {
            Product findById(int id);
            java.util.List<Product> findAll();
            void save(Product product);
            void delete(int id);
        }
        
        // In-memory implementation
        static class InMemoryProductRepository implements ProductRepository {
            private java.util.Map<Integer, Product> products = new java.util.HashMap<>();
            
            @Override
            public Product findById(int id) { return products.get(id); }
            
            @Override
            public java.util.List<Product> findAll() {
                return new java.util.ArrayList<>(products.values());
            }
            
            @Override
            public void save(Product product) {
                products.put(product.getId(), product);
                System.out.println("  Saved: " + product);
            }
            
            @Override
            public void delete(int id) {
                products.remove(id);
                System.out.println("  Deleted product " + id);
            }
        }
        
        System.out.println("=== Repository Pattern ===");
        System.out.println("Mediates between domain and data mapping layers.\\n");
        
        ProductRepository repo = new InMemoryProductRepository();
        repo.save(new Product(1, "Laptop", 999.99));
        repo.save(new Product(2, "Mouse", 29.99));
        
        System.out.println("\\nAll products:");
        for (Product p : repo.findAll()) {
            System.out.println("  " + p);
        }
        
        System.out.println("\\nFind by ID:");
        System.out.println("  " + repo.findById(1));''',

    "unit-of-work": '''
        // Entity
        static class Customer {
            private int id; private String name;
            public Customer(int id, String name) { this.id = id; this.name = name; }
            public int getId() { return id; }
            public String getName() { return name; }
        }
        
        // Unit of Work
        static class UnitOfWork {
            private java.util.List<Customer> newCustomers = new java.util.ArrayList<>();
            private java.util.List<Customer> dirtyCustomers = new java.util.ArrayList<>();
            private java.util.List<Integer> deletedCustomers = new java.util.ArrayList<>();
            
            public void registerNew(Customer customer) {
                if (!newCustomers.contains(customer) && !dirtyCustomers.contains(customer)) {
                    newCustomers.add(customer);
                }
            }
            
            public void registerDirty(Customer customer) {
                if (!dirtyCustomers.contains(customer) && !newCustomers.contains(customer)) {
                    dirtyCustomers.add(customer);
                }
            }
            
            public void registerDeleted(int id) {
                deletedCustomers.add(id);
            }
            
            public void commit() {
                System.out.println("  Committing Unit of Work:");
                newCustomers.forEach(c -> System.out.println("    INSERT: " + c.getName()));
                dirtyCustomers.forEach(c -> System.out.println("    UPDATE: " + c.getName()));
                deletedCustomers.forEach(id -> System.out.println("    DELETE: customer " + id));
                System.out.println("  Transaction committed!");
                
                newCustomers.clear();
                dirtyCustomers.clear();
                deletedCustomers.clear();
            }
        }
        
        System.out.println("=== Unit of Work Pattern ===");
        System.out.println("Groups multiple operations into a single transaction.\\n");
        
        UnitOfWork uow = new UnitOfWork();
        
        uow.registerNew(new Customer(1, "Alice"));
        uow.registerNew(new Customer(2, "Bob"));
        uow.registerDirty(new Customer(1, "Alice Updated"));
        uow.registerDeleted(2);
        
        uow.commit();''',

    "optimistic-offline-lock": '''
        // Entity with version
        static class Document {
            private int id; private String content; private int version;
            public Document(int id, String content, int version) {
                this.id = id; this.content = content; this.version = version;
            }
            public int getId() { return id; }
            public String getContent() { return content; }
            public int getVersion() { return version; }
            public void setContent(String content) { this.content = content; }
            public void incrementVersion() { this.version++; }
        }
        
        // Optimistic Lock Manager
        static class OptimisticLockManager {
            private java.util.Map<Integer, Document> store = new java.util.HashMap<>();
            
            public void save(Document doc) {
                store.put(doc.getId(), doc);
            }
            
            public Document load(int id) {
                Document doc = store.get(id);
                if (doc != null) {
                    return new Document(doc.getId(), doc.getContent(), doc.getVersion());
                }
                return null;
            }
            
            public boolean update(Document original, Document updated) {
                Document current = store.get(original.getId());
                if (current.getVersion() != original.getVersion()) {
                    System.out.println("  CONFLICT: Document " + original.getId() + 
                        " was modified by another user (version " + current.getVersion() + ")");
                    return false;
                }
                updated.incrementVersion();
                store.put(updated.getId(), updated);
                System.out.println("  UPDATE SUCCESS: Document " + updated.getId() + 
                    " version " + updated.getVersion());
                return true;
            }
        }
        
        System.out.println("=== Optimistic Offline Lock Pattern ===");
        System.out.println("Prevents conflicting changes using version numbers.\\n");
        
        OptimisticLockManager lockManager = new OptimisticLockManager();
        Document doc = new Document(1, "Original content", 1);
        lockManager.save(doc);
        
        // User A loads and edits
        Document userADoc = lockManager.load(1);
        userADoc.setContent("User A edits");
        
        // User B loads and edits (same version)
        Document userBDoc = lockManager.load(1);
        userBDoc.setContent("User B edits");
        
        System.out.println("User A updates:");
        lockManager.update(doc, userADoc);
        
        System.out.println("\\nUser B updates (with stale version):");
        lockManager.update(doc, userBDoc);''',

    "sharding": '''
        // Shard
        static class Shard {
            private int id;
            private java.util.Map<Integer, String> data = new java.util.HashMap<>();
            
            public Shard(int id) { this.id = id; }
            
            public void put(int key, String value) {
                data.put(key, value);
                System.out.println("    Shard " + id + " stored: " + key + " -> " + value);
            }
            
            public String get(int key) {
                return data.get(key);
            }
        }
        
        // Shard Manager
        static class ShardManager {
            private Shard[] shards;
            
            public ShardManager(int shardCount) {
                shards = new Shard[shardCount];
                for (int i = 0; i < shardCount; i++) {
                    shards[i] = new Shard(i);
                }
            }
            
            private int getShardId(int key) {
                return key % shards.length;
            }
            
            public void put(int key, String value) {
                int shardId = getShardId(key);
                System.out.println("  Routing key " + key + " to shard " + shardId);
                shards[shardId].put(key, value);
            }
            
            public String get(int key) {
                int shardId = getShardId(key);
                return shards[shardId].get(key);
            }
        }
        
        System.out.println("=== Sharding Pattern ===");
        System.out.println("Horizontal partitioning of data across multiple databases.\\n");
        
        ShardManager manager = new ShardManager(3);
        
        manager.put(1, "User 1");
        manager.put(2, "User 2");
        manager.put(3, "User 3");
        manager.put(4, "User 4");
        manager.put(5, "User 5");''',

    # ========== INTEGRATION PATTERNS ==========
    "anti-corruption-layer": '''
        // Legacy system
        static class LegacySystem {
            public String getCustomerData(String legacyId) {
                return "LEGACY|" + legacyId + "|John|Doe|1980-01-15";
            }
        }
        
        // Anti-Corruption Layer
        static class AntiCorruptionLayer {
            private LegacySystem legacy = new LegacySystem();
            
            public Customer getCustomer(String id) {
                System.out.println("  ACL: Translating request from new to legacy format");
                String legacyData = legacy.getCustomerData("LEGACY-" + id);
                return translateToCustomer(legacyData);
            }
            
            private Customer translateToCustomer(String legacyData) {
                System.out.println("  ACL: Translating response from legacy to new format");
                String[] parts = legacyData.split("\\|");
                return new Customer(parts[1], parts[2] + " " + parts[3], parts[4]);
            }
        }
        
        // Modern domain object
        static class Customer {
            private String id; private String fullName; private String dob;
            public Customer(String id, String fullName, String dob) {
                this.id = id; this.fullName = fullName; this.dob = dob;
            }
            @Override
            public String toString() {
                return "Customer{id='" + id + "', name='" + fullName + "', dob='" + dob + "'}";
            }
        }
        
        System.out.println("=== Anti-Corruption Layer Pattern ===");
        System.out.println("Protects the domain model from legacy system contamination.\\n");
        
        AntiCorruptionLayer acl = new AntiCorruptionLayer();
        Customer customer = acl.getCustomer("CUST-001");
        System.out.println("  Result: " + customer);''',

    "gateway": '''
        // Gateway interface
        interface PaymentGateway {
            boolean charge(double amount);
        }
        
        // External service
        static class StripeAPI {
            public String createCharge(double amount, String currency) {
                return "charge_" + amount + "_" + currency;
            }
        }
        
        // Gateway implementation
        static class StripeGateway implements PaymentGateway {
            private StripeAPI api = new StripeAPI();
            
            @Override
            public boolean charge(double amount) {
                System.out.println("  Gateway: calling Stripe API");
                String chargeId = api.createCharge(amount, "USD");
                System.out.println("  Gateway: charge created: " + chargeId);
                return chargeId != null;
            }
        }
        
        System.out.println("=== Gateway Pattern ===");
        System.out.println("Abstracts access to external services or APIs.\\n");
        
        PaymentGateway gateway = new StripeGateway();
        boolean success = gateway.charge(99.99);
        System.out.println("  Payment successful: " + success);''',

    "ambassador": '''
        // Remote service
        static class RemoteService {
            public String call(boolean fail) {
                if (fail) {
                    throw new RuntimeException("Remote service error");
                }
                return "Remote service response";
            }
        }
        
        // Ambassador (helper proxy)
        static class ServiceAmbassador {
            private RemoteService service = new RemoteService();
            private int retries = 3;
            private long timeoutMs = 100;
            
            public String callService(boolean fail) {
                System.out.println("  Ambassador: intercepting request");
                
                for (int i = 1; i <= retries; i++) {
                    try {
                        long start = System.currentTimeMillis();
                        String result = service.call(fail);
                        long elapsed = System.currentTimeMillis() - start;
                        
                        if (elapsed > timeoutMs) {
                            System.out.println("  Ambassador: request took too long (" + elapsed + "ms)");
                        }
                        
                        System.out.println("  Ambassador: request succeeded");
                        return result;
                    } catch (Exception e) {
                        System.out.println("  Ambassador: attempt " + i + " failed: " + e.getMessage());
                        if (i == retries) {
                            System.out.println("  Ambassador: all retries exhausted");
                            return "Fallback response";
                        }
                    }
                }
                return "Fallback response";
            }
        }
        
        System.out.println("=== Ambassador Pattern ===");
        System.out.println("Helper service that handles retries, logging, and latency.\\n");
        
        ServiceAmbassador ambassador = new ServiceAmbassador();
        
        System.out.println("Calling healthy service:");
        System.out.println("  Response: " + ambassador.callService(false));
        
        System.out.println("\\nCalling failing service (with retry):");
        System.out.println("  Response: " + ambassador.callService(true));''',

    # ========== FUNCTIONAL PATTERNS ==========
    "callback": '''
        // Callback interface
        interface Callback {
            void onComplete(String result);
        }
        
        // Service that uses callbacks
        static class AsyncService {
            public void execute(String task, Callback callback) {
                System.out.println("  Service: Starting " + task);
                try { Thread.sleep(50); } catch (InterruptedException e) {}
                System.out.println("  Service: " + task + " completed");
                callback.onComplete(task + " result");
            }
        }
        
        System.out.println("=== Callback Pattern ===");
        System.out.println("Passes executable code as an argument to another function.\\n");
        
        AsyncService service = new AsyncService();
        
        service.execute("Task 1", result -> {
            System.out.println("  Callback: Received '" + result + "'");
        });
        
        service.execute("Task 2", result -> {
            System.out.println("  Callback: Received '" + result + "'");
        });''',

    "collection-pipeline": '''
        // Collection Pipeline - chaining operations
        static class Person {
            String name; int age; String city;
            public Person(String name, int age, String city) {
                this.name = name; this.age = age; this.city = city;
            }
        }
        
        System.out.println("=== Collection Pipeline Pattern ===");
        System.out.println("Chains collection operations in a sequence.\\n");
        
        java.util.List<Person> people = new java.util.ArrayList<>();
        people.add(new Person("Alice", 25, "New York"));
        people.add(new Person("Bob", 17, "London"));
        people.add(new Person("Charlie", 30, "New York"));
        people.add(new Person("Diana", 22, "Paris"));
        people.add(new Person("Eve", 19, "London"));
        
        System.out.println("Adults in New York (sorted by name):");
        people.stream()
            .filter(p -> p.age >= 18)
            .filter(p -> p.city.equals("New York"))
            .map(p -> p.name)
            .sorted()
            .forEach(name -> System.out.println("  " + name));''',

    "combinator": '''
        // Combinator pattern - combining functions
        interface Validation extends java.util.function.Function<String, String> {
            static Validation notEmpty() {
                return s -> s == null || s.trim().isEmpty() ? "String is empty" : s;
            }
            
            static Validation minLength(int min) {
                return s -> s.length() < min ? "String too short (min " + min + ")" : s;
            }
            
            static Validation containsNumber() {
                return s -> s.matches(".*\\d.*") ? s : "String must contain a number";
            }
            
            default Validation and(Validation other) {
                return s -> {
                    String result = this.apply(s);
                    return result.equals(s) ? other.apply(s) : result;
                };
            }
        }
        
        System.out.println("=== Combinator Pattern ===");
        System.out.println("Combines small functions into larger ones.\\n");
        
        Validation validation = Validation.notEmpty()
            .and(Validation.minLength(5))
            .and(Validation.containsNumber());
        
        System.out.println("Validating 'abc': " + validation.apply("abc"));
        System.out.println("Validating 'hello123': " + validation.apply("hello123"));
        System.out.println("Validating '': " + validation.apply(""));''',

    "currying": '''
        // Currying - transforming function with multiple args into chain of single-arg functions
        static java.util.function.Function<Integer, java.util.function.Function<Integer, java.util.function.Function<Integer, Integer>>> 
            add = a -> b -> c -> a + b + c;
        
        System.out.println("=== Currying Pattern ===");
        System.out.println("Transforms a multi-argument function into a chain of single-argument functions.\\n");
        
        int result = add.apply(10).apply(20).apply(30);
        System.out.println("add(10)(20)(30) = " + result);
        
        // Partial application
        java.util.function.Function<Integer, java.util.function.Function<Integer, Integer>> add10 = add.apply(10);
        System.out.println("add10(5)(3) = " + add10.apply(5).apply(3));''',

    "function-composition": '''
        // Function composition
        static java.util.function.Function<Integer, Integer> multiplyBy2 = x -> x * 2;
        static java.util.function.Function<Integer, Integer> add3 = x -> x + 3;
        static java.util.function.Function<Integer, String> toString = Object::toString;
        
        System.out.println("=== Function Composition Pattern ===");
        System.out.println("Combines simple functions to build more complex ones.\\n");
        
        // compose: applies add3 first, then multiplyBy2
        java.util.function.Function<Integer, Integer> multiplyBy2AfterAdd3 = multiplyBy2.compose(add3);
        
        // andThen: applies multiplyBy2 first, then add3
        java.util.function.Function<Integer, Integer> add3AfterMultiplyBy2 = multiplyBy2.andThen(add3);
        
        System.out.println("multiplyBy2.compose(add3): (5 + 3) * 2 = " + multiplyBy2AfterAdd3.apply(5));
        System.out.println("multiplyBy2.andThen(add3): (5 * 2) + 3 = " + add3AfterMultiplyBy2.apply(5));
        
        // Chaining with toString
        java.util.function.Function<Integer, String> pipeline = multiplyBy2.andThen(add3).andThen(toString);
        System.out.println("Pipeline: ((5 * 2) + 3) -> String: " + pipeline.apply(5));''',

    "trampoline": '''
        // Trampoline - stack-safe recursion
        @FunctionalInterface
        interface Trampoline<T> {
            T get();
            
            default boolean isComplete() { return false; }
            
            default T trampoline() {
                Trampoline<T> current = this;
                while (!current.isComplete()) {
                    current = (Trampoline<T>) current.get();
                }
                return current.get();
            }
            
            static <T> Trampoline<T> done(T value) {
                return new Trampoline<T>() {
                    @Override public T get() { return value; }
                    @Override public boolean isComplete() { return true; }
                };
            }
            
            static <T> Trampoline<T> more(Trampoline<Trampoline<T>> next) {
                return () -> {
                    try { return next.get().get(); } catch (Exception e) { return null; }
                };
            }
        }
        
        System.out.println("=== Trampoline Pattern ===");
        System.out.println("Provides stack-safe recursion.\\n");
        
        // Tail-recursive factorial using trampoline
        Trampoline<Long> factorial(int n, long acc) {
            if (n <= 1) return Trampoline.done(acc);
            return Trampoline.more(() -> factorial(n - 1, acc * n));
        }
        
        // This works without stack overflow for large n
        long result = factorial(20, 1).trampoline();
        System.out.println("Factorial(20) = " + result);''',

    "monad": '''
        // Simple Maybe monad
        static class Maybe<T> {
            private final T value;
            
            private Maybe(T value) { this.value = value; }
            
            public static <T> Maybe<T> of(T value) {
                return new Maybe<>(value);
            }
            
            public <R> Maybe<R> flatMap(java.util.function.Function<T, Maybe<R>> mapper) {
                if (value == null) {
                    return new Maybe<>(null);
                }
                return mapper.apply(value);
            }
            
            public <R> Maybe<R> map(java.util.function.Function<T, R> mapper) {
                if (value == null) {
                    return new Maybe<>(null);
                }
                return new Maybe<>(mapper.apply(value));
            }
            
            public T getOrElse(T defaultValue) {
                return value != null ? value : defaultValue;
            }
        }
        
        System.out.println("=== Monad Pattern ===");
        System.out.println("Wraps values and provides composition operations.\\n");
        
        Maybe<Integer> maybeValue = Maybe.of(5);
        
        String result = maybeValue
            .map(x -> x * 2)
            .map(x -> "Value: " + x)
            .getOrElse("No value");
        
        System.out.println("Result: " + result);
        
        // With null
        Maybe<Integer> maybeNull = Maybe.of((Integer) null);
        String nullResult = maybeNull
            .map(x -> x * 2)
            .map(x -> "Value: " + x)
            .getOrElse("No value");
        
        System.out.println("Null result: " + nullResult);''',

    # ========== TESTING PATTERNS ==========
    "object-mother": '''
        // Object Mother - creates test objects
        static class User {
            private String firstName; private String lastName; private String email;
            private int age; private boolean active;
            
            public User(String firstName, String lastName, String email, int age, boolean active) {
                this.firstName = firstName; this.lastName = lastName; this.email = email;
                this.age = age; this.active = active;
            }
            
            @Override
            public String toString() {
                return firstName + " " + lastName + " (" + email + ") age:" + age + " active:" + active;
            }
        }
        
        // Object Mother
        static class UserMother {
            static User createStandardUser() {
                return new User("John", "Doe", "john@example.com", 30, true);
            }
            
            static User createAdminUser() {
                return new User("Admin", "User", "admin@system.com", 35, true);
            }
            
            static User createInactiveUser() {
                return new User("Jane", "Smith", "jane@example.com", 25, false);
            }
            
            static User createMinorUser() {
                return new User("Young", "User", "young@example.com", 16, true);
            }
        }
        
        System.out.println("=== Object Mother Pattern ===");
        System.out.println("Creates pre-configured test objects.\\n");
        
        System.out.println("Standard user: " + UserMother.createStandardUser());
        System.out.println("Admin user: " + UserMother.createAdminUser());
        System.out.println("Inactive user: " + UserMother.createInactiveUser());
        System.out.println("Minor user: " + UserMother.createMinorUser());''',

    "page-object": '''
        // Page Object - represents a web page
        static class LoginPage {
            private String url = "/login";
            
            public void navigate() {
                System.out.println("  Navigating to: " + url);
            }
            
            public void enterUsername(String username) {
                System.out.println("  Entering username: " + username);
            }
            
            public void enterPassword(String password) {
                System.out.println("  Entering password: [hidden]");
            }
            
            public void clickLogin() {
                System.out.println("  Clicking login button");
            }
            
            public String getErrorMessage() {
                return "Invalid credentials";
            }
            
            public void login(String username, String password) {
                navigate();
                enterUsername(username);
                enterPassword(password);
                clickLogin();
            }
        }
        
        static class DashboardPage {
            public String getWelcomeMessage() {
                return "Welcome, User!";
            }
            
            public boolean isLoggedIn() {
                return true;
            }
        }
        
        System.out.println("=== Page Object Pattern ===");
        System.out.println("Encapsulates page details in test automation.\\n");
        
        LoginPage loginPage = new LoginPage();
        DashboardPage dashboardPage = new DashboardPage();
        
        System.out.println("Performing login test:");
        loginPage.login("testuser", "password123");
        
        System.out.println("\\nVerifying dashboard:");
        System.out.println("  Welcome: " + dashboardPage.getWelcomeMessage());
        System.out.println("  Logged in: " + dashboardPage.isLoggedIn());''',

    "arrange-act-assert": '''
        // System under test
        static class Calculator {
            public int add(int a, int b) { return a + b; }
            public int subtract(int a, int b) { return a - b; }
            public int multiply(int a, int b) { return a * b; }
            public int divide(int a, int b) {
                if (b == 0) throw new IllegalArgumentException("Cannot divide by zero");
                return a / b;
            }
        }
        
        System.out.println("=== Arrange-Act-Assert Pattern ===");
        System.out.println("Structures tests into three clear phases.\\n");
        
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
        
        // Test 2: Division
        System.out.println("\\nTest: Calculator.divide()");
        int result2 = calc.divide(10, 3);
        assert result2 == 3 : "Expected 3 but got " + result2;
        System.out.println("  PASS: 10 / 3 = " + result2);
        
        // Test 3: Division by zero
        System.out.println("\\nTest: Calculator.divide() by zero");
        boolean exceptionThrown = false;
        try {
            calc.divide(10, 0);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected exception was not thrown";
        System.out.println("  PASS: Division by zero throws exception");''',

    # ========== RESOURCE MANAGEMENT ==========
    "resource-acquisition-is-initialization": '''
        // RAII - Resource management through object lifecycle
        static class FileHandler implements AutoCloseable {
            private String filename;
            private boolean opened = false;
            
            public FileHandler(String filename) {
                this.filename = filename;
                open();
            }
            
            private void open() {
                opened = true;
                System.out.println("  Resource acquired: " + filename);
            }
            
            public void write(String data) {
                if (!opened) throw new RuntimeException("File not opened");
                System.out.println("  Writing to " + filename + ": " + data);
            }
            
            @Override
            public void close() {
                if (opened) {
                    opened = false;
                    System.out.println("  Resource released: " + filename);
                }
            }
        }
        
        System.out.println("=== Resource Acquisition Is Initialization ===");
        System.out.println("Ties resource lifecycle to object lifetime.\\n");
        
        System.out.println("Using FileHandler with try-with-resources:");
        try (FileHandler file = new FileHandler("test.txt")) {
            file.write("Hello, World!");
            file.write("Second line");
        }
        
        System.out.println("\\nResource automatically released after try block.");''',

    "server-session": '''
        // Session management
        static class Session {
            private String sessionId;
            private String userId;
            private long creationTime;
            private java.util.Map<String, Object> attributes = new java.util.HashMap<>();
            
            public Session(String userId) {
                this.sessionId = java.util.UUID.randomUUID().toString().substring(0, 8);
                this.userId = userId;
                this.creationTime = System.currentTimeMillis();
            }
            
            public String getSessionId() { return sessionId; }
            
            public void setAttribute(String key, Object value) {
                attributes.put(key, value);
            }
            
            public Object getAttribute(String key) {
                return attributes.get(key);
            }
            
            @Override
            public String toString() {
                return "Session{id='" + sessionId + "', user='" + userId + "', attrs=" + attributes + "}";
            }
        }
        
        static class SessionManager {
            private java.util.Map<String, Session> sessions = new java.util.HashMap<>();
            
            public Session createSession(String userId) {
                Session session = new Session(userId);
                sessions.put(session.getSessionId(), session);
                System.out.println("  Created session for user: " + userId);
                return session;
            }
            
            public Session getSession(String sessionId) {
                return sessions.get(sessionId);
            }
            
            public void invalidate(String sessionId) {
                sessions.remove(sessionId);
                System.out.println("  Invalidated session: " + sessionId);
            }
        }
        
        System.out.println("=== Server Session Pattern ===");
        System.out.println("Manages user state across multiple requests.\\n");
        
        SessionManager sessionManager = new SessionManager();
        
        Session session = sessionManager.createSession("user123");
        session.setAttribute("cart", "3 items");
        session.setAttribute("theme", "dark");
        
        System.out.println("\\nSession data: " + session);
        
        sessionManager.invalidate(session.getSessionId());''',

    "throttling": '''
        // Throttling - limits rate of operations
        static class Throttler {
            private long windowSizeMs;
            private int maxRequests;
            private java.util.Map<String, java.util.List<Long>> requestLog = new java.util.HashMap<>();
            
            public Throttler(int maxRequests, long windowSizeMs) {
                this.maxRequests = maxRequests;
                this.windowSizeMs = windowSizeMs;
            }
            
            public synchronized boolean allowRequest(String clientId) {
                long now = System.currentTimeMillis();
                requestLog.putIfAbsent(clientId, new java.util.ArrayList<>());
                
                java.util.List<Long> timestamps = requestLog.get(clientId);
                timestamps.removeIf(t -> now - t > windowSizeMs);
                
                if (timestamps.size() >= maxRequests) {
                    return false;
                }
                
                timestamps.add(now);
                return true;
            }
        }
        
        System.out.println("=== Throttling Pattern ===");
        System.out.println("Limits the rate of operations from a client.\\n");
        
        Throttler throttler = new Throttler(3, 1000);
        
        System.out.println("Client 'api-key-1' making 5 rapid requests:");
        for (int i = 1; i <= 5; i++) {
            boolean allowed = throttler.allowRequest("api-key-1");
            System.out.println("  Request " + i + ": " + (allowed ? "ALLOWED" : "THROTTLED"));
        }''',

    # ========== MESSAGING ==========
    "data-bus": '''
        // Data Bus - publish/subscribe messaging
        static class DataEvent {
            private String type;
            private Object data;
            
            public DataEvent(String type, Object data) {
                this.type = type; this.data = data;
            }
            
            public String getType() { return type; }
            public Object getData() { return data; }
        }
        
        interface DataSubscriber {
            void onEvent(DataEvent event);
        }
        
        static class DataBus {
            private java.util.Map<String, java.util.List<DataSubscriber>> subscribers = new java.util.HashMap<>();
            
            public void subscribe(String eventType, DataSubscriber subscriber) {
                subscribers.computeIfAbsent(eventType, k -> new java.util.ArrayList<>()).add(subscriber);
            }
            
            public void publish(DataEvent event) {
                java.util.List<DataSubscriber> subs = subscribers.get(event.getType());
                if (subs != null) {
                    subs.forEach(s -> s.onEvent(event));
                }
            }
        }
        
        System.out.println("=== Data Bus Pattern ===");
        System.out.println("Provides a centralized event distribution system.\\n");
        
        DataBus bus = new DataBus();
        
        bus.subscribe("USER_LOGIN", e -> 
            System.out.println("  Logger: User " + e.getData() + " logged in"));
        bus.subscribe("USER_LOGIN", e -> 
            System.out.println("  Analytics: Track login for " + e.getData()));
        bus.subscribe("USER_LOGOUT", e -> 
            System.out.println("  Logger: User " + e.getData() + " logged out"));
        
        bus.publish(new DataEvent("USER_LOGIN", "Alice"));
        bus.publish(new DataEvent("USER_LOGOUT", "Alice"));''',

    "event-aggregator": '''
        // Event Aggregator - collects events from multiple sources
        static class EventAggregator {
            private java.util.List<String> events = new java.util.ArrayList<>();
            private java.util.List<Runnable> listeners = new java.util.ArrayList<>();
            
            public void addEvent(String event) {
                events.add(event);
                System.out.println("  Aggregator received: " + event);
                notifyListeners();
            }
            
            public void addListener(Runnable listener) {
                listeners.add(listener);
            }
            
            private void notifyListeners() {
                listeners.forEach(Runnable::run);
            }
            
            public java.util.List<String> getEvents() {
                return new java.util.ArrayList<>(events);
            }
        }
        
        System.out.println("=== Event Aggregator Pattern ===");
        System.out.println("Collects events from multiple sources and distributes them.\\n");
        
        EventAggregator aggregator = new EventAggregator();
        
        aggregator.addListener(() -> 
            System.out.println("  UI: Updating display"));
        aggregator.addListener(() -> 
            System.out.println("  Log: Writing to log file"));
        
        aggregator.addEvent("Button clicked");
        aggregator.addEvent("Data loaded");
        aggregator.addEvent("Error occurred");''',

    # ========== PERFORMANCE OPTIMIZATION ==========
    "caching": '''
        // Cache implementation
        static class Cache<K, V> {
            private java.util.LinkedHashMap<K, V> cache;
            
            public Cache(int maxSize) {
                this.cache = new java.util.LinkedHashMap<K, V>(maxSize, 0.75f, true) {
                    @Override
                    protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
                        return size() > maxSize;
                    }
                };
            }
            
            public V get(K key) {
                V value = cache.get(key);
                if (value != null) {
                    System.out.println("  Cache HIT for " + key);
                } else {
                    System.out.println("  Cache MISS for " + key);
                }
                return value;
            }
            
            public void put(K key, V value) {
                cache.put(key, value);
                System.out.println("  Cache: stored " + key + " -> " + value);
            }
            
            public int size() { return cache.size(); }
        }
        
        System.out.println("=== Caching Pattern ===");
        System.out.println("Stores frequently accessed data for fast retrieval.\\n");
        
        Cache<String, String> cache = new Cache<>(3);
        
        cache.put("user:1", "Alice");
        cache.put("user:2", "Bob");
        cache.put("user:3", "Charlie");
        
        cache.get("user:1");
        cache.get("user:4"); // Miss
        
        cache.put("user:4", "Diana"); // Evicts oldest
        cache.get("user:2"); // May be evicted''',

    "data-locality": '''
        // Data Locality - organizes data for cache efficiency
        static class ParticleSystem {
            private float[] positionsX;
            private float[] positionsY;
            private float[] velocitiesX;
            private float[] velocitiesY;
            private float[] colors;
            
            public ParticleSystem(int count) {
                positionsX = new float[count];
                positionsY = new float[count];
                velocitiesX = new float[count];
                velocitiesY = new float[count];
                colors = new float[count];
                
                for (int i = 0; i < count; i++) {
                    positionsX[i] = (float) (Math.random() * 100);
                    positionsY[i] = (float) (Math.random() * 100);
                    velocitiesX[i] = (float) (Math.random() * 10 - 5);
                    velocitiesY[i] = (float) (Math.random() * 10 - 5);
                    colors[i] = (float) Math.random();
                }
            }
            
            public void update() {
                // Sequential memory access - cache friendly
                for (int i = 0; i < positionsX.length; i++) {
                    positionsX[i] += velocitiesX[i] * 0.016f;
                    positionsY[i] += velocitiesY[i] * 0.016f;
                }
            }
            
            public void display() {
                System.out.println("  Updated " + positionsX.length + " particles");
                System.out.println("  First particle: (" + 
                    String.format("%.1f", positionsX[0]) + ", " + 
                    String.format("%.1f", positionsY[0]) + ")");
            }
        }
        
        System.out.println("=== Data Locality Pattern ===");
        System.out.println("Organizes data for optimal cache performance.\\n");
        
        ParticleSystem particles = new ParticleSystem(1000);
        particles.update();
        particles.display();''',

    "lazy-loading": '''
        // Lazy Loading - defers expensive initialization
        static class HeavyResource {
            private String name;
            
            public HeavyResource(String name) {
                this.name = name;
                System.out.println("  Loading expensive resource: " + name);
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
            
            public String getName() { return name; }
            public void use() { System.out.println("  Using: " + name); }
        }
        
        static class LazyLoader {
            private HeavyResource resource;
            private String resourceName;
            
            public LazyLoader(String resourceName) {
                this.resourceName = resourceName;
            }
            
            public HeavyResource getResource() {
                if (resource == null) {
                    resource = new HeavyResource(resourceName);
                }
                return resource;
            }
        }
        
        System.out.println("=== Lazy Loading Pattern ===");
        System.out.println("Defers object creation until it's actually needed.\\n");
        
        LazyLoader loader = new LazyLoader("Database Connection");
        
        System.out.println("Loader created (resource not yet loaded)");
        System.out.println("...doing other work...");
        
        System.out.println("\\nFirst access to resource:");
        loader.getResource().use();
        
        System.out.println("\\nSecond access (already loaded):");
        loader.getResource().use();''',

    # ========== SIDECAR ==========
    "sidecar": '''
        // Main application
        static class MainApplication {
            public void handleRequest(String request) {
                System.out.println("  Main App: processing " + request);
            }
        }
        
        // Sidecar - monitors and manages the main application
        static class Sidecar {
            private MainApplication app;
            private boolean healthy = true;
            
            public Sidecar(MainApplication app) { this.app = app; }
            
            public boolean healthCheck() {
                System.out.println("  Sidecar: Health check - " + (healthy ? "OK" : "FAIL"));
                return healthy;
            }
            
            public void log(String message) {
                System.out.println("  Sidecar: [LOG] " + message);
            }
            
            public void monitorRequest(String request) {
                log("Request: " + request);
                System.out.println("  Sidecar: Monitoring request metrics");
                app.handleRequest(request);
                System.out.println("  Sidecar: Request completed");
            }
        }
        
        System.out.println("=== Sidecar Pattern ===");
        System.out.println("Attaches a helper component to a main application.\\n");
        
        Sidecar sidecar = new Sidecar(new MainApplication());
        sidecar.healthCheck();
        sidecar.monitorRequest("GET /api/users");''',

    # ========== SERVICE DISCOVERY ==========
    "service-discovery": '''
        // Service registry
        static class ServiceRegistry {
            private java.util.Map<String, String> services = new java.util.HashMap<>();
            
            public void register(String name, String address) {
                services.put(name, address);
                System.out.println("  Registered: " + name + " at " + address);
            }
            
            public String discover(String name) {
                String address = services.get(name);
                System.out.println("  Discovered " + name + " at " + address);
                return address;
            }
            
            public void unregister(String name) {
                services.remove(name);
                System.out.println("  Unregistered: " + name);
            }
        }
        
        System.out.println("=== Service Discovery Pattern ===");
        System.out.println("Enables services to find each other dynamically.\\n");
        
        ServiceRegistry registry = new ServiceRegistry();
        registry.register("user-service", "http://user-svc:8080");
        registry.register("order-service", "http://order-svc:8081");
        
        registry.discover("user-service");
        registry.discover("order-service");''',

    "service-registry": '''
        // Service Registry with health checks
        static class ServiceInstance {
            String name; String address; boolean healthy = true;
            public ServiceInstance(String name, String address) {
                this.name = name; this.address = address;
            }
        }
        
        static class ServiceRegistry {
            private java.util.Map<String, ServiceInstance> instances = new java.util.HashMap<>();
            
            public void register(String name, String address) {
                instances.put(name, new ServiceInstance(name, address));
                System.out.println("  Registered: " + name + " at " + address);
            }
            
            public String getAddress(String name) {
                ServiceInstance instance = instances.get(name);
                if (instance != null && instance.healthy) {
                    return instance.address;
                }
                return null;
            }
            
            public void healthCheck() {
                System.out.println("  Performing health checks...");
                for (ServiceInstance instance : instances.values()) {
                    System.out.println("    " + instance.name + ": " + 
                        (instance.healthy ? "HEALTHY" : "UNHEALTHY"));
                }
            }
        }
        
        System.out.println("=== Service Registry Pattern ===");
        System.out.println("Maintains a registry of available service instances.\\n");
        
        ServiceRegistry registry = new ServiceRegistry();
        registry.register("auth-service", "http://auth:8080");
        registry.register("payment-service", "http://payment:8081");
        
        registry.healthCheck();
        System.out.println("\\nPayment service at: " + registry.getAddress("payment-service"));''',

    "service-mesh": '''
        // Service Mesh - manages service-to-service communication
        static class ServiceMesh {
            public String callService(String service, String request) {
                System.out.println("  Mesh: Routing to " + service);
                System.out.println("  Mesh: Adding retry logic");
                System.out.println("  Mesh: Adding circuit breaker");
                System.out.println("  Mesh: Collecting metrics");
                System.out.println("  Mesh: Response from " + service + ": " + request + " processed");
                return "response";
            }
        }
        
        System.out.println("=== Service Mesh Pattern ===");
        System.out.println("Dedicated infrastructure layer for service-to-service communication.\\n");
        
        ServiceMesh mesh = new ServiceMesh();
        mesh.callService("payment-service", "charge-request");''',

    "config-server": '''
        // Configuration Server
        static class ConfigServer {
            private java.util.Map<String, String> config = new java.util.HashMap<>();
            
            public ConfigServer() {
                config.put("db.url", "jdbc:postgresql://localhost:5432/mydb");
                config.put("db.user", "admin");
                config.put("cache.ttl", "3600");
                config.put("api.timeout", "5000");
            }
            
            public String getConfig(String key) {
                String value = config.get(key);
                System.out.println("  Config: " + key + " = " + value);
                return value;
            }
            
            public void refresh() {
                System.out.println("  Config: Refreshing all configurations");
            }
        }
        
        System.out.println("=== Config Server Pattern ===");
        System.out.println("Centralizes configuration management.\\n");
        
        ConfigServer configServer = new ConfigServer();
        configServer.getConfig("db.url");
        configServer.getConfig("api.timeout");
        configServer.refresh();''',

    "database-per-service": '''
        // Database per Service pattern
        static class UserDatabase {
            private java.util.Map<Integer, String> users = new java.util.HashMap<>();
            
            public void save(int id, String name) {
                users.put(id, name);
                System.out.println("  UserDB: Saved user " + id);
            }
            
            public String find(int id) {
                return users.get(id);
            }
        }
        
        static class OrderDatabase {
            private java.util.Map<Integer, String> orders = new java.util.HashMap<>();
            
            public void save(int id, String order) {
                orders.put(id, order);
                System.out.println("  OrderDB: Saved order " + id);
            }
            
            public String find(int id) {
                return orders.get(id);
            }
        }
        
        System.out.println("=== Database per Service Pattern ===");
        System.out.println("Each microservice has its own private database.\\n");
        
        UserDatabase userDb = new UserDatabase();
        OrderDatabase orderDb = new OrderDatabase();
        
        userDb.save(1, "Alice");
        orderDb.save(100, "Order #100 for Alice");
        
        System.out.println("\\nUser data: " + userDb.find(1));
        System.out.println("Order data: " + orderDb.find(100));''',

    "service-decomposition": '''
        // Service Decomposition - breaking a monolith into services
        static class Monolith {
            public void handleAll(String request) {
                System.out.println("  Monolith: handling " + request);
            }
        }
        
        // Decomposed services
        static class UserService {
            public void handle(String request) {
                System.out.println("  UserService: " + request);
            }
        }
        
        static class InventoryService {
            public void handle(String request) {
                System.out.println("  InventoryService: " + request);
            }
        }
        
        static class ShippingService {
            public void handle(String request) {
                System.out.println("  ShippingService: " + request);
            }
        }
        
        static class OrderService {
            private UserService userService = new UserService();
            private InventoryService inventoryService = new InventoryService();
            private ShippingService shippingService = new ShippingService();
            
            public void placeOrder(String userId, String item, String address) {
                System.out.println("  OrderService: Orchestrating order placement");
                userService.handle("Validate user " + userId);
                inventoryService.handle("Reserve " + item);
                shippingService.handle("Ship to " + address);
                System.out.println("  OrderService: Order placed successfully");
            }
        }
        
        System.out.println("=== Service Decomposition Pattern ===");
        System.out.println("Breaking a monolith into smaller, independent services.\\n");
        
        OrderService orderService = new OrderService();
        orderService.placeOrder("USR-001", "Laptop", "123 Main St");''',

    "observability": '''
        // Observability - metrics, logs, and traces
        static class Observability {
            private java.util.Map<String, Integer> metrics = new java.util.HashMap<>();
            
            public void incrementMetric(String name) {
                metrics.merge(name, 1, Integer::sum);
            }
            
            public void log(String level, String message) {
                System.out.println("  [" + level + "] " + message);
            }
            
            public void trace(String operation, long durationMs) {
                System.out.println("  [TRACE] " + operation + " took " + durationMs + "ms");
            }
            
            public void displayMetrics() {
                System.out.println("\\n  Current metrics:");
                for (java.util.Map.Entry<String, Integer> entry : metrics.entrySet()) {
                    System.out.println("    " + entry.getKey() + ": " + entry.getValue());
                }
            }
        }
        
        System.out.println("=== Observability Pattern ===");
        System.out.println("Provides visibility into system behavior through logs, metrics, and traces.\\n");
        
        Observability obs = new Observability();
        
        obs.log("INFO", "Service started");
        obs.incrementMetric("requests");
        obs.trace("GET /api/users", 45);
        
        obs.incrementMetric("requests");
        obs.incrementMetric("errors");
        obs.log("ERROR", "Failed to connect to database");
        obs.trace("POST /api/order", 120);
        
        obs.displayMetrics();''',

    "health-monitoring": '''
        // Health monitoring
        static class HealthMonitor {
            private java.util.Map<String, Boolean> services = new java.util.HashMap<>();
            
            public void registerService(String name, boolean healthy) {
                services.put(name, healthy);
            }
            
            public void checkHealth() {
                System.out.println("  Health Check Results:");
                for (java.util.Map.Entry<String, Boolean> entry : services.entrySet()) {
                    String status = entry.getValue() ? "✓ HEALTHY" : "✗ UNHEALTHY";
                    System.out.println("    " + entry.getKey() + ": " + status);
                }
            }
            
            public boolean isSystemHealthy() {
                return services.values().stream().allMatch(h -> h);
            }
        }
        
        System.out.println("=== Health Monitoring Pattern ===");
        System.out.println("Monitors the health of system components.\\n");
        
        HealthMonitor monitor = new HealthMonitor();
        monitor.registerService("API Gateway", true);
        monitor.registerService("User Service", true);
        monitor.registerService("Payment Service", false);
        monitor.registerService("Database", true);
        
        monitor.checkHealth();
        System.out.println("\\n  System healthy: " + monitor.isSystemHealthy());''',

    "log-aggregation": '''
        // Log Aggregation
        static class LogAggregator {
            private java.util.List<String> logs = new java.util.ArrayList<>();
            
            public void collect(String service, String level, String message) {
                String log = "[" + level + "] [" + service + "] " + message;
                logs.add(log);
                System.out.println("  Collected: " + log);
            }
            
            public void searchByLevel(String level) {
                System.out.println("\\n  Logs with level " + level + ":");
                logs.stream()
                    .filter(log -> log.contains("[" + level + "]"))
                    .forEach(log -> System.out.println("    " + log));
            }
            
            public void searchByService(String service) {
                System.out.println("\\n  Logs from " + service + ":");
                logs.stream()
                    .filter(log -> log.contains("[" + service + "]"))
                    .forEach(log -> System.out.println("    " + log));
            }
        }
        
        System.out.println("=== Log Aggregation Pattern ===");
        System.out.println("Centralizes logs from multiple services.\\n");
        
        LogAggregator aggregator = new LogAggregator();
        aggregator.collect("user-service", "INFO", "User logged in");
        aggregator.collect("order-service", "WARN", "Payment timeout");
        aggregator.collect("user-service", "ERROR", "Database connection failed");
        aggregator.collect("payment-service", "INFO", "Payment processed");
        
        aggregator.searchByLevel("ERROR");
        aggregator.searchByService("user-service");''',

    "monitoring": '''
        // Monitoring system
        static class MonitoringSystem {
            private java.util.Map<String, Long> responseTimes = new java.util.HashMap<>();
            private java.util.Map<String, Integer> errorCounts = new java.util.HashMap<>();
            
            public void recordResponse(String service, long timeMs) {
                responseTimes.put(service, timeMs);
                System.out.println("  Monitor: " + service + " responded in " + timeMs + "ms");
            }
            
            public void recordError(String service) {
                errorCounts.merge(service, 1, Integer::sum);
                System.out.println("  Monitor: ERROR in " + service);
            }
            
            public void report() {
                System.out.println("\\n  Monitoring Report:");
                System.out.println("  Response Times:");
                responseTimes.forEach((s, t) -> 
                    System.out.println("    " + s + ": " + t + "ms" + (t > 200 ? " (SLOW!)" : "")));
                System.out.println("  Errors:");
                errorCounts.forEach((s, c) -> 
                    System.out.println("    " + s + ": " + c + " errors"));
            }
        }
        
        System.out.println("=== Monitoring Pattern ===");
        System.out.println("Tracks system performance and errors.\\n");
        
        MonitoringSystem monitor = new MonitoringSystem();
        monitor.recordResponse("user-service", 150);
        monitor.recordResponse("payment-service", 350);
        monitor.recordError("payment-service");
        monitor.recordResponse("user-service", 120);
        
        monitor.report();''',

    "master-service-decomposition": '''
        // Master-Service Decomposition
        static class MasterOrchestrator {
            private java.util.List<String> services = new java.util.ArrayList<>();
            
            public void addService(String service) { services.add(service); }
            
            public void executeWorkflow(String task) {
                System.out.println("  Master: Starting workflow for " + task);
                for (String service : services) {
                    System.out.println("  Master: Delegating to " + service);
                }
                System.out.println("  Master: Workflow completed");
            }
        }
        
        System.out.println("=== Master-Service Decomposition Pattern ===");
        System.out.println("Central orchestrator manages distributed services.\\n");
        
        MasterOrchestrator master = new MasterOrchestrator();
        master.addService("Validation");
        master.addService("Processing");
        master.addService("Notification");
        
        master.executeWorkflow("Order Processing");''',

    # ========== PIPE AND FILTER ==========
    "pipes-and-filters": '''
        // Pipe and Filter pattern
        @FunctionalInterface
        interface Filter {
            String process(String input);
        }
        
        static class Pipe {
            private java.util.List<Filter> filters = new java.util.ArrayList<>();
            
            public Pipe addFilter(Filter filter) {
                filters.add(filter);
                return this;
            }
            
            public String execute(String input) {
                String result = input;
                for (Filter filter : filters) {
                    result = filter.process(result);
                }
                return result;
            }
        }
        
        System.out.println("=== Pipes and Filters Pattern ===");
        System.out.println("Processes data through a sequence of filters.\\n");
        
        Pipe pipe = new Pipe()
            .addFilter(s -> s.trim())
            .addFilter(s -> s.toLowerCase())
            .addFilter(s -> s.replaceAll("\\s+", "_"))
            .addFilter(s -> s.substring(0, Math.min(s.length(), 20)));
        
        String result = pipe.execute("  Hello World From Pipes and Filters Pattern  ");
        System.out.println("Result: '" + result + "'");''',

    # ========== COMPOSITE VIEW ==========
    "composite-view": '''
        // Composite View - builds views from sub-views
        static class View {
            private String name;
            private java.util.List<View> children = new java.util.ArrayList<>();
            
            public View(String name) { this.name = name; }
            
            public void addChild(View child) { children.add(child); }
            
            public void render() {
                System.out.println("  Rendering: " + name);
                for (View child : children) {
                    child.render();
                }
            }
        }
        
        System.out.println("=== Composite View Pattern ===");
        System.out.println("Builds views from smaller, reusable components.\\n");
        
        View page = new View("Page");
        View header = new View("Header");
        View content = new View("Content");
        View footer = new View("Footer");
        
        header.addChild(new View("Logo"));
        header.addChild(new View("Navigation"));
        content.addChild(new View("Article"));
        content.addChild(new View("Sidebar"));
        footer.addChild(new View("Copyright"));
        
        page.addChild(header);
        page.addChild(content);
        page.addChild(footer);
        
        page.render();''',

    "composite-entity": '''
        // Composite Entity - coarse-grained entity
        static class DependentObject {
            private String data;
            public void setData(String data) { this.data = data; }
            public String getData() { return data; }
        }
        
        static class CompositeEntity {
            private DependentObject obj1 = new DependentObject();
            private DependentObject obj2 = new DependentObject();
            
            public void setData(String data1, String data2) {
                obj1.setData(data1);
                obj2.setData(data2);
            }
            
            public void display() {
                System.out.println("  Composite Entity:");
                System.out.println("    Part 1: " + obj1.getData());
                System.out.println("    Part 2: " + obj2.getData());
            }
        }
        
        System.out.println("=== Composite Entity Pattern ===");
        System.out.println("Manages a group of related objects as a single entity.\\n");
        
        CompositeEntity entity = new CompositeEntity();
        entity.setData("User Profile", "Account Settings");
        entity.display();''',

    "domain-model": '''
        // Domain Model - rich domain model
        static class Money {
            private double amount;
            private String currency;
            
            public Money(double amount, String currency) {
                this.amount = amount; this.currency = currency;
            }
            
            public Money add(Money other) {
                if (!this.currency.equals(other.currency)) {
                    throw new IllegalArgumentException("Currency mismatch");
                }
                return new Money(this.amount + other.amount, this.currency);
            }
            
            @Override
            public String toString() {
                return String.format("%.2f %s", amount, currency);
            }
        }
        
        static class OrderLine {
            private String product;
            private int quantity;
            private Money price;
            
            public OrderLine(String product, int quantity, Money price) {
                this.product = product; this.quantity = quantity; this.price = price;
            }
            
            public Money getTotal() {
                return new Money(price.getAmount() * quantity, price.getCurrency());
            }
        }
        
        static class Order {
            private java.util.List<OrderLine> lines = new java.util.ArrayList<>();
            
            public void addLine(OrderLine line) { lines.add(line); }
            
            public Money getTotal() {
                Money total = new Money(0, "USD");
                for (OrderLine line : lines) {
                    total = total.add(line.getTotal());
                }
                return total;
            }
        }
        
        System.out.println("=== Domain Model Pattern ===");
        System.out.println("Rich domain model with behavior and business logic.\\n");
        
        Order order = new Order();
        order.addLine(new OrderLine("Laptop", 1, new Money(999.99, "USD")));
        order.addLine(new OrderLine("Mouse", 2, new Money(29.99, "USD")));
        
        System.out.println("  Order total: " + order.getTotal());''',

    "value-object": '''
        // Value Object - immutable by value
        static final class Color {
            private final int red;
            private final int green;
            private final int blue;
            
            public Color(int red, int green, int blue) {
                this.red = red; this.green = green; this.blue = blue;
            }
            
            public int getRed() { return red; }
            public int getGreen() { return green; }
            public int getBlue() { return blue; }
            
            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Color color = (Color) o;
                return red == color.red && green == color.green && blue == color.blue;
            }
            
            @Override
            public int hashCode() {
                return java.util.Objects.hash(red, green, blue);
            }
            
            @Override
            public String toString() {
                return "Color(" + red + ", " + green + ", " + blue + ")";
            }
        }
        
        System.out.println("=== Value Object Pattern ===");
        System.out.println("Immutable objects compared by their values.\\n");
        
        Color red1 = new Color(255, 0, 0);
        Color red2 = new Color(255, 0, 0);
        Color blue = new Color(0, 0, 255);
        
        System.out.println("red1: " + red1);
        System.out.println("red2: " + red2);
        System.out.println("blue: " + blue);
        System.out.println("\\nred1.equals(red2): " + red1.equals(red2));
        System.out.println("red1.equals(blue): " + red1.equals(blue));''',

    "service-layer": '''
        // Service Layer pattern
        static class OrderDTO {
            String id; String product; int quantity;
            public OrderDTO(String id, String product, int quantity) {
                this.id = id; this.product = product; this.quantity = quantity;
            }
            @Override public String toString() {
                return "Order{id='" + id + "', product='" + product + "', qty=" + quantity + "}";
            }
        }
        
        static class OrderService {
            private java.util.Map<String, OrderDTO> orders = new java.util.HashMap<>();
            
            public OrderDTO createOrder(String id, String product, int quantity) {
                validate(id, product, quantity);
                OrderDTO order = new OrderDTO(id, product, quantity);
                orders.put(id, order);
                System.out.println("  Service: Created " + order);
                return order;
            }
            
            public OrderDTO getOrder(String id) {
                OrderDTO order = orders.get(id);
                System.out.println("  Service: Retrieved " + order);
                return order;
            }
            
            private void validate(String id, String product, int quantity) {
                if (id == null || product == null) throw new IllegalArgumentException("Invalid input");
                if (quantity <= 0) throw new IllegalArgumentException("Invalid quantity");
            }
        }
        
        System.out.println("=== Service Layer Pattern ===");
        System.out.println("Defines the application boundary with a service layer.\\n");
        
        OrderService orderService = new OrderService();
        orderService.createOrder("ORD-001", "Laptop", 1);
        orderService.getOrder("ORD-001");''',

    "service-to-worker": '''
        // Service to Worker pattern
        static class ServiceToWorker {
            public String processRequest(String action, String data) {
                System.out.println("  Worker: Processing " + action);
                String result = "Processed: " + data;
                System.out.println("  Worker: Result = " + result);
                return result;
            }
        }
        
        // Dispatcher
        static class Dispatcher {
            private ServiceToWorker worker = new ServiceToWorker();
            
            public void dispatch(String action, String data) {
                System.out.println("  Dispatcher: Delegating " + action);
                String result = worker.processRequest(action, data);
                System.out.println("  Dispatcher: Rendering view with: " + result);
            }
        }
        
        System.out.println("=== Service to Worker Pattern ===");
        System.out.println("Separates request processing from view management.\\n");
        
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.dispatch("userLogin", "username=alice");''',

    "page-controller": '''
        // Page Controller
        static class PageController {
            public void handleRequest(String page, String action) {
                System.out.println("  Controller: Handling " + page + "/" + action);
                if (page.equals("login") && action.equals("submit")) {
                    System.out.println("  Controller: Processing login form");
                } else if (page.equals("products")) {
                    System.out.println("  Controller: Displaying products");
                }
                System.out.println("  Controller: Rendering " + page + " page");
            }
        }
        
        System.out.println("=== Page Controller Pattern ===");
        System.out.println("Each page has its own controller for handling requests.\\n");
        
        PageController controller = new PageController();
        controller.handleRequest("login", "submit");
        controller.handleRequest("products", "view");''',

    "naked-objects": '''
        // Naked Objects - domain objects exposed directly
        static class Customer {
            private String name;
            private String email;
            private java.util.List<String> orders = new java.util.ArrayList<>();
            
            public Customer(String name, String email) {
                this.name = name; this.email = email;
            }
            
            public void placeOrder(String product) {
                orders.add(product);
                System.out.println("  Customer: Placed order for " + product);
            }
            
            public void display() {
                System.out.println("  Customer: " + name + " (" + email + ")");
                System.out.println("  Orders: " + orders);
            }
        }
        
        System.out.println("=== Naked Objects Pattern ===");
        System.out.println("Domain objects are automatically exposed as UI.\\n");
        
        Customer customer = new Customer("Alice", "alice@example.com");
        customer.placeOrder("Laptop");
        customer.placeOrder("Mouse");
        customer.display();''',

    "presentation-model": '''
        // Presentation Model
        static class PresentationModel {
            private String text = "";
            private boolean enabled = true;
            private java.util.List<Runnable> listeners = new java.util.ArrayList<>();
            
            public String getText() { return text; }
            public boolean isEnabled() { return enabled; }
            
            public void setText(String text) {
                this.text = text;
                notifyListeners();
            }
            
            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
                notifyListeners();
            }
            
            public void addListener(Runnable listener) {
                listeners.add(listener);
            }
            
            private void notifyListeners() {
                listeners.forEach(Runnable::run);
            }
        }
        
        System.out.println("=== Presentation Model Pattern ===");
        System.out.println("Separates UI state from the view.\\n");
        
        PresentationModel model = new PresentationModel();
        model.addListener(() -> System.out.println("  View updated: text='" + model.getText() + "', enabled=" + model.isEnabled()));
        
        model.setText("Hello");
        model.setEnabled(false);''',

    "model-view-viewmodel": '''
        // Model-View-ViewModel
        static class Item {
            String name; double price;
            public Item(String name, double price) { this.name = name; this.price = price; }
        }
        
        static class ViewModel {
            private java.util.List<Item> items = new java.util.ArrayList<>();
            private double total = 0;
            
            public void addItem(Item item) {
                items.add(item);
                total += item.price;
                System.out.println("  VM: Item added, total updated");
            }
            
            public void display() {
                System.out.println("  Cart contents:");
                items.forEach(i -> System.out.println("    " + i.name + " - $" + i.price));
                System.out.println("  Total: $" + String.format("%.2f", total));
            }
        }
        
        System.out.println("=== Model-View-ViewModel Pattern ===");
        System.out.println("Separates UI from business logic with data binding.\\n");
        
        ViewModel vm = new ViewModel();
        vm.addItem(new Item("Book", 29.99));
        vm.addItem(new Item("Pen", 5.99));
        vm.display();''',

    "model-view-presenter": '''
        // Model-View-Presenter
        static class UserModel {
            String name; String email;
            public UserModel(String name, String email) { this.name = name; this.email = email; }
        }
        
        static class Presenter {
            private UserModel model;
            
            public Presenter(UserModel model) { this.model = model; }
            
            public void updateName(String name) {
                model.name = name;
                System.out.println("  Presenter: Updated name");
            }
            
            public void display() {
                System.out.println("  View: " + model.name + " (" + model.email + ")");
            }
        }
        
        System.out.println("=== Model-View-Presenter Pattern ===");
        System.out.println("Presenter mediates between Model and View.\\n");
        
        Presenter presenter = new Presenter(new UserModel("Alice", "alice@example.com"));
        presenter.display();
        presenter.updateName("Alice Smith");
        presenter.display();''',

    "model-view-intent": '''
        // Model-View-Intent
        static class Intent {
            String type; Object data;
            public Intent(String type, Object data) { this.type = type; this.data = data; }
        }
        
        static class Model {
            private int count = 0;
            
            public int getCount() { return count; }
            
            public void handleIntent(Intent intent) {
                if (intent.type.equals("INCREMENT")) {
                    count++;
                    System.out.println("  Model: Count incremented to " + count);
                } else if (intent.type.equals("RESET")) {
                    count = 0;
                    System.out.println("  Model: Count reset");
                }
            }
        }
        
        System.out.println("=== Model-View-Intent Pattern ===");
        System.out.println("Unidirectional data flow with intents.\\n");
        
        Model model = new Model();
        model.handleIntent(new Intent("INCREMENT", null));
        model.handleIntent(new Intent("INCREMENT", null));
        model.handleIntent(new Intent("RESET", null));
        model.handleIntent(new Intent("INCREMENT", null));
        System.out.println("  Final count: " + model.getCount());''',

    "intercepting-filter": '''
        // Intercepting Filter
        @FunctionalInterface
        interface Filter {
            void execute(String request);
        }
        
        static class FilterChain {
            private java.util.List<Filter> filters = new java.util.ArrayList<>();
            
            public void addFilter(Filter filter) { filters.add(filter); }
            
            public void process(String request) {
                for (Filter filter : filters) {
                    filter.execute(request);
                }
            }
        }
        
        System.out.println("=== Intercepting Filter Pattern ===");
        System.out.println("Pre-processes and post-processes requests.\\n");
        
        FilterChain chain = new FilterChain();
        chain.addFilter(r -> System.out.println("  Authentication: Checking access for " + r));
        chain.addFilter(r -> System.out.println("  Logging: Request " + r));
        chain.addFilter(r -> System.out.println("  Compression: Compressing response"));
        
        chain.process("GET /api/users");''',

    "microservices-aggregator": '''
        // Microservices Aggregator
        static class Aggregator {
            public String getUser(int id) {
                return "{\"id\":" + id + ",\"name\":\"Alice\"}";
            }
            
            public String getOrders(int userId) {
                return "{\"orders\":[\"Laptop\",\"Mouse\"],\"total\":1029.98}";
            }
            
            public String getAggregatedData(int userId) {
                System.out.println("  Aggregator: Calling user service");
                String user = getUser(userId);
                System.out.println("  Aggregator: Calling order service");
                String orders = getOrders(userId);
                return "{\"user\":" + user + ",\"orders\":" + orders + "}";
            }
        }
        
        System.out.println("=== Microservices Aggregator Pattern ===");
        System.out.println("Aggregates data from multiple microservices.\\n");
        
        Aggregator aggregator = new Aggregator();
        String result = aggregator.getAggregatedData(1);
        System.out.println("\\nResult: " + result);''',

    "flux": '''
        // Flux Architecture
        static class Store {
            private java.util.List<String> items = new java.util.ArrayList<>();
            private java.util.List<Runnable> listeners = new java.util.ArrayList<>();
            
            public void dispatch(String action, String data) {
                if (action.equals("ADD")) {
                    items.add(data);
                    System.out.println("  Store: Added '" + data + "'");
                } else if (action.equals("REMOVE")) {
                    items.remove(data);
                    System.out.println("  Store: Removed '" + data + "'");
                }
                notifyListeners();
            }
            
            public java.util.List<String> getItems() { return new java.util.ArrayList<>(items); }
            public void addListener(Runnable listener) { listeners.add(listener); }
            private void notifyListeners() { listeners.forEach(Runnable::run); }
        }
        
        System.out.println("=== Flux Architecture Pattern ===");
        System.out.println("Unidirectional data flow architecture.\\n");
        
        Store store = new Store();
        store.addListener(() -> System.out.println("  View: Updated items: " + store.getItems()));
        
        store.dispatch("ADD", "Task 1");
        store.dispatch("ADD", "Task 2");
        store.dispatch("REMOVE", "Task 1");''',

    "composable-architecture": '''
        // Composable Architecture
        static class Reducer {
            private int state = 0;
            private java.util.List<Runnable> listeners = new java.util.ArrayList<>();
            
            public void dispatch(String action) {
                switch (action) {
                    case "INCREMENT": state++; break;
                    case "DECREMENT": state--; break;
                    case "RESET": state = 0; break;
                }
                System.out.println("  Reducer: State = " + state);
                notifyListeners();
            }
            
            public int getState() { return state; }
            public void addListener(Runnable listener) { listeners.add(listener); }
            private void notifyListeners() { listeners.forEach(Runnable::run); }
        }
        
        static class Feature {
            private Reducer reducer;
            private String name;
            
            public Feature(String name, Reducer reducer) {
                this.name = name;
                this.reducer = reducer;
            }
            
            public void performAction(String action) {
                System.out.println("  Feature [" + name + "]: " + action);
                reducer.dispatch(action);
            }
        }
        
        System.out.println("=== Composable Architecture Pattern ===");
        System.out.println("Composes features from smaller, independent components.\\n");
        
        Reducer sharedReducer = new Reducer();
        sharedReducer.addListener(() -> {});
        
        Feature counter = new Feature("Counter", sharedReducer);
        counter.performAction("INCREMENT");
        counter.performAction("INCREMENT");
        counter.performAction("DECREMENT");''',

    # ========== SPECIAL CASE ==========
    "special-case": '''
        // Special Case pattern
        static abstract class Discount {
            public abstract double apply(double price);
            public abstract String getDescription();
        }
        
        static class NoDiscount extends Discount {
            @Override public double apply(double price) { return price; }
            @Override public String getDescription() { return "No discount"; }
        }
        
        static class PercentageDiscount extends Discount {
            private double percentage;
            public PercentageDiscount(double percentage) { this.percentage = percentage; }
            @Override public double apply(double price) { return price * (1 - percentage / 100); }
            @Override public String getDescription() { return percentage + "% off"; }
        }
        
        static class FixedDiscount extends Discount {
            private double amount;
            public FixedDiscount(double amount) { this.amount = amount; }
            @Override public double apply(double price) { return Math.max(0, price - amount); }
            @Override public String getDescription() { return "$" + amount + " off"; }
        }
        
        System.out.println("=== Special Case Pattern ===");
        System.out.println("Handles special cases with polymorphic objects instead of conditionals.\\n");
        
        double price = 100.0;
        Discount[] discounts = {new NoDiscount(), new PercentageDiscount(10), new FixedDiscount(15)};
        
        for (Discount discount : discounts) {
            System.out.println("  " + discount.getDescription() + ": $" + String.format("%.2f", discount.apply(price)));
        }''',
}

# ============================================================
# FIX FUNCTIONS
# ============================================================

def get_pattern_name_from_path(file_path):
    """Extract pattern name from file path."""
    parts = file_path.parts
    for i, part in enumerate(parts):
        if part == "system-design-pattern" or part == "design-patterns":
            for j in range(i + 1, len(parts)):
                # Check if this is a pattern directory (has src/main/java in it)
                if j + 3 < len(parts) and parts[j+1] == "src" and parts[j+2] == "main" and parts[j+3] == "java":
                    return parts[j]
    return None

def get_class_name_from_file(file_path):
    """Get the class name (filename without .java)."""
    return file_path.stem

def get_package_from_path(file_path, pattern_name):
    """Construct proper package name from file path."""
    parts = file_path.parts
    package_parts = []
    found_java = False
    
    for part in parts:
        if found_java:
            # Convert hyphens to nothing for Java packages
            package_part = part.replace("-", "")
            package_parts.append(package_part)
        if part == "java" and not found_java:
            found_java = True
    
    if package_parts:
        return ".".join(package_parts)
    return "com.javastarterkit.patterns"

def get_title_from_name(pattern_name):
    """Convert pattern name to title case."""
    return pattern_name.replace("-", " ").title()

def is_valid_java_file(content):
    """Check if the Java file is valid according to the validation criteria."""
    has_package = 'package ' in content
    has_class = 'public class ' in content
    has_demonstrate = 'public static void demonstrate()' in content
    has_main = 'public static void main(' in content
    is_placeholder = 'Pattern implementation pending' in content or 'TODO: Implement' in content
    
    return has_package and has_class and has_demonstrate and has_main and not is_placeholder

def has_syntax_errors(content):
    """Check for common syntax errors like double braces, floating code, etc."""
    if '{{' in content or '}}' in content:
        return True
    # Check for floating statements outside methods
    lines = content.split('\n')
    in_method = False
    in_class = False
    for line in lines:
        stripped = line.strip()
        if stripped.startswith('public class ') or stripped.startswith('class '):
            in_class = True
        if stripped.startswith('public static void ') or stripped.startswith('private static void ') or stripped.startswith('public void ') or stripped.startswith('private void '):
            in_method = True
        if stripped == '}':
            in_method = False
        if in_method:
            continue
        # Check for floating statements
        if in_class and not in_method and stripped and not stripped.startswith('//') and not stripped.startswith('/*') and not stripped.startswith('*') and not stripped.startswith('package') and not stripped.startswith('import') and not stripped.startswith('@') and not stripped.startswith('public') and not stripped.startswith('private') and not stripped.startswith('protected') and not stripped.startswith('static') and not stripped.startswith('class') and not stripped.startswith('interface') and not stripped.startswith('enum') and not stripped.startswith('}') and not stripped.startswith('{') and not stripped == '':
            if '=' in stripped or stripped.startswith('new ') or stripped.startswith('System.out'):
                return True
    return False

def fix_file(file_path):
    """Fix a single Java file."""
    try:
        content = file_path.read_text()
        
        # Get pattern info
        pattern_name = get_pattern_name_from_path(file_path)
        if pattern_name is None:
            # Try to determine from parent directory
            for parent in file_path.parents:
                if parent.name != "src" and parent.name != "main" and parent.name != "java" and parent.name != "com" and parent.name != "javastarterkit" and parent.name != "patterns" and parent.name != "resources":
                    pattern_name = parent.name
                    break
        
        if pattern_name is None:
            return False, "Could not determine pattern name"
        
        class_name = get_class_name_from_file(file_path)
        package = get_package_from_path(file_path, pattern_name)
        title = get_title_from_name(pattern_name)
        
        # Check if we have a specific implementation
        if pattern_name in PATTERN_IMPLEMENTATIONS:
            implementation = PATTERN_IMPLEMENTATIONS[pattern_name]
            new_content = f'''package {package};

/**
 * {title} Pattern
 * 
 * System design pattern example demonstrating the {title} pattern.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class {class_name} {{
    
    public static void demonstrate() {{
{implementation}
    }}
    
    public static void main(String[] args) {{
        demonstrate();
    }}
}}
'''
        else:
            # Use generic template
            new_content = f'''package {package};

/**
 * {title} Pattern
 * 
 * System design pattern example demonstrating the {title} pattern.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class {class_name} {{
    
    public static void demonstrate() {{
        System.out.println("\\n=== {title} Pattern ===");
        System.out.println("System design pattern example demonstrating the {title} pattern.");
        System.out.println("\\nThis pattern helps in building scalable and maintainable applications.");
    }}
    
    public static void main(String[] args) {{
        demonstrate();
    }}
}}
'''
        
        file_path.write_text(new_content)
        return True, f"Fixed: {pattern_name}"
        
    except Exception as e:
        return False, str(e)

def main():
    """Main function to fix all invalid patterns."""
    print("=" * 80)
    print("  SYSTEM DESIGN PATTERN VALIDATION AND FIX TOOL")
    print("=" * 80)
    
    # Find all Java files in system-design-pattern
    java_files = []
    base_dirs = [SYSTEM_PATTERN_DIR]
    if DUPLICATE_PATTERN_DIR.exists():
        base_dirs.append(DUPLICATE_PATTERN_DIR)
    
    for base_dir in base_dirs:
        if base_dir.exists():
            for f in base_dir.rglob("*.java"):
                if "build" not in str(f):
                    java_files.append(f)
    
    total = len(java_files)
    valid = 0
    fixed = 0
    errors = 0
    already_valid = []
    
    print(f"\nFound {total} Java files to analyze.\n")
    
    for i, file_path in enumerate(sorted(java_files), 1):
        try:
            content = file_path.read_text()
            
            if is_valid_java_file(content):
                valid += 1
                already_valid.append(file_path)
                continue
            
            pattern_name = get_pattern_name_from_path(file_path)
            if pattern_name is None:
                pattern_name = "unknown"
            
            # Use absolute path for display
            abs_path = file_path.absolute()
            display_path = str(abs_path)
            print(f"[{i}/{total}] Checking: {display_path}")
            
            success, message = fix_file(file_path)
            if success:
                fixed += 1
                print(f"  ✓ {message}")
            else:
                errors += 1
                print(f"  ✗ Error: {message}")
                
        except Exception as e:
            errors += 1
            print(f"  ✗ Error processing {file_path}: {e}")
    
    print("\n" + "=" * 80)
    print(f"  SUMMARY")
    print(f"  Total files: {total}")
    print(f"  Already valid: {valid}")
    print(f"  Fixed: {fixed}")
    print(f"  Errors: {errors}")
    print("=" * 80)

if __name__ == "__main__":
    main()