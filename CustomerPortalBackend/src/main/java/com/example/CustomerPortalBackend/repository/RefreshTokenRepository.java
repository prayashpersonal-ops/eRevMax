package com.example.CustomerPortalBackend.repository;

import com.example.CustomerPortalBackend.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
}
