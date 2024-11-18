# E-Store Microservice

## Overview

The E-Store Microservice is a backend service designed to handle product catalog management for an enterprise-grade e-commerce platform. Built with Spring Boot, it provides RESTful APIs for product browsing, searching, filtering, and CRUD operations. It is designed following modern microservice architecture principles, ensuring scalability, maintainability, and performance.

## Features

- **Product Management**: Create, read, update, and delete product records.
- **Catalog Browsing**: Fetch all products with pagination support.
- **Advanced Search**: Filter products by attributes such as name, category, and price range.
- **Validation**: Ensures data integrity through robust input validation.
- **Error Handling**: Provides consistent and informative API error responses.
- **Security**: Uses Spring Security for securing API endpoints with authentication and authorization.
- **Database Integration**: PostgreSQL for production and H2 for local development and testing.

## Project Structure

The service follows a clean and modular structure for ease of development and testing:

```
src/
├── main/
│   ├── java/com/rr/store/
│   │   ├── controller/     # REST controllers for API endpoints
│   │   ├── model/          # Entity definitions
│   │   ├── repository/     # JPA repositories for data access
│   │   ├── service/        # Business logic and data processing
│   │   └── exception/      # Custom exception handling
│   ├── resources/
│       ├── application.properties # Configuration for environments
│       ├── data/merch.json         # Sample product data
│       └── static/                 # Static resources
└── test/
    ├── java/com/rr/store/          # Unit and integration tests
```

## Technology Stack

- **Framework**: Spring Boot 3.3.5
- **Programming Language**: Java 17
- **Database**: PostgreSQL (H2 for local testing)
- **Build Tool**: Maven
- **Testing Frameworks**: JUnit 5, Mockito
- **Dependency Management**: Spring Data JPA, Spring Security, Lombok, Hibernate

## API Documentation

### Base URL

`http://localhost:8080/api/products`

### Endpoints

**GET** `/api/products`
Fetches all products.

- **Response**: 200 OK
- **Example Response**:
```
[
  {
    "id": 1,
    "name": "T-shirt",
    "description": "Comfortable cotton T-shirt",
    "price": 19.99,
    "stock": 50,
    "category": "Clothing",
    "imageUrl": "http://example.com/image.jpg"
  }
]
```

**POST** `/api/products`
Creates a new product.

- **Request Body**:
```
{
  "name": "T-shirt",
  "description": "Comfortable cotton T-shirt",
  "price": 19.99,
  "stock": 50,
  "category": "Clothing",
  "imageUrl": "http://example.com/image.jpg"
}
```

- **Response**: `201 Created`
**PUT** `/api/products/{id}`
Updates an existing product by ID.

**Request Body**:
```
{
  "name": "Updated T-shirt",
  "description": "Updated description",
  "price": 24.99,
  "stock": 30,
  "category": "Clothing",
  "imageUrl": "http://example.com/new-image.jpg"
}
```
- **Response**: `200 OK`
  
**DELETE** `/api/products/{id}`
Deletes a product by ID.

- **Response**: `204 No Content`

## How to Run Locally

### Prerequisites
- Java 17 or higher
- Maven
- PostgreSQL

### Steps
1. **Clone the Repository**:
```
git clone https://github.com/username/e-store-microservice.git
cd e-store-microservice
```

2. **Set Up Database**:

- Ensure PostgreSQL is running.
- Update `application.properties` with your PostgreSQL credentials.

3. **Run the Application**:
```
./mvnw spring-boot:run
```

4. **Access the API**:

- Swagger UI (if enabled): `http://localhost:8080/swagger-ui.html`
- API Endpoints: `http://localhost:8080/api/products`

## Testing

Run unit and integration tests:
```
./mvnw test
```

Test coverage includes:

- Validation of API inputs and outputs
- Business logic in service layer
- Database interaction using in-memory H2

## Deployment

### Docker

A `Dockerfile` can be added to containerize the application. Example:
```
FROM openjdk:17-jdk-slim
COPY target/rr-store-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### CI/CD Integration
The project supports integration with GitHub Actions or Jenkins for build, test, and deploy pipelines.

## Future Enhancements
- Implement rate limiting for public API endpoints.
- Add support for GraphQL for flexible querying.
- Introduce caching for frequently accessed data.
- Integrate a monitoring tool (e.g., Prometheus/Grafana) for observability.

## License
This project is licensed under the MIT License.

## Acknowledgements
Special thanks to the contributors and the open-source community for their valuable libraries and tools.

