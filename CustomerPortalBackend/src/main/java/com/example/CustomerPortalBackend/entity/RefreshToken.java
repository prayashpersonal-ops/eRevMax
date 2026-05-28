package com.example.CustomerPortalBackend.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "refresh_token",indexes = {
        @Index(name = "refresh_token_jti_idx", columnList = "jti",unique = true),
        @Index(name = "refresh_token_user_id_idx", columnList = "user_id")
})
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "jti", nullable = false, unique = true,updatable = false)
    private String jti;
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @JoinTable
    private User userId;
    @Column(nullable = false)
    private boolean revoked;
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
    @Column(nullable = false)
    private Instant expiresAt;
    private String replacedByToken;
}
