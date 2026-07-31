# Visitor Pattern

## Overview
Separates algorithms from objects they operate on.

## Structure
```
visitor/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/visitor/
    └── Visitor.java
```

## Implementation
The Visitor pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:visitor:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:visitor:run
```

## Category
Behavioral

## Java Version
Java 25
