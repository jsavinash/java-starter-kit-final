# Server Session Pattern

## Overview
Manages user state across multiple requests.

## Structure
```
server-session/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/serversession/
    └── ServerSession.java
```

## Implementation
The Server Session pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:resource-management:server-session:build

# Run the pattern example
./gradlew :system-design-pattern:resource-management:server-session:run
```

## Category
Resource Management

## Java Version
Java 25
