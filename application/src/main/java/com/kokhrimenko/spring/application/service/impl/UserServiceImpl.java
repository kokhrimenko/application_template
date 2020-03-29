package com.kokhrimenko.spring.application.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kokhrimenko.spring.application.dao.RoleRepository;
import com.kokhrimenko.spring.application.dao.UserRepository;
import com.kokhrimenko.spring.application.domain.Role;
import com.kokhrimenko.spring.application.domain.User;
import com.kokhrimenko.spring.application.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    static final String USER_RILE = "USER";
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public User createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Can't store null user!");
        }
        
        Optional<Role> role = roleRepository.findByName(USER_RILE);
        if(!role.isPresent()) {
            throw new IllegalArgumentException("Couldn't found default USER role!");
        }
        user.setRoles(List.of(role.get()));
        return userRepository.save(user);
    }

    @Override
    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }
}
