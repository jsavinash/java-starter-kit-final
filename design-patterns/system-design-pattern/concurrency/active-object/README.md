# Active Object Pattern

## Overview
Decouples method execution from invocation.

## Structure
```
active-object/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/activeobject/
    └── ActiveObject.java
```

## Implementation
The Active Object pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:concurrency:active-object:build

# Run the pattern example
./gradlew :system-design-pattern:concurrency:active-object:run
```

## Category
Concurrency

## Java Version
Java 25
