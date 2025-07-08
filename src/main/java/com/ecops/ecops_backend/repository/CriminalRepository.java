package com.ecops.ecops_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecops.ecops_backend.entity.Criminal;

public interface CriminalRepository extends JpaRepository<Criminal, Long> {
    
}
