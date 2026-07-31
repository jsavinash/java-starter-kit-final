# Flyweight Pattern

## Overview
Shares common state between objects to save memory.

## Structure
```
flyweight/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/flyweight/
    └── Flyweight.java
```

## Implementation
The Flyweight pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:flyweight:build

# Run the pattern example
./gradlew :system-design-pattern:structural:flyweight:run
```

## Category
Structural

## Java Version
Java 25
