# Database Per Service Pattern

## Overview
Each service has its own database.

## Structure
```
database-per-service/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/databaseperservice/
    └── DatabasePerService.java
```

## Implementation
The Database Per Service pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:microservices:database-per-service:build

# Run the pattern example
./gradlew :system-design-pattern:microservices:database-per-service:run
```

## Category
Microservices

## Java Version
Java 25
