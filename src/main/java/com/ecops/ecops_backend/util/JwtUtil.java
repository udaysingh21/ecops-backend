package com.ecops.ecops_backend.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ecops.ecops_backend.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component // This annotation indicates that this class is a Spring component, allowing it to be automatically detected and registered as a bean in the Spring application.
public class JwtUtil {
    // This class is responsible for generating and validation JWT Tokens.
    private final String SECRET_KEY = "o4waPxz5XBJAW+F451/6Y0PKPptRpTyzSxPBp2kTYL8=";
    private final long EXPIRATION_TIME = 3600000; // 1 hour in milliseconds


    // This method generates a JWT token for a given email.
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());

        return Jwts.builder()
                .setClaims(claims) // Set the claims in the JWT token, which includes the user's role
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // This method retrives the claims from a JWT token.
    // Claims are the payload of the JWT Token, which contains the information about the user.
    // It is used to extract the email and expiration date from the token.
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // This method extract email from a JWT token.
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // This method checks if the JWT token is expired.
    private boolean isTokenExpired(String token) {
        Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }


    // This method checks if a JWT token is expired.
    public boolean validateToken(String token, String email) {
        String extractedEmail = extractEmail(token);
        return (extractedEmail.equals(email) && !isTokenExpired(token));
    }
}
