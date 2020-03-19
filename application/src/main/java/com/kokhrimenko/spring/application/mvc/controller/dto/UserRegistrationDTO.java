package com.kokhrimenko.spring.application.mvc.controller.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.kokhrimenko.spring.application.domain.User;

import lombok.Data;

@Data
public class UserRegistrationDTO {

    @NotNull
    @Size(min = 1, max = 20)
    private String email;

    @NotNull
    @Size(min = 8, max = 20)
    private String password;

    @NotNull
    @Size(min = 8, max = 20)
    private String confirmPassword;

    public User toUser() {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

}
