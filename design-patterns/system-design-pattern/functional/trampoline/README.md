# Trampoline Pattern

## Overview
Provides stack-safe recursion.

## Structure
```
trampoline/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/trampoline/
    └── Trampoline.java
```

## Implementation
The Trampoline pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:functional:trampoline:build

# Run the pattern example
./gradlew :system-design-pattern:functional:trampoline:run
```

## Category
Functional

## Java Version
Java 25
