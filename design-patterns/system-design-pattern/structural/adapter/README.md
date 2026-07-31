# Adapter Pattern

## Overview
Converts incompatible interfaces so they can work together.

## Structure
```
adapter/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/adapter/
    └── Adapter.java
```

## Implementation
The Adapter pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:adapter:build

# Run the pattern example
./gradlew :system-design-pattern:structural:adapter:run
```

## Category
Structural

## Java Version
Java 25
