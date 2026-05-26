package com.example.demo.authservice.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String token;
    @Column(nullable = false)

    private LocalDateTime expiresAt;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
