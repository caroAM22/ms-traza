<br />
<div align="center">
<h3 align="center">PLAZOLETA TRACEABILITY SERVICE</h3>
  <p align="center">
    Microservice for tracking order status changes in the restaurant chain system. Built with hexagonal architecture and MongoDB.
  </p>
</div>

### Built With

* ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
* ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
* ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
* ![MongoDB](https://img.shields.io/badge/MongoDB-4EA94B?style=for-the-badge&logo=mongodb&logoColor=white)

### Architecture

* **Hexagonal Architecture (Ports & Adapters)**
* **Domain-Driven Design (DDD)**
* **Clean Architecture Principles**
* **Spring Boot 3.2.0**
* **MongoDB with Spring Data**

<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these steps.

### Prerequisites

* JDK 17 [https://jdk.java.net/java-se-ri/17](https://jdk.java.net/java-se-ri/17)
* Gradle [https://gradle.org/install/](https://gradle.org/install/)
* MongoDB [https://www.mongodb.com/try/download/community](https://www.mongodb.com/try/download/community)

### Recommended Tools
* IntelliJ Community [https://www.jetbrains.com/idea/download/](https://www.jetbrains.com/idea/download/)
* Postman [https://www.postman.com/downloads/](https://www.postman.com/downloads/)
* MongoDB Compass [https://www.mongodb.com/try/download/compass](https://www.mongodb.com/try/download/compass)

### Installation

1. Clone the repo
   ```sh
   git clone <repository-url>
   cd ms-traza
   ```

2. Install and start MongoDB
   ```bash
   # On macOS with Homebrew
   brew tap mongodb/brew
   brew install mongodb-community
   brew services start mongodb/brew/mongodb-community
   
   # Verify MongoDB is running
   mongosh --eval "db.runCommand('ping')"
   ```

3. Update the application configuration (optional)
   ```yml
   # src/main/resources/application.yml   
   spring:
     data:
       mongodb:
         host: your_host
         port: your_port
         database: your_db
   
   jwt:
     secret: your_jwt_secret_here
   ```

<!-- USAGE -->
## Usage

1. Run the application
   ```bash
   ./gradlew bootRun
   ```

2. Open Swagger UI
   ```
   http://localhost:8084/swagger-ui/index.html
   ```

## Features

* ✅ **Hexagonal Architecture** - Clean separation of concerns
* ✅ **MongoDB Integration** - NoSQL database for scalability
* ✅ **JWT Security** - Token-based authentication
* ✅ **Comprehensive Error Handling** - Clear error messages
* ✅ **Swagger Documentation** - API documentation
* ✅ **Unit Tests** - Domain layer testing
* ✅ **Validation** - Input validation with clear messages

## Testing

Run the tests:
```bash
# All tests
./gradlew test

# Domain tests only
./gradlew test --tests "com.pragma.plazoleta.domain.*"

# Specific test class
./gradlew test --tests "TraceabilityUseCaseTest"
```


