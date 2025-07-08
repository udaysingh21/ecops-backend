package com.ecops.ecops_backend.service;
import java.util.List;

import com.ecops.ecops_backend.dto.DepartmentRequestDto;
import com.ecops.ecops_backend.dto.DepartmentResponseDto;

public interface DepartmentService {
    DepartmentResponseDto createDepartment(DepartmentRequestDto request);
    List<DepartmentResponseDto> getDepartmentsByPoliceStation(Long policeStationId);
}
