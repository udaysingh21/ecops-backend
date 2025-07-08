package com.ecops.ecops_backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecops.ecops_backend.dto.ComplaintRequestDto;
import com.ecops.ecops_backend.dto.ComplaintResponseDto;
import com.ecops.ecops_backend.dto.ComplaintStatusUpdateRequest;
import com.ecops.ecops_backend.entity.Area;
import com.ecops.ecops_backend.entity.Complaint;
import com.ecops.ecops_backend.entity.User;
import com.ecops.ecops_backend.repository.AreaRepository;
import com.ecops.ecops_backend.repository.ComplaintRepository;
import com.ecops.ecops_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComplaintService {
    
    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;
    private final AreaRepository areaRepository;


    public void submitComplaint(ComplaintRequestDto complaint) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        // SecurityContextHolder is used to get the currently authenticated user's details. 
        //It does this by accessing the SecurityContext, which holds the authentication information of the user currently interacting with the application.

        User citizen = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        Area area = areaRepository.findByNameIgnoreCase(complaint.getArea())
                .orElseThrow(() -> new RuntimeException("Area not found with name: " + complaint.getArea()));


        // Build the compaint object before saving it to the database
        Complaint newComplaint = Complaint.builder()
                .title(complaint.getTitle())
                .description(complaint.getDescription())
                .area(area.getName())
                .dateTime(LocalDateTime.now())
                .citizen(citizen)
                .policeStation(area.getPoliceStation())
                .submittedAt(LocalDateTime.now())
                .build();

        complaintRepository.save(newComplaint);
    
    }

    public List<ComplaintResponseDto> getComplaintByPoliceStationId(Long policeStationId) {
        List<Complaint> complaints = complaintRepository.findByPoliceStationId(policeStationId);

        return complaints.stream()
            .map(c -> ComplaintResponseDto.builder()
                .complaint_id(c.getId())
                .title(c.getTitle())
                .description(c.getDescription())
                .citizen(c.getCitizen().getFullname())  
                .area(c.getArea())
                .policeStation(c.getPoliceStation().getName())
                .submittedAt(c.getSubmittedAt().toString())
                .build())
            .toList();

    }

    public void updateComplaintStatus(ComplaintStatusUpdateRequest request) {
    Complaint complaint = complaintRepository.findById(request.getComplaintId())
        .orElseThrow(() -> new RuntimeException("Complaint not found"));

    complaint.setStatus(request.getStatus());
    complaintRepository.save(complaint);
    }

    public List<ComplaintResponseDto> getComplaintsForLoggedInUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        List<Complaint> complaints = complaintRepository.findByCitizen(user);

        return complaints.stream()
            .map(c -> ComplaintResponseDto.builder()
                .complaint_id(c.getId())
                .title(c.getTitle())
                .description(c.getDescription())
                .area(c.getArea())
                .status(c.getStatus() != null ? c.getStatus().name() : null)
                .submittedAt(c.getSubmittedAt().toString())
                .policeStation(c.getPoliceStation().getName())
                .build())
            .toList();
    }



}
