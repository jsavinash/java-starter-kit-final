# Spring CredHub — Cloud Foundry Credential Management Example

Demonstrates Spring CredHub integration for managing credentials in Cloud Foundry environments.

## 🎯 Purpose

Shows how to integrate with Cloud Foundry CredHub for secure credential storage, retrieval, and rotation.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — REST endpoints
- `spring-credhub` — Spring CredHub integration

## 🚀 How to Run

```bash
./gradlew :apps:security:spring-credhub:bootRun
```

## 📚 Concepts Demonstrated

- **CredHub Integration** — Cloud Foundry credential management
- **Credential Retrieval** — Fetch credentials by name and type
- **Credential Generation** — Generate passwords, certificates, SSH keys
- **Secret Rotation** — Rotate credentials with versioning
- **Credential References** — Reference credentials in application config
- **Permission Management** — ACL-based access control for credentials