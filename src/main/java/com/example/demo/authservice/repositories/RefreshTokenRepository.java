package com.example.demo.authservice.repositories;

import com.example.demo.authservice.Entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}