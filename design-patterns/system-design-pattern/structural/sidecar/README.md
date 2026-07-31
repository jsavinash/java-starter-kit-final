# Sidecar Pattern

## Overview
Attaches a helper component to a main application.

## Structure
```
sidecar/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/sidecar/
    └── Sidecar.java
```

## Implementation
The Sidecar pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:sidecar:build

# Run the pattern example
./gradlew :system-design-pattern:structural:sidecar:run
```

## Category
Structural

## Java Version
Java 25
