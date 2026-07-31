# Rate Limiting Pattern

## Overview
Controls rate of requests.

## Structure
```
rate-limiting/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/ratelimiting/
    └── RateLimiting.java
```

## Implementation
The Rate Limiting pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:resilience:rate-limiting:build

# Run the pattern example
./gradlew :system-design-pattern:resilience:rate-limiting:run
```

## Category
Resilience

## Java Version
Java 25
