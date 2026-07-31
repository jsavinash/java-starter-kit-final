# Mediator Pattern

## Overview
Reduces coupling between communicating objects.

## Structure
```
mediator/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/mediator/
    └── Mediator.java
```

## Implementation
The Mediator pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:mediator:build

# Run the pattern example
./gradlew :system-design-pattern:structural:mediator:run
```

## Category
Structural

## Java Version
Java 25
