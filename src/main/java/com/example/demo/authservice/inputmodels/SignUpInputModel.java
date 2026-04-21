package com.example.demo.authservice.inputmodels;

import com.example.demo.authservice.Entities.enums.Role;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Data
public class SignUpInputModel {
    private String email;
    private String name;
    private String password;


}
