# Fan Out Fan In Pattern

## Overview
Distributes and aggregates work.

## Structure
```
fan-out-fan-in/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/fanoutfanin/
    └── FanOutFanIn.java
```

## Implementation
The Fan Out Fan In pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:concurrency:fan-out-fan-in:build

# Run the pattern example
./gradlew :system-design-pattern:concurrency:fan-out-fan-in:run
```

## Category
Concurrency

## Java Version
Java 25
