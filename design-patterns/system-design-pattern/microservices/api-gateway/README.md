# Api Gateway Pattern

## Overview
Single entry point for client requests.

## Structure
```
api-gateway/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/apigateway/
    └── ApiGateway.java
```

## Implementation
The Api Gateway pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:microservices:api-gateway:build

# Run the pattern example
./gradlew :system-design-pattern:microservices:api-gateway:run
```

## Category
Microservices

## Java Version
Java 25
