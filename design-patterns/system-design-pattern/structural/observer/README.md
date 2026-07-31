# Observer Pattern

## Overview
Notifies dependents of state changes.

## Structure
```
observer/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/observer/
    └── Observer.java
```

## Implementation
The Observer pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:observer:build

# Run the pattern example
./gradlew :system-design-pattern:structural:observer:run
```

## Category
Structural

## Java Version
Java 25
