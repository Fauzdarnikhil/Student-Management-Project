# Student Management System — Spring Boot REST API

A RESTful Student Management API built using **Java, Spring Boot, Spring Data JPA, Hibernate, and MySQL**. The project demonstrates how to build a maintainable backend application using a layered architecture, Data Transfer Objects (DTOs), request validation, custom exceptions, and centralized exception handling.

The application provides APIs to create, retrieve, update, and delete student records, with support for both permanent deletion and soft deletion.

## Features

- **RESTful CRUD APIs:** Create, retrieve, update, and delete student records.
- **DTO-Based Architecture:** Separate request and response objects from database entities.
- **Request Validation:** Validate student details using Jakarta Bean Validation.
- **Centralized Exception Handling:** Handle application exceptions consistently using `@RestControllerAdvice`.
- **Custom Exceptions:** Handle duplicate resources and missing student records using custom exception classes.
- **Structured Error Responses:** Return error details including timestamps, HTTP status codes, messages, request paths, and validation errors.
- **Duplicate Email Detection:** Prevent creating a student when the email already exists.
- **Soft Delete:** Mark a student as deleted without physically removing the database record.
- **Permanent Delete:** Remove a student record from the database.
- **Automatic ID Generation:** Generate student IDs using JPA identity generation.
- **Audit Timestamps:** Maintain `createdAt` and `updatedAt` fields.
- **Database Integration:** Persist student information in MySQL using Spring Data JPA and Hibernate.

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot 4.1.1 | Application framework |
| Spring Web MVC | REST API development |
| Spring Data JPA | Database access and persistence |
| Hibernate | Object-relational mapping |
| Jakarta Bean Validation | Request validation |
| MySQL | Relational database |
| Maven | Dependency management and build automation |
| Postman | API testing |

## Project Architecture

The project follows a layered architecture that separates request handling, business logic, database operations, and data transfer.

```text
                  Client
             (Postman / HTTP)
                    |
                    v
           StudentController
             REST API Layer
                    |
                    v
             StudentService
             Business Logic
                    |
                    v
            StudentRepository
             Data Access Layer
                    |
                    v
              MySQL Database


       Request DTOs / Response DTOs
                    |
                    v
        Data Transfer and Validation

       GlobalExceptionHandler
                    |
                    v
       Consistent Error Responses
```

### Layer Responsibilities

**1. Controller Layer — `StudentController.java`**

- Exposes REST endpoints under `/api/students`.
- Accepts HTTP requests and JSON request bodies.
- Uses `@Valid` to trigger validation for applicable request DTOs.
- Delegates business operations to `StudentService`.
- Returns HTTP responses using `ResponseEntity`.

**2. Service Layer — `StudentService.java`**

- Implements student management business logic.
- Converts request DTOs into entity objects.
- Converts entities into response DTOs.
- Checks whether an email already exists before creating a student.
- Retrieves and updates student records.
- Implements permanent deletion and soft deletion.
- Manages creation and update timestamps.

**3. Repository Layer — `StudentRepository.java`**

- Extends `JpaRepository<Student, Long>`.
- Uses built-in JPA methods for database operations.
- Defines derived query methods to find active students.
- Checks whether a student email already exists.

**4. Entity Layer — `Student.java`**

- Represents the student database entity.
- Uses `@Entity` to map the Java class to a database table.
- Uses `@Id` and `@GeneratedValue` for automatic ID generation.
- Stores student details, deletion status, and timestamps.

**5. DTO Layer**

The project uses separate DTOs for incoming requests and outgoing responses.

- `CreateStudentRequestDTO` — accepts data for creating a student.
- `CreateStudentResponseDTO` — returns student information after creation or retrieval.
- `UpdateStudentRequestDTO` — accepts data for updating a student.
- `UpdateStudentResponseDTO` — returns the updated student information.
- `ExceptionResponseDTO` — represents standard application error responses.
- `ValidationExceptionResponseDTO` — represents validation failures, including field-level errors.

**6. Exception Layer**

- `GlobalExceptionHandler.java` — centralizes exception handling across controllers.
- `DuplicateResourceException.java` — represents duplicate-resource errors.
- `ResourceNotFoundException.java` — represents missing-resource errors.

## Project Structure

```text
CrudDTODemo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── in/nikhil/CrudDTODemo/
│   │   │       ├── CrudDtoDemoApplication.java
│   │   │       ├── controller/
│   │   │       │   └── StudentController.java
│   │   │       ├── DTO/
│   │   │       │   ├── CreateStudentRequestDTO.java
│   │   │       │   ├── CreateStudentResponseDTO.java
│   │   │       │   ├── UpdateStudentRequestDTO.java
│   │   │       │   ├── UpdateStudentResponseDTO.java
│   │   │       │   ├── ExceptionResponseDTO.java
│   │   │       │   └── ValidationExceptionResponseDTO.java
│   │   │       ├── entity/
│   │   │       │   └── Student.java
│   │   │       ├── repostitory/
│   │   │       │   └── StudentRepository.java
│   │   │       ├── service/
│   │   │       │   └── StudentService.java
│   │   │       └── exception/
│   │   │           ├── GlobalExceptionHandler.java
│   │   │           ├── DuplicateResourceException.java
│   │   │           └── ResourceNotFoundException.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── in/nikhil/CrudDTODemo/
│               └── CrudDtoDemoApplicationTests.java
├── .mvn/
│   └── wrapper/
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## Student Data Model

The `Student` entity contains the following fields:

| Field | Type | Description |
|---|---|---|
| `id` | `Long` | Unique, automatically generated identifier |
| `name` | `String` | Student's name |
| `age` | `int` | Student's age |
| `email` | `String` | Student's email address |
| `rollNo` | `int` | Student's roll number |
| `subject` | `String` | Student's subject |
| `deleted` | `boolean` | Indicates whether the student has been soft-deleted |
| `createdAt` | `LocalDateTime` | Timestamp when the record was created |
| `updatedAt` | `LocalDateTime` | Timestamp associated with the latest creation or update |

The database generates the student ID automatically. The service initializes the deletion flag to `false` when creating a new student.

## Data Transfer Objects (DTOs)

DTOs allow the application to separate its API contract from its persistence model.

### Request DTOs

The `CreateStudentRequestDTO` accepts the student's name, email, age, roll number, and subject.

The `UpdateStudentRequestDTO` accepts the student's name, age, roll number, and subject. The current update API does not update the email address.

### Response DTOs

The create/read response includes the student ID, name, email, age, roll number, subject, message, `createdAt`, and `updatedAt`.

The update response includes the student's name, email, age, roll number, subject, message, and `updatedAt`.

This separation helps prevent clients from directly controlling entity-specific fields such as the generated ID, deletion flag, and creation timestamp.

## Request Validation

The project uses Jakarta Bean Validation annotations in `CreateStudentRequestDTO`.

| Field | Validation rule |
|---|---|
| `name` | Must not be blank and must contain between 2 and 50 characters |
| `email` | Must not be blank and must follow a standard email format |
| `age` | Must be at least 18 |
| `rollNo` | Must be positive |
| `subject` | Must not be blank |

The controller uses `@Valid` on the create request DTO. If the request violates these constraints, Spring raises a `MethodArgumentNotValidException`, which is handled centrally.

The current `UpdateStudentRequestDTO` does not define its own validation constraints, even though the controller uses `@Valid` on it.

## Centralized Exception Handling

The project implements centralized exception handling using `@RestControllerAdvice` and `@ExceptionHandler` in `GlobalExceptionHandler.java`.

Instead of repeating exception-handling logic in individual controller methods, the application handles supported exceptions in one place and returns appropriate HTTP responses.

### Exceptions Handled

| Exception | HTTP status | Purpose |
|---|---|---|
| `DuplicateResourceException` | `409 Conflict` | Handles attempts to create a student with an existing email |
| `ResourceNotFoundException` | `404 Not Found` | Handles requests for missing student records |
| `MethodArgumentNotValidException` | `400 Bad Request` | Handles request validation failures |
| `RuntimeException` | `500 Internal Server Error` | Handles runtime exceptions |
| Generic `Exception` handler | `500 Internal Server Error` | Intended as a fallback for other exceptions |

### Standard Error Response

The `ExceptionResponseDTO` provides the following fields:

- `timeStamp`
- `statusCode`
- `error`
- `message`
- `path`

Example response for a missing student:

```json
{
  "timeStamp": "2026-10-24T12:30:00",
  "statusCode": 404,
  "error": "Not Found",
  "message": "Student with id 100 not found",
  "path": "/api/students/100"
}
```

The timestamp and message are illustrative; the actual values depend on the request and exception.

### Validation Error Response

The `ValidationExceptionResponseDTO` additionally contains an `errors` map with field-specific validation messages.

Example:

```json
{
  "timeStamp": "2026-10-24T12:30:00",
  "statusCode": 400,
  "error": "Bad Request",
  "message": "Validation Failed",
  "path": "/api/students",
  "errors": {
    "name": "Student name should be b/w 2 to 50 char",
    "email": "Please use standard email format"
  }
}
```

This structure makes validation failures easier for API clients to understand.

## Getting Started

Follow these steps to run the project locally.

### Prerequisites

Install the following:

- Java Development Kit (JDK) 21
- MySQL Server
- Git
- Postman or another HTTP client
- IntelliJ IDEA or another Java IDE (optional)

Maven Wrapper is included in the project, so a separate Maven installation is not required.

### 1. Clone the Repository

```bash
git clone <YOUR_GITHUB_REPOSITORY_URL>
cd CrudDTODemo/CrudDTODemo
```

Replace `<YOUR_GITHUB_REPOSITORY_URL>` with your actual repository URL.

The second command assumes the repository retains the nested directory structure from the uploaded ZIP. If your GitHub repository contains `pom.xml` at its root, simply run `cd` into that repository directory.

### 2. Create the MySQL Database

Open MySQL and execute:

```sql
CREATE DATABASE student_crud_db;
```

The application uses the `student_crud_db` database.

### 3. Configure Database Credentials

Open:

`src/main/resources/application.properties`

Configure the MySQL connection using your local database credentials.

For safer configuration, use environment variables:

```properties
spring.application.name=CrudDTODemo

spring.datasource.url=jdbc:mysql://localhost:3306/student_crud_db
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Set the environment variables before running the application.

**Windows PowerShell:**

```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_mysql_password"
```

Replace the example values with your actual local MySQL credentials.

**Security note:** Do not commit database passwords or other secrets to GitHub. The uploaded project currently contains a hardcoded database password in `application.properties`; replace it with environment-based configuration before publishing the repository. If the password is real and has been exposed, rotate it.

### 4. Run the Application

Run the following command from the directory containing `pom.xml`.

**Windows:**

```bash
.\mvnw.cmd spring-boot:run
```

**Linux / macOS:**

```bash
./mvnw spring-boot:run
```

Alternatively, open the project in IntelliJ IDEA and run `CrudDtoDemoApplication.java`.

Once the application starts successfully, the API is available at:

```text
http://localhost:8080
```

The project provides a backend API rather than a separate frontend interface.

## API Documentation

The base URL is:

```text
http://localhost:8080/api/students
```

You can test the endpoints using Postman.

### 1. Create a Student

Creates a new student record after validating the request and checking for an existing email address.

- **Method:** `POST`
- **Endpoint:** `/api/students`
- **Success status:** `201 Created`

Request:

```http
POST http://localhost:8080/api/students
Content-Type: application/json
```

Request body:

```json
{
  "name": "Rahul Sharma",
  "email": "rahul@example.com",
  "age": 21,
  "rollNo": 101,
  "subject": "Computer Science"
}
```

The response includes the generated student ID, student details, a message, and timestamps.

If the email already exists, the service throws `DuplicateResourceException`, which the global exception handler maps to `409 Conflict`.

### 2. Get a Student by ID

Retrieves a student by ID.

- **Method:** `GET`
- **Endpoint:** `/api/students/{id}`
- **Success status:** `200 OK`

Example:

```http
GET http://localhost:8080/api/students/1
```

If the ID does not exist, the service throws `ResourceNotFoundException`, resulting in `404 Not Found`.

**Implementation detail:** The current get-by-ID method uses `findById(id)` rather than `findByIdAndDeletedIsFalse(id)`. Therefore, a soft-deleted record can still be retrieved by ID. The get-all and update operations apply the active-record filter.

### 3. Get All Active Students

Retrieves all students whose `deleted` flag is `false`.

- **Method:** `GET`
- **Endpoint:** `/api/students`
- **Success status:** `200 OK`

Example:

```http
GET http://localhost:8080/api/students
```

The response contains a JSON array of active student records. If there are no active students, the repository query returns an empty list.

### 4. Update a Student

Updates the name, age, roll number, and subject of an existing active student.

- **Method:** `PUT`
- **Endpoint:** `/api/students?id={id}`
- **Success status:** `200 OK`

Example:

```http
PUT http://localhost:8080/api/students?id=1
Content-Type: application/json
```

Request body:

```json
{
  "name": "Rahul Sharma",
  "age": 22,
  "rollNo": 101,
  "subject": "Artificial Intelligence"
}
```

The service retrieves the active student, updates the specified fields, saves the entity, and returns an `UpdateStudentResponseDTO`.

The current update API does not modify the email address. It also does not apply field-level validation constraints to `UpdateStudentRequestDTO`.

### 5. Permanently Delete a Student

Permanently removes a student record from the database.

- **Method:** `DELETE`
- **Endpoint:** `/api/students?id={id}`
- **Success status:** `204 No Content`

Example:

```http
DELETE http://localhost:8080/api/students?id=1
```

The service first checks whether the student exists. If found, it deletes the entity from the database. The response contains no body.

### 6. Soft Delete a Student

Marks a student as deleted without physically removing the record.

- **Method:** `PATCH`
- **Endpoint:** `/api/students/soft-delete?id={id}`
- **Success status:** `204 No Content`

Example:

```http
PATCH http://localhost:8080/api/students/soft-delete?id=1
```

The service finds an active student, sets `deleted` to `true`, and saves the entity.

Soft-deleted students are excluded from the get-all and update operations. The current get-by-ID implementation can still retrieve them.

## API Summary

| Operation | HTTP Method | Endpoint |
|---|---|---|
| Create student | POST | `/api/students` |
| Get student by ID | GET | `/api/students/{id}` |
| Get all active students | GET | `/api/students` |
| Update student | PUT | `/api/students?id={id}` |
| Permanently delete | DELETE | `/api/students?id={id}` |
| Soft delete | PATCH | `/api/students/soft-delete?id={id}` |

## Database and Persistence

The project uses Spring Data JPA and Hibernate to map Java entities to relational database tables.

The `StudentRepository` extends `JpaRepository<Student, Long>`, providing standard persistence operations without requiring implementations for common CRUD methods.

Additional repository methods include:

- `findByIdAndDeletedIsFalse(Long id)` — finds an active student by ID.
- `findByDeletedIsFalse()` — retrieves active students.
- `existsByEmail(String email)` — checks whether an email is already present.

The project uses the following Hibernate settings:

| Property | Purpose |
|---|---|
| `spring.jpa.hibernate.ddl-auto=update` | Updates the schema based on entity mappings |
| `spring.jpa.show-sql=true` | Displays generated SQL queries |
| `spring.jpa.properties.hibernate.format_sql=true` | Formats SQL output for readability |

For production use, consider a database migration tool such as Flyway or Liquibase and review SQL logging and schema-management settings.

## Running Tests

Run the tests using Maven Wrapper.

**Windows:**

```bash
.\mvnw.cmd test
```

**Linux / macOS:**

```bash
./mvnw test
```

The project includes a Spring Boot application-context test. Additional unit and integration tests can be added for validation, CRUD operations, duplicate emails, and exception responses.

## Current Scope and Future Improvements

Possible improvements include:

- Add validation constraints to `UpdateStudentRequestDTO`.
- Exclude soft-deleted records from get-by-ID queries.
- Use consistent resource-oriented URL conventions for update and delete operations.
- Add pagination and sorting.
- Add unit and integration tests for service and controller layers.
- Improve global exception handling and ensure every handler has a matching exception parameter type.
- Return a consistent structured error response for unexpected exceptions.
- Add API documentation using OpenAPI/Swagger.
- Add authentication and authorization using Spring Security.
- Use database migrations for production schema management.
- Configure environment variables and production-ready logging.

These are potential improvements, not claims that the features are already implemented.

## Learning Outcomes

This project demonstrates practical experience with:

- Building REST APIs using Spring Boot.
- Designing a controller-service-repository architecture.
- Using constructor-based dependency injection.
- Separating API requests and responses through DTOs.
- Mapping entities with Spring Data JPA and Hibernate.
- Validating incoming JSON using Jakarta Bean Validation.
- Implementing centralized exception handling with `@RestControllerAdvice`.
- Creating and handling custom runtime exceptions.
- Returning structured error responses and appropriate HTTP status codes.
- Implementing soft deletion and permanent deletion.
- Managing database records and timestamps.
- Configuring and running a Maven-based Spring Boot application.

## Author

**Nikhil Singh**

Computer Science Engineering Student

- GitHub: [Add your GitHub profile URL](https://github.com/)
- LinkedIn: [Add your LinkedIn profile URL](https://www.linkedin.com/)

---

This project was built to practice backend development with Spring Boot, database persistence, DTO design, request validation, and exception handling.
