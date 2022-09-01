package com.kavithas84.usermgmt.repository;

import com.kavithas84.usermgmt.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * An extension of the Persisitence API for the UserAccount domain
 */
public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {
    /**
     * Return an user account identified with the name
     * @param name the name of the user
     * @return list of user accounts that match the name.
     */
    List<UserAccount> findByName(String name);

    /**
     * Deletes the user specified by  name
     * @param name
     */
    void deleteByName(String name);

}
