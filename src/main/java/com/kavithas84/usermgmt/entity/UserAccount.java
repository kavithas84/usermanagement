package com.kavithas84.usermgmt.entity;

import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserAccount {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder();
        String result = encoder.encode("myPassword");
        this.password = result;
    }

    public UserAccount(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public UserAccount() {
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
