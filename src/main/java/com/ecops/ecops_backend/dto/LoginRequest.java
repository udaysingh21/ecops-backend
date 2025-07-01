package com.ecops.ecops_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// LoginRequest dto is used to encapsulate the data required for user login.
// It is typically used to transfer data between the client and server.

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
}
