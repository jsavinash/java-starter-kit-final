# Command Pattern

## Overview
Encapsulates requests as objects.

## Structure
```
command/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/command/
    └── Command.java
```

## Implementation
The Command pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:command:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:command:run
```

## Category
Behavioral

## Java Version
Java 25
