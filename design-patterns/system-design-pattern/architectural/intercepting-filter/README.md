# Intercepting Filter Pattern

## Overview
Pre-processes and post-processes requests.

## Structure
```
intercepting-filter/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/interceptingfilter/
    └── InterceptingFilter.java
```

## Implementation
The Intercepting Filter pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:intercepting-filter:build

# Run the pattern example
./gradlew :system-design-pattern:architectural:intercepting-filter:run
```

## Category
Architectural

## Java Version
Java 25
