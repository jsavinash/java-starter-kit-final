# Builder Pattern

## Overview
Constructs complex objects step by step.

## Structure
```
builder/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/builder/
    └── Builder.java
```

## Implementation
The Builder pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:builder:build

# Run the pattern example
./gradlew :system-design-pattern:structural:builder:run
```

## Category
Structural

## Java Version
Java 25
