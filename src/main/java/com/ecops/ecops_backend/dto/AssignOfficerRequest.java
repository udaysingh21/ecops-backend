package com.ecops.ecops_backend.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignOfficerRequest {
    private String officerEmail;
    private Long policeStationId;
}
