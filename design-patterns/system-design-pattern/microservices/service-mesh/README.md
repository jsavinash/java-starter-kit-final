# Service Mesh Pattern

## Overview
Infrastructure layer for service-to-service communication.

## Structure
```
service-mesh/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/servicemesh/
    └── ServiceMesh.java
```

## Implementation
The Service Mesh pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:microservices:service-mesh:build

# Run the pattern example
./gradlew :system-design-pattern:microservices:service-mesh:run
```

## Category
Microservices

## Java Version
Java 25
