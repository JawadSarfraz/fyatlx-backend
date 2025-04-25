# 🌞 FYATLX Backend

This is the backend service for the FYATLX B2B solar project marketplace platform. It's built with **Spring Boot**, and provides RESTful APIs for user authentication, project submissions, and user-specific project management.

---

## 🚀 Features

- ✅ JWT-based Authentication (Register/Login)
- ✅ Role-based user registration (EPC/Developer, Construction Team, etc.)
- ✅ Project submission with optional file attachment
- ✅ PostgreSQL + JPA/Hibernate ORM
- ✅ Email service (optional)
- ✅ CORS configured for Netlify frontend
- ✅ Deployed with Nginx reverse proxy + SSL (Let's Encrypt)

---

## 📦 Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Security
- PostgreSQL
- Jakarta Mail (optional)
- Maven

---

## 🛠️ Setup & Run (Dev)

```bash
# Clone repo
git clone https://github.com/yourname/fyatlx-backend.git
cd fyatlx-backend

# Setup PostgreSQL database locally (if not already)
# Create a database (e.g. fyatlx) and update credentials below

# Run with Maven (tests skipped)
./mvnw clean install -DskipTests
./mvnw spring-boot:run