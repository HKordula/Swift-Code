# Swift Code Application

This project is a **Spring Boot** application that manages **SWIFT codes** using a **MySQL database**. It provides an API for retrieving SWIFT code details and supports data import from a CSV file.

## Features
- Spring Boot backend with MySQL database
- Dockerized for easy deployment
- REST API to query SWIFT codes
- Automatic database initialization
- Supports CSV data import

## Prerequisites
Make sure you have the following installed:
- **Docker & Docker Compose** (for containerized deployment)
- **Java 17+** (for local development)
- **MySQL 8+** (if using a local database)
- **Git** (to clone the repository)

## Installation & Setup

### 1. Clone the Repository
```sh
git https://github.com/HKordula/Swift-Code.git
cd Swift-Code
```

### 2. Configure Environment Variables
Create a `.env` file with the following content (example for automatic data import):
```ini
MYSQL_ROOT_PASSWORD=root
MYSQL_DATABASE=swift_codes
MYSQL_USER=user
MYSQL_PASSWORD=zaq1@WSX

SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/swiftcodesdb
SPRING_DATASOURCE_USERNAME=user
SPRING_DATASOURCE_PASSWORD=zaq1@WSX

SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.MySQL8Dialect

```

### 3. Disble MySQL server (if running locally)
If MySQL is running locally, stop it to avoid port conflicts:
```sh
net stop MySQL80
```

### 4. Run the Application with Docker
Use `docker-compose` to start the application:
```sh
docker-compose up --build
```

The application will be available at `http://localhost:8080`.

## API Endpoints
| Method | Endpoint | Description |
|--------|---------|-------------|
| `GET`  | `/v1/swift-codes/{swift-code}` | Get a SWIFT code by its identifier |
| `GET`  | `/v1/swift-codes/country/{countryISO2code}` | Get all SWIFT codes by its country ISO2 code |
| `POST` | `/v1/swift-codes/` | Add a new SWIFT code |
| `DELETE` | `/v1/swift-codes/{swift-code}` | Delete a SWIFT code |

---
