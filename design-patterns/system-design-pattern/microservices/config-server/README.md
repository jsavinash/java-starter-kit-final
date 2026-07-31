# Config Server Pattern

## Overview
Centralizes configuration management.

## Structure
```
config-server/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/configserver/
    └── ConfigServer.java
```

## Implementation
The Config Server pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:microservices:config-server:build

# Run the pattern example
./gradlew :system-design-pattern:microservices:config-server:run
```

## Category
Microservices

## Java Version
Java 25
