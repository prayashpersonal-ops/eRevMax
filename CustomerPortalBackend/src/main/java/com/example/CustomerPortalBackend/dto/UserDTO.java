package com.example.CustomerPortalBackend.dto;

import com.example.CustomerPortalBackend.entity.Hotels;
import com.example.CustomerPortalBackend.enums.Provider;
import com.example.CustomerPortalBackend.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private UUID id;
    private String email;
    private String name;
    private String password;
    private String companyName;
    private String address;
    private Boolean enable = true;
    private Role role = Role.USER;
    private List<Hotels> hotelsList;
    private String providerId;
    private Provider provider;
}
