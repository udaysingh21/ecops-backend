package com.ecops.ecops_backend.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecops.ecops_backend.dto.AuthResponse;
import com.ecops.ecops_backend.dto.LoginRequest;
import com.ecops.ecops_backend.dto.UserRegistrationDto;
import com.ecops.ecops_backend.entity.User;
import com.ecops.ecops_backend.repository.UserRepository;
import com.ecops.ecops_backend.service.AuthService;
import com.ecops.ecops_backend.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/auth")
@SecurityRequirement(name = "bearerAuth")

public class AuthController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDto userRegistrationDto) {

        // check if the user already exists
        Optional<User> existingUser = userRepository.findByEmail(userRegistrationDto.getEmail());
        if (existingUser.isPresent()) {
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Login hit with: " + loginRequest.getEmail());
        String token = authService.login(loginRequest);
        if (token != null && !token.isEmpty()) {
            return ResponseEntity.ok(new AuthResponse(token));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid email or password"));
        }
    }

}
