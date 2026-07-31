# Factory Method Pattern

## Overview
Defines interface for creating objects, lets subclasses decide which class to instantiate.

## Structure
```
factory-method/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/factorymethod/
    └── FactoryMethod.java
```

## Implementation
The Factory Method pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:creational:factory-method:build

# Run the pattern example
./gradlew :system-design-pattern:creational:factory-method:run
```

## Category
Creational

## Java Version
Java 25
