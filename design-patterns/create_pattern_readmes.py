#!/usr/bin/env python3
"""
Script to analyze each design pattern topic and ensure each has:
1. A Java example file
2. A README.md file

Creates README.md files for patterns that are missing them.
"""

import os
from pathlib import Path

PATTERN_BASE = Path("design-patterns/system-design-pattern")

# Pattern descriptions for README generation
PATTERN_DESCRIPTIONS = {
    # Creational
    "abstract-factory": "Creates families of related objects without specifying concrete classes.",
    "builder": "Constructs complex objects step by step.",
    "dependency-injection": "Passes dependencies from outside rather than creating them internally.",
    "factory": "Creates objects without specifying exact class.",
    "factory-kit": "A flexible factory that can be configured with different builders.",
    "factory-method": "Defines interface for creating objects, lets subclasses decide which class to instantiate.",
    "monostate": "Shares state across instances via static fields.",
    "multiton": "Manages a named set of instances.",
    "object-pool": "Manages reusable objects to avoid expensive creation.",
    "prototype": "Creates new objects by copying existing ones.",
    "registry": "Provides centralized location for accessing objects.",
    "singleton": "Ensures a class has only one instance.",
    "step-builder": "Guides object construction through predefined steps.",
    "type-object": "Allows creation of flexible type systems at runtime.",
    
    # Structural
    "adapter": "Converts incompatible interfaces so they can work together.",
    "bridge": "Decouples abstraction from implementation so they can vary independently.",
    "business-delegate": "Decouples presentation from business logic.",
    "command": "Encapsulates requests as objects.",
    "component": "Allows individual objects to be composed into larger structures.",
    "composite": "Treats individual and composite objects uniformly.",
    "composite-entity": "Manages a group of related objects as a single entity.",
    "composite-view": "Builds views from smaller, reusable components.",
    "converter": "Converts between different data formats.",
    "curiously-recurring-template-pattern": "Template where a class passes itself as a parameter.",
    "data-access-object": "Abstracts database operations behind a clean interface.",
    "data-transfer-object": "Transfers data between subsystems without exposing internals.",
    "decorator": "Adds responsibilities to objects dynamically.",
    "domain-model": "Rich domain model with behavior and business logic.",
    "dynamic-proxy": "Creates proxies dynamically at runtime.",
    "extension-objects": "Adds functionality to objects through extension interfaces.",
    "facade": "Provides simplified interface to a complex subsystem.",
    "factory": "Creates objects without specifying exact class.",
    "flyweight": "Shares common state between objects to save memory.",
    "iterator": "Provides sequential access to elements without exposing structure.",
    "marker-interface": "Uses empty interfaces to mark classes with metadata.",
    "mediator": "Reduces coupling by making objects communicate through a mediator.",
    "observer": "Notifies multiple objects about state changes.",
    "parameter-object": "Groups method parameters into a single object.",
    "private-class-data": "Restricts access to class data.",
    "proxy": "Controls access to an object, acting as a placeholder.",
    "role-object": "Allows objects to adapt to different roles.",
    "separated-interface": "Separates interface definition from implementation.",
    "servant": "Provides behavior to a group of classes.",
    "service-locator": "Provides centralized service lookup.",
    "sidecar": "Attaches a helper component to a main application.",
    "singleton": "Ensures a class has only one instance.",
    "spatial-partition": "Organizes spatial objects for efficient querying.",
    "special-case": "Handles special cases with polymorphic objects.",
    "strangler": "Incrementally replaces legacy systems.",
    "strategy": "Interchangeable algorithms selected at runtime.",
    "template-method": "Defines algorithm skeleton, letting subclasses fill in steps.",
    "twin": "Provides a way to have multiple inheritance.",
    "value-object": "Immutable objects compared by their values.",
    "virtual-proxy": "Delays loading of expensive objects.",
    
    # Behavioral
    "acyclic-visitor": "Visitor pattern without cyclic dependencies.",
    "bytecode": "Implements behavior via bytecode instructions.",
    "chain-of-responsibility": "Passes request through a chain of handlers.",
    "client-session": "Manages client state across multiple requests.",
    "collecting-parameter": "Collects parameters across multiple method calls.",
    "command": "Encapsulates requests as objects.",
    "commander": "Manages distributed transaction execution.",
    "context-object": "Encapsulates system context data.",
    "data-mapper": "Maps between objects and data stores.",
    "delegation": "Delegates work to helper objects.",
    "dirty-flag": "Tracks whether an object has been modified.",
    "double-buffer": "Uses two buffers to prevent visual artifacts.",
    "double-dispatch": "Dispatches calls based on runtime types of two objects.",
    "execute-around": "Executes boilerplate code around business logic.",
    "feature-toggle": "Enables/disables features at runtime.",
    "filterer": "Filters collections based on criteria.",
    "fluent-interface": "Provides readable, chainable API.",
    "game-loop": "Controls game timing and rendering.",
    "health-check": "Monitors system component health.",
    "identity-map": "Ensures each object is loaded only once.",
    "interpreter": "Interprets a language by defining grammar rules.",
    "iterator": "Provides sequential access to elements.",
    "mediator": "Reduces coupling between communicating objects.",
    "memento": "Captures and restores object state.",
    "mute-idiom": "Suppresses exceptions for cleaner code.",
    "null-object": "Provides default object that does nothing.",
    "notification": "Collects notification messages.",
    "observer": "Notifies dependents of state changes.",
    "partial-response": "Filters response data.",
    "pipeline": "Processes data through a sequence of stages.",
    "pipes-and-filters": "Processes data through a chain of filters.",
    "property": "Manages dynamic properties on objects.",
    "specification": "Combines business rules using boolean logic.",
    "state": "Changes behavior when internal state changes.",
    "strategy": "Interchangeable algorithms.",
    "subclass-sandbox": "Provides controlled environment for subclasses.",
    "template-method": "Defines algorithm skeleton.",
    "update-method": "Updates game objects each frame.",
    "visitor": "Separates algorithms from objects they operate on.",
    
    # Architectural
    "backend-for-frontend": "Creates separate backends for each client type.",
    "command-query-responsibility-segregation": "Separates read and write operations.",
    "composable-architecture": "Composes features from independent components.",
    "event-driven-architecture": "Systems communicate through events.",
    "event-sourcing": "Stores state changes as a sequence of events.",
    "flux": "Unidirectional data flow architecture.",
    "front-controller": "Centralizes request handling.",
    "hexagonal-architecture": "Isolates core logic through ports and adapters.",
    "intercepting-filter": "Pre-processes and post-processes requests.",
    "layered-architecture": "Organizes code into layers.",
    "microservices-aggregator": "Aggregates data from multiple services.",
    "model-view-controller": "Separates data, UI, and logic.",
    "model-view-intent": "Unidirectional data flow with intents.",
    "model-view-presenter": "Presenter mediates between Model and View.",
    "model-view-viewmodel": "Separates UI from business logic with data binding.",
    "naked-objects": "Domain objects automatically exposed as UI.",
    "page-controller": "Each page has its own controller.",
    "presentation-model": "Separates UI state from the view.",
    "service-layer": "Defines the application boundary with a service layer.",
    "service-to-worker": "Separates request processing from view management.",
    
    # Concurrency
    "active-object": "Decouples method execution from invocation.",
    "async-method-invocation": "Invokes methods asynchronously.",
    "balking": "Only executes action when in appropriate state.",
    "double-checked-locking": "Reduces lock acquisition overhead.",
    "event-based-asynchronous": "Handles events asynchronously.",
    "event-queue": "Manages event processing order.",
    "fan-out-fan-in": "Distributes and aggregates work.",
    "guarded-suspension": "Suspends execution until condition is met.",
    "half-sync-half-async": "Separates sync and async processing.",
    "leader-election": "Elects a leader among nodes.",
    "leader-followers": "Optimizes thread usage with leader/follower pattern.",
    "lockable-object": "Provides lock mechanism for objects.",
    "master-worker": "Distributes work among worker threads.",
    "monitor": "Synchronizes access to shared resources.",
    "poison-pill": "Signals shutdown of consumer threads.",
    "producer-consumer": "Separates production and consumption of data.",
    "promise": "Represents eventual result of async operation.",
    "reactor": "Handles service requests from multiple sources.",
    "read-write-lock": "Allows concurrent reads, exclusive writes.",
    "thread-pool": "Manages a pool of reusable threads.",
    
    # Resilience
    "bulkheads": "Isolates resources to prevent cascading failures.",
    "circuit-breaker": "Detects failures and prevents cascading.",
    "fallbacks": "Provides alternative response when service fails.",
    "graceful-degradation": "Provides reduced functionality when service is down.",
    "queue-based-load-leveling": "Smooths out workload spikes.",
    "rate-limiting": "Controls rate of requests.",
    "retry": "Automatically retries failed operations.",
    "saga": "Manages distributed transactions with compensation.",
    "timeouts": "Limits wait time for service responses.",
    "tolerant-reader": "Reads only understood fields, ignoring unknown data.",
    
    # Microservices
    "api-gateway": "Single entry point for client requests.",
    "config-server": "Centralizes configuration management.",
    "database-per-service": "Each service has its own database.",
    "health-monitoring": "Monitors health of system components.",
    "log-aggregation": "Centralizes logs from multiple services.",
    "master-service-decomposition": "Central orchestrator manages distributed services.",
    "monitoring": "Tracks system performance and errors.",
    "observability": "Provides visibility through logs, metrics, traces.",
    "service-discovery": "Enables services to find each other dynamically.",
    "service-mesh": "Infrastructure layer for service-to-service communication.",
    "service-registry": "Maintains registry of available service instances.",
    
    # Data Access
    "metadata-mapping": "Maps database metadata to objects.",
    "optimistic-offline-lock": "Prevents conflicts using version numbers.",
    "repository": "Mediates between domain and data mapping layers.",
    "serialized-entity": "Serializes entities for storage.",
    "serialized-lob": "Stores large objects as serialized data.",
    "sharding": "Horizontal partitioning across databases.",
    "single-table-inheritance": "Stores inheritance hierarchy in one table.",
    "table-module": "Single instance handles business logic for all rows.",
    "transaction-script": "Organizes business logic by transaction.",
    "unit-of-work": "Groups operations into a single transaction.",
    "version-number": "Manages concurrent access with version numbers.",
    
    # Integration
    "ambassador": "Helper service handling retries, logging, latency.",
    "anti-corruption-layer": "Protects domain from legacy system contamination.",
    "gateway": "Abstracts access to external services or APIs.",
    
    # Functional
    "callback": "Passes executable code as an argument.",
    "collection-pipeline": "Chains collection operations in sequence.",
    "combinator": "Combines small functions into larger ones.",
    "currying": "Transforms multi-argument functions into chains.",
    "function-composition": "Combines simple functions to build complex ones.",
    "monad": "Wraps values and provides composition operations.",
    "trampoline": "Provides stack-safe recursion.",
    
    # Messaging
    "data-bus": "Centralized event distribution system.",
    "event-aggregator": "Collects events from multiple sources.",
    
    # Testing
    "arrange-act-assert": "Structures tests into three clear phases.",
    "object-mother": "Creates pre-configured test objects.",
    "page-object": "Encapsulates page details in test automation.",
    
    # Performance
    "caching": "Stores frequently accessed data for fast retrieval.",
    "data-locality": "Organizes data for optimal cache performance.",
    "lazy-loading": "Defers object creation until needed.",
    
    # Resource Management
    "resource-acquisition-is-initialization": "Ties resource lifecycle to object lifetime.",
    "server-session": "Manages user state across multiple requests.",
    "throttling": "Limits the rate of operations from a client.",
    
    # SOLID
    "single-responsibility-principle": "A class should have only one reason to change.",
    "open-close-principle": "Open for extension, closed for modification.",
    "liskov-substitution-principle": "Subtypes must be substitutable for base types.",
    "interface-segregation-principle": "Clients should not depend on interfaces they don't use.",
    "dependency-inversion-principle": "Depend on abstractions, not concrete implementations.",
}

def get_title(pattern_name):
    """Convert pattern name to title case."""
    return pattern_name.replace("-", " ").title()

def get_category(pattern_path):
    """Get the category from the pattern path."""
    parts = pattern_path.parts
    for i, part in enumerate(parts):
        if part == "system-design-pattern":
            if i + 1 < len(parts):
                return parts[i + 1]
    return "unknown"

def create_readme(pattern_path, pattern_name, category):
    """Create a README.md for a pattern."""
    title = get_title(pattern_name)
    description = PATTERN_DESCRIPTIONS.get(pattern_name, f"A system design pattern example demonstrating the {title} pattern.")
    
    # Find the Java file
    java_files = list(pattern_path.rglob("*.java"))
    java_file_name = java_files[0].stem if java_files else pattern_name.replace("-", "").title()
    
    content = f"""# {title} Pattern

## Overview
{description}

## Structure
```
{pattern_name}/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/{pattern_name.replace("-", "")}/
    └── {java_file_name}.java
```

## Implementation
The {title} pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:{category}:{pattern_name}:build

# Run the pattern example
./gradlew :system-design-pattern:{category}:{pattern_name}:run
```

## Category
{category.replace("-", " ").title()}

## Java Version
Java 25
"""
    return content

def main():
    """Main function to analyze and create README files."""
    print("=" * 80)
    print("  PATTERN ANALYSIS AND README CREATION TOOL")
    print("=" * 80)
    
    if not PATTERN_BASE.exists():
        print(f"Error: Base directory not found: {PATTERN_BASE}")
        return
    
    # Find all pattern directories
    pattern_dirs = []
    for category_dir in sorted(PATTERN_BASE.iterdir()):
        if category_dir.is_dir() and category_dir.name != "build":
            for pattern_dir in sorted(category_dir.iterdir()):
                if pattern_dir.is_dir() and pattern_dir.name != "build":
                    pattern_dirs.append((category_dir.name, pattern_dir))
    
    total = len(pattern_dirs)
    has_java = 0
    has_readme = 0
    missing_java = []
    missing_readme = []
    created_readme = 0
    
    print(f"\nFound {total} pattern directories to analyze.\n")
    
    for i, (category, pattern_dir) in enumerate(pattern_dirs, 1):
        pattern_name = pattern_dir.name
        
        # Check for Java files
        java_files = list(pattern_dir.rglob("*.java"))
        java_files = [f for f in java_files if "build" not in str(f)]
        
        # Check for README.md
        readme_path = pattern_dir / "README.md"
        
        if java_files:
            has_java += 1
        else:
            missing_java.append((category, pattern_name))
        
        if readme_path.exists():
            has_readme += 1
        else:
            missing_readme.append((category, pattern_name))
            # Create README.md
            try:
                content = create_readme(pattern_dir, pattern_name, category)
                readme_path.write_text(content)
                created_readme += 1
                print(f"  ✓ Created README.md: {category}/{pattern_name}")
            except Exception as e:
                print(f"  ✗ Error creating README for {category}/{pattern_name}: {e}")
    
    print("\n" + "=" * 80)
    print("  SUMMARY")
    print(f"  Total patterns: {total}")
    print(f"  Has Java example: {has_java}")
    print(f"  Has README.md: {has_readme + created_readme}")
    print(f"  Missing Java: {len(missing_java)}")
    print(f"  Missing README (before): {len(missing_readme)}")
    print(f"  READMEs created: {created_readme}")
    print("=" * 80)
    
    if missing_java:
        print("\nPatterns missing Java examples:")
        for cat, name in missing_java:
            print(f"  - {cat}/{name}")

if __name__ == "__main__":
    main()