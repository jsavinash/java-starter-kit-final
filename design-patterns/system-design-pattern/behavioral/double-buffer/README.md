# Double Buffer Pattern

## Overview
Uses two buffers to prevent visual artifacts.

## Structure
```
double-buffer/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/doublebuffer/
    └── DoubleBuffer.java
```

## Implementation
The Double Buffer pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:double-buffer:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:double-buffer:run
```

## Category
Behavioral

## Java Version
Java 25
