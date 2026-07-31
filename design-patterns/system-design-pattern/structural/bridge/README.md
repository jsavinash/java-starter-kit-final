# Bridge Pattern

## Overview
Decouples abstraction from implementation so they can vary independently.

## Structure
```
bridge/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/bridge/
    └── Bridge.java
```

## Implementation
The Bridge pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:bridge:build

# Run the pattern example
./gradlew :system-design-pattern:structural:bridge:run
```

## Category
Structural

## Java Version
Java 25
