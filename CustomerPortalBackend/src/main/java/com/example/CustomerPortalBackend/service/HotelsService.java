package com.example.CustomerPortalBackend.service;

import com.example.CustomerPortalBackend.dto.HotelsDTO;
import com.example.CustomerPortalBackend.payload.response.ApiResponse;

import java.util.List;

public interface HotelsService {
    ApiResponse<List<HotelsDTO>> addHotels(List<HotelsDTO> listOfHotelsDTO);

    ApiResponse<List<HotelsDTO>> getListOfHotelsOnlineByNames(String names);
}
