package com.kokhrimenko.spring.application.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kokhrimenko.spring.application.dao.RoleRepository;
import com.kokhrimenko.spring.application.dao.UserRepository;
import com.kokhrimenko.spring.application.domain.Role;
import com.kokhrimenko.spring.application.domain.User;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService tests.")
public class UserServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Test loadUserByEmail - user doesn't exists.")
    public void testLoadUserByUsernameWithoutUser() {
        final String email = "junit@test.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertNull(userService.loadUserByEmail(email));
    }

    @Test
    @DisplayName("Test loadUserByEmail - success case.")
    public void testLoadUserByUsername() {
        final String email = "junit@test.com",
                password = "junit pass";
        User testedUser = new User(1L, email, password, Collections.emptyList());

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(testedUser));

        User user = userService.loadUserByEmail(email);
        assertNotNull(user);
        assertEquals(testedUser, user);
    }

    @Test
    @DisplayName("Test createUser - null as a user.")
    public void testCreateUserWithNullUser() {
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(null));
    }

    @Test
    @DisplayName("Test UserService.createUser - predefined User role doesn't exists.")
    public void testCreateUserWithoutUserRole() {
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(new User()));
    }

    @Test
    @DisplayName("Test UserService.createUser - success case.")
    public void testCreateUser() {
        User user = new User();        

        when(roleRepository.findByName(UserServiceImpl.USER_RILE)).thenReturn(Optional.of(new Role()));

        userService.createUser(user);
        verify(userRepository, times(1)).save(user);
    }
}
