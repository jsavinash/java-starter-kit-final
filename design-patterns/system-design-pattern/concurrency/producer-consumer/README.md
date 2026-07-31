# Producer Consumer Pattern

## Overview
Separates production and consumption of data.

## Structure
```
producer-consumer/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/producerconsumer/
    └── ProducerConsumer.java
```

## Implementation
The Producer Consumer pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:concurrency:producer-consumer:build

# Run the pattern example
./gradlew :system-design-pattern:concurrency:producer-consumer:run
```

## Category
Concurrency

## Java Version
Java 25
