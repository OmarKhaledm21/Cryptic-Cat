# Cryptic Cat

#### Cryptic Cat is a  social media platform for connecting with friends, sharing posts, and engaging with content.

### Key Features 

- **User Authentication**: Secure login and signup using JWT-based token system. 
- **Follow System**: Users can follow each other to stay connected. 
- **Posts**: Users can create posts with text and media. 
- **Likes and Comments**: Users can engage with posts by liking and commenting. 
- **User Profile**: Display user information, posts, followers, and profile picture.

### Installation Instructions 

1. Clone the repository: `git clone https://github.com/OmarKhaledm21/Cryptic-Cat.git` 
2. Configure MySQL database connection in `application.properties`. 
3. Build the project using Maven: `mvn clean install`. 
4. Run the application: `mvn spring-boot:run`.

### API Documentation

- A Postman collection is available to help you test the Cryptic Cat API. You can find it in the `docs` directory of this repository.

### Layered Architecture

- Project structure follows the **Layered Architecture** (also known as **N-tier Architecture**), which is a common approach in Java Spring Boot applications. Each package in the project corresponds to a distinct layer of the application, ensuring separation of concerns. Here's a breakdown of the architecture based on the package names:
	1. **config**: Contains configuration files 
	    
	2. **controller**: This is the **presentation layer**. Controllers handle incoming HTTP requests, process them using services, and return appropriate responses.
	    
	3. **entity**: Represents the **data model layer** (entities or domain models) corresponding to the database tables.
	    
	4. **enums**: Contains enumerations, typically used to define a fixed set of constants, such as roles or statuses.
	    
	5. **exception**: This layer handles exceptions, often through custom exceptions and global exception handling.
	    
	6. **mapper**: Contains classes responsible for mapping between different object types (e.g., mapping between DTOs and entities).
	    
	7. **payload (request & response)**: This handles **data transfer objects (DTOs)**, separating internal entities from external requests and responses.
	    
	8. **repository**: Represents the **data access layer** (DAO), interacting with the database to perform CRUD operations through JPA repositories.
	    
	9. **security**: Contains security configurations and logic, such as JWT handling, authorization, and authentication.
	    
	10. **service**: This is the **business logic layer**, where the core business operations are implemented.
	    
	11. **CrypticCatApplication.java**: The main class that boots up the Spring application.

		![[https://github.com/OmarKhaledm21/Cryptic-Cat/blob/main/docs/screenshots/sc1.png]]


### Project Dependencies

- This project is made using Java Spring Boot backend technology.
- Here is a list of the dependencies used in the project:
	1. **Spring Boot dependencies**:
	   - `spring-boot-starter-data-jpa`: Spring Data JPA for working with databases.
	   - `spring-boot-starter-security`: Spring Security for authentication and authorization.
	   - `spring-boot-starter-web`: To build RESTful web applications.
	   - `spring-boot-devtools`: Provides development tools like live reload during development (optional, runtime scope).
	   - `spring-boot-starter-test`: Includes testing libraries (JUnit, Mockito, etc.) for testing Spring applications (scope: test).
	   - `spring-security-test`: Adds support for testing Spring Security features (scope: test).
	
	2. **Database dependencies**:
	   - `com.h2database:h2`: H2 in-memory database (scope: test).
	   - `mysql:mysql-connector-j`: MySQL connector for interacting with MySQL databases (scope: runtime).
	
	3. **Lombok**:
	   - `org.projectlombok:lombok`: Library for reducing boilerplate code in Java, such as getters, setters, and constructors.
	
	4. **Validation dependencies**:
	   - `jakarta.validation:jakarta.validation-api`: Jakarta validation API for bean validation.
	   - `org.hibernate.validator:hibernate-validator`: Hibernate Validator, the reference implementation of the Jakarta Bean Validation API.
	
	5. **JWT dependencies**:
	   - `io.jsonwebtoken:jjwt-api`: JSON Web Token (JWT) API for creating and verifying tokens.
	   - `io.jsonwebtoken:jjwt-impl`: Implementation library for JWT.
	   - `io.jsonwebtoken:jjwt-jackson`: Integration for using JWT with Jackson for JSON processing.
	
	6. **Testing dependencies**:
	   - `org.assertj:assertj-core`: AssertJ for fluent assertion style in testing (scope: test).
	
These dependencies cover a broad range of functionalities including web development, security, data access, testing, JWT handling, and validation.
