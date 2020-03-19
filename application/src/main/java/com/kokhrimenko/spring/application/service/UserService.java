package com.kokhrimenko.spring.application.service;

import org.springframework.stereotype.Service;

import com.kokhrimenko.spring.application.domain.User;

@Service
public interface UserService {

    User createUser(User user);
    
    User loadUserByEmail(String email);
}
