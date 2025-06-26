package com.ecops.ecops_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecops.ecops_backend.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    
}
