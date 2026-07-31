# Proxy Pattern

## Overview
Controls access to an object, acting as a placeholder.

## Structure
```
proxy/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/proxy/
    └── Proxy.java
```

## Implementation
The Proxy pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:proxy:build

# Run the pattern example
./gradlew :system-design-pattern:structural:proxy:run
```

## Category
Structural

## Java Version
Java 25
