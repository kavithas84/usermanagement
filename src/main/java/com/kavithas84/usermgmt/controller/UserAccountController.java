package com.kavithas84.usermgmt.controller;


import com.kavithas84.usermgmt.entity.UserAccount;
import com.kavithas84.usermgmt.error.UserNotFoundException;
import com.kavithas84.usermgmt.repository.UserAccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class UserAccountController {

    private final UserAccountRepository repository;

    UserAccountController(UserAccountRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/users")
    List<UserAccount> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    UserAccount newUser(@RequestBody UserAccount newUser) {
        return repository.save(newUser);
    }

    @PostMapping("/users/authenticate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    UserAccount authenticateUser(@RequestBody UserAccount newUser) {
        System.out.println("########### In AUTHENTICATE ##############");
        return repository.save(newUser);
    }

    // Single item

    @GetMapping("/users/{id}")
    UserAccount one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/users/{id}")
    UserAccount replaceUser(@RequestBody UserAccount newUser, @PathVariable Long id) {

        return repository.findById(id)
                .map(userAccount -> {
                    userAccount.setName(newUser.getName());
                    userAccount.setPassword(newUser.getPassword());
                    return repository.save(userAccount);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
