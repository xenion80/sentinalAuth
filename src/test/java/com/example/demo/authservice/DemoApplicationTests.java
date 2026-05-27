package com.example.demo.authservice;

import com.example.demo.authservice.services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
	@Autowired
	private EmailService service;



	@Test
	void contextLoads() {
		service.sendMail("snsardarkaran61@gmail.com","in regard to testing","If you see this, mail works");


	}

}
