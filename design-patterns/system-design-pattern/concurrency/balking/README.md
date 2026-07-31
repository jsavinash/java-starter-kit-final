# Balking Pattern

## Overview
Only executes action when in appropriate state.

## Structure
```
balking/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/balking/
    └── Balking.java
```

## Implementation
The Balking pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:concurrency:balking:build

# Run the pattern example
./gradlew :system-design-pattern:concurrency:balking:run
```

## Category
Concurrency

## Java Version
Java 25
