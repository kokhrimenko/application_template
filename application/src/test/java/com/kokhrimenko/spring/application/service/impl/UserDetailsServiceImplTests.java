package com.kokhrimenko.spring.application.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.kokhrimenko.spring.application.dao.UserRepository;
import com.kokhrimenko.spring.application.domain.Privilege;
import com.kokhrimenko.spring.application.domain.Role;
import com.kokhrimenko.spring.application.domain.User;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserDetailsService tests.")
public class UserDetailsServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    @DisplayName("Test get user by email with a wrong email.")
    public void testLoadUserByUsernameWithWrongOne() {
        final String userName = "test";
        when(userRepository.findByEmail(userName)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(userName));
    }

    @Test
    @DisplayName("Test get user by email with a correct email and check user data. Case with EMPTY Authorities")
    public void testLoadUserByUsername() {
        final String userName = "test",
                pass = "pass";
        User user = new User();
        user.setId(1L);
        user.setEmail(userName);
        user.setPassword(pass);
        user.setRoles(Collections.emptyList());

        when(userRepository.findByEmail(userName)).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        assertNotNull(userDetails);
        assertEquals(userName, userDetails.getUsername());
        assertEquals(pass, userDetails.getPassword());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isEnabled());
        assertNotNull(userDetails.getAuthorities());
        assertTrue(userDetails.getAuthorities().isEmpty());
    }

    @Test
    @DisplayName("Test get user by email with a correct email and check user data. Case with Authorities")
    public void testLoadUserByUsernameWithAuthorities() {
        final String userName = "test",
                pass = "pass";
        final String userRoleName = "jUnit USER role",
                adminRoleName = "jUnit ADMIN role";
        final String userRoleView = "user view Authority",
                adminRoleManage = "admin view Authority",
                adminRoleEdit = "admin edit Authority";
        User user = new User();
        user.setId(1L);
        user.setEmail(userName);
        user.setPassword(pass);
        Role userRole = new Role();
        userRole.setName(userRoleName);
        userRole.setPrivileges(List.of(new Privilege(1L, userRoleView, Collections.emptyList())));
        Role adminRole = new Role();
        adminRole.setName(adminRoleName);
        adminRole.setPrivileges(List.of(
                new Privilege(2L, adminRoleEdit, Collections.emptyList()),
                new Privilege(3L, adminRoleManage, Collections.emptyList())
                ));
        user.setRoles(List.of(userRole, adminRole));

        when(userRepository.findByEmail(userName)).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        assertNotNull(userDetails);
        assertEquals(userName, userDetails.getUsername());
        assertEquals(pass, userDetails.getPassword());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isEnabled());
        assertNotNull(userDetails.getAuthorities());
        assertFalse(userDetails.getAuthorities().isEmpty());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority(userRoleView)));
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority(adminRoleEdit)));
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority(adminRoleManage)));
    }
}
