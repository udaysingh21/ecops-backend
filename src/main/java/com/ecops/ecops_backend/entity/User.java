package com.ecops.ecops_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Lombok annotation to generate boilerplate code for getter , setter , constructor and builder pattern 
// Build pattern is a pattern that allows for the step-by-step construction of complex objects.

public class User {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullname;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String citizenshipId;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        CITIZEN,
        OFFICER,
        ADMIN,
        MAGISTRATE
    }
}
