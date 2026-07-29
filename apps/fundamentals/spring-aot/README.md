# Spring AOT — Ahead-of-Time Compilation Example

Demonstrates Spring's AOT (Ahead-of-Time) processing for GraalVM native image compilation, enabling instant startup and reduced memory footprint.

## 🎯 Purpose

Shows how Spring AOT optimizes applications at build time for native compilation, eliminating the need for Just-In-Time (JIT) compilation at runtime.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — Web endpoints
- `spring-aot` — AOT processing support

## 🚀 How to Run

```bash
# Standard JVM mode
./gradlew :apps:fundamentals:spring-aot:bootRun

# Native image compilation (requires GraalVM)
./gradlew :apps:fundamentals:spring-aot:nativeCompile
```

## 📚 Concepts Demonstrated

- **AOT Processing** — Build-time application analysis and optimization
- **GraalVM Native Images** — Compile Java to native machine code
- **Reflection Hints** — Configuring reflection for native compilation
- **Resource Processing** — Build-time resource handling
- **Instant Startup** — Milliseconds-level startup time
- **Reduced Memory** — Smaller heap and RSS footprint
- **Closed-world Assumption** — All classes known at build time