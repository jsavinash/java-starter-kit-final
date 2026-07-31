# Registry Pattern

## Overview
Provides centralized location for accessing objects.

## Structure
```
registry/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/registry/
    └── Registry.java
```

## Implementation
The Registry pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:creational:registry:build

# Run the pattern example
./gradlew :system-design-pattern:creational:registry:run
```

## Category
Creational

## Java Version
Java 25
