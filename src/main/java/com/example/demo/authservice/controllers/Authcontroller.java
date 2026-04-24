package com.example.demo.authservice.controllers;

import com.example.demo.authservice.Dtos.requests.LoginRequest;
import com.example.demo.authservice.Dtos.responses.LoginResponse;
import com.example.demo.authservice.Dtos.responses.UserResponse;
import com.example.demo.authservice.Dtos.requests.SignUpInputModel;
import com.example.demo.authservice.services.AuthService;
import com.example.demo.authservice.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class Authcontroller {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> signUp(@RequestBody SignUpInputModel signUpInputModel){
        UserResponse userDTO= userService.signup(signUpInputModel);
        return ResponseEntity.ok(userDTO);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse httpServletResponse){

        LoginResponse loginResponse=authService.login(loginRequest);

        Cookie cookie=new Cookie("refreshToken",loginResponse.getRefreshToken());
        cookie.setHttpOnly(true);
        httpServletResponse.addCookie(cookie);

        return ResponseEntity.ok(loginResponse);
    }
}
