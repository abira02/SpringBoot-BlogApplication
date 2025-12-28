📝 Blog Application – Spring Boot & JWT Security

A secure RESTful blogging application built with Spring Boot demonstrating authentication, authorization, and clean backend architecture.

🚀 Features

User Registration & Login with JWT Authentication

Role-based Authorization (USER / ADMIN)

CRUD operations on Posts

Post ownership validation (users can modify only their own posts)

Admin override privileges

DTO-based request & response handling

Global exception handling

Pagination & Sorting for posts

Swagger/OpenAPI documentation with JWT support

H2 in-memory database for development

🛠️ Tech Stack

Java 17

Spring Boot 3

Spring Security

Spring Data JPA

JWT (io.jsonwebtoken)

H2 Database

Lombok

Swagger / OpenAPI (springdoc)

Maven

🏗️ Project Architecture
controller → service → repository
              ↓
            DTOs
              ↓
          Entities (JPA)


Controllers handle HTTP requests

Services contain business logic

Repositories interact with the database

DTOs prevent direct entity exposure

Security handled via JWT filters

🔐 Authentication Flow

User registers via /api/auth/register

User logs in via /api/auth/login

Server returns JWT token

Client sends token in header:

Authorization: Bearer <JWT_TOKEN>


JWT filter validates token for protected APIs

📚 API Documentation (Swagger)

Once the application is running:

http://localhost:8081/swagger-ui.html

Using JWT in Swagger:

Login using /api/auth/login

Copy the token

Click Authorize

Paste:

Bearer <your-token>

📄 Sample APIs
Register
POST /api/auth/register

Login
POST /api/auth/login

Create Post
POST /api/posts

Get Posts (Pagination)
GET /api/posts?page=0&size=5&sort=createdAt,desc

Delete Post
DELETE /api/posts/{id}


Allowed for post owner or ADMIN

🧪 Database

Uses H2 in-memory database

H2 Console:

http://localhost:8081/h2-console

▶️ How to Run Locally

Clone the repository

git clone https://github.com/abira02/SpringBoot-BlogApplication.git


Open in IntelliJ IDEA

Run:

mvn clean install


Start the application

📌 What This Project Demonstrates

Secure API design

Real-world Spring Security usage

JWT-based stateless authentication

Ownership-based authorization logic

Clean layered architecture

Pagination & validation best practices

👤 Author

Abirami P
Backend Developer | Java | Spring Boot
