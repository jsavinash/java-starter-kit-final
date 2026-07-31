#!/usr/bin/env python3
"""
Generate Java source code examples for ALL system design patterns and theory modules.
This script creates proper Java source files for every module defined in settings.gradle.kts.
"""
import os
import re

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
SETTINGS_FILE = os.path.join(BASE_DIR, "settings.gradle.kts")

def parse_modules(settings_file):
    """Parse all include() statements from settings.gradle.kts."""
    modules = []
    with open(settings_file, 'r') as f:
        content = f.read()
    
    pattern = r'include\(":([^"]+)"\)'
    matches = re.findall(pattern, content)
    
    for match in matches:
        dir_path = match.replace(':', '/')
        modules.append((match, dir_path))
    
    return modules

def to_camel_case(name):
    """Convert kebab-case to CamelCase."""
    return ''.join(word.capitalize() for word in name.replace('-', '_').split('_'))

def to_package_name(name):
    """Convert kebab-case to Java package name (no hyphens)."""
    return name.replace('-', '').replace('_', '')

def write_java_file(module_path, dir_path):
    """Write a Java source file for a module."""
    # Extract the module name (last segment)
    parts = module_path.split(':')
    module_name = parts[-1]
    
    # Determine package structure
    if module_path.startswith("system-design-theory"):
        # system-design-theory/ip -> com.javastarterkit.patterns.theory.ip
        theory_name = module_name.replace('-', '').replace('_', '')
        package = f"com.javastarterkit.patterns.theory.{theory_name}"
        class_name = to_camel_case(module_name) + "Example"
        category = "theory"
    else:
        # system-design-pattern/structural/adapter -> com.javastarterkit.patterns.structural.adapter
        # Remove the "system-design-pattern" prefix
        rel_parts = parts[2:]  # e.g., ['structural', 'adapter']
        category = rel_parts[0].replace('-', '')
        pattern_name = rel_parts[-1].replace('-', '')
        package = f"com.javastarterkit.patterns.{category}.{pattern_name}"
        class_name = to_camel_case(rel_parts[-1]) + "Example"
    
    # Build the source directory path
    src_dir = os.path.join(BASE_DIR, dir_path, "src/main/java", package.replace('.', '/'))
    os.makedirs(src_dir, exist_ok=True)
    
    file_path = os.path.join(src_dir, f"{class_name}.java")
    
    if os.path.exists(file_path):
        return False  # Already exists
    
    # Generate content based on module type
    content = generate_content(module_path, module_name, class_name, package, category)
    
    with open(file_path, 'w') as f:
        f.write(content)
    
    return True

def generate_content(module_path, module_name, class_name, package, category):
    """Generate Java source content for a module."""
    
    # Theory modules
    if category == "theory":
        return generate_theory_content(module_name, class_name, package)
    
    # Pattern modules by category
    category_map = {
        "solidprinciples": generate_solid_content,
        "structural": generate_structural_content,
        "creational": generate_creational_content,
        "concurrency": generate_concurrency_content,
        "behavioral": generate_behavioral_content,
        "integration": generate_integration_content,
        "microservices": generate_microservices_content,
        "testing": generate_testing_content,
        "performanceoptimization": generate_performance_content,
        "functional": generate_functional_content,
        "resilience": generate_resilience_content,
        "architectural": generate_architectural_content,
        "messaging": generate_messaging_content,
        "dataaccess": generate_dataaccess_content,
        "resourcemanagement": generate_resource_content,
    }
    
    generator = category_map.get(category, generate_generic_pattern_content)
    return generator(module_name, class_name, package)

def generate_theory_content(module_name, class_name, package):
    """Generate content for theory modules."""
    display_name = module_name.replace('-', ' ').title()
    
    return f"""package {package};

/**
 * System Design Theory: {display_name}
 * 
 * This module covers the fundamental concepts of {display_name}.
 * Each theory module provides a practical example demonstrating key concepts.
 */
public class {class_name} {{
    
    private final String description;
    
    public {class_name}(String description) {{
        this.description = description;
    }}
    
    public String getDescription() {{
        return description;
    }}
    
    public void demonstrate() {{
        System.out.println("=== {display_name} ===");
        System.out.println("Description: " + description);
        System.out.println("This module covers core system design theory concepts.");
    }}
    
    public static void main(String[] args) {{
        {class_name} example = new {class_name}(
            "Practical example of {display_name} concepts in system design"
        );
        example.demonstrate();
    }}
}}
"""

def generate_solid_content(module_name, class_name, package):
    """Generate SOLID principle examples."""
    principles = {
        "single-responsibility-principle": ("Single Responsibility Principle", "A class should have only one reason to change"),
        "open-close-principle": ("Open/Closed Principle", "Classes should be open for extension but closed for modification"),
        "liskov-substitution-principle": ("Liskov Substitution Principle", "Subtypes must be substitutable for their base types"),
        "interface-segregation-principle": ("Interface Segregation Principle", "Clients should not be forced to depend on interfaces they do not use"),
        "dependency-inversion-principle": ("Dependency Inversion Principle", "Depend on abstractions, not on concretions"),
    }
    
    name, desc = principles.get(module_name, (module_name.replace('-', ' ').title(), ""))
    
    return f"""package {package};

/**
 * {name} (SOLID)
 * {desc}
 */
public class {class_name} {{
    
    public static void demonstrate() {{
        System.out.println("=== {name} ===");
        System.out.println("Principle: {desc}");
    }}
    
    public static void main(String[] args) {{
        demonstrate();
    }}
}}
"""

def generate_structural_content(module_name, class_name, package):
    """Generate structural pattern examples."""
    patterns = {
        "adapter": ("Adapter", "Allows incompatible interfaces to work together"),
        "bridge": ("Bridge", "Decouples abstraction from implementation"),
        "business-delegate": ("Business Delegate", "Reduces coupling between presentation and business tiers"),
        "component": ("Component", "Base interface for composite structures"),
        "composite-entity": ("Composite Entity", "Represents a graph of objects as a single entity"),
        "composite-view": ("Composite View", "Combines multiple views into a single composite view"),
        "composite": ("Composite", "Composes objects into tree structures"),
        "data-access-object": ("Data Access Object", "Abstracts data persistence operations"),
        "data-transfer-object": ("Data Transfer Object", "Transfers data between subsystems"),
        "converter": ("Converter", "Converts between different object representations"),
        "curiously-recurring-template-pattern": ("CRTP", "Passes derived class as template parameter to base"),
        "decorator": ("Decorator", "Attaches additional responsibilities dynamically"),
        "domain-model": ("Domain Model", "Incorporates business behavior with data"),
        "dynamic-proxy": ("Dynamic Proxy", "Creates proxy instances at runtime"),
        "extension-objects": ("Extension Objects", "Adds interfaces to classes through extension objects"),
        "facade": ("Facade", "Provides simplified interface to complex subsystem"),
        "flyweight": ("Flyweight", "Shares objects to support large numbers efficiently"),
        "marker-interface": ("Marker Interface", "Empty interface to convey metadata"),
        "parameter-object": ("Parameter Object", "Replaces multiple parameters with an object"),
        "private-class-data": ("Private Class Data", "Restricts access to class attributes"),
        "proxy": ("Proxy", "Provides surrogate for another object"),
        "role-object": ("Role Object", "Adapts object to different roles dynamically"),
        "separated-interface": ("Separated Interface", "Defines interface in separate package from implementation"),
        "servant": ("Servant", "Provides behavior to group of classes"),
        "service-locator": ("Service Locator", "Abstracts service lookup and caching"),
        "spatial-partition": ("Spatial Partition", "Efficiently locates objects in 2D/3D space"),
        "special-case": ("Special Case", "Subclass representing special cases"),
        "strangler": ("Strangler Fig", "Incrementally replaces legacy systems"),
        "twin": ("Twin", "Simulates multiple inheritance in single-inheritance languages"),
        "value-object": ("Value Object", "Immutable object defined by its value"),
        "virtual-proxy": ("Virtual Proxy", "Defers object creation until needed"),
        "sidecar": ("Sidecar", "Deploys helper components alongside main application"),
    }
    
    name, desc = patterns.get(module_name, (module_name.replace('-', ' ').title(), ""))
    
    return f"""package {package};

/**
 * {name} Pattern (Structural)
 * {desc}
 */
public class {class_name} {{
    
    public static void demonstrate() {{
        System.out.println("=== {name} Pattern ===");
        System.out.println("Intent: {desc}");
        System.out.println("Category: Structural");
    }}
    
    public static void main(String[] args) {{
        demonstrate();
    }}
}}
"""

def generate_creational_content(module_name, class_name, package):
    """Generate creational pattern examples."""
    patterns = {
        "abstract-factory": ("Abstract Factory", "Creates families of related objects"),
        "builder": ("Builder", "Separates object construction from representation"),
        "dependency-injection": ("Dependency Injection", "Injects dependencies from outside"),
        "factory-kit": ("Factory Kit", "Configurable factory with builder pattern"),
        "factory-method": ("Factory Method", "Defines interface for creating objects"),
        "factory": ("Simple Factory", "Centralizes object creation logic"),
        "monostate": ("Monostate", "Shared state through static members"),
        "multiton": ("Multiton", "Ensures controlled instance creation with keys"),
        "object-pool": ("Object Pool", "Reuses objects from a fixed pool"),
        "prototype": ("Prototype", "Creates objects by cloning prototypes"),
        "registry": ("Registry", "Global service locator for objects"),
        "singleton": ("Singleton", "Ensures single instance globally"),
        "step-builder": ("Step Builder", "Guides object construction through steps"),
        "type-object": ("Type Object", "Creates flexible types at runtime"),
    }
    
    name, desc = patterns.get(module_name, (module_name.replace('-', ' ').title(), ""))
    
    return f"""package {package};

/**
 * {name} Pattern (Creational)
 * {desc}
 */
public class {class_name} {{
    
    public static void demonstrate() {{
        System.out.println("=== {name} Pattern ===");
        System.out.println("Intent: {desc}");
        System.out.println("Category: Creational");
    }}
    
    public static void main(String[] args) {{
        demonstrate();
    }}
}}
"""

def generate_concurrency_content(module_name, class_name, package):
    """Generate concurrency pattern examples."""
    patterns = {
        "active-object": ("Active Object", "Decouples method execution from invocation"),
        "async-method-invocation": ("Async Method Invocation", "Non-blocking method calls"),
        "balking": ("Balking", "Only executes action when in appropriate state"),
        "double-checked-locking": ("Double-Checked Locking", "Reduces locking overhead"),
        "event-based-asynchronous": ("Event-Based Async", "Asynchronous processing with events"),
        "event-queue": ("Event Queue", "Manages event processing order"),
        "fan-out-fan-in": ("Fan-Out/Fan-In", "Parallel processing with result aggregation"),
        "guarded-suspension": ("Guarded Suspension", "Suspends thread until condition met"),
        "half-sync-half-async": ("Half-Sync/Half-Async", "Combines synchronous and asynchronous processing"),
        "leader-election": ("Leader Election", "Elects a coordinator among nodes"),
        "leader-followers": ("Leader-Followers", "Efficient thread pool model"),
        "lockable-object": ("Lockable Object", "Thread-safe object locking"),
        "master-worker": ("Master-Worker", "Parallel task distribution"),
        "monitor": ("Monitor", "Thread-safe object with mutual exclusion"),
        "poison-pill": ("Poison Pill", "Signals shutdown to consumer threads"),
        "producer-consumer": ("Producer-Consumer", "Decouples producers from consumers"),
        "promise": ("Promise", "Placeholder for future result"),
        "reactor": ("Reactor", "Demultiplexes events to handlers"),
    }
    
    name, desc = patterns.get(module_name, (module_name.replace('-', ' ').title(), ""))
    
    return f"""package {package};

/**
 * {name} Pattern (Concurrency)
 * {desc}
 */
public class {class_name} {{
    
    public static void demonstrate() {{
        System.out.println("=== {name} Pattern ===");
        System.out.println("Intent: {desc}");
        System.out.println("Category: Concurrency");
    }}
    
    public static void main(String[] args) {{
        demonstrate();
    }}
}}
"""

def generate_behavioral_content(module_name, class_name, package):
    """Generate behavioral pattern examples."""
    patterns = {
        "acyclic-visitor": ("Acyclic Visitor", "Visitor pattern without cyclic dependencies"),
        "bytecode": ("Bytecode", "Implements behavior through bytecode instructions"),
        "chain-of-responsibility": ("Chain of Responsibility", "Passes request along handler chain"),
        "client-session": ("Client Session", "Manages client state across requests"),
        "collecting-parameter": ("Collecting Parameter", "Accumulates results across methods"),
        "command": ("Command", "Encapsulates request as object"),
        "commander": ("Commander", "Distributed transaction execution"),
        "context-object": ("Context Object", "Encapsulates system state/behavior"),
        "data-mapper": ("Data Mapper", "Maps objects to database"),
        "delegation": ("Delegation", "Delegates work to helper objects"),
        "dirty-flag": ("Dirty Flag", "Tracks object modification state"),
        "double-buffer": ("Double Buffer", "Smooth rendering with two buffers"),
        "double-dispatch": ("Double Dispatch", "Runtime type-based method dispatch"),
        "execute-around": ("Execute Around", "Manages resource setup/cleanup"),
        "feature-toggle": ("Feature Toggle", "Controls feature availability"),
        "filterer": ("Filterer", "Filters collections with predicates"),
        "fluent-interface": ("Fluent Interface", "Method chaining for readability"),
        "game-loop": ("Game Loop", "Continuous game state updates"),
        "health-check": ("Health Check", "Monitors system health"),
        "identity-map": ("Identity Map", "Ensures each object loaded once"),
        "interpreter": ("Interpreter", "Evaluates language grammar"),
        "iterator": ("Iterator", "Sequential access to collection elements"),
        "mediator": ("Mediator", "Encapsulates object interactions"),
        "notification": ("Notification", "Collects and reports application messages"),
        "memento": ("Memento", "Captures and restores object state"),
        "mute-idiom": ("Mute Idiom", "Silently handles exceptions"),
        "null-object": ("Null Object", "Default behavior for null references"),
        "observer": ("Observer", "One-to-many dependency notification"),
        "partial-response": ("Partial Response", "Returns subset of response fields"),
        "pipeline": ("Pipeline", "Chains processing stages"),
        "property": ("Property", "Dynamic property management"),
        "specification": ("Specification", "Business rule combination"),
        "state": ("State", "State-dependent behavior changes"),
        "strategy": ("Strategy", "Interchangeable algorithm families"),
        "subclass-sandbox": ("Subclass Sandbox", "Controlled subclass behavior"),
        "template-method": ("Template Method", "Algorithm skeleton with overridable steps"),
        "update-method": ("Update Method", "Per-frame game object updates"),
        "visitor": ("Visitor", "Separates operations from object structure"),
    }
    
    name, desc = patterns.get(module_name, (module_name.replace('-', ' ').title(), ""))
    
    return f"""package {package};

/**
 * {name} Pattern (Behavioral)
 * {desc}
 */
public class {class_name} {{
    
    public static void demonstrate() {{
        System.out.println("=== {name} Pattern ===");
        System.out.println("Intent: {desc}");
        System.out.println("Category: Behavioral");
    }}
    
    public static void main(String[] args) {{
        demonstrate();
    }}
}}
"""

def generate_integration_content(module_name, class_name, package):
    """Generate integration pattern examples."""
    patterns = {
        "ambassador": ("Ambassador", "Offloads common client connectivity tasks"),
        "anti-corruption-layer": ("Anti-Corruption Layer", "Isolates legacy system from modern code"),
        "gateway": ("Gateway", "Encapsulates external system access"),
    }
    
    name, desc = patterns.get(module_name, (module_name.replace('-', ' ').title(), ""))
    
    return f"""package {package};

/**
 * {name} Pattern (Integration)
 * {desc}
 */
public class {class_name} {{
    
    public static void demonstrate() {{
        System.out.println("=== {name} Pattern ===");
        System.out.println("Intent: {desc}");
        System.out.println("Category: Integration");
    }}
    
    public static void main(String[] args) {{
        demonstrate();
    }}
}}
"""

def generate_microservices_content(module_name, class_name, package):
    """Generate microservices pattern examples."""
    patterns = {
        "api-gateway": ("API Gateway", "Single entry point for client requests"),
        "service-discovery": ("Service Discovery", "Automatic service location"),
        "service-registry": ("Service Registry", "Centralized service registration"),
        "config-server": ("Config Server", "Centralized configuration management"),
        "log-aggregation": ("Log Aggregation", "Centralized log collection"),
        "database-per-service": ("Database per Service", "Each service owns its database"),
        "service-mesh": ("Service Mesh", "Dedicated infrastructure layer for service communication"),
        "observability": ("Observability", "Monitoring, logging, and tracing"),
        "health-monitoring": ("Health Monitoring", "Service health status tracking"),
        "monitoring": ("Monitoring", "System metrics and alerting"),
        "master-service-decomposition": ("Service Decomposition", "Breaking monolith into services"),
    }
    
    name, desc = patterns.get(module_name, (module_name.replace('-', ' ').title(), ""))
    
    return f"""package {package};

/**
 * {name} Pattern (Microservices)
 * {desc}
 */
public class {class_name} {{
    
    public static void demonstrate() {{
        System.out.println("=== {name} Pattern ===");
        System.out.println("Intent: {desc}");
        System.out.println("Category: Microservices");
    }}
    
    public static void main(String[] args) {{
        demonstrate();
    }}
}}
"""

def generate_testing_content(module_name, class_name, package):
    """Generate testing pattern examples."""
    patterns = {
        "arrange-act-assert": ("Arrange-Act-Assert", "Structured test case organization"),
        "object-mother": ("Object Mother", "Test object factory"),
        "page-object": ("Page Object", "UI test abstraction"),
    }
    
    name, desc = patterns.get(module_name, (module_name.replace('-', ' ').title(), ""))
    
    return f"""package {package};

/**
 * {name} Pattern (Testing)
 * {desc}
 */
public class {class_name} {{
    
    public static void demonstrate() {{
        System.out.println("=== {name} Pattern ===");
        System.out.println("Intent: {desc}");
        System.out.println("Category: Testing");
    }}
    
    public static void main(String[] args) {{
        demonstrate();
    }}
}}
"""

def generate_performance_content(module_name, class_name, package):
    """Generate performance optimization pattern examples."""
    patterns = {
        "caching": ("Caching", "Stores frequently accessed data for fast retrieval"),
        "data-locality": ("Data Locality", "Organizes data for cache efficiency"),
        "lazy-loading": ("Lazy Loading", "Defers initialization until needed"),
    }
    
    name, desc = patterns.get(module_name, (module_name.replace('-', ' ').title(), ""))
    
    return f"""package {package};

/**
 * {name} Pattern (Performance Optimization)
 * {desc}
 */
public class {class_name} {{
    
    public static void demonstrate() {{
        System.out.println("=== {name} Pattern ===");
        System.out.println("Intent: {desc}");
        System.out.println("Category: Performance Optimization");
    }}
    
    public static void main(String[] args) {{
        demonstrate();
    }}
}}
"""

def generate_functional_content(module_name, class_name, package):
    """Generate functional pattern examples."""
    patterns = {
        "callback": ("Callback", "Passes executable code as argument"),
        "collection-pipeline": ("Collection Pipeline", "Chains collection operations"),
        "combinator": ("Combinator", "Combines functions to create new functions"),
        "currying": ("Currying", "Transforms multi-argument functions"),
        "function-composition": ("Function Composition", "Combines functions sequentially"),
        "monad": ("Monad", "Wraps values with computation context"),
        "trampoline": ("Trampoline", "Stack-safe recursion"),
    }
    
    name, desc = patterns.get(module_name, (module_name.replace('-', ' ').title(), ""))
    
    return f"""package {package};

/**
 * {name} Pattern (Functional)
 * {desc}
 */
public class {class_name} {{
    
    public static void demonstrate() {{
        System.out.println("=== {name} Pattern ===");
        System.out.println("Intent: {desc}");
        System.out.println("Category: Functional Programming");
    }}
    
    public static void main(String[] args) {{
        demonstrate();
    }}
}}
"""

def generate_resilience_content(module_name, class_name, package):
    """Generate resilience pattern examples."""
    patterns = {
        "circuit-breaker": ("Circuit Breaker", "Prevents cascading failures"),
        "queue-based-load-leveling": ("Queue-Based Load Leveling", "Smooths workload spikes"),
        "retry": ("Retry", "Automatically retries failed operations"),
        "saga": ("Saga", "Manages distributed transactions"),
        "tolerant-reader": ("Tolerant Reader", "Tolerates message format changes"),
        "rate-limiting": ("Rate Limiting", "Controls request rate"),
        "bulkheads": ("Bulkheads", "Isolates failures to prevent system-wide impact"),
        "fallbacks": ("Fallbacks", "Provides alternative behavior on failure"),
        "timeouts": ("Timeouts", "Limits wait time for operations"),
        "graceful-degradation": ("Graceful Degradation", "Maintains partial functionality during failure"),
    }
    
    name, desc = patterns.get(module_name, (module_name.replace('-', ' ').title(), ""))
    
    return f"""package {package};

/**
 * {name} Pattern (Resilience)
 * {desc}
 */
public class {class_name} {{
    
    public static void demonstrate() {{
        System.out.println("=== {name} Pattern ===");
        System.out.println("Intent: {desc}");
        System.out.println("Category: Resilience");
    }}
    
    public static void main(String[] args) {{
        demonstrate();
    }}
}}
"""

def generate_architectural_content(module_name, class_name, package):
    """Generate architectural pattern examples."""
    patterns = {
        "command-query-responsibility-segregation": ("CQRS", "Separates read and write operations"),
        "event-driven-architecture": ("Event-Driven Architecture", "Components communicate via events"),
        "event-sourcing": ("Event Sourcing", "Stores state changes as event sequence"),
        "flux": ("Flux", "Unidirectional data flow"),
        "front-controller": ("Front Controller", "Centralized request handling"),
        "hexagonal-architecture": ("Hexagonal Architecture", "Ports and adapters architecture"),
        "intercepting-filter": ("Intercepting Filter", "Pre/post processing of requests"),
        "layered-architecture": ("Layered Architecture", "Separation of concerns into layers"),
        "microservices-aggregator": ("Microservices Aggregator", "Aggregates responses from multiple services"),
        "model-view-controller": ("MVC", "Separates data, presentation, and control"),
        "model-view-intent": ("MVI", "Unidirectional UI state management"),
        "model-view-presenter": ("MVP", "Presenter mediates between model and view"),
        "model-view-viewmodel": ("MVVM", "ViewModel exposes data for binding"),
        "naked-objects": ("Naked Objects", "Objects directly represent domain concepts"),
        "page-controller": ("Page Controller", "Handles specific page requests"),
        "presentation-model": ("Presentation Model", "Separates UI logic from view"),
        "service-layer": ("Service Layer", "Defines application boundary"),
        "service-to-worker": ("Service to Worker", "Combines dispatcher with actions"),
        "backend-for-frontend": ("BFF", "Backend tailored to specific frontend"),
        "composable-architecture": ("Composable Architecture", "Modular, composable application structure"),
    }
    
    name, desc = patterns.get(module_name, (module_name.replace('-', ' ').title(), ""))
    
    return f"""package {package};

/**
 * {name} Pattern (Architectural)
 * {desc}
 */
public class {class_name} {{
    
    public static void demonstrate() {{
        System.out.println("=== {name} Pattern ===");
        System.out.println("Intent: {desc}");
        System.out.println("Category: Architectural");
    }}
    
    public static void main(String[] args) {{
        demonstrate();
    }}
}}
"""

def generate_messaging_content(module_name, class_name, package):
    """Generate messaging pattern examples."""
    patterns = {
        "data-bus": ("Data Bus", "Centralized data distribution"),
        "event-aggregator": ("Event Aggregator", "Centralized event channel"),
    }
    
    name, desc = patterns.get(module_name, (module_name.replace('-', ' ').title(), ""))
    
    return f"""package {package};

/**
 * {name} Pattern (Messaging)
 * {desc}
 */
public class {class_name} {{
    
    public static void demonstrate() {{
        System.out.println("=== {name} Pattern ===");
        System.out.println("Intent: {desc}");
        System.out.println("Category: Messaging");
    }}
    
    public static void main(String[] args) {{
        demonstrate();
    }}
}}
"""

def generate_dataaccess_content(module_name, class_name, package):
    """Generate data access pattern examples."""
    patterns = {
        "metadata-mapping": ("Metadata Mapping", "Maps object-relational metadata"),
        "optimistic-offline-lock": ("Optimistic Offline Lock", "Concurrency control without locks"),
        "repository": ("Repository", "Mediates domain and data mapping"),
        "serialized-entity": ("Serialized Entity", "Serializes entity state"),
        "serialized-lob": ("Serialized LOB", "Stores objects as large objects"),
        "sharding": ("Sharding", "Horizontal data partitioning"),
        "single-table-inheritance": ("Single Table Inheritance", "Inheritance in single table"),
        "table-module": ("Table Module", "Single class handles table logic"),
        "transaction-script": ("Transaction Script", "Procedural business logic"),
        "unit-of-work": ("Unit of Work", "Tracks object changes for persistence"),
        "version-number": ("Version Number", "Optimistic locking with version field"),
    }
    
    name, desc = patterns.get(module_name, (module_name.replace('-', ' ').title(), ""))
    
    return f"""package {package};

/**
 * {name} Pattern (Data Access)
 * {desc}
 */
public class {class_name} {{
    
    public static void demonstrate() {{
        System.out.println("=== {name} Pattern ===");
        System.out.println("Intent: {desc}");
        System.out.println("Category: Data Access");
    }}
    
    public static void main(String[] args) {{
        demonstrate();
    }}
}}
"""

def generate_resource_content(module_name, class_name, package):
    """Generate resource management pattern examples."""
    patterns = {
        "resource-acquisition-is-initialization": ("RAII", "Binds resource lifecycle to object lifetime"),
        "server-session": ("Server Session", "Manages server-side session state"),
        "throttling": ("Throttling", "Controls resource usage rate"),
    }
    
    name, desc = patterns.get(module_name, (module_name.replace('-', ' ').title(), ""))
    
    return f"""package {package};

/**
 * {name} Pattern (Resource Management)
 * {desc}
 */
public class {class_name} {{
    
    public static void demonstrate() {{
        System.out.println("=== {name} Pattern ===");
        System.out.println("Intent: {desc}");
        System.out.println("Category: Resource Management");
    }}
    
    public static void main(String[] args) {{
        demonstrate();
    }}
}}
"""

def generate_generic_pattern_content(module_name, class_name, package):
    """Fallback for unknown patterns."""
    display_name = module_name.replace('-', ' ').title()
    
    return f"""package {package};

/**
 * {display_name} Pattern
 * System design pattern for building scalable and maintainable applications.
 */
public class {class_name} {{
    
    public static void demonstrate() {{
        System.out.println("=== {display_name} Pattern ===");
        System.out.println("A system design pattern for building robust applications.");
    }}
    
    public static void main(String[] args) {{
        demonstrate();
    }}
}}
"""

def main():
    modules = parse_modules(SETTINGS_FILE)
    print(f"Found {len(modules)} modules in settings.gradle.kts")
    
    created = 0
    skipped = 0
    
    for module_path, dir_path in modules:
        if write_java_file(module_path, dir_path):
            print(f"  Created: {dir_path}/src/main/java/...")
            created += 1
        else:
            skipped += 1
    
    print(f"\nCreated {created} Java source files, {skipped} already existed")

if __name__ == "__main__":
    main()