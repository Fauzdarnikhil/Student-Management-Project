# Student Management REST API

A backend REST API built using **Java, Spring Boot, Spring Data JPA, Hibernate, and MySQL** for managing student records.

The project follows a layered architecture using **Controller → Service → Repository**, along with Spring IoC and constructor-based Dependency Injection.

---

## 🚀 Features

- Create a new student
- Get all students
- Get student by ID
- Update student details
- Soft delete students
- Retrieve active student records
- Persistent data storage using MySQL
- RESTful API architecture
- Layered Controller-Service-Repository architecture
- Dependency Injection using Spring
- Database operations using Spring Data JPA and Hibernate

---

## 🛠️ Tech Stack

### Backend
- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate
- Maven

### Database
- MySQL

### Tools
- IntelliJ IDEA
- Postman
- Git
- GitHub

---

## 🏗️ Project Architecture

The application follows a layered architecture:

    Client
       ↓
    Controller
       ↓
    Service
       ↓
    Repository
       ↓
    MySQL Database

### Controller Layer

Handles incoming HTTP requests and returns appropriate responses to the client.

### Service Layer

Contains the application's business logic and acts as a bridge between the Controller and Repository layers.

### Repository Layer

Handles database operations using Spring Data JPA.

### Database Layer

MySQL is used for persistent storage of student records.

---

## 📁 Project Structure

    src
    └── main
        ├── java
        │   └── com.example.studentmanagement
        │       ├── controller
        │       │   └── StudentController.java
        │       │
        │       ├── service
        │       │   └── StudentService.java
        │       │
        │       ├── repository
        │       │   └── StudentRepository.java
        │       │
        │       ├── entity
        │       │   └── Student.java
        │       │
        │       └── StudentManagementApplication.java
        │
        └── resources
            └── application.properties

---

## 🔌 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/students` | Create a new student |
| GET | `/students` | Get all students |
| GET | `/students/{id}` | Get student by ID |
| PUT | `/students/{id}` | Update student |
| DELETE | `/students/{id}` | Soft delete student |

---

## 📌 Example API Request

### Create Student

**POST**

    http://localhost:8080/students

Request Body:

    {
        "name": "Nikhil Singh",
        "email": "nikhil@example.com",
        "age": 21
    }

---

## 🗄️ Database Configuration

The database configuration is stored in:

    src/main/resources/application.properties

Example configuration:

    spring.datasource.url=jdbc:mysql://localhost:3306/student_db
    spring.datasource.username=root
    spring.datasource.password=your_password

    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true

> Replace `your_password` with your local MySQL password.

---

## ▶️ How to Run

### 1. Clone the Repository

    git clone https://github.com/your-username/student-management-api.git

### 2. Open the Project

Open the project in **IntelliJ IDEA** or another Java IDE.

### 3. Create the MySQL Database

Run:

    CREATE DATABASE student_db;

### 4. Configure MySQL

Update your MySQL username and password in:

    application.properties

### 5. Run the Application

Run:

    StudentManagementApplication.java

Or use Maven:

    mvn spring-boot:run

The application will start at:

    http://localhost:8080

---

## 🧪 Testing

The APIs can be tested using **Postman**.

### Create Student

    POST http://localhost:8080/students

### Get All Students

    GET http://localhost:8080/students

### Get Student by ID

    GET http://localhost:8080/students/1

### Update Student

    PUT http://localhost:8080/students/1

### Delete Student

    DELETE http://localhost:8080/students/1

---

## 🔄 Request Flow

A typical request follows this flow:

    HTTP Request
         ↓
    Controller
         ↓
    Service
         ↓
    Repository
         ↓
    JPA / Hibernate
         ↓
    MySQL
         ↓
    Repository
         ↓
    Service
         ↓
    Controller
         ↓
    HTTP Response

This separation keeps the application modular, maintainable, and easier to extend.

---

## 🧠 Key Concepts Demonstrated

- Spring Boot
- Spring MVC
- REST API Development
- IoC (Inversion of Control)
- Dependency Injection
- Constructor Injection
- Spring Beans
- Layered Architecture
- CRUD Operations
- Spring Data JPA
- Hibernate ORM
- MySQL Database Integration
- Entity Mapping
- Soft Delete
- HTTP Methods
- Maven

---

## 🔮 Future Improvements

The project can be extended with:

- DTO-based request and response handling
- Input validation
- Global exception handling
- Pagination and sorting
- Advanced search and filtering
- Spring Security
- JWT Authentication
- Unit and Integration Testing
- Swagger/OpenAPI Documentation
- Dockerization

---

## 👨‍💻 Author

**Nikhil Singh**

Computer Science Student  
GLA University, Mathura

---

## 🎯 Project Purpose

This project was built to gain hands-on experience with **Java backend development and the Spring Boot ecosystem**.

It demonstrates practical implementation of REST APIs, Dependency Injection, layered architecture, Spring Data JPA, Hibernate, and MySQL database integration.
