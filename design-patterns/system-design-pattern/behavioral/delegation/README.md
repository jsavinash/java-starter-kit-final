# Delegation Pattern

## Overview
Delegates work to helper objects.

## Structure
```
delegation/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/delegation/
    └── Delegation.java
```

## Implementation
The Delegation pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:delegation:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:delegation:run
```

## Category
Behavioral

## Java Version
Java 25
