# Naked Objects Pattern

## Overview
Domain objects automatically exposed as UI.

## Structure
```
naked-objects/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/nakedobjects/
    └── NakedObjects.java
```

## Implementation
The Naked Objects pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:naked-objects:build

# Run the pattern example
./gradlew :system-design-pattern:architectural:naked-objects:run
```

## Category
Architectural

## Java Version
Java 25
