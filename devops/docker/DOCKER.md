# Backend Docker Setup

This document explains how to run the backend services using Docker.

## Prerequisites
- Docker installed on your system
- Docker Compose installed on your system

## Services Included
- **API Gateway** (Port 8080) - Main entry point with OAuth2 security
- **User Service** (Port 8081) - User management with PostgreSQL
- **Activity Service** (Port 8082) - Activity tracking with MongoDB
- **AI Service** (Port 8083) - AI recommendations with MongoDB
- **Config Server** (Port 8888) - Centralized configuration
- **Eureka Server** (Port 8761) - Service discovery
- **PostgreSQL** (Port 5432) - Database for User Service
- **MongoDB** (Port 27017) - Database for Activity and AI Services
- **RabbitMQ** (Port 5672/15672) - Message broker

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
1. Infrastructure (PostgreSQL, MongoDB, RabbitMQ)
2. Eureka (Service Discovery)
3. Config Server
4. Business Services (User, Activity, AI)
5. API Gateway

## Health Checks
All services include health checks. You can monitor service status:
```bash
docker-compose ps
```

## Access Points
- **API Gateway**: http://localhost:8080
- **Eureka Dashboard**: http://localhost:8761
- **Config Server**: http://localhost:8888
- **RabbitMQ Management**: http://localhost:15672 (admin/admin)

## Environment Variables
The services are configured with default database credentials:
- PostgreSQL: postgres/postgres
- RabbitMQ: admin/admin

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
