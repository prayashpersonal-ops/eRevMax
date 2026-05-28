package com.example.CustomerPortalBackend.repository;

import com.example.CustomerPortalBackend.entity.Hotels;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HotelsRepository extends JpaRepository<Hotels, UUID> {

    Optional<List<Hotels>> findByNameContainingIgnoreCase(String name);
}
