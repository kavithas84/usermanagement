package com.kavithas84.usermgmt.error;

import com.kavithas84.usermgmt.entity.UserAccount;

public class UnAunthenticatedUserException extends RuntimeException {

    public UnAunthenticatedUserException(UserAccount userAccount) {
        super("User with name"+userAccount.getName()+" and password  "
                + userAccount.getPassword()+" not authenticated");
    }
}
