# Duplicate Application Detection System

## Project Overview
The Duplicate Application Detection System is a web-based application developed using Java, Spring Boot, and PostgreSQL. It detects duplicate application submissions by checking existing email addresses and phone numbers.

## Features
- Register new applications
- View all registered applications
- Detect duplicates based on email or phone number
- Identify the reason for duplicate detection
- View duplicate detection reports
- Interactive web dashboard

## Technologies Used
- **Backend:** Java 17, Spring Boot, Spring Data JPA, Hibernate
- **Database:** PostgreSQL
- **Frontend:** HTML, CSS, JavaScript
- **Build Tool:** Maven
- **API:** REST API

## Duplicate Detection Logic
- If the email already exists, the application is flagged as a duplicate.
- If the phone number already exists, the application is flagged as a duplicate.
- If both email and phone match, the application is flagged as a duplicate.
- If neither matches, the application is registered successfully.

## Project Structure

```text
duplicate-application-detection-system/
├── src/
│   └── main/
│       ├── java/
│       └── resources/
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## Prerequisites
- Java Development Kit (JDK) 17
- PostgreSQL
- Maven (optional if using the Maven Wrapper)

## How to Run the Project
1. Clone or download this repository.
2. Create a PostgreSQL database named `duplicate_app_db`.
3. Configure your database URL, username, and password in `application.properties`.
4. Run the Spring Boot main application class.
5. Open `http://localhost:8182` in your browser.

## Future Enhancements
- User authentication and authorization
- Improved duplicate report management
- Cloud deployment
- Automated testing

## Author
Nagireddy Greeshma

## License
This project is available for educational and portfolio purposes.
