# Serialized Lob Pattern

## Overview
Stores large objects as serialized data.

## Structure
```
serialized-lob/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/serializedlob/
    └── SerializedLob.java
```

## Implementation
The Serialized Lob pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:data-access:serialized-lob:build

# Run the pattern example
./gradlew :system-design-pattern:data-access:serialized-lob:run
```

## Category
Data Access

## Java Version
Java 25
