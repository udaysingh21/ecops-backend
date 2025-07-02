package com.ecops.ecops_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecops.ecops_backend.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // we are returning an Optional<User> to handle the case where a user with the given email does not exist
    // i.e we can return orElseThrow or orElse(null) to handle the case where the user is not found
    // this is a good practice to avoid NullPointerException
    Optional<User> findByEmail(String email);
    
}
