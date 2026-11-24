

\# API + Database Integration Testing Framework



\## Project Overview



This project demonstrates end-to-end API and database integration testing using Java, RestAssured, TestNG, JDBC, and MySQL.



The framework validates REST API operations and verifies that the corresponding data is correctly stored, updated, and deleted in the MySQL database.



\## Technology Stack



\- Java 17

\- Maven

\- Spring Boot

\- RestAssured

\- TestNG

\- MySQL 9

\- JDBC

\- Postman

\- Log4j2

\- ExtentReports

\- Eclipse IDE



\## Project Architecture



The project consists of three main components:



1\. \*\*User Management API:\*\* Spring Boot application exposing REST endpoints.

2\. \*\*API Automation Framework:\*\* Java-based automation framework using RestAssured and TestNG.

3\. \*\*MySQL Database:\*\* Stores user records and supports direct database validation through JDBC.



\### Testing Flow



```text

Java TestNG Framework

&#x20;       |

&#x20;       v

&#x20;  RestAssured

&#x20;       |

&#x20;       v

&#x20;User Management API

&#x20;   (Spring Boot)

&#x20;       |

&#x20;       v

&#x20;  MySQL Database

&#x20;       ^

&#x20;       |

&#x20;      JDBC

&#x20;       |

&#x20;Database Validation

```



\## Project Structure



```text

api-database-integration-testing/

|

|-- user-management-api/

|-- api-automation-tests/

|-- postman/

|-- database/

|   |-- user\_management\_setup.sql

|

|-- screenshots/

|   |-- Extent\_Report.png

|   |-- TestNG\_Results.png

|

|-- .gitignore

|-- README.md

```



\## API Endpoints



Base URL: `http://localhost:8081`



| Method | Endpoint | Description |

|---|---|---|

| POST | /api/users | Create user |

| GET | /api/users | Retrieve all users |

| GET | /api/users/{id} | Retrieve user by ID |

| PUT | /api/users/{id} | Update user |

| DELETE | /api/users/{id} | Delete user |



\## Automated Test Scenarios



The framework covers the following scenarios:



1\. Create a new user and verify the API response and database record.

2\. Retrieve a user by ID.

3\. Retrieve all users.

4\. Update user details and verify database changes.

5\. Verify duplicate email rejection.

6\. Delete a user and verify database deletion.

7\. Verify that a deleted user returns 404.

8\. Verify that a nonexistent user returns 404.



\## Database Validation



JDBC is used to connect directly to MySQL.



Database validations include:



\- Verify user existence by email.

\- Verify user existence by ID.

\- Retrieve user email using ID.

\- Verify updated last name and phone number.

\- Verify user deletion.

\- Verify database connection.



\## Postman Testing



A Postman collection is included in the `postman` folder.



It contains CRUD requests and automated response assertions for the User Management API.



Import the collection into Postman and execute it using Collection Runner.



\## Reports and Logging



The framework uses:



\- \*\*TestNG:\*\* Test execution and result management.

\- \*\*ExtentReports:\*\* HTML execution reports.

\- \*\*Log4j2:\*\* Execution logs.



Generated report location:



`test-output/ExtentReport.html`



Log file location:



`logs/automation.log`



These generated files are excluded from Git using `.gitignore`.



\## Test Execution



\### Prerequisites



\- JDK 17 or later

\- Maven

\- MySQL Server

\- Eclipse IDE (optional)



\### Database Configuration



Create the database using the SQL script in:



`database/user\_management\_setup.sql`



Configure the database password as a Windows environment variable:



`DB\_PASSWORD`



Do not store database credentials in source code.



\### Start the Application



Run the Spring Boot application from the `user-management-api` project.



The API should be available at:



`http://localhost:8081`



\### Run Automation Tests



Navigate to the `api-automation-tests` directory and execute:



```bash

mvn clean test

```



\## Test Execution Results



\*\*Latest verified execution:\*\*



\- Total tests: 8

\- Passed: 8

\- Failed: 0

\- Skipped: 0



\## Screenshots



\### Extent Report



!\[Extent Report](screenshots/Extent\_Report.png)



\### TestNG Results



!\[TestNG Results](screenshots/TestNG\_Results.png)



\## Key Skills Demonstrated



\- REST API Automation Testing

\- CRUD Operations

\- API and Database Integration Testing

\- SQL and JDBC Validation

\- Positive and Negative Testing

\- TestNG Framework Development

\- Maven Project Management

\- Logging and Reporting

\- Postman API Testing

\- Java Automation Framework Design



