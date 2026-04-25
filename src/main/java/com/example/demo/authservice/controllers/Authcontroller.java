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
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

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
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request,HttpServletResponse response){
        authService.logout(request,response);
        return ResponseEntity.ok("Logout successful");
    }
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(HttpServletRequest request){
        String refreshToken= Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(()->new AuthenticationServiceException("RefreshToken not found in the request"));
        LoginResponse loginResponse=authService.refreshToken(refreshToken);
        return ResponseEntity.ok(loginResponse);
    }
}
