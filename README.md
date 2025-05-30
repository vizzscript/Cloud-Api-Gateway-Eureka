# Cloud API Gateway with Eureka Service Registry

## Project Overview

This project demonstrates a microservices architecture using Java Spring Boot. It features a central API Gateway that routes requests to various backend services, all registered with a Eureka service registry for dynamic discovery and load balancing.

**Overall Architecture:**

The system is composed of several key components:

*   **API Gateway (`api-gateway-service`):** Acts as the single entry point for all client requests. It handles request routing, forwarding traffic to the appropriate downstream microservices. This simplifies client interaction and decouples clients from the internal microservice structure.
*   **Service Registry (`central-service`):** Utilizes Spring Eureka to allow microservices to register themselves and discover other services dynamically. This is crucial for maintaining a resilient and scalable architecture where service instances can change.
*   **Backend Microservices:**
    *   **School Service (`school-service`):** Manages school-related data (e.g., school details, courses). It uses a relational database (MySQL) via Spring Data JPA.
    *   **Student Service (`student-service`):** Manages student-related data (e.g., student profiles, enrollments). It leverages a NoSQL database (MongoDB) via Spring Data MongoDB for flexibility and scalability.

**Core Technologies:**

*   **Java Spring Boot:** For building robust and standalone microservices.
*   **Spring Cloud Gateway:** Implements the API Gateway pattern.
*   **Spring Eureka:** For service discovery and registration.
*   **Lombok:** To reduce boilerplate code (e.g., getters, setters, constructors).
*   **Spring Data JPA:** For the School service to interact with a MySQL database.
*   **Spring Data MongoDB:** For the Student service to interact with a MongoDB database.

**Service Interactions:**

1.  All microservices (School, Student, and API Gateway itself) register with the Eureka Server (`central-service`) upon startup.
2.  Client applications send requests to the API Gateway (`api-gateway-service`).
3.  The API Gateway, configured with routes, looks up the location of the target microservice (School or Student) from the Eureka Server.
4.  The API Gateway then forwards the request to an available instance of the target microservice.
5.  Services can also communicate with each other by looking up service locations from Eureka, enabling inter-service communication if needed (though this example primarily focuses on gateway-to-service interaction).

This architecture provides a decoupled, scalable, and resilient system where services can be developed, deployed, and scaled independently.

## Setup and Installation

### Prerequisites

*   **Java Development Kit (JDK):** Version 17 or later.
*   **Apache Maven:** For building the project.
*   **Docker:** Optional, but recommended for running local database instances (MySQL and MongoDB).

### 1. Cloning the Repository

Clone the project to your local machine:

```bash
git clone <repository-url> # Replace <repository-url> with the actual URL
cd <repository-folder>
```

### 2. Environment Configuration

Some services require environment-specific configurations. You'll find `.env.example` files in their respective directories. Copy these to `.env` and customize them as needed.

*   **`api-gateway-service/.env.example`:**
    *   Copy to `api-gateway-service/.env`.
    *   Contains:
        *   `SERVER_PORT`: Port for the API Gateway (e.g., `8080`).
    *   The Eureka server address is configured in `api-gateway-service/src/main/resources/application.yml` and defaults to `http://localhost:8761/eureka/`. You can modify it there if needed, or externalize it to an environment variable if you prefer for more complex setups.

*   **`school-service/.env.example`:**
    *   Copy to `school-service/.env`.
    *   Contains:
        *   `DB_URL`: JDBC URL for your MySQL database (e.g., `jdbc:mysql://localhost:3306/school_db`).
        *   `DB_USERNAME`: MySQL username (e.g., `root`).
        *   `DB_PASSWORD`: MySQL password.
    *   Ensure you have a MySQL instance running and the database `school_db` (or your configured name) created.

*   **`student-service/.env.example`:**
    *   Copy to `student-service/.env`.
    *   Contains:
        *   `MONGO_URI`: Connection URI for your MongoDB instance (e.g., `mongodb://localhost:27017/student_db`).
        *   `SERVER_PORT`: Port for the Student service (e.g., `8082`).
    *   Ensure you have a MongoDB instance running.

*   **`central-service` (Eureka Server):**
    *   Does not use an `.env` file by default. It runs on port `8761`.

### 3. Building the Project

Build all modules using Maven Wrapper:

```bash
./mvnw clean install
```
(Use `mvnw.cmd clean install` on Windows)

### 4. Running the Services

It's crucial to start the services in the correct order. Ensure any required backing services (MySQL for `school-service`, MongoDB for `student-service`) are running before starting the respective microservices.

1.  **`central-service` (Eureka Server):**
    ```bash
    ./mvnw -pl central-service spring-boot:run
    ```
    Wait for it to start up (typically on port `8761`). You can check its status at `http://localhost:8761/`.

2.  **`school-service`:**
    ```bash
    ./mvnw -pl school-service spring-boot:run
    ```
    (Ensure MySQL is running and configured in `school-service/.env`)

3.  **`student-service`:**
    ```bash
    ./mvnw -pl student-service spring-boot:run
    ```
    (Ensure MongoDB is running and configured in `student-service/.env`)

4.  **`api-gateway-service`:**
    ```bash
    ./mvnw -pl api-gateway-service spring-boot:run
    ```
    (Will connect to Eureka to discover the other services)

Once all services are running, the API Gateway (e.g., on port `8080` if configured) will be the main entry point for requests.

### Microservices Details

This section provides a brief overview of each microservice's specific role and technology.

#### 1. **School Microservice (`school-service`)**

- **Role:** Manages school-related data.
- **Technologies:** `Java Spring Boot`, `Lombok`, `Spring Data JPA`, `Spring Web`, `MySQL Driver`.
- **Database:** SQL (MySQL).
- **Functionality:** Performs CRUD operations for school entities using Spring Boot’s JPA repository and exposes RESTful API endpoints.

#### 2. **Student Microservice (`student-service`)**

- **Role:** Manages student-related data.
- **Technologies:** `Java Spring Boot`, `Lombok`, `Spring Web`, `Spring Data MongoDB`.
- **Database:** NoSQL (MongoDB).
- **Functionality:** Stores and retrieves student records using Mongo repository and exposes RESTful API endpoints.

#### 3. **Service Registry / Eureka Server (`central-service`)**

- **Role:** Service discovery and registration.
- **Technology:** `Spring Eureka Server`.
- **Functionality:**
    - Acts as a central repository maintaining the locations and statuses of all registered microservices.
    - Enables dynamic registration and discovery of services, allowing them to communicate via application names instead of hardcoded URLs.
    - Facilitates load balancing when multiple instances of a service are running (e.g., by annotating `RestTemplate` with `@LoadBalanced` in client services).

#### 4. **API Gateway (`api-gateway-service`)**

- **Role:** Single entry point for client requests and request routing.
- **Technology:** `Spring Cloud Gateway`.
- **Functionality:**
    - Intercepts all incoming client requests.
    - Uses URL patterns and service discovery (via Eureka) to route requests to the appropriate backend microservices (School or Student).
    - Simplifies the client-side by providing a unified interface to the microservices ecosystem.

## Summary of Roles

- **`central-service` (Eureka Server)**: Enables service discovery and registration.
- **`school-service`**: Manages school data using Spring Data JPA and MySQL.
- **`student-service`**: Manages student data using Spring Data MongoDB.
- **`api-gateway-service`**: Provides a single entry point, routing requests to backend services.

This setup ensures a scalable, flexible, and resilient microservices architecture with simplified communication and management.
