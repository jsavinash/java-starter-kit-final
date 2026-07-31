# Monitoring Pattern

## Overview
Tracks system performance and errors.

## Structure
```
monitoring/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/monitoring/
    └── Monitoring.java
```

## Implementation
The Monitoring pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:microservices:monitoring:build

# Run the pattern example
./gradlew :system-design-pattern:microservices:monitoring:run
```

## Category
Microservices

## Java Version
Java 25
