# Banking Backend Application
 
A Spring Boot REST API for managing bank accounts, handling financial transactions (deposits and withdrawals), and validating core business logic.
 
## Tech Stack
 
* **Java** 25
* **Spring Boot** (4.1.1)
* * **Maven** (Build & Dependency Management)
* **Spring Data JPA** & **H2 Database** (In-Memory)
* **Spring WebMVC**
* **Project Lombok**
* **SpringDoc OpenAPI / Swagger UI** (3.1.0 - Interactive API Documentation)
 
---
 
## Features
 
* **Deposit Operations:** Add funds with strict validation (rejects zero or negative amounts).
* **Withdrawal Operations:** Securely withdraw funds with business logic checks.
* **Transfer Operations:** Securely transfer funds between accounts with business logic checks.
* **In-Memory Persistence:** Fast local setup using H2 Database.
* **Interactive API Docs:** Fully documented via Swagger UI.
 
---
 
## Running Locally
 
### Prerequisites
 
* JDK installed (Java 25 or higher)
* Maven

### Steps to Run
 
1. Clone the repository or open the project in your IDE (e.g., IntelliJ IDEA).
2. Build the project using Maven:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
   Or, run the packaged JAR directly:
   ```bash
   java -jar target/banking-backend-0.0.1-SNAPSHOT.jar
   ```
4. The application will start on the default port **8080**.
   Base URL: `http://localhost:8080`

Swagger UI for testing URL: `http://localhost:8080/swagger-ui/index.html`
H2 console to see the loaded data URL: `http://localhost:8080/h2-console`

---

## Project Structure
 
```
src
├── main
│   ├── java/.../bankingapp
│   │   ├── config       # Data loader
│   │   ├── controller   # Controller layer
│   │   ├── dto          # dto record files
│   │   ├── entity       # Entity classes
│   │   ├── repository   # Repository, database connection
│   │   └── service      # Service layer
│   └── resources
│       └── application.properties
└── test
    └── java/.../banking  # Unit & integration tests
```
