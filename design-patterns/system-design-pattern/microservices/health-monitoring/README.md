# Health Monitoring Pattern

## Overview
Monitors health of system components.

## Structure
```
health-monitoring/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/healthmonitoring/
    └── HealthMonitoring.java
```

## Implementation
The Health Monitoring pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:microservices:health-monitoring:build

# Run the pattern example
./gradlew :system-design-pattern:microservices:health-monitoring:run
```

## Category
Microservices

## Java Version
Java 25
