# Cloud API Gateway with Eureka Service Registry

This project is a miniature implementation of a microservices architecture using **Java Spring Boot**, **Spring Eureka** (Service Registry), and **Spring Cloud Gateway**. The **API Gateway** provides a unified entry point for incoming requests while abstracting the complexity of the underlying microservices.

## Developer Guide

### Running Services Locally

To run a specific microservice, use the following commands:

```bash
# Run individual services
./mvnw -pl central-service spring-boot:run
./mvnw -pl school-service spring-boot:run
./mvnw -pl student-service spring-boot:run
./mvnw -pl api-gateway-service spring-boot:run
```

### Microservices Overview

#### 1. **School Microservice**

- Dependencies: `Lombok`, `Spring Data JPA`, `Spring Web`, `MySQL Driver`
- Uses an SQL database to perform CRUD operations using Spring Boot’s JPA repository.
- Provides RESTful API endpoints via a controller to handle database interactions.

#### 2. **Student Microservice**

- Dependencies: `Lombok`, `Spring Web`, `Spring Boot MongoDB`
- Uses a NoSQL database (MongoDB) to store and retrieve student records.
- Implements Mongo repository for CRUD operations.
- Exposes RESTful API endpoints through a controller.

#### 3. **Service Registry (Eureka Server)**

- **Purpose**: Acts as a central repository in the microservices architecture, maintaining the location and status of all registered microservices.
- **Implementation**:
  - Uses `Eureka Server` as a dependency to enable service discovery.
  - Allows microservices to register themselves dynamically and discover other services.
  - Instead of using direct URLs (e.g., `localhost:8082`), services communicate using their application names.

##### **Load Balancing with Eureka**

- Load balancing distributes requests across multiple instances of a microservice.
- **Implementation**:
  - In the `StudentService` main class, annotate `RestTemplate` with `@LoadBalanced`.
  - Requires **Netflix Eureka Server** to be running as the load balancer.

#### 4. **API Gateway (Spring Cloud Gateway)**

- **Purpose**: Acts as an intermediary between client applications and microservices.
- **Implementation**:
  - Routes all client requests through the gateway instead of directly accessing microservices.
  - Uses URL patterns to redirect requests to the appropriate microservices.

## Summary

- **Eureka Server**: Service discovery and registry.
- **School Service**: SQL-based microservice using Spring Data JPA.
- **Student Service**: NoSQL-based microservice using MongoDB.
- **API Gateway**: Single entry point for all microservices, simplifying client-side architecture.

This setup ensures scalability, flexibility, and simplified communication between microservices in a distributed system.
