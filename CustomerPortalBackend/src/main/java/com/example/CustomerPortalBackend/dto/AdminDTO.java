package com.example.CustomerPortalBackend.dto;

import com.example.CustomerPortalBackend.enums.Provider;
import com.example.CustomerPortalBackend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDTO {
    private UUID id;
    private String email;
    private String password;
    private Role role = Role.ADMIN;
    private Boolean enable;
    private String providerId;
    private Provider provider;
}
