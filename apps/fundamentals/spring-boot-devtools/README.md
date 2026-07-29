# Spring Boot DevTools — Example Application

Focused demonstration of Spring Boot Developer Tools for an enhanced development experience.

## 🎯 Purpose

Shows how DevTools accelerates development with automatic restarts, LiveReload, and development-time optimizations.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — REST endpoints
- `spring-boot-devtools` — Developer tools (`developmentOnly` scope)

## 🚀 How to Run

```bash
./gradlew :apps:fundamentals:spring-boot-devtools:bootRun
```

## 📚 Concepts Demonstrated

- **Automatic Restart** — Application restarts automatically on classpath changes
- **LiveReload** — Built-in LiveReload server triggers browser refresh
- **Development-only Dependencies** — `developmentOnly` configuration scope
- **Property Defaults** — DevTools overrides for development (template caching disabled, etc.)
- **Remote Debugging** — Remote application monitoring via DevTools
- **File Change Detection** — Watching classpath and resource directories