package com.mprybicki.rfagent.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@Getter
public class User {

    @Length(min = 5)
    @NotBlank
    private String userName;

    @Length(min = 5)
    @NotBlank
    private String password;

    private List<String> roles;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
