# Abstract Factory Pattern

## Overview
Creates families of related objects without specifying concrete classes.

## Structure
```
abstract-factory/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/abstractfactory/
    └── Abstractfactory.java
```

## Implementation
The Abstract Factory pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:creational:abstract-factory:build

# Run the pattern example
./gradlew :system-design-pattern:creational:abstract-factory:run
```

## Category
Creational

## Java Version
Java 25
