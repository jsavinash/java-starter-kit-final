# Fluent Interface Pattern

## Overview
Provides readable, chainable API.

## Structure
```
fluent-interface/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/fluentinterface/
    └── FluentInterface.java
```

## Implementation
The Fluent Interface pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:fluent-interface:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:fluent-interface:run
```

## Category
Behavioral

## Java Version
Java 25
