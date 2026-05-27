package com.example.demo.authservice.Dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpInputModel {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String name;
    @NotNull
    private String password;


}
