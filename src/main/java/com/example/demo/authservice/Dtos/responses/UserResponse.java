package com.example.demo.authservice.Dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String name;
}
