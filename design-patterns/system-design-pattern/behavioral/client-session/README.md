# Client Session Pattern

## Overview
Manages client state across multiple requests.

## Structure
```
client-session/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/clientsession/
    └── ClientSession.java
```

## Implementation
The Client Session pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:behavioral:client-session:build

# Run the pattern example
./gradlew :system-design-pattern:behavioral:client-session:run
```

## Category
Behavioral

## Java Version
Java 25
