# Journal App

**Description**: The primary goal of this project is to manage journal of an user. Built on the Spring Boot framework, this application serves as the backend for a Journal App, providing essential functionalities for handling journal-related tasks.

---

## Table of Contents
1. [Features](#features)
2. [Installation](#installation)
3. [Usage](#usage)
4. [Technologies Used](#technologies-used)
5. [Contributing](#contributing)
6. [Contact](#contact)

---

## Features
- Manage journal data and related entities.
- Used an external weather API with **Redis**.
- Send mails for their sentiment analysis with **Kafka**.
- Log in and sign up features are also included.
- Used Spring Boot Security and JWT.
- RESTful API for CRUD operations on employees and their associated data.
- Easy integration with a frontend via well-defined API endpoints.

Example:
- Employee management with role-based access control.
- Fetch all the Projects of an employee or fetch all the employees based on a designation.
- Integration with PostgreSQL for database management.

---

## Installation

Provide a step-by-step guide to setting up the project locally.

1. **Clone the repository:**

   ```bash
   git clone https://github.com/SajibBarua-art/Employee-Management-System
   ```

2. **Navigate to the project directory:**

   ```bash
   cd Employee-Management-System
   ```

3. **Build your project:** (using Maven)

   ```bash
   mvn clear install
   ```

4. **Configure the MongoDB database:**

   Set up the database connection in **application.yml** (for Spring Boot projects):

   ```
   spring:
    server:
     port: 8080
    redis:
     host: redis-15906.c305.ap-south-1-1.ec2.redns.redis-cloud.com
     port: 15906
     password: vZ7QJPylyydod1ZMP1aet9y3IoETw2Rn
    data:
     mongodb:
      uri: mongodb+srv://<username>:<password>@cluster0.smd1bmx.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0
      database: journaldb
      auto-index-creation: true
   ```

5. **Configure the mail:**

   Set up the email connection in **application.yml**:

   ```
   spring:
    mail:
      host: smtp.gmail.com
      port: 587
      username: your_email_id
      password: xxxx xxxx xxxx xxxx
      properties:
        mail:
          smtp:
            auth: true
            starttls:
              enable: true
   ```

6. **Configure the Redis:**

   Set up the Redis connection in **application.yml**:

   ```
   spring:
    redis:
     host: redis-15906.c305.ap-south-1-1.ec2.redns.redis-cloud.com
     port: 15906
     password: your_redis_password
   ```

7. **Configure the Kafka:**

   Set up the Kafka connection in **application.yml**:

   ```
   spring:
    kafka:
      bootstrap-servers: pkc-921jm.us-east-2.aws.confluent.cloud:9092
      producer:
        key-serializer: org.apache.kafka.common.serialization.StringSerializer
        value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
      consumer:
        group-id: weekly-sentiment-group
        key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
        value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
        properties:
          spring:
            json:
              trusted:
                packages: com.example.journalApp.model
      properties:
        security:
          protocol: SASL_SSL
        sasl:
          mechanism: PLAIN
          jaas:
            config: org.apache.kafka.common.security.plain.PlainLoginModule required username='your_kafka_username' password='your_kafka_password';
        session:
          timeout:
            ms: 45000
   ```

8. **Configure the JWT secret key:**

   Add the line in **application.properties**:

   ```
   jwt:
    util:
     secret:
      key: TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$V=your_secret_key
   ```

9. **Configure the logback:**

   Set up the logback in **logback.xml**:

   ```
   <configuration>

    <appender name = "myConsoleAppender" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>
                %d{yy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg %n
            </pattern>
        </encoder>
    </appender>

    <appender name = "myFileAppender" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>
            journalApp.log
        </file>
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <fileNamePattern>journalApp-%d{yy-MM-dd_HH-mm}.%i.log</fileNamePattern>
            <maxFileSize>10MB</maxFileSize>
            <maxHistory>10</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>
                %d{yy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg %n
            </pattern>
        </encoder>
    </appender>

    <root level="INFO">
        <appender-ref ref="myConsoleAppender" />
        <appender-ref ref="myFileAppender" />
    </root>


   </configuration>
   ```

10. **Configure the api.weatherstack.com:**

    Set up the weatherstack connection in **application.yml**:

    ```
    weather:
      api:
        key: your_weathestack_api_key
    ```

    Set up weathestack set up in **MongoDB**:
   - create a database named **config_journal_app**
   - Inside the database post this object:

    ```
    {
      key:"WEATHER_API"
      value:"http://api.weatherstack.com/current?access_key=<apiKey>&query=<city>"
    }
    ```

---

## Usage
1. **Firstly, to create a new employee or sign up, navigate to:**

   ```http://localhost:8080/public/signup```

   Into the Postman >> body >> raw >> json, pass them:

   ```
   {
      "userName": "Robin",
      "password": "Robin",
      "email": "robin@gmail.com"
   }
   ```

2. **Secondly, to log in, navigate to:**

   ```http://localhost:8080/public/login```

   Into the Postman >> body >> raw >> json, pass them:

   ```
   {
       "userName": "Robin",
       "password": "robin"
   }
   ```

   After succesfull log in, you will get a **Bearer** token.

3. **To set Bearer token in Postman:**
   Postman >> Authorization >> Auth Type >> Bearer Token.

   For more Details: [(visit the Postman collection)](https://journal-app.postman.co/workspace/Journal-app-Workspace~6e5c16be-a0e7-40a3-a4e4-0fbb78360ee9/collection/36644546-c7d731be-efe0-4ee8-8967-8aaa175115be?action=share&creator=36644546)

---

## Technologies Used
- **Java:** (version 1.8)
- **Spring Boot:** (version 2.7.16)
- **Spring Security version:** (version 2.7.16)
- **MongoDB:** (version 3.1.4)
- **Redis:** (version 2.7.16)
- **Kafka:** (version 2.8.11)
- **Maven/Gradle:** Maven

---

## Contributing
- Fork the repository.
- Create a new branch ```(git checkout -b feature-branch)```
- Commit your changes ```(git commit -m 'Add some feature')```
- Push to the branch ```(git push origin feature-branch)```
- Open a pull request.

---

## Contact
For any kinds of suggestions, issues, or contributions:
- Email: sajib715b@gmail.com
- LinkedIn: [Sajib Barua](https://www.linkedin.com/in/sajib-barua-475814203)