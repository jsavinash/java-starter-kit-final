# Event Sourcing Pattern

## Overview
Stores state changes as a sequence of events.

## Structure
```
event-sourcing/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/eventsourcing/
    └── EventSourcing.java
```

## Implementation
The Event Sourcing pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:event-sourcing:build

# Run the pattern example
./gradlew :system-design-pattern:architectural:event-sourcing:run
```

## Category
Architectural

## Java Version
Java 25
