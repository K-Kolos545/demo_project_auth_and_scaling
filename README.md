# demo_project_auth_and_scaling

A Spring Boot backend project demonstrating scalable authentication architecture concepts, including federated authentication, external identity providers, JWT authentication, login analytics, and distributed backend communication.

---

# Roles

The roles are defined but are not actively used in this demo project.  
They are included to reflect a production-like architecture and allow easy future extension to role-based access control.

---

# Authentication Types

## Local Authentication

For local users:
- registration is required
- passwords are stored hashed
- the backend validates credentials locally
- JWT tokens are issued after successful login

---

## Federated Authentication (Mock SAML / eduID-like Flow)

This project also demonstrates a simplified federated authentication flow inspired by systems such as eduID and SAML-based identity federation.

In this flow:
- users do not register manually in the main application
- authentication is delegated to an external identity provider
- the main backend does not validate passwords directly

Instead:
1. the main backend forwards the credentials to the external provider service
2. the provider validates the credentials against its own database
3. the provider returns identity information
4. the main backend issues its own JWT token

This simulates real-world federated identity architectures where authentication is handled externally.

---

# Architecture

```text
Client
   ↓
Main Backend Application (JWT + Analytics)
   ↓ REST
Mock External Identity Provider
   ↓
Provider Database
