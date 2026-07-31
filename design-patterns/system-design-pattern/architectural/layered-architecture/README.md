# Layered Architecture Pattern

## Overview
Organizes code into layers.

## Structure
```
layered-architecture/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/layeredarchitecture/
    └── LayeredArchitecture.java
```

## Implementation
The Layered Architecture pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:layered-architecture:build

# Run the pattern example
./gradlew :system-design-pattern:architectural:layered-architecture:run
```

## Category
Architectural

## Java Version
Java 25
