package com.ecops.ecops_backend.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.ecops.ecops_backend.entity.User;
import com.ecops.ecops_backend.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    // This clas is typically used to load user-specific data. It is used by Spring Security to perform authentication and authorization.
    // It implements the UserDetailsService interface, which has a single method loadUserByUsername that is used to retrieve user details by username.
    // In this case, we will use the UserRepository to load the user by email.


    // Spring asks this class, “Do we have this user?” → it checks DB → and gives back the user info.
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+ user.getRole().name());

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            Collections.singletonList(authority) // Convert the role to a SimpleGrantedAuthority which means that the user has a single role.
        );
    }

}
