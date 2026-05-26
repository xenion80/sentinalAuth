package com.example.demo.authservice.controllers;

import com.example.demo.authservice.Dtos.responses.UserResponse;
import com.example.demo.authservice.Entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    @GetMapping("/users/me")
    public ResponseEntity<UserResponse>  me(Authentication authentication){
        User user= (User) authentication.getPrincipal();
        return ResponseEntity.ok(new UserResponse(
                user.getId(),
                user.getRoles().stream().map(roleEntity -> roleEntity.getName()).collect(Collectors.toSet()).toString(),
                user.getEmail()

        ));
    }
}
