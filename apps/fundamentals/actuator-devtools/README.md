# Spring Boot Actuator & DevTools — Example Application

Demonstrates production-ready monitoring with Actuator and development-time productivity with DevTools.

## 🎯 Purpose

Shows how to monitor and manage Spring Boot applications in production using Actuator endpoints, and enhance development workflow with DevTools for hot reloading.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — REST endpoints
- `spring-boot-starter-actuator` — Production monitoring endpoints
- `spring-boot-devtools` — Development-time tools

## 🚀 How to Run

```bash
./gradlew :apps:fundamentals:actuator-devtools:bootRun
```

## 📚 Concepts Demonstrated

- **Actuator Endpoints** — `/health`, `/info`, `/metrics`, `/beans`, `/env`, `/loggers`
- **Custom Health Indicators** — `HealthIndicator` interface for custom health checks
- **Custom Info Contributors** — `InfoContributor` interface for custom info
- **DevTools Auto-Restart** — Automatic restart on classpath changes
- **LiveReload** — Browser auto-refresh during development
- **Production-ready Features** — Monitoring, management, and metrics