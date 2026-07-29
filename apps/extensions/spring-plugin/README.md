# Spring Plugin — Plugin Architecture Example

Demonstrates Spring Plugin for building extensible applications with a plugin-based architecture.

## 🎯 Purpose

Shows how to create applications that can be extended with plugins, supporting dynamic discovery, registration, and lifecycle management.

## 🧩 Key Dependencies

- `spring-plugin` — Spring Plugin framework

## 🚀 How to Run

```bash
./gradlew :apps:extensions:spring-plugin:bootRun
```

## 📚 Concepts Demonstrated

- **Plugin Registry** — Central registry for plugin discovery
- **Plugin Lifecycle** — Plugin initialization, activation, and shutdown
- **Plugin Context** — Isolated application context per plugin
- **Extension Points** — Define interfaces for plugin extensions
- **Dynamic Loading** — Load plugins at runtime
- **Plugin Ordering** — Priority-based plugin execution
- **Plugin Metadata** — Plugin descriptor and configuration