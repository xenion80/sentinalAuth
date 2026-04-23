package com.example.demo.authservice.Dtos.responses;

import com.example.demo.authservice.Entities.RoleEntity;
import lombok.Data;

import java.util.Set;
@Data
public class LoginResponse {
    private Long id;
    private String name;
    private String email;
    private Set<String> roles;
    private String accessToken;
    private String refreshToken;

}
