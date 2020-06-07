# Afitest API
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2FSpring-Boot-Framework%2FSpring-Boot-Application-Template.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2FSpring-Boot-Framework%2FSpring-Boot-Application-Template?ref=badge_shield)

This is the Affitest API description and how to run the application

## Built With
*  	[Kotlin](https://kotlinlang.org/) -Kotlin is a cross-platform, statically typed, general-purpose programming language with type inference
* 	[Maven](https://maven.apache.org/) - Dependency Management
* 	[Spring Boot](https://spring.io/projects/spring-boot) - Framework to ease the bootstrapping and development of new Spring Applications
* 	[Hibernate](https://hibernate.org/) - Hibernate ORM is an object-relational mapping tool for the Java programming language
* 	[git](https://git-scm.com/) - Free and Open-Source distributed version control system 
* 	[Thymeleaf](https://www.thymeleaf.org/) - Modern server-side Java template engine for both web and standalone environments.


## Running the application locally
You can run the application either using an in-memory database or an actual database
For easy access,  the application uses an in-memory by default. To change this behaviour provide the mysql database credentials in /src/main/resources/applicaton-dev.properties
To run the application cd into the root directory and use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) to run the command below:
### For In-memory
./mvnw spring-boot:run
### For actual database
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev





## Documentation

* [API documentation](https://documenter.getpostman.com/view/10234227/SzRyy9K5?version=latest) - online, with code auto-generated snippets in cURL, jQuery, Java, Swift, Objective-C, Shell Ruby,Python Requests, Node, PHP and Go programming languages


## Files and Directories

The project (a.k.a. project directory) has a particular directory structure. A representative project is shown below:

```
.
├── Spring Elements
├── src
│   └── main
│       └── kotlin
│           ├── com.techustle.afitest
│           ├── com.techustle.afitest.config
│           ├── com.techustle.afitest.controller
│           ├── com.techustle.afitest.repository
|           |-- com.techustle.afitest.dto
│           ├── com.techustle.afitest.exception
│           ├── com.techustle.afitest.model
│           ├── com.techustle.afitest.security
|           |-- com.techustle.afitest.dto
│           ├── com.techustle.afitest.utils
│           └── com.techustle.afitest.service
├── src
│   └── main
│       └── resources
│           └── static
│           ├── templates
│           ├── application.properties
│           ├── error.properties
│           ├── notification.properties

├── src
│   └── test
│       └── kotlin
│           ├── com.techustle.afitest
│           ├── com.techustle.afitest.utils
│           ├── com.techustle.afitest.v1
├── JRE System Library
├── Maven Dependencies
├── bin
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## packages

- `models` — to hold our entities;
- `repository` — to communicate with the database;
- `dto` — to transform object for clients. ie Data Transfer Objects;
- `services` — to hold our business logic;
- `controllers` — to listen to the client;

- `resources/` - Contains all the static resources, templates and property files.
- `resources/application.properties` - It contains application-wide properties. Spring reads the properties defined in this file to configure your application. You can define server’s default port, server’s context path, database URLs etc, in this file.
- `resources/application-dev.properties` - Contains application properties when using an actual database
- `resources/application-test.properties` - Contains application properties when using an in-memory database and also use for testing
- `resources/error.properties` - It contains templates for error messages.
- `resources/notification.properties` - It contains templates for notification messages.

- `test/` - contains unit and integration tests

- `pom.xml` - contains all the project dependencies


 
  
## Resources

* [The 12factor app](https://12factor.net/)
* [My API Lifecycle Checklist and Scorecard](https://dzone.com/articles/my-api-lifecycle-checklist-and-scorecard)
* [HTTP Status Codes](https://www.restapitutorial.com/httpstatuscodes.html)
* [Spring Boot](https://spring.io/guides/)
* [Common application properties](https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html)
