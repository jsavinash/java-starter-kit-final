# Spring Security Kerberos — SPNEGO/SSO Authentication Example

Demonstrates Spring Security Kerberos integration for SPNEGO-based Single Sign-On (SSO) with Active Directory.

## 🎯 Purpose

Shows how to integrate Kerberos authentication protocol with Spring Security for enterprise SSO scenarios.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — REST endpoints
- `spring-boot-starter-security` — Spring Security
- `spring-security-kerberos` — Kerberos/SPNEGO support

## 🚀 How to Run

```bash
./gradlew :apps:security:spring-security-kerberos:bootRun
```

## 📚 Concepts Demonstrated

- **Kerberos Protocol** — Ticket-based authentication protocol
- **SPNEGO Authentication** — Negotiate authentication mechanism
- **Active Directory Integration** — Windows domain authentication
- **Keytab Configuration** — Service principal keytab files
- **Single Sign-On (SSO)** — Transparent authentication across services
- **Kerberos Authentication Provider** — Custom authentication provider
- **Ticket Granting Ticket (TGT)** — Kerberos ticket management