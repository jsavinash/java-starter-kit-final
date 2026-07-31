# Service Registry Pattern

## Overview
Maintains registry of available service instances.

## Structure
```
service-registry/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/serviceregistry/
    └── ServiceRegistry.java
```

## Implementation
The Service Registry pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:microservices:service-registry:build

# Run the pattern example
./gradlew :system-design-pattern:microservices:service-registry:run
```

## Category
Microservices

## Java Version
Java 25
