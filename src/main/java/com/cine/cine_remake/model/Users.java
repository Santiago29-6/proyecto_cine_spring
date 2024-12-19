package com.cine.cine_remake.model;

import java.time.LocalDateTime;

import com.cine.cine_remake.model.enums.Role;
import com.cine.cine_remake.model.enums.UserStatus;

import jakarta.persistence.*;
import lombok.*;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone", nullable = false)
    private String phone;
    
    @Column(name = "address", nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "estate", nullable = false)
    private UserStatus estate;

    @Column(name = "registration_date", nullable = false, updatable = false)
    private LocalDateTime registrationDate;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    @Transient
    private String token;

    @PrePersist
    protected void onCreate() {
        registrationDate = LocalDateTime.now();
        lastUpdated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdated = LocalDateTime.now();
    }

}
