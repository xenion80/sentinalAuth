package com.example.demo.authservice.Dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignUpInputModel {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String name;
    @NotNull
    private String password;


}
