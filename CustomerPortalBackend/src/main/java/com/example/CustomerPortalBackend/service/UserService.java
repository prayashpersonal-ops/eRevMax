package com.example.CustomerPortalBackend.service;

import com.example.CustomerPortalBackend.dto.HotelsDTO;
import com.example.CustomerPortalBackend.dto.UserDTO;
import com.example.CustomerPortalBackend.payload.request.LoginRequest;
import com.example.CustomerPortalBackend.payload.request.SearchHotelsRequest;
import com.example.CustomerPortalBackend.payload.response.ApiResponse;

import java.util.List;

public interface UserService {
    ApiResponse<List<UserDTO>> createUser(List<UserDTO> listOfUserDTO);

    ApiResponse<UserDTO> loginUser(LoginRequest loginRequest);

    ApiResponse<List<HotelsDTO>> getListOfHotelsTheUserHave(String email);

    ApiResponse<List<HotelsDTO>> searchListOfHotelsByUser(SearchHotelsRequest searchHotelsRequest);

    ApiResponse<List<HotelsDTO>> addListOfHotelsByUser(SearchHotelsRequest searchHotelsRequest);
}
