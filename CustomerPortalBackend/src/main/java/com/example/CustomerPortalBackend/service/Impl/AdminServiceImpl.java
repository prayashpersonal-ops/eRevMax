package com.example.CustomerPortalBackend.service.Impl;
import com.example.CustomerPortalBackend.dto.AdminDTO;
import com.example.CustomerPortalBackend.dto.UserDTO;
import com.example.CustomerPortalBackend.entity.Admin;
import com.example.CustomerPortalBackend.entity.User;
import com.example.CustomerPortalBackend.enums.Role;
import com.example.CustomerPortalBackend.payload.response.ApiResponse;
import com.example.CustomerPortalBackend.repository.AdminRepository;
import com.example.CustomerPortalBackend.repository.UserRepository;
import com.example.CustomerPortalBackend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.example.CustomerPortalBackend.payload.request.LoginRequest;


@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResponse<AdminDTO> logInAdmin(LoginRequest loginRequest) {
        if (loginRequest == null) {
            throw new RuntimeException("Login request is null");
        }
        Optional<Admin> existingAdmin = adminRepository.findByEmail(loginRequest.email());
        Admin admin;
        if (existingAdmin.isPresent()) {
            admin = existingAdmin.get();
            if (admin.getPassword() == null || !admin.getPassword().equals(loginRequest.password())) {
                throw new RuntimeException("Invalid password");
            }
        } else {
            throw new RuntimeException("Admin not found");
        }
        AdminDTO responseDTO = AdminDTO.builder()
                .id(admin.getId())
                .email(admin.getEmail())
                .password(admin.getPassword())
                .provider(admin.getProvider())
                .enable(admin.getEnable())
                .role(admin.getRole())
                .build();

        return ApiResponse.<AdminDTO>builder()
                .success(true)
                .message("Admin login successful")
                .data(responseDTO)
                .status(HttpStatus.OK)
                .timestamp(Instant.now())
                .build();
    }

    @Override
    public ApiResponse<List<UserDTO>> seeAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = users.stream()
                .map(this::mapToUserDTO)
                .collect(Collectors.toList());

        return ApiResponse.<List<UserDTO>>builder()
                .success(true)
                .message("All users fetched successfully")
                .data(userDTOS)
                .status(HttpStatus.OK)
                .timestamp(Instant.now())
                .build();
    }

    @Override
    public ApiResponse<UserDTO> userAccessDeniedByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            User userEntity = user.get();
            userEntity.setEnable(false);
            userRepository.save(userEntity);
            return ApiResponse.<UserDTO>builder()
                    .success(true)
                    .message("User access denied successfully")
                    .data(mapToUserDTO(userEntity))
                    .status(HttpStatus.OK)
                    .timestamp(Instant.now())
                    .build();
        }
        return ApiResponse.<UserDTO>builder()
                .success(false)
                .message("User not found")
                .data(null)
                .status(HttpStatus.NOT_FOUND)
                .timestamp(Instant.now())
                .build();
    }

    @Override
    public ApiResponse<UserDTO> userAccessGrantedByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            User userEntity = user.get();
            userEntity.setEnable(true);
            userRepository.save(userEntity);
            return ApiResponse.<UserDTO>builder()
                    .success(false)
                    .message("User access denied successfully")
                    .data(mapToUserDTO(userEntity))
                    .status(HttpStatus.OK)
                    .timestamp(Instant.now())
                    .build();
        }
        return ApiResponse.<UserDTO>builder()
                .success(false)
                .message("User not found")
                .data(null)
                .status(HttpStatus.NOT_FOUND)
                .timestamp(Instant.now())
                .build();
    }

    private UserDTO mapToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .provider(user.getProvider())
                .enable(user.getEnable())
                .hotelsList(user.getHotelsList())
                .companyName(user.getCompanyName())
                .address(user.getAddress())
                .role(user.getRole())
                .build();
    }
}
