# Gateway Pattern

## Overview
Abstracts access to external services or APIs.

## Structure
```
gateway/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/gateway/
    └── Gateway.java
```

## Implementation
The Gateway pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:integration:gateway:build

# Run the pattern example
./gradlew :system-design-pattern:integration:gateway:run
```

## Category
Integration

## Java Version
Java 25
