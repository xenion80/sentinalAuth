package com.example.demo.authservice.repositories;

import com.example.demo.authservice.Entities.ForgotPasswordResetToken;
import com.example.demo.authservice.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<ForgotPasswordResetToken, Long> {

    Optional<ForgotPasswordResetToken> findByToken(String token);
    void deleteByUser(User user);
}