package com.example.CustomerPortalBackend.payload.request;


import com.example.CustomerPortalBackend.enums.Role;

public record LoginRequest(
        String email,
        String password
) {}