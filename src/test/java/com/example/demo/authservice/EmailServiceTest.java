package com.example.demo.authservice;

import com.example.demo.authservice.services.EmailService;
import com.example.demo.authservice.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ActiveProfiles("test")

public class EmailServiceTest {
    @Autowired
    private EmailService service;

    @Test
    void sendEmail(){
        service.sendMail("snsardarkaran61@gmail.com","in regard to testing","If you see this, mail works");

    }


}
