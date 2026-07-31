# Iterator Pattern

## Overview
Provides sequential access to elements.

## Structure
```
iterator/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/iterator/
    └── Iterator.java
```

## Implementation
The Iterator pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:iterator:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:iterator:run
```

## Category
Behavioral

## Java Version
Java 25
