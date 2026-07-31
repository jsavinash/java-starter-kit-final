# Strangler Pattern

## Overview
Incrementally replaces legacy systems.

## Structure
```
strangler/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/strangler/
    └── Strangler.java
```

## Implementation
The Strangler pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:strangler:build

# Run the pattern example
./gradlew :system-design-pattern:structural:strangler:run
```

## Category
Structural

## Java Version
Java 25
