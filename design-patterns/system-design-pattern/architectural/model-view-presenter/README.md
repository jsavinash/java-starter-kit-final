# Model View Presenter Pattern

## Overview
Presenter mediates between Model and View.

## Structure
```
model-view-presenter/
├── build.gradle.kts
├── README.md
└── src/main/java/com/javastarterkit/patterns/modelviewpresenter/
    └── ModelViewPresenter.java
```

## Implementation
The Model View Presenter pattern is implemented as a single self-contained Java file with:
- Inner static classes/interfaces that implement the pattern
- A `demonstrate()` method that runs the pattern example
- A `main()` method that calls `demonstrate()`

## Usage
```bash
# Build the pattern
./gradlew :system-design-pattern:architectural:model-view-presenter:build

# Run the pattern example
./gradlew :system-design-pattern:architectural:model-view-presenter:run
```

## Category
Architectural

## Java Version
Java 25
