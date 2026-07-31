# Strategy Pattern

## Overview
Interchangeable algorithms.

## Structure
```
strategy/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/strategy/
    └── Strategy.java
```

## Implementation
The Strategy pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:strategy:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:strategy:run
```

## Category
Behavioral

## Java Version
Java 25
