# Auth Service - Spring Boot JWT Authentication API

A backend authentication service built with **Spring Boot**, **Spring Security**, **JWT**, and **MySQL**.
This project provides secure user registration, login, role-based authorization, access tokens, and refresh token support.

---

## Features

* User Registration / Signup
* Secure Login with Email + Password
* Password Hashing using BCrypt
* JWT Access Token Authentication
* Refresh Token Support
* Role-Based Authorization (`ROLE_USER`, `ROLE_ADMIN`,`ROLE_CREATOR`)
* Stateless Authentication using Spring Security
* MySQL Database Integration
* Environment Variable Based Secret Key Configuration

---

## Tech Stack

* Java
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* MySQL
* JWT (JJWT)
* Lombok
* ModelMapper
* Maven

---

## Project Structure

```text
src/main/java/com/example/demo/authservice

├── Config
├── controllers
├── Dtos
│   ├── requests
│   └── responses
├── Entities
├── exceptions
├── filters
├── repositories
└── services
```

---

## Authentication Flow

### Signup

```text
POST /auth/register
```

Creates a new user with encrypted password and assigns default role.

### Login

```text
POST /auth/login
```

Validates credentials and returns:

* Access Token
* Refresh Token (HttpOnly Cookie)

### Protected Routes

Use header:

```text
Authorization: Bearer <access_token>
```

---

## Example Login Request

```json
{
  "email": "user@gmail.com",
  "password": "password123"
}
```

---

## Example Login Response

```json
{
  "id": 1,
  "name": "Karan",
  "email": "user@gmail.com",
  "roles": ["ROLE_USER"],
  "accessToken": "jwt_token_here",
  "refreshToken": "jwt_refresh-token_here"
}
```

---

## Environment Variables

Create environment variable:

```bash

export JWT_SECRET=your_super_secret_key_here
```

```bash

export DB_PASSWORD=your_DB_password_here
```

Application uses:

```yaml
jwt:
  secretKey: ${JWT_SECRET}
```


---

## Database Configuration

Update `application.yml`

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/Auth_service
    username: your_username
    password: ${DB_PASSWORD}
```

---

## Security Highlights

* Passwords stored hashed using BCrypt
* JWT signed using secret key
* Stateless sessions
* HttpOnly refresh token cookie
* Route protection using Spring Security Filter Chain

---

## Current Endpoints

| Method | Endpoint       | Description                                    |
| ------ | -------------- | ---------------------------------------------- |
| POST   | /auth/register | Register user                                  |
| POST   | /auth/login    | Login user                                     |
| POST   | /auth/refresh  | Refresh access token *(planned / in progress)* |

---

## Future Improvements

* Logout endpoint
* Email verification
* Password reset flow
* Token revocation blacklist
* Swagger API docs
* Docker deployment
* Unit & Integration tests

---

## How To Run

```bash
git clone <your-repo-url>
cd auth-service
mvn spring-boot:run
```

---

## Learning Goals of This Project

This project was built to deeply understand:

* Spring Security internals
* JWT authentication flow
* Filter chains
* Role-based access control
* Real-world backend architecture

---

## Author

Built by Karan Sardar as a backend portfolio project.
