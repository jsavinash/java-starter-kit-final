# Arrange Act Assert Pattern

## Overview
Structures tests into three clear phases.

## Structure
```
arrange-act-assert/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/arrangeactassert/
    └── Arrangeactassert.java
```

## Implementation
The Arrange Act Assert pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:testing:arrange-act-assert:build

# Run the pattern example
./gradlew :system-design-pattern:testing:arrange-act-assert:run
```

## Category
Testing

## Java Version
Java 25
