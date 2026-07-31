# Single Responsibility Principle Pattern

## Overview
A class should have only one reason to change.

## Structure
```
single-responsibility-principle/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/singleresponsibilityprinciple/
    └── SingleResponsibilityPrinciple.java
```

## Implementation
The Single Responsibility Principle pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:solid-principles:single-responsibility-principle:build

# Run the pattern example
./gradlew :system-design-pattern:solid-principles:single-responsibility-principle:run
```

## Category
Solid Principles

## Java Version
Java 25
