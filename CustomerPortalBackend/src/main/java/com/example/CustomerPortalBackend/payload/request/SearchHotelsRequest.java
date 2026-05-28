package com.example.CustomerPortalBackend.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchHotelsRequest {
    private LoginRequest loginRequest;
    private String name;
}
