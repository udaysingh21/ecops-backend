package com.ecops.ecops_backend.dto;

import com.ecops.ecops_backend.entity.User.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder 
// Lombok annotation to generate boilerplate code for getter, setter, constructor, and builder pattern, 
// we do not need to use @Getter and @Setter as @Data already includes them.
public class UserRegistrationDto {
    private String fullname;
    private String email;
    private String password;
    private String citizenshipId;
    private Role role;
}
