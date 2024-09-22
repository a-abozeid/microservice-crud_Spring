# microservice-crud_Spring

This Java-based application consists of five microservices, each with a specific purpose:

1. Basic-CRUD
 - Communicates with a MySQL server to manage data.
 - Implements Spring Security with JWT to handle authentication and token generation (including refresh tokens).
 - Uses bcrypt for password hashing to enhance security.
 - Applies JPA Specification for dynamic filtering and pagination, allowing users to search by partial names and customize data retrieval.
 - Performs data validation to maintain consistency and integrity within the database.
 - Includes Mockito to unit test the service layer.
2. Naming-Server
 - Acts as a Eureka discovery server for service registration and discovery.
3. API-Gateway
 - Manages routing between services.
 - Secures routes using JWT tokens, ensuring that only verified users can access protected resources.
4. Config-Server
 - Hosts configuration files stored in a GitHub repository.
 - Secured with Spring Security to prevent unauthorized access to the configuration.
5. Rest-4CRUD
 - Provides a REST API for interaction with the Basic-CRUD service, enabling CRUD operations.

*. Additional Features:
 - Logging: Logs each method's name, arguments, and return values for debugging and auditing purposes.
 - Security Enhancement: Special character validation in all request bodies to prevent script injection attacks.
