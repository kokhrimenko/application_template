package com.kokhrimenko.spring.application.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.kokhrimenko.spring.application.domain.User;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    /**
     * 
     */
    private static final long serialVersionUID = 4781078446130409513L;

    private User user;
    private Set<GrantedAuthority> authorities = Collections.emptySet();

    public UserDetails(User user) {
        super();
        if (user == null) {
            throw new IllegalArgumentException("User couldn't be NULL!");
        }
        this.user = user;

        authorities = user.getRoles().stream()
                .flatMap(role -> role.getPrivileges().stream())
                .map(privilage -> new SimpleGrantedAuthority(privilage.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user != null;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user != null;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user != null;
    }

    @Override
    public boolean isEnabled() {
        return user != null;
    }

}
