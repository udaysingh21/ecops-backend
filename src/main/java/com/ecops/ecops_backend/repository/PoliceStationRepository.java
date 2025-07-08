package com.ecops.ecops_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecops.ecops_backend.entity.PoliceStation;

@Repository
public interface  PoliceStationRepository extends JpaRepository<PoliceStation, Object>{
    boolean existsByName(String name);
}
