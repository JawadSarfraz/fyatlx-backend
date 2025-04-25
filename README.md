# FYATLX Backend

This is the backend service for the FYATLX B2B solar project marketplace platform. It's built with **Spring Boot**, and provides RESTful APIs for user authentication, project submissions, and user-specific project management.

---

## Features

- ✅ JWT-based Authentication (Register/Login)
- ✅ Role-based user registration (EPC/Developer, Construction Team, etc.)
- ✅ Project submission with optional file attachment
- ✅ PostgreSQL + JPA/Hibernate ORM
- ✅ Email service (optional)
- ✅ CORS configured for Netlify frontend
- ✅ Deployed with Nginx reverse proxy + SSL (Let's Encrypt)

---

## Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Security
- PostgreSQL
- Jakarta Mail (optional)
- Maven

---

## Setup & Run (Dev)

```bash
# Clone repo
git clone https://github.com/yourname/fyatlx-backend.git
cd fyatlx-backend

# Setup PostgreSQL database locally (if not already)
# Create a database (e.g. fyatlx) and update credentials below

# Run with Maven (tests skipped)
./mvnw clean install -DskipTests
./mvnw spring-boot:run

```

## application.properties (local dev)

spring.datasource.url=jdbc:postgresql://localhost:5432/fyatlx
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT
jwt.secret=your_super_secret_key
jwt.expiration=86400000













## 🚀 Deployment (Production: EC2 + Nginx + SSL)

1. Build the JAR:

```bash
./mvnw clean package -DskipTests
```

2. Copy `.jar` to EC2 instance:

```bash
scp target/backend-0.0.1-SNAPSHOT.jar ubuntu@<your-ec2-ip>:~/fyatlx-backend/
```

3. Create external `application.properties` on EC2:

```bash
nano ~/application.properties
# Paste the production version of configs
```

4. Start the app:

```bash
java -jar target/backend-0.0.1-SNAPSHOT.jar --spring.config.location=/home/ubuntu/application.properties
```

---

## 🌍 CORS Setup (for Frontend)

CORS is configured in `CorsConfig.java`:

```java
.allowedOrigins(
    "https://gentle-truffle-fd4dd0.netlify.app", // production frontend
    "http://localhost:5173"                     // local dev
)
```

---

## ✅ API Endpoints

### 🔐 Auth
- `POST /api/auth/register`
- `POST /api/auth/login`

### 📦 Projects
- `POST /api/project/submit` (multipart with file)
- `GET /api/project/mine` (JWT required)

---

## ⚠️ Known Issues

- EmailService must be commented out if mail credentials are not configured.
- Spring Mail dependency can be excluded from production if unused.

---
