# 🔐 Spring Boot Security Application

A production-ready Spring Boot application demonstrating JWT-based authentication and Google OAuth2 login using Spring Security.

---

## ✨ Features

- **JWT Authentication** — Stateless token-based login with custom filters
- **Google OAuth2 Login** — Social login via OAuth2 with a custom `OAuth2SuccessHandler`
- **Spring Security** — Role-based access control and secured endpoints
- **JPA + MySQL** — Persistent user storage with Hibernate ORM
- **ModelMapper** — Clean DTO-to-entity mapping
- **Lombok** — Boilerplate-free Java code

---

## 🛠️ Tech Stack

| Technology          | Version     |
|---------------------|-------------|
| Java                | 17          |
| Spring Boot         | 4.0.5       |
| Spring Security     | 6.x         |
| Spring OAuth2 Client| 4.x         |
| JJWT                | 0.13.0      |
| MySQL               | 8.x         |
| Lombok              | Latest      |
| ModelMapper         | 3.2.6       |

---

## 📁 Project Structure

```
src/
└── main/
    ├── java/com/SecurityApp/
    │   ├── config/          # Security config, JWT filter, OAuth2 handler
    │   ├── controller/      # REST controllers
    │   ├── model/           # JPA entities
    │   ├── dto/             # Data transfer objects
    │   ├── repository/      # Spring Data JPA repositories
    │   └── service/         # Business logic
    └── resources/
        └── application.yml  # App config (uses environment variables)
```

---

## ⚙️ Prerequisites

- Java 17+
- Maven 3.8+
- MySQL 8+
- A Google Cloud OAuth2 Client ID & Secret

---

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/istiakahasan/Security_Application-Spring_boot-.git
cd Security_Application-Spring_boot-
```

### 2. Set up the database

```sql
CREATE DATABASE `security-app`;
```

### 3. Configure environment variables

Create a `.env` file in the project root (never commit this):

```env
GOOGLE_CLIENT_ID=your-google-client-id
GOOGLE_CLIENT_SECRET=your-google-client-secret
JWT_SECRET_KEY=your-jwt-secret-key
DB_PASSWORD=your-mysql-password
```

> ⚠️ The `.env` file is listed in `.gitignore` and should **never** be pushed to GitHub.

### 4. Run the application

```bash
./mvnw spring-boot:run
```

Or build and run the JAR:

```bash
./mvnw clean package
java -jar target/SecurityApplication-0.0.1-SNAPSHOT.jar
```

---

## 🔑 Authentication Flow

### JWT Login

```
POST /api/auth/login
Content-Type: application/json

{
  "username": "your-email@example.com",
  "password": "your-password"
}
```

Returns a JWT token to be included in subsequent requests:

```
Authorization: Bearer <your-jwt-token>
```

### Google OAuth2 Login

```
GET /oauth2/authorization/google
```

Redirects to Google login. On success, the custom `OAuth2SuccessHandler` issues a JWT and redirects to the frontend.

---

## 🔒 Security Configuration

- Public endpoints: `/api/auth/**`, `/oauth2/**`
- All other endpoints require a valid JWT
- Tokens are validated via a custom `JwtAuthFilter` on every request

---

## 🌍 Environment Variables Reference

| Variable              | Description                        |
|-----------------------|------------------------------------|
| `GOOGLE_CLIENT_ID`    | Google OAuth2 Client ID            |
| `GOOGLE_CLIENT_SECRET`| Google OAuth2 Client Secret        |
| `JWT_SECRET_KEY`      | Secret key used to sign JWT tokens |
| `DB_PASSWORD`         | MySQL database password            |

---

## 📌 Notes

- Make sure your Google OAuth2 redirect URI is set to `http://localhost:8080/login/oauth2/code/google` in Google Cloud Console
- JWT tokens expire based on the configured expiry duration in the security config


**Istiak Ahasan**
- GitHub: [@istiakahasan](https://github.com/istiakahasan)
