package com.example.school_website.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET = "mySuperSecureSecretKeyForJwtAuthentication1234567890";

    // Force UTF-8 encoding to prevent cross-platform mismatch issues
    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key)
                .compact();
    }
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    //Extract Role
    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    //Validate Token
    public boolean validateToken(String token, String username) {
        return username.equals(extractUsername(token)) && !isExpired(token);
    }

    //Check Expiration
    private boolean isExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    //Common method
    private Claims extractClaims(String token) {
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            throw new RuntimeException("Invalid token");
        }
    }
}