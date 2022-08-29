package com.kavithas84.usermgmt.error;

public class UserNotFoundException extends RuntimeException  {

    public UserNotFoundException(Long id) {
        super("Could not find User " + id);
    }
}
