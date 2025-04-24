# FYATLX Solar Marketplace Backend

A secure Spring Boot backend application that powers the FYATLX Solar Marketplace platform. This backend handles user registration, authentication, and project submissions, and is fully integrated with AWS services.

---

## 🚀 Tech Stack

- **Java 17**  
- **Spring Boot 3**
- **Spring Security** (JWT-based auth)
- **Spring Data JPA**
- **PostgreSQL** (hosted on AWS RDS)
- **Maven**
- **AWS EC2** (for backend deployment)
- **AWS RDS** (for database hosting)

---

## 🧩 Features

- User Registration with Company Details & Role Selection
- Secure JWT-based Login/Logout
- Role-based Access Control (EPC/Developer, Construction Team, Engineering Services)
- Project Submission via REST API
- Spring Security with CORS setup for Netlify

---

## 🗂️ Project Structure (Clean Architecture)

```bash
src/main/java/com/fyatlx/backend
├── config                 # CORS, JWT Filter, Security
├── controller             # API Endpoints (Auth, Profile, Projects)
├── dto                   # Data Transfer Objects
├── entity                # JPA Entities
├── repository            # Spring Data Repositories
├── service               # Business Logic
├── util                  # Utilities
└── BackendApplication.java
```

---

## 🛠️ Getting Started (Local Dev)

### Prerequisites:
- Java 17+
- Maven
- PostgreSQL (locally or on RDS)

### Setup:
1. Clone the repo:
```bash
git clone https://github.com/JawadSarfraz/fyatlx-backend.git
cd fyatlx-backend
```
2. Update DB credentials in `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://<host>:5432/<dbname>
spring.datasource.username=your_user
spring.datasource.password=your_password
```
3. Run the application:
```bash
./mvnw spring-boot:run
```

---

## Deployment

### Backend is deployed on:
- **AWS EC2** with public access on port `8080`
- Connected to **AWS RDS PostgreSQL**

---

## Authentication Flow

1. `POST /api/auth/signup` — Register a user with name, email, password, company, and role.
2. `POST /api/auth/signin` — Authenticate user, receive JWT.
3. Use `Authorization: Bearer <token>` in headers for all protected endpoints.
