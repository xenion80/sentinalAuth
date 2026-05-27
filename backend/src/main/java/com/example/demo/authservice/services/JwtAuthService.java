package com.example.demo.authservice.services;

import com.example.demo.authservice.Entities.RoleEntity;
import com.example.demo.authservice.Entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtAuthService {

    private final SecretKey secretKey;

    public String generateAccessToken(User user){
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email",user.getEmail())
                .claim("roles",user.getRoles().stream().map(RoleEntity::getName).toList())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*10))
                .signWith(secretKey)
                .compact();
    }
    public String generateRefreshToken(User user){
        return Jwts.builder()
                .subject(user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*24*90))
                .signWith(secretKey)
                .compact();
    }
    public boolean isTokenExpired(String token){
        return extractAllClaims(token)
                        .getExpiration()
                        .before(new Date());

    }
    public Long extractUserId(String token){
        return Long.parseLong(
                extractAllClaims(token).getSubject()
        );
    }
    public boolean validToken(String token){
        return !isTokenExpired(token);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


}
