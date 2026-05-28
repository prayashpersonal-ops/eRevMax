package com.example.CustomerPortalBackend.service;

import com.example.CustomerPortalBackend.dto.AdminDTO;
import com.example.CustomerPortalBackend.dto.UserDTO;
import com.example.CustomerPortalBackend.payload.response.ApiResponse;
import com.example.CustomerPortalBackend.payload.request.LoginRequest;

import java.util.List;

public interface AdminService {

    ApiResponse<List<UserDTO>> seeAllUsers();

    ApiResponse<UserDTO> userAccessDeniedByEmail(String email);

    ApiResponse<UserDTO> userAccessGrantedByEmail(String email);

    ApiResponse<AdminDTO> logInAdmin(LoginRequest loginRequest);
}
