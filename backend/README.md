# Backend — Spring Boot REST API

Spring Boot 2.7 REST API with JWT authentication and PostgreSQL.

## Requirements

- Java 17
- Maven 3.8+
- PostgreSQL 16

## Configuration

`src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/school_management
spring.datasource.username=dev_user
spring.datasource.password=password
server.port=8081
```

## Run Locally

```bash
# 1. Create PostgreSQL database
psql -U postgres -c "CREATE USER dev_user WITH PASSWORD 'password';"
psql -U postgres -c "CREATE DATABASE school_management OWNER dev_user;"

# 2. Start the application
mvn spring-boot:run
```

## Run with Docker

From the project root:

```bash
docker compose up --build backend
```

## API Endpoints

| Controller | Base Path | Description |
|---|---|---|
| Auth | `/auth` | Login |
| Admin | `/admin` | Admin management |
| Dean | `/dean` | Dean management |
| Vice Dean | `/vicedean` | Vice dean management |
| Teacher | `/teachers` | Teacher management |
| Student | `/students` | Student management |
| Education Term | `/educationTerms` | Semester terms |
| Lesson | `/lessons` | Course catalogue |
| Lesson Program | `/lessonPrograms` | Weekly schedules |
| Meet | `/meet` | Advisory meetings |
| Student Info | `/studentInfo` | Grades & attendance |
| Contact Message | `/contactMessages` | Public contact form |

Full interactive documentation: `http://localhost:8081/swagger-ui/index.html`

## Grade Calculation

- Midterm weight: **40%**
- Final weight: **60%**
- Compulsory course multiplier: **1.5×**
- Elective course multiplier: **0.75×**

Letter grades: AA, BA, BB, CB, CC, DC, DD, DZ, FF
