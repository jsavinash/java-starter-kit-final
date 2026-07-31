# Double Dispatch Pattern

## Overview
Dispatches calls based on runtime types of two objects.

## Structure
```
double-dispatch/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/doubledispatch/
    └── DoubleDispatch.java
```

## Implementation
The Double Dispatch pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:double-dispatch:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:double-dispatch:run
```

## Category
Behavioral

## Java Version
Java 25
