# Fake Flight Scheduler API Application

SpringBoot Application that demonstrates REST API Development using Spring MVC, Spring Data JPA, Spring Security, JWT using Java 8 features

## API Endpoints

This REST API has 2 end-points that create and provide flight schedules

1. GET /flights - will return all flights schedule
   GET /flights?airlines={airline,...} - will return all flights schedule of the specified airlines
2. POST /flights - will create new flight schedule
	Restrictions: admin role only

This REST API is also secured via jwt authentication

1.POST /auth/signup - signup for new user

	{
		"username":"string"
		"password":"password"
		"roles":["user", "admin"]
	}
2.POST /auth/login - login to the RestAPI

	{
		"username":"string"
		"password":"string"
	}
3.POST /auth/logout - logout to the RestAPI

## Technologies used

1. Java (Programming Language)
2. Spring Boot (Application Platform)
3. Spring Data JPA (Data persistence)
4. Spring Security
5. JWT
6. H2 (Database)
7. JUnit, with Spring Testing (Unit & Integration Testing)

## Getting Started

The source code can be checked out to your local and then build and run the application either from your IDE after importing to it as a maven project, or just from a command line. Follow these steps for the command-line option:

### Prerequisites
1. Java 8
2. Maven 3
3. Git

### Installing & Running

#### Clone this repo into your local:

```
git clone https://github.com/rojonh23/fakeflightscheduler.git
```

####  Build using maven

```
mvn clean install
```

#### Start the app

```
mvn spring-boot:run
```

## Security

This Rest API uses JWT for security

ROLE_ADMIN access:
 - POST /flights
 - GET /flights

ROLE_USER access:
 - GET /flights


#### Test User Accounts
1. UserRole
	username: "user"
	password: "user123"
2. AdminRole
	username: "admin"
	password: "admin123"

## API Documentation and Integration Testing

API documentation can be accessed via [Swagger UI](http://localhost:8080/swagger-ui/)

Steps on how to test GET /flights
1. Fire payload in /auth/login using either UserRole or AdminRole account
2. Execute GET /flights API with or without 'airlines' parameter

Steps on how to test POST /flights
1. Fire payload in /auth/login using AdminRole account
2. Execute POST /flights API with payload sample below:

	{
	    "departurePort":"MEL",
	    "arrivalPort":"SYD",
	    "departureTime":"2020-06-11T09:00:23Z",
	    "arrivalTime":"2020-06-11T10:25:23Z",
	    "airline":"QF"
	}
## Running the Test Cases

Unit test available as of now for the Flight API

You can run it either from:

- Command line

```
mvn test
```

- Your IDE


	Right click on this file and "Run As JUnit Testcase"


## Database

This application is using H2 in-memory database, which (database as well as data) will be removed from memory when the application goes down.

While the application is running, you can access the [H2 Console](http://localhost:8080/h2-ui) if you want to see the data outside the application.

You can connect to the DB using the JDBC URL: 'jdbc:h2:mem:fakeflight' and user 'sa' with NO password.


## Data pre-loading

Sample data is pre-loaded using data.sql file
