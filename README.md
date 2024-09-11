# microservice-crud_Spring

Java application consisting of five microservices:
Basic-CRUD:
Communicates with a MySQL server.
Utilizes Spring JWT and Security to generate tokens and refresh tokens, ensuring security and session continuity.
Validates data to maintain consistency in the database.
Hashes passwords using bcrypt.
Enhanced with JPA Specification for filtering and pagination, allowing users to search by partial names and customize data retrieval.
Naming-Server:
Uses Eureka as a discovery server.
API-Gateway:
Handles routing.
Implements JWT tokens to ensure only verified users can access the gateway.
Config-Server:
Hosts configuration files in a GitHub repository.
Secured with Spring Security to prevent unauthorized access to configuration files.
Rest-4CRUD:
Provides a REST API to interact with Basic-CRUD.

There is also logging on the level of each method (method name, arguments and returns).