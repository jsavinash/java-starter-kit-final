# Multiton Pattern

## Overview
Manages a named set of instances.

## Structure
```
multiton/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/multiton/
    └── Multiton.java
```

## Implementation
The Multiton pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:creational:multiton:build

# Run the pattern example
./gradlew :system-design-pattern:creational:multiton:run
```

## Category
Creational

## Java Version
Java 25
