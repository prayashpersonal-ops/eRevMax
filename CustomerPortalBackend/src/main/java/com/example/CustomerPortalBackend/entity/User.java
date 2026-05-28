package com.example.CustomerPortalBackend.entity;


import com.example.CustomerPortalBackend.enums.Provider;
import com.example.CustomerPortalBackend.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Email(message = "Enter a valid Email")
    @NotBlank(message = "Email is required")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    private String name;

    /*@NotBlank(message = "Password is required")*/
    private String password;

    @NotBlank
    private String companyName;

    @NotBlank
    private String address;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    @JoinTable(name = "user_hotels",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "hotel_id")
    )
    @JsonIgnore
    private List<Hotels>  hotelsList;

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
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }*/
}
