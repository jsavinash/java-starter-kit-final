# Interface Segregation Principle Pattern

## Overview
Clients should not depend on interfaces they don't use.

## Structure
```
interface-segregation-principle/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/interfacesegregationprinciple/
    └── InterfaceSegregationPrinciple.java
```

## Implementation
The Interface Segregation Principle pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:solid-principles:interface-segregation-principle:build

# Run the pattern example
./gradlew :system-design-pattern:solid-principles:interface-segregation-principle:run
```

## Category
Solid Principles

## Java Version
Java 25
