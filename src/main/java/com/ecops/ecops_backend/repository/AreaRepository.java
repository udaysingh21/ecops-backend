package com.ecops.ecops_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecops.ecops_backend.entity.Area;

@Repository
public interface  AreaRepository extends JpaRepository<Area, Long> {
    Optional<Area> findByNameIgnoreCase(String name);
}
