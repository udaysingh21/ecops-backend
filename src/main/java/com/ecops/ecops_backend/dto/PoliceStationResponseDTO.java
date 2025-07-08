package com.ecops.ecops_backend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PoliceStationResponseDTO {
    private Long id;
    private String name;
    private String zone;
    private String district;
    private List<String> areas;
}
