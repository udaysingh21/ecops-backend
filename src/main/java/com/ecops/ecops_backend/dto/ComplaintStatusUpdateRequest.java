package com.ecops.ecops_backend.dto;

import com.ecops.ecops_backend.entity.Complaint.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComplaintStatusUpdateRequest {
    private Long complaintId;
    private Status status;
}