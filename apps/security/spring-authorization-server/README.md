# Spring Authorization Server — OAuth 2.0 Example

Demonstrates Spring Authorization Server for implementing OAuth 2.0 and OpenID Connect authorization flows.

## 🎯 Purpose

Shows how to set up a complete OAuth 2.0 Authorization Server with client registration, JWT token issuance, and authorization code flow.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — REST endpoints
- `spring-authorization-server` — OAuth 2.0 Authorization Server
- `spring-boot-starter-security` — Spring Security
- `jjwt-api`, `jjwt-impl`, `jjwt-jackson` — JWT support

## 🚀 How to Run

```bash
./gradlew :apps:security:spring-authorization-server:bootRun
```

## 📚 Concepts Demonstrated

- **OAuth 2.0 Authorization Server** — Full authorization server implementation
- **Client Registration** — `RegisteredClient` configuration
- **Authorization Code Flow** — Standard OAuth 2.0 flow
- **JWT Token Issuance** — Signed JWT access tokens
- **Token Endpoints** — `/oauth2/token`, `/oauth2/authorize`
- **JWK Set URI** — `/oauth2/jwks` for public key distribution
- **Client Credentials Grant** — Machine-to-machine authentication
- **Authorization Code Grant** — User-facing authentication