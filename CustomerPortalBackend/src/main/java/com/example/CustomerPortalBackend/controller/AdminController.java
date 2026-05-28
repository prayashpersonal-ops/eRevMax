package com.example.CustomerPortalBackend.controller;


import com.example.CustomerPortalBackend.dto.AdminDTO;
import com.example.CustomerPortalBackend.dto.UserDTO;
import com.example.CustomerPortalBackend.payload.request.LoginRequest;
import com.example.CustomerPortalBackend.payload.response.ApiResponse;
import com.example.CustomerPortalBackend.service.AdminService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/")
/*@PreAuthorize("hasRole('ADMIN')")*/
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("login")
    public ResponseEntity<ApiResponse<AdminDTO>> loginAdmin(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(adminService.logInAdmin(loginRequest));
    }

    @GetMapping("see-all-users")
    public ResponseEntity<ApiResponse<List<UserDTO>>> seeAllUsers() {
        return ResponseEntity.ok(adminService.seeAllUsers());
    }

    @PostMapping("user-access-denied-by-id/{email}")
    public ResponseEntity<ApiResponse<UserDTO>> userAccessDeniedByEmail(
            @PathVariable @Email(message = "Enter valid Email") @Valid String email
    ) {
        return ResponseEntity.ok(adminService.userAccessDeniedByEmail(email));
    }

    @PostMapping("user-access-granted-by-id/{email}")
    public ResponseEntity<ApiResponse<UserDTO>> userAccessGrantedByEmail(
            @PathVariable @Email(message = "Enter valid Email") @Valid String email
    ) {
        return ResponseEntity.ok(adminService.userAccessGrantedByEmail(email));
    }
}
