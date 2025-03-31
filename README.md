# Cloud-Api-Gateway-Eureka
This project is miniature application of microservice architecture built using Java spring boot, spring Eureka a service registry &amp; cloud gateway  which provides a unified entry point for incoming requests and abstracting the underlying complexity of the microservices.

## Developer Guide
### Running Services Locally
```bash
# Run a specific service
./mvnw -pl central-service spring-boot:run
./mvnw -pl school-service spring-boot:run
./mvnw -pl student-service spring-boot:run
./mvnw -pl api-gateway-service spring-boot:run
```