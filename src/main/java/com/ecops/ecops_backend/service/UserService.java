package com.ecops.ecops_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecops.ecops_backend.repository.UserRepository;
import com.ecops.ecops_backend.dto.UserRegistrationDto;
import com.ecops.ecops_backend.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerCitizen(UserRegistrationDto dto) {
        User user = User.builder()
                .fullname(dto.getFullname())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .citizenshipId(dto.getCitizenshipId())
                .role(User.Role.CITIZEN)
                .build();

        return userRepository.save(user);
    }
    
}
