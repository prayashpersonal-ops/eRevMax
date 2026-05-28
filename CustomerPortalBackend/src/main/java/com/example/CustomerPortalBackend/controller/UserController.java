package com.example.CustomerPortalBackend.controller;


import com.example.CustomerPortalBackend.dto.HotelsDTO;
import com.example.CustomerPortalBackend.dto.UserDTO;
import com.example.CustomerPortalBackend.payload.request.LoginRequest;
import com.example.CustomerPortalBackend.payload.request.SearchHotelsRequest;
import com.example.CustomerPortalBackend.payload.response.ApiResponse;
import com.example.CustomerPortalBackend.service.UserService;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<ApiResponse<List<UserDTO>>> registerUser(@RequestBody List<UserDTO> listOfUserDTO){
        return ResponseEntity.ok().body(userService.createUser(listOfUserDTO));
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<UserDTO>> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok().body(userService.loginUser(loginRequest));
    }

    @GetMapping("list-of-hotels-of-user/{email}")
    public ResponseEntity<ApiResponse<List<HotelsDTO>>> getListOfHotelsTheUserHaveByEmail(@PathVariable @Email String email){
        return ResponseEntity.ok().body(userService.getListOfHotelsTheUserHave(email));
    }

    @PostMapping("search-list-of-hotels-by-user")
    public ResponseEntity<ApiResponse<List<HotelsDTO>>> searchListOfHotelsByUser(
            @RequestBody SearchHotelsRequest searchHotelsRequest
    ){
        return ResponseEntity.ok().body(userService.searchListOfHotelsByUser(searchHotelsRequest));
    }

    @PostMapping("add-list-of-hotels-by-user")
    public ResponseEntity<ApiResponse<List<HotelsDTO>>> addListOfHotelsByUser(
            @RequestBody SearchHotelsRequest searchHotelsRequest
    ){
        return ResponseEntity.ok().body(userService.addListOfHotelsByUser(searchHotelsRequest));
    }
}
