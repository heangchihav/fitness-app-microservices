# DevOps Docker Setup

This directory contains Docker configuration for the Todo List application backend services.

## Quick Start

From the project root directory:

```bash
# Navigate to docker directory
cd devops/docker

# Build and start all services
docker compose up --build

# Or run in background
docker compose up -d --build
```

## Stop Services

```bash
# Stop all services
docker compose down

# Stop and remove volumes
docker compose down -v
```

## Services
- **API Gateway**: http://localhost:8080 (Reactive Gateway with OAuth2)
- **User Service**: http://localhost:8081
- **Todo Service**: http://localhost:8082
- **Eureka Dashboard**: http://localhost:8761
- **Config Server**: http://localhost:8888
- **Keycloak**: http://localhost:8181
- **PostgreSQL**: localhost:5432

## Keycloak Setup

Keycloak is automatically configured with:
- **Realm**: `todo-oauth2`
- **Client**: `oauth2-pkce-client` (PKCE flow)
- **Admin**: admin/admin
- **User Registration**: ✅ Enabled
- **Default Role**: USER

Access Keycloak Admin Console: http://localhost:8181/admin

### User Registration

Users can register themselves with simple username/password:
- **Registration URL**: http://localhost:8181/realms/todo-oauth2/protocol/openid-connect/registrations
- **Username Field**: ✅ Required (primary identifier)
- **Password Field**: ✅ Required
- **First Name**: ✅ Required
- **Last Name**: ✅ Required
- **Email**: ❌ Optional (not required for login)
- **Email Verification**: ❌ Disabled (for development)
- **Default Role**: USER (automatically assigned)

### Registration Flow

1. User clicks "Register" on login page
2. Fills out registration form (username, password, first name, last name)
3. Account created automatically with USER role
4. User can immediately log in with username/password

### Login Options

- **Username/Password**: ✅ Primary method
- **Email Login**: ❌ Disabled
- **Social Login**: ❌ Not configured

## OAuth2 Flow

1. Frontend redirects to: `http://localhost:8181/realms/todo-oauth2/protocol/openid-connect/auth`
2. User authenticates with Keycloak
3. Frontend receives JWT token
4. API Gateway validates JWT using Keycloak JWKS endpoint
5. Backend services receive user context

## Troubleshooting

### Keycloak Setup
If Keycloak realm is not created automatically:
```bash
# Run the setup script
./setup-keycloak.sh
```

### Port Conflicts
If ports are already in use, modify them in `docker-compose.yml`.

### Service Startup Issues
Check logs for specific services:
```bash
docker logs [service-name]
```

### Database Connection Issues
Ensure database services are healthy before application services start.

## Development vs Production
- This setup is optimized for development
- For production, consider:
  - External managed databases
  - Proper secrets management
  - Network policies
  - Resource limits

See [DOCKER.md](./DOCKER.md) for detailed documentation.
