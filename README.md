# 🔐 Auth Service — Spring Boot JWT Authentication API

A backend authentication and authorization service built with **Spring Boot**, **Spring Security**, and **JWT**, implementing **stateless authentication with refresh token support**.

---

## 🚀 Overview

This project provides a complete authentication system with:

* Secure login & signup
* JWT-based access control
* Refresh token mechanism
* Logout with token invalidation
* Role-based authorization

---

## ✨ Features

* User Registration & Login
* BCrypt password hashing
* JWT Access Token (short-lived)
* Refresh Token (long-lived, stored in DB)
* Logout with refresh token invalidation
* Role-Based Authorization (`ROLE_USER`, `ROLE_ADMIN`, `ROLE_CREATOR`)
* Stateless authentication using Spring Security

---

## 🔐 Authentication Flow

### 1. Signup

```http
POST /auth/register
```

```text
Validate → Check email → Hash password → Assign role → Save user
```

---

### 2. Login

```http
POST /auth/login
```

```text
Verify credentials → Generate tokens → Save refresh token → Return response
```

Returns:

* Access Token
* Refresh Token

---

### 3. Access Protected Routes

```http
GET /api/users/me
Authorization: Bearer <access_token>
```

```text
Request → JWT Filter → Validate token → Load user → Set SecurityContext → Controller
```

---

### 4. Refresh Token

```http
POST /auth/refresh
```

```text
Validate refresh token (DB + expiry + revoked)
→ Generate new access token
→ Return response
```

---

### 5. Logout

```http
POST /auth/logout
```

```text
Receive refresh token
↓
Invalidate token in backend (revoked = true OR delete)
↓
Clear refresh token cookie (maxAge = 0)
↓
User session terminated
```

### 🔑 Important Note

Logout is handled at **both levels**:

* **Client-side** → Refresh token cookie is cleared
* **Server-side** → Refresh token is invalidated in database

This ensures:

* Logged-out tokens cannot be reused
* Stolen tokens cannot generate new access tokens
* Session is securely terminated

---

## 🗄️ Database Design

```text
users
roles
user_roles
refresh_tokens
```

---

## 🔒 Security Highlights

* Passwords hashed using BCrypt
* JWT signed with secret key
* Stateless authentication (no sessions)
* Refresh tokens stored and controlled in DB
* Logout invalidates refresh tokens
* Role-based access control via Spring Security

---

## 📡 API Endpoints

| Method | Endpoint       | Description                 |
| ------ | -------------- | --------------------------- |
| POST   | /auth/register | Register user               |
| POST   | /auth/login    | Login user                  |
| POST   | /auth/refresh  | Refresh access token        |
| POST   | /auth/logout   | Logout (invalidate session) |
| GET    | /api/users/me  | Get current user (secured)  |

---

## ▶️ Running the Project

```bash
git clone <your-repo-url>
cd auth-service
mvn spring-boot:run
```

---

## 🧠 Learning Outcomes

* JWT authentication flow
* Refresh token handling
* Secure logout implementation
* Spring Security filter chain
* Stateless backend design

---

## 👨‍💻 Author

**Karan Sardar**
Backend Developer (Spring Boot)
