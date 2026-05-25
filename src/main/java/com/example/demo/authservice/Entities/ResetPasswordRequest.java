package com.example.demo.authservice.Entities;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    @NotBlank(message = "Token is Required")
    private String token;
    @NotBlank(message = "password cannot be empty")
    private String newPassword;
}
