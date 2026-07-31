# Dependency Inversion Principle Pattern

## Overview
Depend on abstractions, not concrete implementations.

## Structure
```
dependency-inversion-principle/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/dependencyinversionprinciple/
    └── DependencyInversionPrinciple.java
```

## Implementation
The Dependency Inversion Principle pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:solid-principles:dependency-inversion-principle:build

# Run the pattern example
./gradlew :system-design-pattern:solid-principles:dependency-inversion-principle:run
```

## Category
Solid Principles

## Java Version
Java 25
