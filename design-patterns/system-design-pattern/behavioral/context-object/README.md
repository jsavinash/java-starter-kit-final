# Context Object Pattern

## Overview
Encapsulates system context data.

## Structure
```
context-object/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/contextobject/
    └── ContextObject.java
```

## Implementation
The Context Object pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:context-object:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:context-object:run
```

## Category
Behavioral

## Java Version
Java 25
