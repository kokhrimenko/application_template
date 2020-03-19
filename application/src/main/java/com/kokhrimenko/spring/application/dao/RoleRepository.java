package com.kokhrimenko.spring.application.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.kokhrimenko.spring.application.domain.Role;

@Repository
public interface RoleRepository extends org.springframework.data.repository.Repository<Role, Long> {

    Optional<Role> findByName(String name);
}
