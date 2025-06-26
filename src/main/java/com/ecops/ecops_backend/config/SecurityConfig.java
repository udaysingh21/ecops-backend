package com.ecops.ecops_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF for non-browser clients (like Bruno/Postman)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Allow all endpoints without login
            )
            .formLogin().disable() // Disable form-based login
            .httpBasic().disable(); // Disable basic auth popup

        return http.build();
    }
}
