# Business Delegate Pattern

## Overview
Decouples presentation from business logic.

## Structure
```
business-delegate/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/businessdelegate/
    └── BusinessDelegate.java
```

## Implementation
The Business Delegate pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:business-delegate:build

# Run the pattern example
./gradlew :system-design-pattern:structural:business-delegate:run
```

## Category
Structural

## Java Version
Java 25
