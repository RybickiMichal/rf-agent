package com.mprybicki.rfagent.model;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
public class User {

    @Length(min = 5)
    @NotBlank
    private String userName;

    @Length(min = 5)
    @NotBlank
    private String password;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
