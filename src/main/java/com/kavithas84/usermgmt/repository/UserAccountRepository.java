package com.kavithas84.usermgmt.repository;

import com.kavithas84.usermgmt.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {

}
