# Subclass Sandbox Pattern

## Overview
Provides controlled environment for subclasses.

## Structure
```
subclass-sandbox/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/subclasssandbox/
    └── SubclassSandbox.java
```

## Implementation
The Subclass Sandbox pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:subclass-sandbox:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:subclass-sandbox:run
```

## Category
Behavioral

## Java Version
Java 25
