# Event Based Asynchronous Pattern

## Overview
Handles events asynchronously.

## Structure
```
event-based-asynchronous/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/eventbasedasynchronous/
    └── EventBasedAsynchronous.java
```

## Implementation
The Event Based Asynchronous pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:concurrency:event-based-asynchronous:build

# Run the pattern example
./gradlew :system-design-pattern:concurrency:event-based-asynchronous:run
```

## Category
Concurrency

## Java Version
Java 25
