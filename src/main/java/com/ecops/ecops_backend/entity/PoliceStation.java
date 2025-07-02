package com.ecops.ecops_backend.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "police_stations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PoliceStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "police_station_id") // ✅ Map correctly
    private Long id;

    private String name;
    private String zone;
    private String district;

    @OneToMany(mappedBy="policeStation") // Indicates that a police station has one-to-many relationship with the User entity.
    private List<User> officers; 

    @OneToMany(mappedBy="policeStation")// Indicates that a police station has one-to-many relationship with the Complain entity.
    private List<Complaint> complaints; 

    @OneToMany(mappedBy = "policeStation", cascade = CascadeType.ALL) // CascadeType.ALL means that any operation (like persist, remove) on the police station will also be applied to the areas.
    private List<Area> areas;

}
