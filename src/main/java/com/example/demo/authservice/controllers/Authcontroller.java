package com.example.demo.authservice.controllers;

import com.example.demo.authservice.Dtos.responses.UserResponse;
import com.example.demo.authservice.Dtos.requests.SignUpInputModel;
import com.example.demo.authservice.services.UserService;
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

    @PostMapping("/register")
    public ResponseEntity<UserResponse> signUp(@RequestBody SignUpInputModel signUpInputModel){
        UserResponse userDTO= userService.signup(signUpInputModel);
        return ResponseEntity.ok(userDTO);
    }
}
