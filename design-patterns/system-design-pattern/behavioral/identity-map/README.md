# Identity Map Pattern

## Overview
Ensures each object is loaded only once.

## Structure
```
identity-map/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/identitymap/
    └── IdentityMap.java
```

## Implementation
The Identity Map pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:identity-map:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:identity-map:run
```

## Category
Behavioral

## Java Version
Java 25
