package com.kavithas84.usermgmt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class UserAccount {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    @Column(unique=true)
    private String name;

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password is mandatory")
    @Size(min = 6,  message = "Password must be atleast 6 characters long")
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

        this.password = password;
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

                '}';
    }
}
