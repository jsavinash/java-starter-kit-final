# Facade Pattern

## Overview
Provides simplified interface to a complex subsystem.

## Structure
```
facade/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/facade/
    └── Facade.java
```

## Implementation
The Facade pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:facade:build

# Run the pattern example
./gradlew :system-design-pattern:structural:facade:run
```

## Category
Structural

## Java Version
Java 25
