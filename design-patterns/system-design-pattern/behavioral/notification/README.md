# Notification Pattern

## Overview
Collects notification messages.

## Structure
```
notification/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/notification/
    └── Notification.java
```

## Implementation
The Notification pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:notification:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:notification:run
```

## Category
Behavioral

## Java Version
Java 25
