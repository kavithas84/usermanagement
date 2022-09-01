package com.kavithas84.usermgmt.controller;


import com.kavithas84.usermgmt.entity.UserAccount;
import com.kavithas84.usermgmt.error.UserAlreadyExistsException;
import com.kavithas84.usermgmt.error.UserNotFoundException;
import com.kavithas84.usermgmt.repository.UserAccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
/**
 * The controller class to handle the hhtp request. Maps the HTTP action to a relevant user action
 *
 */
class UserAccountController {

    private final UserAccountRepository repository;
    private final Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder();


    UserAccountController(UserAccountRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/users")
    /**
     * Returns the list of all users in the system.
     * Mapped to the GET http request
     */
    List<UserAccount> all() {
        return repository.findAll();
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    /**
     * Creates a new user specified in the newUser object.
     * VAlidates if a current user already exists with the give unique name.
     */
    UserAccount newUser(@Valid @RequestBody UserAccount newUser) {
        String name = newUser.getName();
        //check if user with existing name already exists
        List<UserAccount> existingUsers = repository.findByName(name);
        if (!existingUsers.isEmpty()) {
            throw new UserAlreadyExistsException(name);
        }
        String encodedPassword = encoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);
        return repository.save(newUser);
    }

    @PostMapping("/users/authenticate")
    @ResponseStatus(HttpStatus.OK)
    UserAccount authenticateUser(@RequestBody UserAccount loginUser) {
        System.out.println("########### In AUTHENTICATE ##############");
        List<UserAccount> existingUsers = repository.findByName(loginUser.getName());

        //check if user with the username exists.If not, throw user not found exception. If yes, authetiate password
        if (existingUsers.isEmpty()) {
            throw new UserNotFoundException("User with provided username and password does not exist");
        }

        //validate password
        if (existingUsers.get(0) != null) {
            boolean passwordMatches = encoder.matches(loginUser.getPassword(), existingUsers.get(0).getPassword());
            if (!passwordMatches) {
                throw new UserNotFoundException("User with provided username and password does not exist");
            }
        }

        return loginUser;
    }

    // Single item

    @GetMapping("/users/{name}")
    @ResponseStatus(HttpStatus.OK)
    UserAccount one(@PathVariable String name) {
        List<UserAccount> existingUsers = repository.findByName(name);

        //check if user with the username exists.If not, throw user not found exception. if (existingUsers.isEmpty()) {
        if (existingUsers.isEmpty() || existingUsers.get(0) == null) {
            throw new UserNotFoundException("User with provided username does not exist");
        }

        return existingUsers.get(0);
    }


    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/users/{name}")
    void deleteUser(@PathVariable String name) {
        repository.deleteByName(name);
    }
}
