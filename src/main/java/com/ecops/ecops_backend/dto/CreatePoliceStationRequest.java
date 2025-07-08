package com.ecops.ecops_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePoliceStationRequest {
    private String name;
    private String zone;
    private String district;
}
