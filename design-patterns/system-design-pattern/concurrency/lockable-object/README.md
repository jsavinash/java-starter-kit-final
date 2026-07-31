# Lockable Object Pattern

## Overview
Provides lock mechanism for objects.

## Structure
```
lockable-object/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/lockableobject/
    └── LockableObject.java
```

## Implementation
The Lockable Object pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:concurrency:lockable-object:build

# Run the pattern example
./gradlew :system-design-pattern:concurrency:lockable-object:run
```

## Category
Concurrency

## Java Version
Java 25
