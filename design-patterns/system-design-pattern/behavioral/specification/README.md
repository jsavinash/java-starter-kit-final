# Specification Pattern

## Overview
Combines business rules using boolean logic.

## Structure
```
specification/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/specification/
    └── Specification.java
```

## Implementation
The Specification pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:specification:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:specification:run
```

## Category
Behavioral

## Java Version
Java 25
