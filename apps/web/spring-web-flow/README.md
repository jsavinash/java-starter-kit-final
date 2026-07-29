# Spring Web Flow — Multi-Page Workflow Example

Demonstrates Spring Web Flow for orchestrating multi-page conversational workflows.

## 🎯 Purpose

Shows how to build multi-step, stateful web flows with conversation scope, view states, and action states.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — Web container
- `spring-webflow` — Spring Web Flow engine
- `spring-boot-starter-thymeleaf` — View templating

## 🚀 How to Run

```bash
./gradlew :apps:web:spring-web-flow:bootRun
```

## 📚 Concepts Demonstrated

- **Flow Definition** — XML-based flow DSL (`<flow>`, `<view-state>`, `<action-state>`)
- **Conversation Scope** — Stateful data across multiple requests
- **View State** — Rendering views in a flow
- **Action State** — Executing business logic in transitions
- **Flow Transitions** — Navigation between states
- **Sub-flows** — Reusable nested flows
- **Flow Execution** — Flow lifecycle and execution management