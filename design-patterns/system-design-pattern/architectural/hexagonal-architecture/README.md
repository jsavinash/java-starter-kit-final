# Hexagonal Architecture Pattern

## Overview
Isolates core logic through ports and adapters.

## Structure
```
hexagonal-architecture/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/hexagonalarchitecture/
    └── HexagonalArchitecture.java
```

## Implementation
The Hexagonal Architecture pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:hexagonal-architecture:build

# Run the pattern example
./gradlew :system-design-pattern:architectural:hexagonal-architecture:run
```

## Category
Architectural

## Java Version
Java 25
