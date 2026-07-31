# Virtual Proxy Pattern

## Overview
Delays loading of expensive objects.

## Structure
```
virtual-proxy/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/virtualproxy/
    └── VirtualProxy.java
```

## Implementation
The Virtual Proxy pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:structural:virtual-proxy:build

# Run the pattern example
./gradlew :system-design-pattern:structural:virtual-proxy:run
```

## Category
Structural

## Java Version
Java 25
