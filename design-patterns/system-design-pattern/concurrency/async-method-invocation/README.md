# Async Method Invocation Pattern

## Overview
Invokes methods asynchronously.

## Structure
```
async-method-invocation/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/asyncmethodinvocation/
    └── AsyncMethodInvocation.java
```

## Implementation
The Async Method Invocation pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:concurrency:async-method-invocation:build

# Run the pattern example
./gradlew :system-design-pattern:concurrency:async-method-invocation:run
```

## Category
Concurrency

## Java Version
Java 25
