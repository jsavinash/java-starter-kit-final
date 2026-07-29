# Spring Vault — HashiCorp Vault Integration Example

Demonstrates Spring Vault for secrets management with HashiCorp Vault.

## 🎯 Purpose

Shows how to securely manage sensitive configuration (database passwords, API keys, certificates) using HashiCorp Vault.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — REST endpoints
- `spring-boot-starter-vault` — Spring Vault integration

## 🚀 How to Run

```bash
./gradlew :apps:security:spring-vault:bootRun
```

## 📚 Concepts Demonstrated

- **VaultTemplate** — Programmatic access to Vault secrets
- **@VaultPropertySource** — Inject Vault secrets as Spring properties
- **Key-Value Secrets** — Read/write KV secrets engine
- **Secret Rotation** — Dynamic secret generation and rotation
- **Authentication Methods** — Token, AppRole, Kubernetes auth
- **Vault Configuration** — Connection settings, retry policies
- **Secure Property Resolution** — Externalizing sensitive config