package com.kokhrimenko.spring.application.mvc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kokhrimenko.spring.application.domain.User;
import com.kokhrimenko.spring.application.mvc.controller.dto.UserRegistrationDTO;
import com.kokhrimenko.spring.application.service.UserService;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    public static final String PASSWORD_ERROR_MSG = "validation.reg.wrong_pass";
    public static final String USERNAME_ERROR_MSG = "validation.reg.already_exists";
    public static final String PASSWORD_FIELD = "password";
    public static final String USERNAME_FIELD = "email";
    public static final String REG_PAGE = "register";

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ModelAttribute("user")
    public UserRegistrationDTO userRegistrationDto() {
        return new UserRegistrationDTO();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return REG_PAGE;
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDTO userDto, BindingResult result) {
        if (result.hasErrors()) {
            return REG_PAGE;
        }

        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            result.rejectValue(PASSWORD_FIELD, PASSWORD_ERROR_MSG);

            return REG_PAGE;
        }
        User existing = userService.loadUserByEmail(userDto.getEmail());
        if (existing != null) {
            result.rejectValue(USERNAME_FIELD, USERNAME_ERROR_MSG);

            return REG_PAGE;
        }

        User regUser = userDto.toUser();
        regUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userService.createUser(regUser);
        return String.format("redirect:/%s?success", REG_PAGE);
    }
}
