package com.example.demo.authservice.controllers;

import com.example.demo.authservice.Dtos.UserDTO;
import com.example.demo.authservice.inputmodels.SignUpInputModel;
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
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpInputModel signUpInputModel){
        UserDTO userDTO= userService.signup(signUpInputModel);
        return ResponseEntity.ok(userDTO);
    }
}
