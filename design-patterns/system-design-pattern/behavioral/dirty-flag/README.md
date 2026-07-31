# Dirty Flag Pattern

## Overview
Tracks whether an object has been modified.

## Structure
```
dirty-flag/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/dirtyflag/
    └── DirtyFlag.java
```

## Implementation
The Dirty Flag pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:dirty-flag:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:dirty-flag:run
```

## Category
Behavioral

## Java Version
Java 25
