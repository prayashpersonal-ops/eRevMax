package com.example.CustomerPortalBackend.dto;

import com.example.CustomerPortalBackend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenDTO {
    private UUID id;
    private String tokenHash;
    private User userId;
    private Instant expiryAt;
    private Instant createdAt;
    private boolean revoked = false;
    private String replacedByToken;
}
