package com.example.CustomerPortalBackend.payload.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {//T is Generic type Parameter
        private boolean success;
        private String message;
        private T data;
        private HttpStatus status;
        private Instant timestamp;
}