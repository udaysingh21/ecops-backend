package com.ecops.ecops_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecops.ecops_backend.dto.AssignAreasRequest;
import com.ecops.ecops_backend.dto.AssignOfficerRequest;
import com.ecops.ecops_backend.dto.CreatePoliceStationRequest;
import com.ecops.ecops_backend.dto.PoliceStationResponseDTO;
import com.ecops.ecops_backend.entity.Area;
import com.ecops.ecops_backend.entity.PoliceStation;
import com.ecops.ecops_backend.entity.User;
import com.ecops.ecops_backend.repository.PoliceStationRepository;
import com.ecops.ecops_backend.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PoliceStationService {
    private final PoliceStationRepository policeStationRepository;
    private final UserRepository userRepository;

    public PoliceStation createPoliceStation(CreatePoliceStationRequest request) {
        if (policeStationRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Police station with this name already exists.");
        }
        PoliceStation station = PoliceStation.builder()
                .name(request.getName())
                .zone(request.getZone())
                .district(request.getDistrict())
                .build();

        return policeStationRepository.save(station);

    }

    @Transactional 
    // This annotation ensures that the method is executed within a transaction context which means that if any exception occurs, the transaction will be rolled back.
    // It is helpful here to ensure that the creation of the police station is atomic, meaning either it fully succeeds or fails without leaving the database in an inconsisten state.
    public void assignAreas(Long StationId, AssignAreasRequest request) {
        PoliceStation station = policeStationRepository.findById(StationId)
                .orElseThrow(() -> new IllegalArgumentException("Police station not found with id: " + StationId));

        List<Area> areaList = request.getAreas().stream()
                .map(areaName -> Area.builder()
                        .name(areaName)
                        .policeStation(station)
                        .build())
                .toList();
        station.setAreas(areaList);
    }

    public List<PoliceStationResponseDTO> getAllPoliceStationsWithAreas() {
    return policeStationRepository.findAll().stream()
            .map(ps -> PoliceStationResponseDTO.builder()
                    .id(ps.getId())
                    .name(ps.getName())
                    .zone(ps.getZone())
                    .district(ps.getDistrict())
                    .areas(ps.getAreas().stream().map(Area::getName).toList())
                    .build())
            .toList();  
    }

    public void assignOfficerToStation(AssignOfficerRequest request) {
        User officer = userRepository.findByEmail(request.getOfficerEmail())
                .orElseThrow(() -> new IllegalArgumentException("Officer not found with email: " + request.getOfficerEmail()));

        if (officer.getRole() != User.Role.OFFICER) {
            throw new IllegalArgumentException("User is not an officer.");
        }

        PoliceStation station = policeStationRepository.findById(request.getPoliceStationId())
                .orElseThrow(() -> new IllegalArgumentException("Police station not found with id: " + request.getPoliceStationId()));

        officer.setPoliceStation(station);
        userRepository.save(officer);
    }



}
