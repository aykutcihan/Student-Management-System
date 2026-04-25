# CampusMS — School Management System

A full-stack school management platform built with Spring Boot and React.

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Spring Boot 2.7, Spring Security, JWT, JPA |
| Database | PostgreSQL 16 |
| Frontend | React 18, Redux Toolkit, Bootstrap 5, PrimeReact |
| Container | Docker, Docker Compose |

## Project Structure

```
Student-Management-System/
├── backend/          # Spring Boot REST API (port 8081)
├── frontend/         # React SPA (port 3000 / 80)
└── docker-compose.yml
```

## Quick Start

**Requires:** Docker & Docker Compose

```bash
git clone https://github.com/aykutcihan/Student-Management-System.git
cd Student-Management-System
docker compose up --build
```

| Service | URL |
|---|---|
| Frontend | http://localhost:3000 |
| Backend API | http://localhost:8081 |
| Swagger UI | http://localhost:8081/swagger-ui/index.html |

> First build takes ~5 minutes (Maven downloads dependencies). Subsequent builds use cache and are much faster.

## User Roles

| Role | Access |
|---|---|
| `ADMIN` | Full access — all management pages |
| `MANAGER` (Dean) | Dean, Vice Dean, Teacher, Student management |
| `ASSISTANT_MANAGER` (Vice Dean) | Student management |
| `TEACHER` | Student info, Meetings, Grades |
| `STUDENT` | Choose lessons, View grades & meetings |

## Local Development (without Docker)

### Backend

```bash
cd backend
# Requires: Java 17, Maven, PostgreSQL running on localhost:5432
# Create DB: school_management, user: dev_user, password: password
mvn spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm start
```

## Environment Variables

### Frontend

| Variable | Default | Description |
|---|---|---|
| `REACT_APP_API_URL` | `http://localhost:8081` | Backend API base URL |

Create `frontend/.env.local` to override:
```
REACT_APP_API_URL=http://localhost:8081
```

## Features

- **Authentication** — JWT-based login, role-based access control
- **Admin Management** — Manage admins, deans, vice deans, teachers, students
- **Lesson Management** — Lessons, education terms, lesson programs
- **Grade Tracking** — Midterm/final grades, automatic average & letter grade
- **Meetings** — Advisory teachers can schedule meetings with students
- **Student Portal** — Choose lesson programs, view grades and meetings
- **Contact Messages** — Public contact form, admin inbox

## API Documentation

Swagger UI is available at `http://localhost:8081/swagger-ui/index.html` when the backend is running.
