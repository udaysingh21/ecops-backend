package com.ecops.ecops_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecops.ecops_backend.dto.ComplaintResponseDto;
import com.ecops.ecops_backend.service.ComplaintService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/citizen")
@RequiredArgsConstructor
public class CitizenController {
    private final ComplaintService complaintService;

    @PreAuthorize("hasRole('CITIZEN')")
    @GetMapping("/complaints/my")
    public ResponseEntity<List<ComplaintResponseDto>> getMyComplaints() {
        return ResponseEntity.ok(complaintService.getComplaintsForLoggedInUser());
    }
}
