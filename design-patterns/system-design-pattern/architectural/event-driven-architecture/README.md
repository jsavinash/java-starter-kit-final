# Event Driven Architecture Pattern

## Overview
Systems communicate through events.

## Structure
```
event-driven-architecture/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/eventdrivenarchitecture/
    └── EventDrivenArchitecture.java
```

## Implementation
The Event Driven Architecture pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:event-driven-architecture:build

# Run the pattern example
./gradlew :system-design-pattern:architectural:event-driven-architecture:run
```

## Category
Architectural

## Java Version
Java 25
