package com.example.CustomerPortalBackend.dto;

import com.example.CustomerPortalBackend.entity.User;
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
public class HotelsDTO {
    private UUID id;
    private String name;
    private List<User> users;
}
