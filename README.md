# E-Store Microservice

## Overview

The E-Store Microservice is a backend service engineered for enterprise-grade e-commerce platforms. It facilitates efficient product catalog management, leveraging Spring Boot to provide robust, secure, and scalable RESTful APIs. The service adheres to modern microservice architecture principles, ensuring high maintainability and performance. It is containerized for portability and deployed via Docker and Kubernetes, with full observability provided through Prometheus and Grafana.

![Products](./misc/Screen%20Shot%202024-12-04%20at%201.22.30%20AM.png)
![Product](./misc/Screen%20Shot%202024-12-04%20at%201.23.16%20AM.png)
## Features

### Core Capabilities

- **Product Management**: Full CRUD operations for product records.
- **Catalog Browsing**: Retrieve products with advanced filtering and pagination.
- **Search and Filtering**: Query products by name, category, price range, and stock levels.
- **Error Handling**: Consistent and structured API responses for predictable client behavior.
- **Security**: Role-based access control and authentication with Spring Security.
- **Database Integration**: Supports PostgreSQL for production and H2 for testing and local development.

### Design Principles

- **Scalability**: Designed with a microservice-first architecture to support horizontal scaling and service independence.
- **Testability**: High test coverage enabled by modular code structure, unit tests (JUnit 5), and integration tests with Mockito.
- **Performance**: Efficient database interactions using Hibernate ORM with tuned entity relationships and query execution.

## Technology Stack
- **Framework**: Spring Boot (v3.3.6)
- **Language**: Java 17
- **Database**: PostgreSQL (production), H2 (development/testing)
- **Build Tool**: Maven
- **Testing Frameworks**: JUnit 5, Mockito
- **Security**: Spring Security with role-based access control
- **Serialization**: Jackson (JSON processing)
- **Utilities**: Lombok for code reduction, Hibernate for ORM

## API Documentation

**Base URL**: ``http://localhost:8080/api/products``

### Endpoints
HTTP Method | Endpoint | Description | Authentication | Example Status
- GET ``/api/products`` Fetch all products Public ``200 OK``
- POST ``/api/products`` Create a new product Admin ``201 Created``
- GET ``/api/products/{id}`` Fetch a product by ID Public ``200 OK``
- PUT ``/api/products/{id}`` Update an existing product by ID Admin ``200 OK``
- DELETE ``/api/products/{id}`` Delete a product by ID Admin ``204 No Content``

For detailed request/response payloads, see the [API Reference](#api-documentation).

## Project Structure
```
src/
├── main/
│   ├── java/com/rr/store/
│   │   ├── controller/     # RESTful API controllers
│   │   ├── domain/         # Business domain models
│   │   ├── service/        # Business logic services
│   │   ├── repository/     # JPA repositories
│   │   ├── config/         # Security and application configurations
│   │   └── exception/      # Custom exception handlers
│   ├── resources/
│       ├── application.yml          # Consolidated configuration
│       ├── data/merch.json          # Sample product data
└── test/
    ├── java/com/rr/store/           # Unit and integration tests
```

## Local Development

### Prerequisites
- Java 17 or higher
- Maven
- PostgreSQL (for production)

### Setup
1. **Clone the Repository**:

```
git clone https://github.com/username/e-store-microservice.git
cd e-store-microservice
```

2. **Run the Application:**

- Use the development profile:
```
./mvnw spring-boot:run \
  -Dspring-boot.run.profiles=dev \
  -Dspring-boot.run.mainClass=com.rr.store.RrStoreApplication

```

3. **Access the API**:

- Base URL: ``http://localhost:8080/api/products``
- Swagger (if enabled): ``http://localhost:8080/swagger-ui.html``
  
4. **Run Tests**:
```
./mvnw test
```

## Deployment

### Railway Deployment
- Configure environment variables:

``DB_HOST``, ``DB_USERNAME``, ``DB_PASSWORD``, and ``SPRING_PROFILES_ACTIVE=prod``.

- Deployment command:
```
mvn package
java -jar target/rr-store-0.0.1-SNAPSHOT.jar
```

### Docker Deployment

#### Running E-Store Microservice with Docker

The E-Store Microservice can be containerized and run using Docker, ensuring a consistent environment for development and deployment.

#### Prerequisites
- Install Docker on your system
- Ensure PostgreSQL is set up in a Docker network

#### Building the Docker Image
To build the Docker image, run:
```
docker build -t rr-store .
```

#### Running the Docker Container
To start the container:
```
docker run -d --name rr-store \
  --network shared_network \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=dev \
  -e DB_HOST=postgres-db \
  -e DB_USERNAME=postgres \
  -e DB_PASSWORD=your_secure_password \
  rr-store
```

#### Running with Docker Compose
Alternatively, use Docker Compose to manage dependencies:

1. Ensure ``docker-compose.yml`` is correctly configured.
2. Run the following command:
```
docker-compose up -d --build
```

#### Stopping and Restarting
To stop the container:
```
docker stop rr-store
```
To restart the container:
```
docker start rr-store
```
To remove the container:
```
docker rm -f rr-store
```

## Kubernetes Deployment

This service is production-ready and optimized for deployment in containerized environments using Kubernetes. The deployment architecture supports scalability, resilience, and full-stack observability through Prometheus and Grafana.

### Prerequisites
- Minikube or Kubernetes cluster
- Docker installed and configured
- Helm (for Prometheus/Grafana setup)

### Step-by-Step Deployment

1. **Start Minikube and Enable Add-ons:**
   ```bash
   minikube start
   minikube addons enable ingress
   minikube addons enable metrics-server
   ```

2. **Build Docker Image Locally (Optional for Minikube):**
   ```bash
   eval $(minikube docker-env)
   docker build -t rr-store:latest ./rr-store
   ```

3. **Apply Kubernetes Manifests:**
   ```bash
   kubectl apply -f rr-store/postgres-deployment.yaml
   kubectl apply -f rr-store/rr-store-deployment.yaml
   kubectl apply -f rr-store/rr-store-service.yaml
   ```

4. **Monitor Deployment Status:**
   ```bash
   kubectl get pods -A
   kubectl get deployments -A
   kubectl get svc -A
   ```

5. **Restart the Deployment (if needed):**
   ```bash
   kubectl rollout restart deployment rr-store
   ```

### Observability Integration

The deployment is annotated for Prometheus metrics scraping via `/actuator/prometheus`, and exposes health endpoints at `/actuator/health`. You can visualize metrics using a Grafana dashboard.

To install Prometheus and Grafana via Helm:
```bash
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update
helm install prometheus prometheus-community/kube-prometheus-stack --namespace monitoring --create-namespace
```

To port-forward the Prometheus and Grafana dashboards:
```bash
kubectl port-forward -n monitoring svc/prometheus-kube-prometheus-prometheus 9090
kubectl port-forward -n monitoring svc/prometheus-grafana 3000:80
```

### Verify Prometheus Metrics Endpoint
Once deployed, confirm `/actuator/prometheus` is reachable and serving metrics:

`curl http://<pod-ip>:8080/actuator/prometheus`

Or check Prometheus targets via http://localhost:9090/targets.


## Testing

### Test Coverage

- **Unit Tests**:
  - Validates core service logic and data transformations.
- **Integration Tests**:
  - Covers database operations and API endpoints with in-memory H2 database.

### Commands

Run all tests:
```
./mvnw test
```

## Future Enhancements

- Add GraphQL support for flexible querying.
- Implement rate limiting for enhanced security.
- Introduce caching mechanisms (e.g., Redis) for frequently accessed data.
- Add monitoring and observability tools (e.g., Prometheus, Grafana).

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE.md) file for details.

## Contact and Support

For questions or support, contact pritchard.tyler@gmail.com.
