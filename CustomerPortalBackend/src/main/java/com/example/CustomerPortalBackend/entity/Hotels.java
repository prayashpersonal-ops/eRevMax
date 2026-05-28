package com.example.CustomerPortalBackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "hotels")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hotels {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @ManyToMany(mappedBy = "hotelsList")
    @JsonIgnore
    private List<User>  users;
}
