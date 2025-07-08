package com.ecops.ecops_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecops.ecops_backend.dto.ComplaintStatusUpdateRequest;
import com.ecops.ecops_backend.service.ComplaintService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/officer")
@SecurityRequirement(name = "bearerAuth")

public class OfficerController {

    private final ComplaintService complaintService;

    @PreAuthorize("hasAnyRole('OFFICER')")
    @PostMapping("/complaints/update-status")
    public ResponseEntity<String> updateComplaintStatus(@RequestBody ComplaintStatusUpdateRequest request) {
        complaintService.updateComplaintStatus(request);
        return ResponseEntity.ok("Complaint status updated successfully.");
    }

}
