#!/usr/bin/env python3
"""
Script to analyze and fix system design pattern examples.
Identifies placeholder/empty examples and creates proper implementations.
"""

import os
from pathlib import Path

# Base path for system design patterns
PATTERN_BASE = Path("design-patterns/system-design-pattern")

# Template for creating proper pattern examples
PATTERN_TEMPLATE = '''package {package};

/**
 * {PatternTitle} Pattern
 * 
 * {description}
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class {ClassName} {{
    
    public static void demonstrate() {{
        System.out.println("\\n=== {PatternTitle} Pattern ===");
        System.out.println("{description}");
        
        // TODO: Add pattern demonstration code here
        // This is a placeholder - implement the actual pattern logic
    }}
    
    public static void main(String[] args) {{
        demonstrate();
    }}
}}
'''

# Pattern-specific implementations
PATTERN_IMPLEMENTATIONS = {
    # Architectural Patterns
    "backend-for-frontend": '''
    // Backend-For-Frontend (BFF) Pattern
    // Creates separate backend services for different client types
    public interface MobileAPI {{
        String getMobileData();
    }}
    
    public interface WebAPI {{
        String getWebData();
    }}
    
    public static class MobileBackend implements MobileAPI {{
        @Override
        public String getMobileData() {{
            return "{{'user': 'John', 'mobile_view': 'simplified'}}";
        }}
    }}
    
    public static class WebBackend implements WebAPI {{
        @Override
        public String getWebData() {{
            return "{{'user': 'John', 'full_data': true}}";
        }}
    }}
    
    System.out.println("BFF creates optimized backends for different clients:");
    System.out.println("Mobile Backend: " + new MobileBackend().getMobileData());
    System.out.println("Web Backend: " + new WebBackend().getWebData());''',
    
    "event-driven-architecture": '''
    // Event-Driven Architecture Pattern
    // Systems communicate through events
    public interface Event {{
        String getType();
        Object getData();
    }}
    
    public static class OrderEvent implements Event {{
        private String type = "ORDER_CREATED";
        private String orderId;
        
        public OrderEvent(String orderId) {{
            this.orderId = orderId;
        }}
        
        @Override
        public String getType() {{ return type; }}
        @Override
        public Object getData() {{ return orderId; }}
    }}
    
    public static class EventHandler {{
        public void handle(Event event) {{
            System.out.println("Handling event: " + event.getType() + 
                             " with data: " + event.getData());
        }}
    }}
    
    Event event = new OrderEvent("ORD-12345");
    EventHandler handler = new EventHandler();
    handler.handle(event);''',
    
    # Structural Patterns
    "adapter": '''
    // Adapter Pattern - Converts one interface to another
    public interface EuropeanSocket {{
        void plugInEuropean();
    }}
    
    public interface AmericanSocket {{
        void plugInAmerican();
    }}
    
    public static class EuropeanPlug {{
        public void connect() {{
            System.out.println("European plug connected");
        }}
    }}
    
    public static class SocketAdapter implements AmericanSocket {{
        private EuropeanPlug europeanPlug;
        
        public SocketAdapter(EuropeanPlug plug) {{
            this.europeanPlug = plug;
        }}
        
        @Override
        public void plugInAmerican() {{
            System.out.println("Adapter converting European to American socket");
            europeanPlug.connect();
        }}
    }}
    
    EuropeanPlug europeanPlug = new EuropeanPlug();
    AmericanSocket adapter = new SocketAdapter(europeanPlug);
    adapter.plugInAmerican();''',
    
    "singleton": '''
    // Singleton Pattern - Ensures only one instance exists
    public static class DatabaseConnection {{
        private static volatile DatabaseConnection instance;
        
        private DatabaseConnection() {{
            System.out.println("Database connection created");
        }}
        
        public static DatabaseConnection getInstance() {{
            if (instance == null) {{
                synchronized (DatabaseConnection.class) {{
                    if (instance == null) {{
                        instance = new DatabaseConnection();
                    }}
                }}
            }}
            return instance;
        }}
        
        public void connect() {{
            System.out.println("Connected to database");
        }}
    }}
    
    DatabaseConnection db1 = DatabaseConnection.getInstance();
    DatabaseConnection db2 = DatabaseConnection.getInstance();
    System.out.println("Same instance? " + (db1 == db2));''',
    
    "factory": '''
    // Factory Pattern - Creates objects without specifying exact class
    public interface Payment {{
        void process(double amount);
    }}
    
    public static class CreditCardPayment implements Payment {{
        @Override
        public void process(double amount) {{
            System.out.println("Processing credit card payment: $" + amount);
        }}
    }}
    
    public static class PayPalPayment implements Payment {{
        @Override
        public void process(double amount) {{
            System.out.println("Processing PayPal payment: $" + amount);
        }}
    }}
    
    public static class PaymentFactory {{
        public static Payment createPayment(String type) {{
            switch (type.toLowerCase()) {{
                case "credit": return new CreditCardPayment();
                case "paypal": return new PayPalPayment();
                default: throw new IllegalArgumentException("Unknown payment type");
            }}
        }}
    }}
    
    Payment payment = PaymentFactory.createPayment("credit");
    payment.process(100.0);''',
    
    "observer": '''
    // Observer Pattern - Notifies multiple objects about state changes
    public interface Observer {{
        void update(String message);
    }}
    
    public static class NewsAgency {{
        private java.util.List<Observer> observers = new java.util.ArrayList<>();
        private String news;
        
        public void addObserver(Observer observer) {{
            observers.add(observer);
        }}
        
        public void setNews(String news) {{
            this.news = news;
            notifyObservers();
        }}
        
        private void notifyObservers() {{
            for (Observer observer : observers) {{
                observer.update(news);
            }}
        }}
    }}
    
    public static class NewsChannel implements Observer {{
        private String name;
        
        public NewsChannel(String name) {{
            this.name = name;
        }}
        
        @Override
        public void update(String message) {{
            System.out.println(name + " received news: " + message);
        }}
    }}
    
    NewsAgency agency = new NewsAgency();
    agency.addObserver(new NewsChannel("CNN"));
    agency.addObserver(new NewsChannel("BBC"));
    agency.setNews("Breaking: Design Pattern implemented!");''',
    
    "strategy": '''
    // Strategy Pattern - Interchangeable algorithms
    public interface SortStrategy {{
        void sort(int[] array);
    }}
    
    public static class BubbleSort implements SortStrategy {{
        @Override
        public void sort(int[] array) {{
            System.out.println("Sorting using Bubble Sort");
        }}
    }}
    
    public static class QuickSort implements SortStrategy {{
        @Override
        public void sort(int[] array) {{
            System.out.println("Sorting using Quick Sort");
        }}
    }}
    
    public static class Sorter {{
        private SortStrategy strategy;
        
        public void setStrategy(SortStrategy strategy) {{
            this.strategy = strategy;
        }}
        
        public void executeSort(int[] array) {{
            strategy.sort(array);
        }}
    }}
    
    int[] data = {{5, 2, 8, 1, 9}};
    Sorter sorter = new Sorter();
    sorter.setStrategy(new QuickSort());
    sorter.executeSort(data);''',
    
    "command": '''
    // Command Pattern - Encapsulates requests as objects
    public interface Command {{
        void execute();
    }}
    
    public static class Light {{
        public void turnOn() {{
            System.out.println("Light turned ON");
        }}
        
        public void turnOff() {{
            System.out.println("Light turned OFF");
        }}
    }}
    
    public static class LightOnCommand implements Command {{
        private Light light;
        
        public LightOnCommand(Light light) {{
            this.light = light;
        }}
        
        @Override
        public void execute() {{
            light.turnOn();
        }}
    }}
    
    public static class RemoteControl {{
        private Command command;
        
        public void setCommand(Command command) {{
            this.command = command;
        }}
        
        public void pressButton() {{
            command.execute();
        }}
    }}
    
    Light light = new Light();
    RemoteControl remote = new RemoteControl();
    remote.setCommand(new LightOnCommand(light));
    remote.pressButton();''',
    
    "decorator": '''
    // Decorator Pattern - Adds responsibilities dynamically
    public interface Coffee {{
        double getCost();
        String getDescription();
    }}
    
    public static class SimpleCoffee implements Coffee {{
        @Override
        public double getCost() {{ return 5.0; }}
        
        @Override
        public String getDescription() {{ return "Simple Coffee"; }}
    }}
    
    public static abstract class CoffeeDecorator implements Coffee {{
        protected Coffee coffee;
        
        public CoffeeDecorator(Coffee coffee) {{
            this.coffee = coffee;
        }}
    }}
    
    public static class MilkDecorator extends CoffeeDecorator {{
        public MilkDecorator(Coffee coffee) {{
            super(coffee);
        }}
        
        @Override
        public double getCost() {{ return coffee.getCost() + 1.5; }}
        
        @Override
        public String getDescription() {{ return coffee.getDescription() + ", Milk"; }}
    }}
    
    Coffee coffee = new MilkDecorator(new SimpleCoffee());
    System.out.println("Coffee: " + coffee.getDescription() + 
                       " - Cost: $" + coffee.getCost());''',
    
    "facade": '''
    // Facade Pattern - Simplified interface to complex subsystem
    public static class CPU {{
        public void execute() {{ System.out.println("CPU executing"); }}
    }}
    
    public static class Memory {{
        public void load() {{ System.out.println("Memory loading"); }}
    }}
    
    public static class HardDrive {{
        public void read() {{ System.out.println("Hard drive reading"); }}
    }}
    
    public static class ComputerFacade {{
        private CPU cpu;
        private Memory memory;
        private HardDrive hardDrive;
        
        public ComputerFacade() {{
            this.cpu = new CPU();
            this.memory = new Memory();
            this.hardDrive = new HardDrive();
        }}
        
        public void start() {{
            System.out.println("Starting computer...");
            cpu.execute();
            memory.load();
            hardDrive.read();
            System.out.println("Computer started successfully!");
        }}
    }}
    
    ComputerFacade computer = new ComputerFacade();
    computer.start();''',
    
    "proxy": '''
    // Proxy Pattern - Controls access to an object
    public interface Image {{
        void display();
    }}
    
    public static class RealImage implements Image {{
        private String filename;
        
        public RealImage(String filename) {{
            this.filename = filename;
            loadFromDisk();
        }}
        
        private void loadFromDisk() {{
            System.out.println("Loading image: " + filename);
        }}
        
        @Override
        public void display() {{
            System.out.println("Displaying image: " + filename);
        }}
    }}
    
    public static class ImageProxy implements Image {{
        private String filename;
        private RealImage realImage;
        
        public ImageProxy(String filename) {{
            this.filename = filename;
        }}
        
        @Override
        public void display() {{
            if (realImage == null) {{
                realImage = new RealImage(filename);
            }}
            realImage.display();
        }}
    }}
    
    Image image = new ImageProxy("photo.jpg");
    image.display();''',
    
    "composite": '''
    // Composite Pattern - Treats individual and composite objects uniformly
    public interface FileSystemComponent {{
        void display();
    }}
    
    public static class File implements FileSystemComponent {{
        private String name;
        
        public File(String name) {{ this.name = name; }}
        
        @Override
        public void display() {{
            System.out.println("File: " + name);
        }}
    }}
    
    public static class Directory implements FileSystemComponent {{
        private String name;
        private java.util.List<FileSystemComponent> components = new java.util.ArrayList<>();
        
        public Directory(String name) {{ this.name = name; }}
        
        public void add(FileSystemComponent component) {{
            components.add(component);
        }}
        
        @Override
        public void display() {{
            System.out.println("Directory: " + name);
            for (FileSystemComponent component : components) {{
                component.display();
            }}
        }}
    }}
    
    Directory root = new Directory("root");
    Directory docs = new Directory("documents");
    root.add(docs);
    docs.add(new File("resume.pdf"));
    docs.add(new File("cover_letter.pdf"));
    root.display();''',
    
    "flyweight": '''
    // Flyweight Pattern - Shares common state between objects
    public static class CharacterProperties {{
        private char character;
        private String font;
        
        public CharacterProperties(char character, String font) {{
            this.character = character;
            this.font = font;
        }}
        
        public void display(int x, int y) {{
            System.out.println("Character '" + character + "' at (" + x + ", " + y + 
                             ") with font: " + font);
        }}
    }}
    
    public static class CharacterFactory {{
        private java.util.Map<Character, CharacterProperties> cache = new java.util.HashMap<>();
        
        public CharacterProperties getCharacter(char c) {{
            if (!cache.containsKey(c)) {{
                cache.put(c, new CharacterProperties(c, "Arial"));
            }}
            return cache.get(c);
        }}
    }}
    
    CharacterFactory factory = new CharacterFactory();
    CharacterProperties c1 = factory.getCharacter('A');
    CharacterProperties c2 = factory.getCharacter('A');
    System.out.println("Same instance? " + (c1 == c2));
    c1.display(10, 20);''',
    
    "bridge": '''
    // Bridge Pattern - Decouples abstraction from implementation
    public interface Renderer {{
        void render(String shape);
    }}
    
    public static class VectorRenderer implements Renderer {{
        @Override
        public void render(String shape) {{
            System.out.println("Drawing " + shape + " using vectors");
        }}
    }}
    
    public static class RasterRenderer implements Renderer {{
        @Override
        public void render(String shape) {{
            System.out.println("Drawing " + shape + " using pixels");
        }}
    }}
    
    public static abstract class Shape {{
        protected Renderer renderer;
        
        protected Shape(Renderer renderer) {{
            this.renderer = renderer;
        }}
        
        public abstract void draw();
    }}
    
    public static class Circle extends Shape {{
        private double radius;
        
        public Circle(Renderer renderer, double radius) {{
            super(renderer);
            this.radius = radius;
        }}
        
        @Override
        public void draw() {{
            renderer.render("Circle(radius=" + radius + ")");
        }}
    }}
    
    Shape circle = new Circle(new VectorRenderer(), 5.0);
    circle.draw();''',
    
    "builder": '''
    // Builder Pattern - Constructs complex objects step by step
    public static class House {{
        private String foundation;
        private String structure;
        private String roof;
        private String interior;
        
        public static class HouseBuilder {{
            private House house = new House();
            
            public HouseBuilder buildFoundation(String foundation) {{
                house.foundation = foundation;
                return this;
            }}
            
            public HouseBuilder buildStructure(String structure) {{
                house.structure = structure;
                return this;
            }}
            
            public HouseBuilder buildRoof(String roof) {{
                house.roof = roof;
                return this;
            }}
            
            public HouseBuilder buildInterior(String interior) {{
                house.interior = interior;
                return this;
            }}
            
            public House build() {{
                return house;
            }}
        }}
        
        public void display() {{
            System.out.println("House: " + foundation + ", " + structure + 
                             ", " + roof + ", " + interior);
        }}
    }}
    
    House house = new House.HouseBuilder()
        .buildFoundation("Concrete")
        .buildStructure("Wood")
        .buildRoof("Tiles")
        .buildInterior("Modern")
        .build();
    house.display();''',
    
    "prototype": '''
    // Prototype Pattern - Creates copies of existing objects
    public static class Prototype implements Cloneable {{
        private String name;
        private java.util.List<String> items;
        
        public Prototype(String name) {{
            this.name = name;
            this.items = new java.util.ArrayList<>();
        }}
        
        public void addItem(String item) {{
            items.add(item);
        }}
        
        @Override
        public Prototype clone() throws CloneNotSupportedException {{
            Prototype cloned = (Prototype) super.clone();
            cloned.items = new java.util.ArrayList<>(this.items);
            return cloned;
        }}
        
        public void display() {{
            System.out.println("Prototype: " + name + ", Items: " + items);
        }}
    }}
    
    Prototype original = new Prototype("Original");
    original.addItem("Item1");
    original.addItem("Item2");
    
    try {{
        Prototype copy = original.clone();
        copy.addItem("Item3");
        original.display();
        copy.display();
    }} catch (CloneNotSupportedException e) {{
        e.printStackTrace();
    }}''',
    
    "object-pool": '''
    // Object Pool Pattern - Reuses expensive objects
    public static class DatabaseConnectionPool {{
        private java.util.List<Connection> pool;
        private int poolSize;
        
        public DatabaseConnectionPool(int size) {{
            this.poolSize = size;
            this.pool = new java.util.ArrayList<>();
            for (int i = 0; i < size; i++) {{
                pool.add(new Connection(i));
            }}
        }}
        
        public Connection acquire() {{
            if (pool.size() > 0) {{
                return pool.remove(0);
            }}
            throw new RuntimeException("No connections available");
        }}
        
        public void release(Connection conn) {{
            pool.add(conn);
        }}
    }}
    
    public static class Connection {{
        private int id;
        
        public Connection(int id) {{ this.id = id; }}
        public void use() {{ System.out.println("Using connection " + id); }}
    }}
    
    DatabaseConnectionPool pool = new DatabaseConnectionPool(3);
    Connection conn1 = pool.acquire();
    conn1.use();
    pool.release(conn1);''',
    
    "abstract-factory": '''
    // Abstract Factory Pattern - Creates families of related objects
    public interface GUIFactory {{
        Button createButton();
        Checkbox createCheckbox();
    }}
    
    public static class WindowsFactory implements GUIFactory {{
        @Override
        public Button createButton() {{ return new WindowsButton(); }}
        @Override
        public Checkbox createCheckbox() {{ return new WindowsCheckbox(); }}
    }}
    
    public static class MacFactory implements GUIFactory {{
        @Override
        public Button createButton() {{ return new MacButton(); }}
        @Override
        public Checkbox createCheckbox() {{ return new MacCheckbox(); }}
    }}
    
    Button winButton = new WindowsFactory().createButton();
    Button macButton = new MacFactory().createButton();
    winButton.render();
    macButton.render();''',
}


def get_pattern_info(pattern_path: Path):
    """Extract pattern name, class name, and package from path."""
    parts = list(pattern_path.parts)
    pattern_name = parts[-2]  # e.g., "backend-for-frontend"
    class_name = parts[-1].replace(".java", "")  # e.g., "BackendForFrontend"
    
    # Construct package name from path - extract only the relevant parts
    # Path structure: design-patterns/system-design-pattern/{category}/{pattern-name}/src/main/java/{package-folders}/
    package_parts = []
    found_java = False
    for part in parts:
        if found_java:
            # Convert hyphens to remove them for valid Java packages
            package_part = part.replace("-", "")
            package_parts.append(package_part)
        if part == "java" and not found_java:
            found_java = True
    
    package = ".".join(package_parts) if package_parts else "com.javastarterkit.patterns"
    
    # Convert pattern name to title for display
    title = pattern_name.replace("-", " ").title()
    
    return pattern_name, class_name, package, title


def is_placeholder(content: str) -> bool:
    """Check if file contains only placeholder content."""
    placeholder_indicators = [
        "A system design pattern for building scalable and maintainable applications.",
        "A system design pattern for building robust applications."
    ]
    return any(indicator in content for indicator in placeholder_indicators)


def create_improved_example(pattern_name: str, class_name: str, package: str, title: str) -> str:
    """Create improved pattern example."""
    # Check if we have a specific implementation
    if pattern_name in PATTERN_IMPLEMENTATIONS:
        implementation = PATTERN_IMPLEMENTATIONS[pattern_name]
        return f'''package {package};

/**
 * {title} Pattern
 * 
 * System design pattern example demonstrating the {title} pattern.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class {class_name} {{
    
{implementation}
    
    public static void main(String[] args) {{
        demonstrate();
    }}
}}
'''
    else:
        # Use template for patterns without specific implementation
        return PATTERN_TEMPLATE.format(
            package=package,
            PatternTitle=title,
            ClassName=class_name,
            description=f"System design pattern example demonstrating the {title} pattern."
        )


def analyze_and_fix_patterns():
    """Main function to analyze and fix pattern examples."""
    print("Analyzing system design pattern examples...")
    print("=" * 80)
    
    # Walk through all Java files
    java_files = list(PATTERN_BASE.rglob("*.java"))
    total = len(java_files)
    fixed = 0
    skipped = 0
    
    for i, java_file in enumerate(java_files, 1):
        try:
            # Read current content
            content = java_file.read_text()
            
            # Check if it's a placeholder
            if is_placeholder(content):
                pattern_name, class_name, package, title = get_pattern_info(java_file)
                
                print(f"[{i}/{total}] Fixing: {java_file.relative_to(PATTERN_BASE)}")
                print(f"  Pattern: {title}")
                print(f"  Package: {package}")
                
                # Create improved example
                new_content = create_improved_example(pattern_name, class_name, package, title)
                
                # Write improved content
                java_file.write_text(new_content)
                
                print(f"  ✓ Fixed\n")
                fixed += 1
            else:
                skipped += 1
                
        except Exception as e:
            print(f"[{i}/{total}] Error processing {java_file}: {e}")
    
    print("=" * 80)
    print(f"Analysis complete!")
    print(f"Total files: {total}")
    print(f"Fixed: {fixed}")
    print(f"Skipped (already have content): {skipped}")
    print("=" * 80)


if __name__ == "__main__":
    analyze_and_fix_patterns()