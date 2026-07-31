# Open Close Principle Pattern

## Overview
Open for extension, closed for modification.

## Structure
```
open-close-principle/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/opencloseprinciple/
    └── OpenClosePrinciple.java
```

## Implementation
The Open Close Principle pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:solid-principles:open-close-principle:build

# Run the pattern example
./gradlew :system-design-pattern:solid-principles:open-close-principle:run
```

## Category
Solid Principles

## Java Version
Java 25
