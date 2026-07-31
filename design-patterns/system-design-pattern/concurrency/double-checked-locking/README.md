# Double Checked Locking Pattern

## Overview
Reduces lock acquisition overhead.

## Structure
```
double-checked-locking/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/doublecheckedlocking/
    └── DoubleCheckedLocking.java
```

## Implementation
The Double Checked Locking pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:concurrency:double-checked-locking:build

# Run the pattern example
./gradlew :system-design-pattern:concurrency:double-checked-locking:run
```

## Category
Concurrency

## Java Version
Java 25
