# Smart Campus Sensor & Room Management API

**Module:** 5COSC022W – Client-Server Architectures  
**Student:** Nipun Gajasingha 


---

## 1. Overview

This project implements a RESTful API using **JAX-RS (Jersey)** deployed on **Apache Tomcat 9**.

The system is designed to manage **Rooms and Sensors** within a Smart Campus environment, demonstrating core client-server architecture concepts.

### Key Functionalities:

* Full CRUD operations for Rooms and Sensors
* Validation of relationships between resources
* Enforcement of business rules (e.g., sensor must belong to a valid room)
* Structured and consistent error handling
* In-memory data storage using HashMaps (no external database)

---

## 2. Part 1 – Service Architecture & Setup

### Q1: JAX-RS Resource Lifecycle

JAX-RS resource classes are **request-scoped by default**, meaning a new object instance is created for each incoming HTTP request.

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

## 3. Part 2 – Room Management

### Q1: Returning IDs vs Full Objects

There are two approaches when designing API responses:

**Returning only IDs:**

* Lightweight responses
* Requires additional API calls to retrieve full details

**Returning full objects:**

* Provides complete data in a single response
* Increases response size

### Conclusion:

A balance must be maintained between performance and usability depending on system requirements.

---

### Q2: DELETE Idempotency

DELETE operations in REST are **idempotent**, meaning repeated execution produces the same result.

* First request - resource is deleted
* Subsequent requests - resource no longer exists (404)

This behavior ensures consistency and predictability in API operations.

---

## 4. Part 3 – Sensor Operations

### Q1: @Consumes(JSON) Mismatch

The API is configured to accept JSON input using `@Consumes(MediaType.APPLICATION_JSON)`.

If a client sends data in an unsupported format (e.g., XML), the server responds with:

**415 Unsupported Media Type**

This enforces strict API contracts and prevents invalid data processing.

---

### Q2: Query Parameters vs Path Parameters

Query parameters are used for filtering and optional inputs.

Advantages:

* Flexible and optional usage
* Cleaner and more readable URLs
* Easier to extend with additional filters

Therefore, query parameters are more suitable for search and filtering operations in REST APIs.

---

## 5. Part 4 – Sub-Resources

### Q1: Benefits of Sub-Resource Locator Pattern

The sub-resource locator pattern helps organize API endpoints into smaller, manageable components.

### Benefits:

* Improves modularity and separation of concerns
* Enhances code readability
* Reduces complexity in large resource classes
* Simplifies maintenance and future extensions

---

## 6. Part 5 – Error Handling & Logging

### Q1: Why HTTP 422 Instead of 404

HTTP **422 (Unprocessable Entity)** is used when:

* The request structure is valid
* But the provided data is logically incorrect

Example: Creating a sensor with a non-existent roomId

404 is used only when the requested resource itself cannot be found.

---

### Q2: Risks of Exposing Stack Traces

Exposing internal stack traces can reveal sensitive system details such as:

* Internal class structures
* File paths
* Application logic

This creates potential security vulnerabilities and should be avoided in production systems.

---

### Q3: Why Use Filters for Logging

JAX-RS filters provide a centralized mechanism for handling request and response logging.

### Advantages:

* Avoids repeating logging code in every resource class
* Improves code cleanliness
* Ensures consistent logging across the application

---

## 7. Deployment on Apache Tomcat

The application is deployed as a **WAR file** on Apache Tomcat 9.

### Deployment Process:

1. Configure Apache Tomcat in NetBeans
2. Set Tomcat as the project server
3. Build the project to generate the WAR file
4. Deploy the application to Tomcat
5. Access the API through the deployed context path

### Note on Credentials:

Tomcat manager credentials (e.g., username and password) are Including credentials like `admin/admin` is **not recommended** for security reasons and is not part of academic requirements.

---

## 8. Conclusion

This project demonstrates a well-structured RESTful API implementation using JAX-RS and Apache Tomcat.

Key achievements include:

* Proper use of REST principles
* Clear resource-based API design
* Implementation of validation and business rules
* Effective error handling strategies
* Maintainable and modular architecture

The system successfully models a Smart Campus environment while ensuring reliability, scalability, and clean design.

---
