# Leader Followers Pattern

## Overview
Optimizes thread usage with leader/follower pattern.

## Structure
```
leader-followers/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/leaderfollowers/
    └── LeaderFollowers.java
```

## Implementation
The Leader Followers pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:concurrency:leader-followers:build

# Run the pattern example
./gradlew :system-design-pattern:concurrency:leader-followers:run
```

## Category
Concurrency

## Java Version
Java 25
