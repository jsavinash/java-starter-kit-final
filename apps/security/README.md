# Spring Security — Authentication & Authorization

Security-related Spring projects covering authentication, authorization, session management, secrets management, and identity protocols.

## 🛠 Tech Stack

| Component | Version |
|-----------|---------|
| Java | 25 (Amazon Corretto 25.0.4) |
| Spring Boot | 4.0.7 |
| Spring Security | Managed by Spring Boot BOM |
| JJWT | 0.12.6 (JWT support) |
| Gradle | 9.6.1 |

## Projects

| Project | Description |
|---------|-------------|
| [**spring-security**](./spring-security/) | Core Spring Security — JWT-based authentication, method-level security, security configuration |
| [**spring-authorization-server**](./spring-authorization-server/) | OAuth 2.0 Authorization Server — Client registration, JWT tokens, authorization endpoints |
| [**spring-security-kerberos**](./spring-security-kerberos/) | Kerberos/SPNEGO authentication — Active Directory SSO integration |
| [**spring-ldap**](./spring-ldap/) | LDAP directory services — Authentication, user search, directory operations |
| [**spring-vault**](./spring-vault/) | HashiCorp Vault integration — Secrets management, credential retrieval |
| [**spring-credhub**](./spring-credhub/) | Cloud Foundry CredHub — Credential management and secret rotation |
| [**spring-session**](./spring-session/) | Distributed session management with Redis |
| [**spring-session-data-geode**](./spring-session-data-geode/) | Distributed session storage with Apache Geode |

## Concepts Covered

- Authentication & Authorization
- JWT (JSON Web Tokens)
- OAuth 2.0 / OpenID Connect
- Kerberos / SPNEGO / SSO
- LDAP Directory Services
- Secrets Management (Vault, CredHub)
- Distributed Session Management
- Method-level Security

## 🚀 Build Commands

```bash
# Build all security projects
./gradlew :apps:security:spring-security:build
./gradlew :apps:security:spring-authorization-server:build
./gradlew :apps:security:spring-session:build

# Run a specific application
./gradlew :apps:security:spring-security:bootRun

# Apply code formatting
./gradlew spotlessApply
```

> **Note:** The `spring-security` module uses JJWT 0.12.6 which requires `javax.crypto.SecretKey` for HMAC-SHA key signing. Some projects require external services (LDAP, Vault, Redis) to run fully.
