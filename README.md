# 🔐 sentinalAuth — Full Stack JWT Authentication System

A full-stack authentication and authorization system built using **Spring Boot**, **Spring Security**, **JWT**, and **React**, implementing secure stateless authentication with refresh token support, email verification, and password recovery.

---

# 🚀 Overview

This project provides a complete authentication system with:

- Secure user registration & login
- JWT-based authentication
- Refresh token mechanism
- Email verification system
- Forgot/reset password flow
- Role-based authorization
- React frontend integration
- Stateless security architecture

---

# ✨ Features

## 🔑 Authentication Features

- User Registration & Login
- BCrypt password hashing
- JWT Access Token (short-lived)
- Refresh Token (long-lived)
- Secure Logout
- Stateless authentication using Spring Security

---

## 📧 Email Features

- Email Verification
- Verification Token System
- Forgot Password
- Password Reset via Email
- Expiring Reset Tokens
- SMTP Integration using Gmail

---

## 🛡️ Authorization Features

- Role-Based Authorization
- `ROLE_USER`
- `ROLE_ADMIN`
- `ROLE_CREATOR`

---

## 🌐 Frontend Features

- React + Vite frontend
- Login Page
- Registration Page
- Forgot Password Page
- Reset Password Page
- Email Verification Page
- Axios API Integration
- React Router DOM Routing

---

# 🔐 Authentication Flow

## 1. Signup

```http
POST /auth/register
```

```text
Validate input
↓
Check existing email
↓
Hash password using BCrypt
↓
Assign default role
↓
Save user
↓
Generate verification token
↓
Send verification email
```

---

## 2. Email Verification

```http
GET /auth/verify-email?token=...
```

```text
Validate token
↓
Check token expiry
↓
Enable account
↓
Mark email as verified
↓
Delete verification token
```

---

## 3. Login

```http
POST /auth/login
```

```text
Verify credentials
↓
Generate JWT access token
↓
Generate refresh token
↓
Save refresh token
↓
Return authentication response
```

Returns:

- Access Token
- Refresh Token

---

## 4. Access Protected Routes

```http
GET /api/users/me
Authorization: Bearer <access_token>
```

```text
Request
↓
JWT Filter
↓
Validate token
↓
Load user
↓
Set SecurityContext
↓
Access secured controller
```

---

## 5. Refresh Token

```http
POST /auth/refresh
```

```text
Validate refresh token
(DB validation + expiry + revocation check)
↓
Generate new access token
↓
Return response
```

---

## 6. Forgot Password

```http
POST /auth/forgot-password
```

```text
Validate email
↓
Generate reset token
↓
Save token with expiry
↓
Send password reset email
```

---

## 7. Reset Password

```http
POST /auth/reset-password
```

```text
Validate token
↓
Check expiry
↓
Hash new password
↓
Update user password
↓
Delete reset token
```

---

## 8. Logout

```http
POST /auth/logout
```

```text
Receive refresh token
↓
Invalidate token in backend
↓
Clear refresh token cookie
↓
Terminate session
```

### 🔑 Important Note

Logout is handled at **both levels**:

- **Client-side** → Refresh token cookie is cleared
- **Server-side** → Refresh token is invalidated in database

This ensures:

- Logged-out tokens cannot be reused
- Stolen refresh tokens become useless
- Sessions are securely terminated

---

# 🗄️ Database Design

```text
users
roles
user_role
refresh_token
verification_token
forgot_password_reset_token
```

---

# 🔒 Security Highlights

- BCrypt password hashing
- JWT signed using secret key
- Stateless authentication
- Refresh token persistence
- Secure logout handling
- Email verification before account activation
- Expiring verification/reset tokens
- Role-based access control
- Spring Security filter chain

---

# 📡 API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | /auth/register | Register user |
| POST | /auth/login | Login user |
| POST | /auth/refresh | Refresh access token |
| POST | /auth/logout | Logout user |
| GET | /auth/verify-email | Verify email |
| POST | /auth/forgot-password | Send password reset email |
| POST | /auth/reset-password | Reset password |
| GET | /api/users/me | Get current user |

---

# 🛠️ Tech Stack

## Backend

- Spring Boot
- Spring Security
- Spring Data JPA
- JWT
- MySQL
- Maven
- Lombok

---

## Frontend

- React
- Vite
- Axios
- React Router DOM

---

# 📁 Project Structure

```bash
SentinalAuth/
├── backend/
└── frontend/
```

---

# ▶️ Running the Project

## Backend Setup

```bash
cd backend
```

Configure `application.properties`:

```properties
spring.datasource.url=YOUR_DB_URL
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

jwt.secret=YOUR_SECRET

spring.mail.username=YOUR_EMAIL
spring.mail.password=YOUR_APP_PASSWORD

app.base-url=http://localhost:5173
```

Run backend:

```bash
./mvnw spring-boot:run
```

Backend runs on:

```text
http://localhost:8080
```

---

## Frontend Setup

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Run frontend:

```bash
npm run dev
```

Frontend runs on:

```text
http://localhost:5173
```

---

# ⚠️ Major Issues Faced During Development

- React StrictMode duplicate API requests
- Foreign key constraint failures
- Schema drift (`user` vs `users`)
- Role seeding after DB reset
- CORS configuration issues
- JWT filter route exclusions
- Frontend/backend route separation
- Nested Git repository issue
- Token validation edge cases

---

# 🧠 Learning Outcomes

- JWT authentication flow
- Refresh token architecture
- Secure logout implementation
- Spring Security filter chain
- Stateless backend design
- Role-Based Access Control (RBAC)
- React + Spring Boot integration
- SMTP email workflows
- Database relationship management
- Authentication system architecture

---

# 🚀 Future Improvements

- Docker support
- Admin dashboard
- Protected frontend routes
- Better exception handling
- Profile management
- Token cleanup scheduler
- Rate limiting
- Account lockout system

---

# 👨‍💻 Author

**Karan Sardar**
