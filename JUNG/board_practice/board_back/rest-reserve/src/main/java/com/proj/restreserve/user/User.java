package com.proj.restreserve.user;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Entity
@Data
@Table(name="user")
public class User {

    @Id
    @Column(name = "userhash")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userhash;

    @Column(name = "useremail", unique = true, nullable = false)
    private String useremail;

    @Column(name ="passowrd", nullable = false)
    private String password;

    @Column(name ="username", nullable = false)
    private String username;

    @Column(name ="phone", nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name ="role", nullable = false)
    private Role role;

    @Column(name="ban", nullable = false)
    private Boolean ban;

    public Set<Role> getRoles() {
        return Collections.singleton(this.role);
    }
}