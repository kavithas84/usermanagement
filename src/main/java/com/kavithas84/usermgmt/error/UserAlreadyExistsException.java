package com.kavithas84.usermgmt.error;

/**
 * A runtime exception to handle the situation when a user with username already exists
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String name) {
        super("User with name " + name + " already exists. Please choose another name.");
    }
}
