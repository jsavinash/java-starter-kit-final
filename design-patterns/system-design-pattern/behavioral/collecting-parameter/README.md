# Collecting Parameter Pattern

## Overview
Collects parameters across multiple method calls.

## Structure
```
collecting-parameter/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/collectingparameter/
    └── CollectingParameter.java
```

## Implementation
The Collecting Parameter pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:collecting-parameter:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:collecting-parameter:run
```

## Category
Behavioral

## Java Version
Java 25
