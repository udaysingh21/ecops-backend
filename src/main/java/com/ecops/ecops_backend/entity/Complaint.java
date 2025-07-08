package com.ecops.ecops_backend.entity;

import java.time.LocalDateTime;

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
@Table(name = "complaints")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complaint_id") 
    private Long id;

    private String title;
    private String description;
    private String area;

    private LocalDateTime dateTime;
    private LocalDateTime submittedAt;

    @ManyToOne // It means that many complaints can be associated with one citizen.
    @JoinColumn(name = "citizen_id", nullable = false)
    private User citizen;

    @ManyToOne // It means that many complaints can be associated with one police station.
    @JoinColumn(name = "police_station_id", nullable = false)
    private PoliceStation policeStation;

    @Enumerated(EnumType.STRING)
    private Status status;
    public enum Status {
        FILED,
        IN_PROGRESS,
        RESOLVED    }
}
