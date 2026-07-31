# Service Discovery Pattern

## Overview
Enables services to find each other dynamically.

## Structure
```
service-discovery/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/servicediscovery/
    └── ServiceDiscovery.java
```

## Implementation
The Service Discovery pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:microservices:service-discovery:build

# Run the pattern example
./gradlew :system-design-pattern:microservices:service-discovery:run
```

## Category
Microservices

## Java Version
Java 25
