package com.ecops.ecops_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecops.ecops_backend.entity.Department;

public interface DepartmentRepository extends  JpaRepository<Department, Long> {
    List<Department> findByPoliceStationId(Long policeStationId);
    
}
