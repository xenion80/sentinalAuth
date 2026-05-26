package com.example.demo.authservice.Config;

import jakarta.mail.internet.MimeMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.io.InputStream;

@Configuration


public class Requiredconfig {
    @Value("${jwt.secretKey}")
    private String jwtSecretKey;
    @Bean
    ModelMapper getModelMapper(){
        return new ModelMapper();
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecretKey secretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
//    @Bean
//    public JavaMailSender mailSender(){
//        return new JavaMailSender() {
//            @Override
//            public MimeMessage createMimeMessage() {
//                return null;
//            }
//
//            @Override
//            public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
//                return null;
//            }

//            @Override
//            public void send(MimeMessage... mimeMessages) throws MailException {
//
//            }
//
//            @Override
//            public void send(SimpleMailMessage... simpleMessages) throws MailException {
//
//            }
//        };
//    }
//    @Bean
//    public JavaMailSender javaMailSender(){
//        return new JavaMailSenderImpl();
//    }

}
