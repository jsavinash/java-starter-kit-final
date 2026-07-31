# Bytecode Pattern

## Overview
Implements behavior via bytecode instructions.

## Structure
```
bytecode/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/bytecode/
    └── Bytecode.java
```

## Implementation
The Bytecode pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:bytecode:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:bytecode:run
```

## Category
Behavioral

## Java Version
Java 25
