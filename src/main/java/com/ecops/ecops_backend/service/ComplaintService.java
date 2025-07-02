package com.ecops.ecops_backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecops.ecops_backend.dto.ComplaintRequestDto;
import com.ecops.ecops_backend.dto.ComplaintResponseDto;
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


}
