# Event Aggregator Pattern

## Overview
Collects events from multiple sources.

## Structure
```
event-aggregator/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/eventaggregator/
    └── Eventaggregator.java
```

## Implementation
The Event Aggregator pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:messaging:event-aggregator:build

# Run the pattern example
./gradlew :system-design-pattern:messaging:event-aggregator:run
```

## Category
Messaging

## Java Version
Java 25
