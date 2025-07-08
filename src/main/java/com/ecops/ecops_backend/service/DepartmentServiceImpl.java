package com.ecops.ecops_backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecops.ecops_backend.dto.DepartmentRequestDto;
import com.ecops.ecops_backend.dto.DepartmentResponseDto;
import com.ecops.ecops_backend.entity.Department;
import com.ecops.ecops_backend.entity.PoliceStation;
import com.ecops.ecops_backend.repository.DepartmentRepository;
import com.ecops.ecops_backend.repository.PoliceStationRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final PoliceStationRepository policeStationRepository;

    @Override
    public DepartmentResponseDto createDepartment(DepartmentRequestDto request) {
        PoliceStation ps = policeStationRepository.findById(request.getPoliceStationId())
                .orElseThrow(() -> new EntityNotFoundException("Police Station not found"));

        Department department = Department.builder()
                .name(request.getName())
                .policeStation(ps)
                .build();

        department = departmentRepository.save(department);

        return DepartmentResponseDto.builder()
                .id(department.getId())
                .name(department.getName())
                .policeStationName(ps.getName())
                .build();
    }

    @Override
    public List<DepartmentResponseDto> getDepartmentsByPoliceStation(Long psId) {
        List<Department> departments = departmentRepository.findByPoliceStationId(psId);
        return departments.stream().map(dept -> DepartmentResponseDto.builder()
                .id(dept.getId())
                .name(dept.getName())
                .policeStationName(dept.getPoliceStation().getName())
                .build()).collect(Collectors.toList());
    }
}
