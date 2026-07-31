# Converter Pattern

## Overview
Converts between different data formats.

## Structure
```
converter/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/converter/
    └── Converter.java
```

## Implementation
The Converter pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:converter:build

# Run the pattern example
./gradlew :system-design-pattern:structural:converter:run
```

## Category
Structural

## Java Version
Java 25
