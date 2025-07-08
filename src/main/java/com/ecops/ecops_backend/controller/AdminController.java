package com.ecops.ecops_backend.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecops.ecops_backend.dto.AssignAreasRequest;
import com.ecops.ecops_backend.dto.AssignOfficerRequest;
import com.ecops.ecops_backend.dto.ComplaintResponseDto;
import com.ecops.ecops_backend.dto.CreatePoliceStationRequest;
import com.ecops.ecops_backend.dto.DepartmentRequestDto;
import com.ecops.ecops_backend.dto.DepartmentResponseDto;
import com.ecops.ecops_backend.dto.PoliceStationResponseDTO;
import com.ecops.ecops_backend.entity.PoliceStation;
import com.ecops.ecops_backend.service.ComplaintService;
import com.ecops.ecops_backend.service.DepartmentService;
import com.ecops.ecops_backend.service.PoliceStationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final ComplaintService complaintService;
    private final PoliceStationService policeStationService;
    private final DepartmentService departmentService;


    @GetMapping("/complaints")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ComplaintResponseDto>> getAllComplaints(@RequestParam Long stationId) {
        try {
            List<ComplaintResponseDto> complaints = complaintService.getComplaintByPoliceStationId(stationId);
            return ResponseEntity.ok(complaints);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Collections.emptyList());     
        }
    }


    @PostMapping("/police-stations")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createPoliceStation(@RequestBody CreatePoliceStationRequest request) {
        try {
            PoliceStation ps = policeStationService.createPoliceStation(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Police Station created successfully: " + ps.getName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error creating police station: " + e.getMessage());
        }
    }

    @PostMapping("/police-stations/{stationId}/areas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> assignAreasToStation(
            @PathVariable Long stationId,
            @RequestBody AssignAreasRequest request) {

        policeStationService.assignAreas(stationId, request);
        return ResponseEntity.ok("Areas assigned to Police Station ID: " + stationId);
    }

    @GetMapping("/police-stations")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PoliceStationResponseDTO>> getAllPoliceStations() {
        return ResponseEntity.ok(policeStationService.getAllPoliceStationsWithAreas());
    }

    @PostMapping("/assign-officer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> assignOfficerToStation(@RequestBody AssignOfficerRequest request) {
        try {
            policeStationService.assignOfficerToStation(request);
            return ResponseEntity.ok("Officer assigned successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error assigning officer: " + e.getMessage());  
        }
    }

    @PostMapping("/departments/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DepartmentResponseDto> create(@RequestBody DepartmentRequestDto dto) {
        return ResponseEntity.ok(departmentService.createDepartment(dto));
    }

    @GetMapping("/departments/by-police-station/{psId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DepartmentResponseDto>> getByPoliceStation(@PathVariable Long psId) {
        return ResponseEntity.ok(departmentService.getDepartmentsByPoliceStation(psId));
    }

}