# Exambyte 
## Full-stack web application for managing exams and assessments. 

This project focuses on clean backend architecture, security, and explicit data persistence. 

--- 
## Get started
Follow these steps to start the project in your local environment (e.g., WSL or Linux). 

### 1. Requirements 
* **Java 21** (JDK)
* **Docker & Docker Desktop**
* **GitHub OAuth App**:
* Create a new OAuth app under [GitHub Developer Settings](https://github.com/settings/developers).
* **Homepage URL**:
  ```bash
  http://localhost:8080
  ```
* **Authorization callback URL**:
  ```bash
  http://localhost:8080
  ```

### 2. Configuration (environment variables) 
We have integrated OAuth2 so that users can log in securely via GitHub. Since client secrets are sensitive, they are injected via environment variables and are not checked into the repository.
The application requires access to your GitHub app data. To do this, set the following environment variables in your terminal (or in your IDE): 
```bash
export CLIENT_ID=Your_GitHub_Client_ID 
export CLIENT_SECRET=Your_GitHub_Client_Secret
```

### 3. Role assignment 
To access protected areas after logging in, enter your GitHub username in the src/main/resources/application.yml file under the corresponding roles: 
```bash
exambyte:
  rollen:
    studenten: YourGithubName
    korrektoren: YourGithubName
    organisatoren: YourGithubName
```
    
### 4. Start Docker 
Start the PostgreSQL database via Docker Compose: 
```bash
docker compose up -d
```

### 5. Start application 
Start the application with the Gradle wrapper: 
```bash
./gradlew bootRun
```

Once the startup process is complete, you can access the application at:
```bash
http://localhost:8080
```

--- 

## Features 
- User authentication and authorization with OAuth2
- Role-based access control (student/grader/organizer)
- Server-side rendered frontend with Thymeleaf
- SQL-based persistence with explicit JDBC access
- Database versioning and migrations with Flyway

---

## Tech Stack 
### Backend
- Java 21
- Spring Boot
- Spring Security (OAuth2)
- JDBC
- Flyway
- Gradle

### Frontend 
- Thymeleaf
- HTML
- CSS

### Database 
- Relational SQL database (PostgreSQL)

--- 

### Background 
This project is based on a university project, whereby the task was set by the instructor and all implementation was carried out by [Miran](https://github.com/Narr1m) and [Kerstin](https://github.com/Kxrstin). 

--- 

### What we learned 
Test-driven development and structuring of larger Java applications with clean architecture Implementation of secure authentication and authorization processes Design of web-based services and their integration into server-side user interfaces Management of relational databases and migrations 

---

## Architecture 
The application follows the **Onion Architecture**: 
- **Domain layer** Contains the core business logic (Exams, Questions, ...) and domain models.
- **Application layer** Implements use cases and application-specific logic.
- **Presentation layer** Spring MVC Controllers and Thymeleaf templates.
Dependencies refer strictly inward to keep domain logic independent and testable.


## Images

This project uses graphics from external providers. The rights belong to the respective authors:

* **Logo (ExamByte)**: Created via [Design.com](https://www.design.com/maker/logo/creative-document-file-17357?text=ExamByte&isVariation=True)
* **Green check mark**: [iStock / Getty Images](https://www.istockphoto.com/de/vektor/h%C3%A4kchensymbol-vektor-design-vorlage-in-wei%C3%9Fem-hintergrund-gm1435212785-476685817)
* **Red cross**: [Vecteezy](https://www.vecteezy.com/vector-art/28579407-red-cross-wrong-symbol-incorrect-sign-error-in-circle)
* **Warning symbol**: [Freepik](https://www.freepik.com/premium-vector/warning-sign-with-exclamation-mark-vector-illustration-attention-symbol-information-flat-icon_129857701.htm)


