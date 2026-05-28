package com.example.CustomerPortalBackend.service.Impl;

import com.example.CustomerPortalBackend.dto.HotelsDTO;
import com.example.CustomerPortalBackend.entity.Hotels;
import com.example.CustomerPortalBackend.payload.response.ApiResponse;
import com.example.CustomerPortalBackend.repository.HotelsRepository;
import com.example.CustomerPortalBackend.service.HotelsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelsServiceImpl implements HotelsService {

    private final HotelsRepository hotelsRepository;

    @Override
    public ApiResponse<List<HotelsDTO>> addHotels(List<HotelsDTO> listOfHotelsDTO) {
        if (listOfHotelsDTO == null || listOfHotelsDTO.isEmpty()) {
            return buildHotelErrorResponse(
                    "Hotel list cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
        List<Hotels> hotels = listOfHotelsDTO.stream().map(this::mapToEntity).toList();
        List<Hotels> savedHotels = hotelsRepository.saveAll(hotels);
        List<HotelsDTO> hotelsDTOList = savedHotels.stream().map(this::mapToDTO).toList();
        return buildHotelSuccessResponse(
                "Hotels added successfully", hotelsDTOList, HttpStatus.CREATED
        );
    }

    @Override
    public ApiResponse<List<HotelsDTO>> getListOfHotelsOnlineByNames(String names) {
        Optional<List<Hotels>> optionalHotels = hotelsRepository.findByNameContainingIgnoreCase(names);
        if (optionalHotels.isPresent()) {
            List<Hotels> hotels = optionalHotels.get();
            List<HotelsDTO> hotelsDTOList = hotels.stream().map(this::mapToDTO).toList();
            return buildHotelSuccessResponse("Hotels found",hotelsDTOList,HttpStatus.OK);
        }
        return buildHotelResponse(optionalHotels);
    }

    private ApiResponse<List<HotelsDTO>> buildHotelResponse(
            Optional<List<Hotels>> optionalHotels) {
        if (optionalHotels.isEmpty() || optionalHotels.get().isEmpty()) {
            return buildHotelErrorResponse("No hotels found with the specified name", HttpStatus.NOT_FOUND);
        }
        List<HotelsDTO> hotelsDTOList = optionalHotels.get().stream()
                .map(this::mapToDTO)
                .toList();
        return buildHotelSuccessResponse("Hotels fetched successfully", hotelsDTOList, HttpStatus.OK);
    }

    private Hotels mapToEntity(HotelsDTO hotelsDTO) {
        return Hotels.builder().id(hotelsDTO.getId()).name(hotelsDTO.getName()).build();
    }

    private HotelsDTO mapToDTO(Hotels hotels) {
        return HotelsDTO.builder().id(hotels.getId()).name(hotels.getName()).build();
    }

    private ApiResponse<List<HotelsDTO>> buildHotelSuccessResponse(
            String message, List<HotelsDTO> data, HttpStatus status
    ) {
        return ApiResponse.<List<HotelsDTO>>builder().success(true).message(message)
                .data(data).status(status).timestamp(Instant.now()).build();
    }

    private ApiResponse<List<HotelsDTO>> buildHotelErrorResponse(
            String message, HttpStatus status
    ) {
        return ApiResponse.<List<HotelsDTO>>builder().success(false).message(message).data(List.of())
                .status(status).timestamp(Instant.now()).build();
    }
}