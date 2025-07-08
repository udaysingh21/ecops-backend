package com.ecops.ecops_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY) // FetchType.LAZY is used to avoid loading the police station data until it's explicitly accessed which means it will not load the police station data immediately when the department is loaded. This can help with performance, especially if the police station data is large or not always needed.
    @JoinColumn(name = "police_station_id", nullable = false) // This column will hold the foreign key reference to the police station.
    private PoliceStation policeStation;
}
