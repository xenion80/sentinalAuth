package com.example.demo.authservice;

import com.example.demo.authservice.Dtos.requests.SignUpInputModel;
import com.example.demo.authservice.Entities.RoleEntity;
import com.example.demo.authservice.Entities.enums.Role;
import com.example.demo.authservice.repositories.RoleEntityRepository;
import com.example.demo.authservice.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleEntityRepository roleRepository;
    @BeforeEach
    void setup() {

        if(roleRepository.findByName(Role.ROLE_USER).isEmpty()) {

            RoleEntity roleEntity=new RoleEntity();
            roleEntity.setName(Role.ROLE_USER);
            roleRepository.save(roleEntity);
        }
    }
    SignUpInputModel input=SignUpInputModel.builder()
            .name("Karan")
            .email("snsardarkaran61@gmail.com")
            .password("karn")
            .build();
    @Test
    void emailverify(){
        userService.signup(input);
    }
}
