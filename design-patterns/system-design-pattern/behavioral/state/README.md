# State Pattern

## Overview
Changes behavior when internal state changes.

## Structure
```
state/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/state/
    └── State.java
```

## Implementation
The State pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:state:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:state:run
```

## Category
Behavioral

## Java Version
Java 25
