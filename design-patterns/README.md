# System Design Patterns - Composite Build

This is a **Gradle composite build** that includes comprehensive examples of **191 system design patterns** and **41 system design theory topics** with working Java implementations.

## Project Structure

```
design-patterns/
├── settings.gradle.kts          # Composite build configuration
├── build.gradle.kts             # Root build configuration
├── system-design-theory/        # 41 theoretical topics
│   ├── ip/
│   ├── osi-model/
│   ├── dns/
│   ├── load-balancing/
│   ├── caching/
│   ├── databases-dbms/
│   └── ... (41 total modules)
└── system-design-pattern/       # 191 pattern implementations
    ├── solid-principles/        #   5 patterns
    ├── structural/              #  41 patterns
    ├── creational/              #  14 patterns
    ├── concurrency/             #  20 patterns
    ├── behavioral/              #  38 patterns
    ├── integration/             #   3 patterns
    ├── microservices/           #  11 patterns
    ├── testing/                 #   3 patterns
    ├── performance-optimization/#   3 patterns
    ├── functional/              #   7 patterns
    ├── resilience/              #  10 patterns
    ├── architectural/           #  20 patterns
    ├── messaging/               #   2 patterns
    ├── data-access/             #  11 patterns
    └── resource-management/     #   3 patterns
```

## Pattern Example Structure

Each pattern implementation follows a consistent structure:

```java
package com.javastarterkit.patterns.<category>.<pattern>;

/**
 * PatternName Pattern
 * 
 * Description of the pattern.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class PatternName {
    
    public static void demonstrate() {
        // Pattern demonstration code
        // Includes inner classes, interfaces, and usage examples
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}
```

Each pattern example:
- Is a **single self-contained Java file** with no external dependencies
- Contains **inner static classes/interfaces** that implement the pattern
- Has a `demonstrate()` method that runs the pattern example
- Has a `main()` method that calls `demonstrate()`
- Prints output showing the pattern in action

## Integration with Main Project

This composite build is automatically included in the root project via `settings.gradle.kts`:

```kotlin
includeBuild("design-patterns")
```

## Usage

### Build All Patterns
```bash
cd /Users/avinash/Documents/development/java-starter-kit-final
./gradlew :system-design-pattern:build
```

### Build Specific Pattern Category
```bash
# Creational patterns
./gradlew :system-design-pattern:creational:build

# Structural patterns
./gradlew :system-design-pattern:structural:build

# Behavioral patterns
./gradlew :system-design-pattern:behavioral:build

# Architectural patterns
./gradlew :system-design-pattern:architectural:build
```

### Build Individual Pattern
```bash
# Singleton pattern
./gradlew :system-design-pattern:creational:singleton:build

# Observer pattern
./gradlew :system-design-pattern:behavioral:observer:build

# Factory pattern
./gradlew :system-design-pattern:creational:factory:build
```

### Run a Pattern Example
```bash
# Run any pattern by executing its main class
./gradlew :system-design-pattern:creational:singleton:run
```

### List All Available Modules
```bash
./gradlew projects
```

## Java Version

All patterns are configured for **Java 25** with modern language features including:
- Records
- Sealed classes
- Pattern matching
- Virtual threads
- Structured concurrency

## Pattern Categories

### Creational Patterns (14)
Patterns that deal with object creation mechanisms.

| Pattern | Description |
|---------|-------------|
| Abstract Factory | Creates families of related objects without specifying concrete classes |
| Builder | Constructs complex objects step by step |
| Dependency Injection | Passes dependencies from outside rather than creating them internally |
| Factory Method | Defines interface for creating objects, lets subclasses decide which class to instantiate |
| Factory | Creates objects without specifying exact class |
| Factory Kit | Flexible factory configurable with different builders |
| Monostate | Shares state across instances via static fields |
| Multiton | Manages a named set of instances |
| Object Pool | Manages reusable objects to avoid expensive creation |
| Prototype | Creates new objects by copying existing ones |
| Registry | Provides centralized location for accessing objects |
| Singleton | Ensures a class has only one instance |
| Step Builder | Guides object construction through predefined steps |
| Type Object | Allows creation of flexible type systems at runtime |

### Structural Patterns (41)
Patterns that compose objects and classes into larger structures.

| Pattern | Description |
|---------|-------------|
| Adapter | Converts incompatible interfaces so they can work together |
| Bridge | Decouples abstraction from implementation so they can vary independently |
| Business Delegate | Decouples presentation from business logic |
| Builder | Constructs complex objects step by step |
| Command | Encapsulates requests as objects |
| Component | Allows individual objects to be composed into larger structures |
| Composite | Treats individual and composite objects uniformly |
| Composite Entity | Manages a group of related objects as a single entity |
| Composite View | Builds views from smaller, reusable components |
| Converter | Converts between different data formats |
| Curiously Recurring Template Pattern | Template where a class passes itself as a parameter |
| Data Access Object | Abstracts database operations behind a clean interface |
| Data Transfer Object | Transfers data between subsystems without exposing internals |
| Decorator | Adds responsibilities to objects dynamically |
| Domain Model | Rich domain model with behavior and business logic |
| Dynamic Proxy | Creates proxies dynamically at runtime |
| Extension Objects | Adds functionality to objects through extension interfaces |
| Facade | Provides simplified interface to a complex subsystem |
| Factory | Creates objects without specifying exact class |
| Flyweight | Shares common state between objects to save memory |
| Iterator | Provides sequential access to elements without exposing structure |
| Marker Interface | Uses empty interfaces to mark classes with metadata |
| Mediator | Reduces coupling by making objects communicate through a mediator |
| Observer | Notifies multiple objects about state changes |
| Parameter Object | Groups method parameters into a single object |
| Private Class Data | Restricts access to class data |
| Proxy | Controls access to an object, acting as a placeholder |
| Role Object | Allows objects to adapt to different roles |
| Separated Interface | Separates interface definition from implementation |
| Servant | Provides behavior to a group of classes |
| Service Locator | Provides centralized service lookup |
| Sidecar | Attaches a helper component to a main application |
| Singleton | Ensures a class has only one instance |
| Spatial Partition | Organizes spatial objects for efficient querying |
| Special Case | Handles special cases with polymorphic objects |
| Strangler | Incrementally replaces legacy systems |
| Strategy | Interchangeable algorithms selected at runtime |
| Template Method | Defines algorithm skeleton, letting subclasses fill in steps |
| Twin | Provides a way to have multiple inheritance |
| Value Object | Immutable objects compared by their values |
| Virtual Proxy | Delays loading of expensive objects |

### Behavioral Patterns (38)
Patterns that identify communication patterns between objects.

| Pattern | Description |
|---------|-------------|
| Acyclic Visitor | Visitor pattern without cyclic dependencies |
| Bytecode | Implements behavior via bytecode instructions |
| Chain of Responsibility | Passes request through a chain of handlers |
| Client Session | Manages client state across multiple requests |
| Collecting Parameter | Collects parameters across multiple method calls |
| Command | Encapsulates requests as objects |
| Commander | Manages distributed transaction execution |
| Context Object | Encapsulates system context data |
| Data Mapper | Maps between objects and data stores |
| Delegation | Delegates work to helper objects |
| Dirty Flag | Tracks whether an object has been modified |
| Double Buffer | Uses two buffers to prevent visual artifacts |
| Double Dispatch | Dispatches calls based on runtime types of two objects |
| Execute Around | Executes boilerplate code around business logic |
| Feature Toggle | Enables/disables features at runtime |
| Filterer | Filters collections based on criteria |
| Fluent Interface | Provides readable, chainable API |
| Game Loop | Controls game timing and rendering |
| Health Check | Monitors system component health |
| Identity Map | Ensures each object is loaded only once |
| Interpreter | Interprets a language by defining grammar rules |
| Iterator | Provides sequential access to elements |
| Mediator | Reduces coupling between communicating objects |
| Memento | Captures and restores object state |
| Mute Idiom | Suppresses exceptions for cleaner code |
| Null Object | Provides default object that does nothing |
| Notification | Collects notification messages |
| Observer | Notifies dependents of state changes |
| Partial Response | Filters response data |
| Pipeline | Processes data through a sequence of stages |
| Property | Manages dynamic properties on objects |
| Specification | Combines business rules using boolean logic |
| State | Changes behavior when internal state changes |
| Strategy | Interchangeable algorithms |
| Subclass Sandbox | Provides controlled environment for subclasses |
| Template Method | Defines algorithm skeleton |
| Update Method | Updates game objects each frame |
| Visitor | Separates algorithms from objects they operate on |

### Architectural Patterns (20)
Patterns that define high-level system architecture.

| Pattern | Description |
|---------|-------------|
| Backend for Frontend (BFF) | Creates separate backends for each client type |
| Command Query Responsibility Segregation (CQRS) | Separates read and write operations |
| Composable Architecture | Composes features from independent components |
| Event-Driven Architecture | Systems communicate through events |
| Event Sourcing | Stores state changes as a sequence of events |
| Flux | Unidirectional data flow architecture |
| Front Controller | Centralizes request handling |
| Hexagonal Architecture | Isolates core logic through ports and adapters |
| Intercepting Filter | Pre-processes and post-processes requests |
| Layered Architecture | Organizes code into layers |
| Microservices Aggregator | Aggregates data from multiple services |
| Model-View-Controller (MVC) | Separates data, UI, and logic |
| Model-View-Intent (MVI) | Unidirectional data flow with intents |
| Model-View-Presenter (MVP) | Presenter mediates between Model and View |
| Model-View-ViewModel (MVVM) | Separates UI from business logic with data binding |
| Naked Objects | Domain objects automatically exposed as UI |
| Page Controller | Each page has its own controller |
| Presentation Model | Separates UI state from the view |
| Service Layer | Defines the application boundary with a service layer |
| Service to Worker | Separates request processing from view management |

### Concurrency Patterns (20)
Patterns that handle multi-threaded and concurrent execution.

| Pattern | Description |
|---------|-------------|
| Active Object | Decouples method execution from invocation |
| Async Method Invocation | Invokes methods asynchronously |
| Balking | Only executes action when in appropriate state |
| Double Checked Locking | Reduces lock acquisition overhead |
| Event-Based Asynchronous | Handles events asynchronously |
| Event Queue | Manages event processing order |
| Fan-Out/Fan-In | Distributes and aggregates work |
| Guarded Suspension | Suspends execution until condition is met |
| Half-Sync/Half-Async | Separates sync and async processing |
| Leader Election | Elects a leader among nodes |
| Leader Followers | Optimizes thread usage with leader/follower pattern |
| Lockable Object | Provides lock mechanism for objects |
| Master-Worker | Distributes work among worker threads |
| Monitor | Synchronizes access to shared resources |
| Poison Pill | Signals shutdown of consumer threads |
| Producer-Consumer | Separates production and consumption of data |
| Promise | Represents eventual result of async operation |
| Reactor | Handles service requests from multiple sources |
| Read-Write Lock | Allows concurrent reads, exclusive writes |
| Thread Pool | Manages a pool of reusable threads |

### Resilience Patterns (10)
Patterns that make systems resilient to failures.

| Pattern | Description |
|---------|-------------|
| Bulkheads | Isolates resources to prevent cascading failures |
| Circuit Breaker | Detects failures and prevents cascading |
| Fallbacks | Provides alternative response when service fails |
| Graceful Degradation | Provides reduced functionality when service is down |
| Queue-Based Load Leveling | Smooths out workload spikes |
| Rate Limiting | Controls rate of requests |
| Retry | Automatically retries failed operations |
| Saga | Manages distributed transactions with compensation |
| Timeouts | Limits wait time for service responses |
| Tolerant Reader | Reads only understood fields, ignoring unknown data |

### Microservices Patterns (11)
Patterns for building microservice architectures.

| Pattern | Description |
|---------|-------------|
| API Gateway | Single entry point for client requests |
| Config Server | Centralizes configuration management |
| Database per Service | Each service has its own database |
| Health Monitoring | Monitors health of system components |
| Log Aggregation | Centralizes logs from multiple services |
| Master Service Decomposition | Central orchestrator manages distributed services |
| Monitoring | Tracks system performance and errors |
| Observability | Provides visibility through logs, metrics, traces |
| Service Discovery | Enables services to find each other dynamically |
| Service Mesh | Infrastructure layer for service-to-service communication |
| Service Registry | Maintains registry of available service instances |

### Data Access Patterns (11)
Patterns for data access and persistence.

| Pattern | Description |
|---------|-------------|
| Metadata Mapping | Maps database metadata to objects |
| Optimistic Offline Lock | Prevents conflicts using version numbers |
| Repository | Mediates between domain and data mapping layers |
| Serialized Entity | Serializes entities for storage |
| Serialized LOB | Stores large objects as serialized data |
| Sharding | Horizontal partitioning across databases |
| Single Table Inheritance | Stores inheritance hierarchy in one table |
| Table Module | Single instance handles business logic for all rows |
| Transaction Script | Organizes business logic by transaction |
| Unit of Work | Groups operations into a single transaction |
| Version Number | Manages concurrent access with version numbers |

### Integration Patterns (3)
Patterns for integrating with external systems.

| Pattern | Description |
|---------|-------------|
| Ambassador | Helper service handling retries, logging, latency |
| Anti-Corruption Layer | Protects domain from legacy system contamination |
| Gateway | Abstracts access to external services or APIs |

### Functional Patterns (7)
Patterns that leverage functional programming concepts.

| Pattern | Description |
|---------|-------------|
| Callback | Passes executable code as an argument |
| Collection Pipeline | Chains collection operations in sequence |
| Combinator | Combines small functions into larger ones |
| Currying | Transforms multi-argument functions into chains |
| Function Composition | Combines simple functions to build complex ones |
| Monad | Wraps values and provides composition operations |
| Trampoline | Provides stack-safe recursion |

### Messaging Patterns (2)
Patterns for message-based communication.

| Pattern | Description |
|---------|-------------|
| Data Bus | Centralized event distribution system |
| Event Aggregator | Collects events from multiple sources |

### Testing Patterns (3)
Patterns for automated testing.

| Pattern | Description |
|---------|-------------|
| Arrange-Act-Assert (AAA) | Structures tests into three clear phases |
| Object Mother | Creates pre-configured test objects |
| Page Object | Encapsulates page details in test automation |

### Performance Optimization Patterns (3)
Patterns for improving system performance.

| Pattern | Description |
|---------|-------------|
| Caching | Stores frequently accessed data for fast retrieval |
| Data Locality | Organizes data for optimal cache performance |
| Lazy Loading | Defers object creation until needed |

### Resource Management Patterns (3)
Patterns for managing system resources.

| Pattern | Description |
|---------|-------------|
| Resource Acquisition Is Initialization (RAII) | Ties resource lifecycle to object lifetime |
| Server Session | Manages user state across multiple requests |
| Throttling | Limits the rate of operations from a client |

### S.O.L.I.D Principles (5)
Fundamental design principles for object-oriented programming.

| Principle | Description |
|-----------|-------------|
| Single Responsibility | A class should have only one reason to change |
| Open/Closed | Open for extension, closed for modification |
| Liskov Substitution | Subtypes must be substitutable for base types |
| Interface Segregation | Clients should not depend on interfaces they don't use |
| Dependency Inversion | Depend on abstractions, not concrete implementations |

## Statistics

- **Total System Design Theory Topics**: 41
- **Total Pattern Implementations**: 191
- **Total Java Examples**: 232+ working examples
- **Java Version**: 25
- **Build System**: Gradle with composite builds

## Build Features

- ✅ Java 25 compilation
- ✅ JUnit 5.11.4 testing framework
- ✅ Modular architecture with composite builds
- ✅ Type-safe accessors via libs.versions.toml
- ✅ Standardized build configuration across all modules
- ✅ No external dependencies required (pure Java implementations)
- ✅ Self-contained single-file examples (each pattern in one Java file)
- ✅ Consistent `demonstrate()` + `main()` structure across all patterns

## Troubleshooting

### Build Failures
If you encounter build failures:
1. Ensure Java 25 is installed: `java -version`
2. Clean and rebuild: `./gradlew clean build`
3. Check for compilation errors in specific modules

### Missing Dependencies
All patterns use standard Java libraries. No external dependencies are required for most patterns.

## Contributing

When adding new patterns:
1. Follow the existing package structure: `com.javastarterkit.patterns.<category>.<pattern>`
2. Include comprehensive JavaDoc comments
3. Use the `demonstrate()` + `main()` pattern structure
4. Add unit tests demonstrating the pattern
5. Update `settings.gradle.kts` if adding new modules
6. Update this README with the new pattern

## License

Part of the java-starter-kit project.