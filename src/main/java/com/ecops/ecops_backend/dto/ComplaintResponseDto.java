package com.ecops.ecops_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplaintResponseDto {
    private Long complaint_id;
    private String title;
    private String description;
    private String citizen;
    private String area;
    private String policeStation;
    private String submittedAt;
}
