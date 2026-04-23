# Smart Campus Sensor & Room Management API

**Module:** 5COSC022W – Client-Server Architectures
**Student Name :** Nipun Gajasingha 

---

## 1. Overview

This project implements a RESTful API using **JAX-RS (Jersey)** deployed on **Apache Tomcat 9**.

The system is designed to manage **Rooms and Sensors** within a Smart Campus environment, demonstrating key client-server architecture concepts.

### Key Functionalities:

* Full CRUD operations for Rooms and Sensors
* Validation of relationships between resources
* Enforcement of business rules
* Structured and consistent error handling
* In-memory data storage using HashMaps (no external database)

The API follows REST principles using proper HTTP methods (GET, POST, DELETE) and standard status codes.

---

## 2. RESTful Design Principles

This API follows REST architectural principles:

* Resources are identified using clear URIs (e.g., `/rooms`, `/sensors`)
* HTTP methods are used appropriately (GET, POST, DELETE)
* The system is stateless (each request is independent)
* JSON is used for data representation
* Standard HTTP status codes are returned (200, 201, 404, 422, 415)

This ensures scalability, maintainability, and consistency.

---

## 3. Part 1 – Service Architecture & Setup

### Q1: JAX-RS Resource Lifecycle

JAX-RS resource classes are **request-scoped by default**, meaning a new instance is created for each incoming HTTP request.

This design prevents issues related to shared mutable state but requires a separate mechanism for storing shared data.

In this project, a centralized **DataStore class** is used with static HashMaps to persist application data during runtime.

This approach ensures:

* Data consistency across requests
* Avoidance of race conditions
* Stable shared state management

---

### Q2: Hypermedia (HATEOAS)

Hypermedia enables API responses to include links to related resources, allowing clients to dynamically navigate the API.

### Importance:

* Makes the API self-descriptive
* Reduces reliance on external documentation
* Allows flexibility if endpoints change

In this project, the root endpoint provides links to main resources such as rooms and sensors.

---

## 4. Part 2 – Room Management

### Q1: Returning IDs vs Full Objects

There are two approaches when designing API responses:

**Returning only IDs:**

* Lightweight responses
* Requires additional API calls

**Returning full objects:**

* Provides complete data in a single response
* Increases response size

### Conclusion:

A balance must be maintained between performance and usability depending on system requirements.

---

### Q2: DELETE Idempotency

DELETE operations in REST are **idempotent**, meaning repeated execution produces the same result.

* First request → resource is deleted
* Subsequent requests → resource no longer exists (404)

This ensures predictable and consistent API behavior.

---

## 5. Part 3 – Sensor Operations

### Q1: @Consumes(JSON) Mismatch

The API is configured to accept JSON input using `@Consumes(MediaType.APPLICATION_JSON)`.

If a client sends data in an unsupported format (e.g., XML), the server responds with:

→ **415 Unsupported Media Type**

This enforces strict API contracts and prevents invalid data processing.

---

### Q2: Query Parameters vs Path Parameters

Query parameters are used for filtering and optional inputs.

Advantages:

* Flexible and optional usage
* Cleaner and more readable URLs
* Easily extendable for additional filters

Therefore, query parameters are more suitable for filtering operations in REST APIs.

---

## 6. Part 4 – Sub-Resources

### Q1: Benefits of Sub-Resource Locator Pattern

The sub-resource locator pattern helps organize API endpoints into smaller, manageable components.

### Benefits:

* Improves modularity and separation of concerns
* Enhances readability
* Reduces complexity
* Simplifies maintenance and scalability

---

## 7. Part 5 – Error Handling & Logging

### Q1: Why HTTP 422 Instead of 404

HTTP **422 (Unprocessable Entity)** is used when:

* The request format is correct
* But the provided data is logically invalid

Example: Creating a sensor with a non-existent roomId.

404 is used only when the resource itself cannot be found.

---

### Q2: Risks of Exposing Stack Traces

Exposing stack traces can reveal:

* Internal class structures
* File paths
* Application logic

This creates security vulnerabilities and should be avoided.

---

### Q3: Why Use Filters for Logging

JAX-RS filters provide centralized logging.

### Advantages:

* Reduces code duplication
* Keeps resource classes clean
* Ensures consistent logging

---

## 8. System Design & Relationships

The system follows a layered architecture:

* Resource Layer (API endpoints)
* Model Layer (Room, Sensor)
* Data Layer (DataStore)

### Relationship:

The relationship between Room and Sensor is **one-to-many**:

* One room can contain multiple sensors
* Each sensor belongs to only one room

---

## 9. Business Rules & Validation

The API enforces important rules:

* A sensor cannot be created without a valid room
* A room cannot be deleted if it contains sensors

These rules ensure data integrity and consistency.

---

## 10. HTTP Status Codes Used

* 200 OK → Successful request
* 201 Created → Resource created
* 404 Not Found → Resource does not exist
* 422 Unprocessable Entity → Invalid data
* 415 Unsupported Media Type → Wrong format

---

## 11. Deployment on Apache Tomcat

The application is deployed as a **WAR file** on Apache Tomcat 9.

### Steps:

1. Configure Apache Tomcat in NetBeans
2. Set Tomcat as the project server
3. Build the project
4. Deploy the application
5. Access API via deployed URL

### Note on Credentials:

Tomcat credentials are not included in this README.
Including credentials such as `admin/admin` is not recommended and is not required for this coursework.

---

## 12. Conclusion

This project demonstrates a well-structured RESTful API using JAX-RS and Apache Tomcat.

Key achievements include:

* Proper RESTful API design
* Validation and business rule enforcement
* Clean and modular architecture
* Effective error handling

The system successfully models a Smart Campus environment while ensuring:

*  Data consistency
*  Maintainability
*  Scalability

---

