package com.example.demo.authservice.controllers;

import com.example.demo.authservice.Dtos.requests.LoginRequest;
import com.example.demo.authservice.Dtos.requests.forgetPasswordRequest;
import com.example.demo.authservice.Dtos.responses.LoginResponse;
import com.example.demo.authservice.Dtos.responses.UserResponse;
import com.example.demo.authservice.Dtos.requests.SignUpInputModel;
import com.example.demo.authservice.Entities.ResetPasswordRequest;
import com.example.demo.authservice.repositories.UserRepository;
import com.example.demo.authservice.services.AuthService;
import com.example.demo.authservice.services.EmailService;
import com.example.demo.authservice.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class Authcontroller {
    private final UserService userService;
    private final AuthService authService;
    private final EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> signUp(@Valid @RequestBody SignUpInputModel signUpInputModel){
        UserResponse userDTO= userService.signup(signUpInputModel);
        return ResponseEntity.ok(userDTO);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse httpServletResponse){

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

    @GetMapping("/verify-email")
    public ResponseEntity<String> verify(@RequestParam String token){
        authService.verify(token);
        return ResponseEntity.ok("Email Verified Successfully");

    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@Valid@RequestBody forgetPasswordRequest request){
        authService.requestPasswordReset(request.getEmail());
        return ResponseEntity.ok("If an account with that email exists, a reset link has been sent.");
    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> reset_password(@RequestBody ResetPasswordRequest request){
        authService.resetPassword(request.getToken(),request.getNewPassword());
        return ResponseEntity.ok("Password Reset successfully");
    }
    @GetMapping("/test-mail")
    public String testMail() {

        emailService.sendMail(
                "your_email@gmail.com",
                "TEST MAIL",
                "If you received this, SMTP works."
        );

        return "Mail sent";
    }
}
