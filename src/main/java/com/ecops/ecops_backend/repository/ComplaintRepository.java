package com.ecops.ecops_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecops.ecops_backend.entity.Complaint;

@Repository
public interface  ComplaintRepository extends JpaRepository<Complaint, Object>{
    List<Complaint> findByPoliceStationId(Long policeStationId);
}
