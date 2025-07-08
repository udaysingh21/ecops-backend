package com.ecops.ecops_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "area")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "area_id") 
    private Long id;

    private String name;

    @ManyToOne // It means that many areas can be associated with one police station.
    @JoinColumn(name = "police_station_id", nullable = false)
    private PoliceStation policeStation; 
}
