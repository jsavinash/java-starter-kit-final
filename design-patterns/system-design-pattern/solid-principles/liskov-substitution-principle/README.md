# Liskov Substitution Principle Pattern

## Overview
Subtypes must be substitutable for base types.

## Structure
```
liskov-substitution-principle/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/liskovsubstitutionprinciple/
    └── LiskovSubstitutionPrinciple.java
```

## Implementation
The Liskov Substitution Principle pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:solid-principles:liskov-substitution-principle:build

# Run the pattern example
./gradlew :system-design-pattern:solid-principles:liskov-substitution-principle:run
```

## Category
Solid Principles

## Java Version
Java 25
