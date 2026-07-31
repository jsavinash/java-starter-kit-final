# Callback Pattern

## Overview
Passes executable code as an argument.

## Structure
```
callback/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/callback/
    └── Callback.java
```

## Implementation
The Callback pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:functional:callback:build

# Run the pattern example
./gradlew :system-design-pattern:functional:callback:run
```

## Category
Functional

## Java Version
Java 25
