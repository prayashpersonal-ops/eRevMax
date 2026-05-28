package com.example.CustomerPortalBackend.entity;

import com.example.CustomerPortalBackend.enums.Provider;
import com.example.CustomerPortalBackend.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "admins")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Email(message = "Enter a valid Email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "password is required")
    private String password;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.ADMIN;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean enable;

    private String providerId;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @PrePersist
    public void prePersist() {
        if (enable == null || provider == null) {
            enable = true;
            provider = Provider.LOCAL;
        }
    }

    /*@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(Role.ADMIN.toString()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }*/
}
