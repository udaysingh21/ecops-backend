package com.ecops.ecops_backend.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecops.ecops_backend.dto.UserRegistrationDto;
import com.ecops.ecops_backend.entity.User;
import com.ecops.ecops_backend.repository.UserRepository;
import com.ecops.ecops_backend.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDto userRegistrationDto) {

    // check if the user already exists
    User existingUser = userRepository.findByEmail(userRegistrationDto.getEmail());
    if (existingUser != null) {
        return ResponseEntity.badRequest().body(
            Map.of("error", "User with email " + userRegistrationDto.getEmail() + " already exists.")
        );
    }

    User user = userService.registerCitizen(userRegistrationDto);
    return ResponseEntity.ok(
        Map.of(
            "message", user.getRole() + " registered successfully",
            "userId", user.getId()
        )
    );
}

}
