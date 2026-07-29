# Spring LDAP — Example Application

Demonstrates Spring LDAP for authentication and directory operations against LDAP servers.

## 🎯 Purpose

Shows how to integrate LDAP directories for user authentication, user search, and directory operations.

## 🧩 Key Dependencies

- `spring-boot-starter-web` — REST endpoints
- `spring-boot-starter-data-ldap` — Spring Data LDAP support

## 🚀 How to Run

```bash
./gradlew :apps:security:spring-ldap:bootRun
```

## 📚 Concepts Demonstrated

- **LDAP Authentication** — Authenticate users against LDAP directory
- **LdapTemplate** — Simplify LDAP operations (search, lookup, bind)
- **LdapRepository** — Spring Data repository for LDAP entities
- **User Search** — Search for users by attributes
- **Directory Operations** — Create, update, delete LDAP entries
- **Password Compare** — Bind-based and password-compare authentication
- **LDAP Context Source** — Connection configuration to LDAP server