package com.ecops.ecops_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecops.ecops_backend.dto.ComplaintRequestDto;
import com.ecops.ecops_backend.service.ComplaintService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor // This annotation generates a constructor with required arguments for all the final fields which is useful for dependency injection.
@SecurityRequirement(name = "bearerAuth")

public class ComplaintController {

    private final ComplaintService complaintService;
    /**
     * Endpoint to submit a complaint.
     * Only accessible by users with the 'CITIZEN' role.
     *
     * @param dto The complaint request data transfer object containing the complaint details.
     * @return ResponseEntity with a success message or an error message.
     */
    @PostMapping("/submit")
    @PreAuthorize("hasRole('CITIZEN')")

    public ResponseEntity<String> submitComplaint(@RequestBody ComplaintRequestDto dto) {
        try {
            complaintService.submitComplaint(dto);
            return ResponseEntity.ok("Complaint submitted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error submitting complaint: " + e.getMessage());
        }
    }
    
}
