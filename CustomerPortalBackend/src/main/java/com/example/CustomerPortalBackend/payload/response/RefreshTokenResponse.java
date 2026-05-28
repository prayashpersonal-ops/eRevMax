package com.example.CustomerPortalBackend.payload.response;

public record RefreshTokenResponse(
        String accessToken,
        String refreshToken) {}