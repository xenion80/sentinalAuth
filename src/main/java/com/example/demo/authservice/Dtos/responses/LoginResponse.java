package com.example.demo.authservice.Dtos.responses;

import com.example.demo.authservice.Entities.RoleEntity;
import com.example.demo.authservice.Entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private Long id;
    private String name;
    private String email;
    private Set<Role> roles;
    private String accessToken;
    private String refreshToken;


}
