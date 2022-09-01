# User Management

## Requirement:
To implement a simple account/authentication service.  At a minimum, your application should be able to create a user account, 
look-up a user, and authenticate a user via password.  Please demo sample http requests/responses for each of the APIs that you create

## Project Description
A simple REST UserAccount management interface that can do the following:create users, autheticate users, retrieveuser/users, delete users

### What it does?

```
    -createAccount(username, password)
        -validate username – length, alphanumeric
        -validate password – length, omitted chars
        -check duplicate user
        -encodePassword

	-getUserAccount(username)
        -return user details for a given user account
        -return an error if  no user exists

    -authenticateUser(username,Password)
        -check if the username and password cobination exists in the system. 

    -all
        List all users in the system


```
   



### Technologies used/Tech Design
Spring boot is used create this Spring based java application. 
An in Memory H2 db is used for integration testing. 
Spring RESt 
spring JPA

### Testing
Spring  mock rest template 

# Deployment
This service is exposed as a Stateless REST api which can be deployed on 
multiple servers behind a load balancer connected to a RDBMS DB like MySql for production readiness.

### Assumptions and challenges
No session management  is implemented. 
No authorization of any call is made. Any request can be called by any user. 


### Sample request and response 
```json
GET  "http://localhost:8080/users"
Response 
[
  {
    "id": 1,
    "name": "LorelaiGilmore"
  }
  {
    "id": 2,
    "name": "RoryGilmore"
  },
  {
    "id": 3,
    "name": "EmilyGilmore"
  }
]

POST http://localhost:8080/users/RoryGilmore
Body {
"id": 1,
"name": "RoryGilmore",
"password":"testpassword"
}
Response
{
"id": 1,
"name": "RoryGilmore"
}
ServletRequest:       
HTTP Method = POST
Request URI = /users
Parameters = {}
Headers = [Content-Type:"application/json;charset=UTF-8", Accept:"application/json", Content-Length:"33"]
Body = {"id":null,"name":"EmilyGilmore"}
ServletResponse:
Status = 400
Error message = null
Headers = [Content-Type:"application/json"]
Content type = application/json
Body = {"password":"Password is mandatory"}

POST     http://localhost:8080/users/authenticate
{
    "id": 1,
    "name": "RoryGilmore",
    "password":"testpassword"
}
Response
{
"id": 1,
"name": "RoryGilmore"
}
OR User with provided username and password does not exist

```
## How to install and run
Import the project as a maven project into your ide. 

## Credits
Some refresher websites used for clarity on any of the dependencies.
https://start.spring.io/
https://spring.io/guides/tutorials/rest/
https://www.baeldung.com/spring-boot-testing
https://www.baeldung.com/spring-mock-rest-template
https://www.baeldung.com/spring-hateoas-tutorial
https://www.baeldung.com/spring-security-with-maven

