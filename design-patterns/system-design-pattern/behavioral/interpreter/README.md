# Interpreter Pattern

## Overview
Interprets a language by defining grammar rules.

## Structure
```
interpreter/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/interpreter/
    └── Interpreter.java
```

## Implementation
The Interpreter pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:interpreter:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:interpreter:run
```

## Category
Behavioral

## Java Version
Java 25
