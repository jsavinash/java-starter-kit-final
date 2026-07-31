# Chain Of Responsibility Pattern

## Overview
Passes request through a chain of handlers.

## Structure
```
chain-of-responsibility/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/chainofresponsibility/
    └── ChainOfResponsibility.java
```

## Implementation
The Chain Of Responsibility pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:chain-of-responsibility:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:chain-of-responsibility:run
```

## Category
Behavioral

## Java Version
Java 25
