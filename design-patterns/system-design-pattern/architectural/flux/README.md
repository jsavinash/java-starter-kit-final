# Flux Pattern

## Overview
Unidirectional data flow architecture.

## Structure
```
flux/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/flux/
    └── Flux.java
```

## Implementation
The Flux pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:flux:build

# Run the pattern example
./gradlew :system-design-pattern:architectural:flux:run
```

## Category
Architectural

## Java Version
Java 25
