# Composable Architecture Pattern

## Overview
Composes features from independent components.

## Structure
```
composable-architecture/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/composablearchitecture/
    └── ComposableArchitecture.java
```

## Implementation
The Composable Architecture pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:composable-architecture:build

# Run the pattern example
./gradlew :system-design-pattern:architectural:composable-architecture:run
```

## Category
Architectural

## Java Version
Java 25
