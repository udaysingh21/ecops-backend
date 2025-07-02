package com.ecops.ecops_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne
    @JoinColumn(name = "police_station_id")
    private PoliceStation policeStation;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        CITIZEN,
        OFFICER,
        ADMIN,
        MAGISTRATE
    }
}
