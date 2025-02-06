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
git clone https://github.com/yourusername/swiftcode-app.git
cd swiftcode-app
```

### 2. Configure Environment Variables
Create a `.env` file with the following content:
```ini
MYSQL_ROOT_PASSWORD=root
MYSQL_DATABASE=swiftcodesdb
MYSQL_USER=user
MYSQL_PASSWORD=zaq1@WSX
```

### 3. Run the Application with Docker
Use `docker-compose` to start the application:
```sh
docker-compose up -d
```

The application will be available at `http://localhost:8080`.

### 4. Import SWIFT Codes from CSV
Copy `codes.csv` into the MySQL container:
```sh
docker cp codes.csv mysql-db:/var/lib/mysql-files/codes.csv
```

Then, log into MySQL and import the data:
```sql
USE swiftcodesdb;
LOAD DATA INFILE '/var/lib/mysql-files/codes.csv'
INTO TABLE swift_codes
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(country_iso2, swift_code, code_type, bank_name, address, town_name, country_name, time_zone);
```

## Running Locally Without Docker
1. Start MySQL and create the database:
```sql
CREATE DATABASE swiftcodesdb;
GRANT ALL PRIVILEGES ON swiftcodesdb.* TO 'user'@'localhost' IDENTIFIED BY 'zaq1@WSX';
```
2. Update `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/swiftcodesdb
spring.datasource.username=user
spring.datasource.password=zaq1@WSX
spring.jpa.hibernate.ddl-auto=update
```
3. Run the application:
```sh
mvn spring-boot:run
```

## API Endpoints
| Method | Endpoint | Description |
|--------|---------|-------------|
| `GET`  | `/api/swift-codes` | Get all SWIFT codes |
| `GET`  | `/api/swift-codes/{code}` | Get a SWIFT code by its identifier |
| `POST` | `/api/swift-codes` | Add a new SWIFT code |
| `DELETE` | `/api/swift-codes/{id}` | Delete a SWIFT code |

## Troubleshooting
- If MySQL fails to start, check logs with:
  ```sh
  docker logs mysql-db
  ```
- If Spring Boot fails to connect, verify database credentials.
- Reset containers if necessary:
  ```sh
  docker-compose down -v
  docker-compose up -d
  ```

## License
This project is licensed under the MIT License.

---
