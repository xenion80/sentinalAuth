package com.example.demo.authservice.Dtos.responses;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String name;
}
