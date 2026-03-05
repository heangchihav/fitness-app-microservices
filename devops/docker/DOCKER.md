# Backend Docker Setup

This document explains how to run backend services using Docker.

## Prerequisites
- Docker installed on your system
- Docker Compose installed on your system

## Services Included
- **API Gateway** (Port 8080) - Main entry point with OAuth2 security
- **User Service** (Port 8081) - User management with PostgreSQL
- **Todo Service** (Port 8082) - Todo management with PostgreSQL
- **Config Server** (Port 8888) - Centralized configuration
- **PostgreSQL** (Port 5432) - Database for User and Todo Services
- **Keycloak** (Port 8181) - OAuth2/OIDC authentication provider

## Quick Start

1. **Build and start all services:**
   ```bash
   docker-compose up --build
   ```

2. **Start in detached mode:**
   ```bash
   docker-compose up -d --build
   ```

3. **Stop all services:**
   ```bash
   docker-compose down
   ```

4. **Stop and remove volumes:**
   ```bash
   docker-compose down -v
   ```

## Service Dependencies
Services start in this order:
1. Infrastructure (PostgreSQL, Keycloak)
2. Config Server
3. Business Services (User, Todo)
4. API Gateway

## Health Checks
All services include health checks. You can monitor service status:
```bash
docker-compose ps
```

## Access Points
- **API Gateway**: http://localhost:8080
- **Config Server**: http://localhost:8888
- **Keycloak Admin**: http://localhost:8181/admin (admin/admin)

## Environment Variables
The services are configured with default database credentials:
- PostgreSQL: postgres/postgres
- Keycloak: admin/admin

## Troubleshooting

### Port Conflicts
If ports are already in use, modify them in `docker-compose.yml`.

### Service Startup Issues
Check logs for specific services:
```bash
docker-compose logs [service-name]
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
