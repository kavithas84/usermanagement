# usermanagement

##Requirement:
To implement a simple account/authentication service.  At a minimum, your application should be able to create a user account, 
look-up a user, and authenticate a user via password.  Please demo sample http requests/responses for each of the APIs that you create

## project description
A simple REST UserAccount management interface that can do the following:create users, autheticate users, retrieveuser/users, delete users

### What it does?
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





### Technologies used/TEch Design
Spring boot is used create this Spring based java application. 
An in Memory H2 db is used for integration testing. 

### Assumptions and challenges
No session management or login is created. 
No authentication of the call is made. Any request can be called by any user. 



##How to install and run
Import the project as a maven project into your ide. 

##Credits
Some refresher websites used for clarity on any of the dependencies.
https://start.spring.io/
https://spring.io/guides/tutorials/rest/
https://www.baeldung.com/spring-boot-testing
https://www.baeldung.com/spring-mock-rest-template
https://www.baeldung.com/spring-hateoas-tutorial
https://www.baeldung.com/spring-security-with-maven

