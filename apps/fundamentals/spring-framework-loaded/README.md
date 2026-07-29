# Spring Framework Loaded — Runtime Class Reloading Example

Demonstrates Spring Loaded, a JVM agent that enables hot-swapping of classes at runtime without application restart.

## 🎯 Purpose

Shows how Spring Loaded enables runtime class reloading, allowing code changes to take effect immediately during development, significantly reducing feedback loops.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — Web endpoints
- `spring-loaded` — Runtime class reloading agent

## 🚀 How to Run

```bash
./gradlew :apps:fundamentals:spring-framework-loaded:bootRun
```

## 📚 Concepts Demonstrated

- **Spring Loaded Agent** — JVM agent for hot swapping classes
- **Runtime Class Reloading** — Apply code changes without server restart
- **Application Context Refresh** — Dynamic bean reconfiguration
- **Development Workflow** — Edit code, see changes instantly
- **HotSwap vs Restart** — Comparison of hot swapping techniques