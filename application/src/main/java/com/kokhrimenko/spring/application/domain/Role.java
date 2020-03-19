package com.kokhrimenko.spring.application.domain;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "ROLE")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, name = "NAME")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
    
    @ManyToMany
    @JoinTable(name = "ROLE_PRIVILEGES",
        joinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"), 
        inverseJoinColumns = @JoinColumn(name = "PRIVILEGE_ID", referencedColumnName = "ID"))
    private Collection<Privilege> privileges; 
}
