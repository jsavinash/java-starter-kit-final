# Dynamic Proxy Pattern

## Overview
Creates proxies dynamically at runtime.

## Structure
```
dynamic-proxy/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/dynamicproxy/
    └── DynamicProxy.java
```

## Implementation
The Dynamic Proxy pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:dynamic-proxy:build

# Run the pattern example
./gradlew :system-design-pattern:structural:dynamic-proxy:run
```

## Category
Structural

## Java Version
Java 25
