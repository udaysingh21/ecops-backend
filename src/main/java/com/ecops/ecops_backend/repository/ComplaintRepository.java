package com.ecops.ecops_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecops.ecops_backend.entity.Complaint;
import com.ecops.ecops_backend.entity.User;

@Repository
public interface  ComplaintRepository extends JpaRepository<Complaint, Object>{
    List<Complaint> findByPoliceStationId(Long policeStationId);
    List<Complaint> findByCitizen(User citizen);
}
