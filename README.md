# Interview Calendar API

Calendar API for interview scheduling for job related interviews. 


## Requirements

For building and running the application you need:

- [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven 4](https://maven.apache.org)

## Running the application locally

There are several ways to run Interview Calendar API on your local machine. One way is to execute the `main` method in the `pt.rt.interviewcalendar.application.Application` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Project Structure

The project is divided into 7 layers:

-  **Application**: responsible for starting the application and its
configuration
-  **API**: responsible for the generation of the front-facing interface
- **Facade**: responsible for the implementation of the API interface
- **Management**: intermediary layer responsible for abstracting the
persistence of the data
- **Persistence**: responsible for enabling services that allow the data
persistence and all its underlying logic; JPA-based repositories were
used for data persistence using the abstraction provided by Spring
Data
- **Model**: responsible for providing the data transfer models and domain
models
- **Foundation**: responsible for providing the core exceptions of the
application

## API

For the definition of the API was used [OpenAPI 3.0](https://swagger.io/specification/).

Its Swagger JSON file can be visualized in `interviewcalendar-api\src\main\resources`:
- You can load it into [Swagger Editor](https://editor.swagger.io) to have a view on the documented API
- You can use it to generate your client code with [Swagger Codegen](https://swagger.io/tools/swagger-codegen)


