package com.example.demo.authservice.Dtos.requests;

import lombok.Data;

@Data
public class SignUpInputModel {
    private String email;
    private String name;
    private String password;


}
