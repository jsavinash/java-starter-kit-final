# Game Loop Pattern

## Overview
Controls game timing and rendering.

## Structure
```
game-loop/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/gameloop/
    └── GameLoop.java
```

## Implementation
The Game Loop pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:game-loop:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:game-loop:run
```

## Category
Behavioral

## Java Version
Java 25
