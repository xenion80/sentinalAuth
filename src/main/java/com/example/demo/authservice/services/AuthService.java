package com.example.demo.authservice.services;

import com.example.demo.authservice.Dtos.requests.LoginRequest;
import com.example.demo.authservice.Dtos.responses.LoginResponse;
import com.example.demo.authservice.Entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtAuthService jwtAuthService;

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),loginRequest.getPassword()));

        User user= (User) authentication.getPrincipal();
        String accesstoken=jwtAuthService.generateAccessToken(user);
        String refreshToken=jwtAuthService.generateRefreshToken(user);

        return new LoginResponse(user.getId(),user.getName(),user.getEmail(),user.getRoles().stream().map(role->role.getName()).collect(Collectors.toSet()),accesstoken,refreshToken);


    }
}
