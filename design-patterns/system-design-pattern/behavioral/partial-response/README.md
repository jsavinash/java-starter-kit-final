# Partial Response Pattern

## Overview
Filters response data.

## Structure
```
partial-response/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/partialresponse/
    └── PartialResponse.java
```

## Implementation
The Partial Response pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:partial-response:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:partial-response:run
```

## Category
Behavioral

## Java Version
Java 25
