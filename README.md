# Todo List App - Full Stack Microservices

A modern full-stack todo list application built with Spring Boot microservices and React.

## Architecture Overview

This project demonstrates a clean microservices architecture with the following components:

### Backend Services
- **API Gateway** - Single entry point with routing and security
- **Config Server** - Centralized configuration management
- **User Service** - User management and authentication
- **Todo Service** - Todo CRUD operations

### Infrastructure
- **PostgreSQL** - Primary database for all services
- **Keycloak** - OAuth2/OIDC authentication provider
- **Docker Compose** - Container orchestration

### Frontend
- **React 19** - Modern UI framework
- **Material-UI** - Component library
- **Redux Toolkit** - State management
- **OAuth2 PKCE** - Authentication

## Quick Start

### Prerequisites
- Docker and Docker Compose
- Node.js 18+ (for frontend development)
- Java 21+ (for local development)

### Running the Application

1. **Start Backend Services:**
   ```bash
   cd devops/docker
   docker compose up --build -d
   ```

2. **Start Frontend:**
   ```bash
   cd frontend
   npm install
   npm run dev
   ```

3. **Access the Application:**
   - Frontend: http://localhost:5173
   - API Gateway: http://localhost:8080
   - Config Server: http://localhost:8888
   - Keycloak: http://localhost:8181

## Features

### Todo Management
- Create todos with title, description, priority, and status
- View all todos with filtering capabilities
- Priority levels (High, Medium, Low)
- Real-time updates
- Responsive design

### Security & Authentication
- OAuth2/OIDC authentication via Keycloak
- JWT token-based security
- User management service

### Microservices Features
- Centralized configuration
- API Gateway routing
- Health checks and monitoring

## Technology Stack

### Backend
- **Java 21** - Programming language
- **Spring Boot 3.4** - Application framework
- **Spring Cloud** - Microservices framework
- **Spring Security** - Security framework
- **Spring Data JPA** - Database access
- **PostgreSQL** - Database
- **Keycloak** - Identity and access management

### Frontend
- **React 19** - UI framework
- **Vite** - Build tool
- **Material-UI** - Component library
- **Redux Toolkit** - State management
- **Axios** - HTTP client

### DevOps
- **Docker** - Containerization
- **Docker Compose** - Orchestration
- **Maven** - Build tool

## Project Structure

```bash
├── backend/
│   ├── infrastructure/
│   │   ├── gateway/          # API Gateway
│   │   ├── configserver/     # Config Server
│   │   └── userservice/     # User Management
│   └── services/
│       └── todolistservice/  # Todo Service
├── frontend/                # React Application
├── devops/
│   └── docker/             # Docker Compose files
└── docs/                  # Documentation
```

## Configuration

### Environment Variables
- Database connections configured in docker-compose.yml
- Service configurations in Config Server
- OAuth2 settings in authConfig.js

### Database Setup
- PostgreSQL automatically creates required databases
- JPA handles schema creation with `ddl-auto: update`

## Development

### Running Services Individually
Each service can be run independently:
```bash
cd backend/[service-name]
mvn spring-boot:run
```

### Frontend Development
```bash
cd frontend
npm run dev
```

### Stopping the Application
```bash
cd devops/docker
docker compose down
```

### Building for Production
```bash
# Backend
mvn clean package

# Frontend
npm run build
```

## API Endpoints

### Todo Service
- `GET /api/todos` - Get all todos
- `POST /api/todos` - Create todo
- `GET /api/todos/{id}` - Get todo by ID
- `PUT /api/todos/{id}` - Update todo
- `DELETE /api/todos/{id}` - Delete todo
- `GET /api/todos/status/{completed}` - Filter by status

### User Service
- `POST /api/users/register` - Register user
- `GET /api/users/{id}` - Get user details

## Authentication

The application uses OAuth2 with PKCE flow:
- Login via Keycloak at `http://localhost:8181`
- Default admin credentials: `admin/admin`
- JWT tokens used for API authentication

## Monitoring
- Service health checks available
- Logs accessible via Docker containers

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License.
