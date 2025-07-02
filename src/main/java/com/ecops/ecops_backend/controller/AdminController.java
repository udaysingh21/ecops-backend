package com.ecops.ecops_backend.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecops.ecops_backend.dto.ComplaintResponseDto;
import com.ecops.ecops_backend.service.ComplaintService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final ComplaintService complaintService;

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
}