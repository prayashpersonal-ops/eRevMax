package com.example.CustomerPortalBackend.service.Impl;

import com.example.CustomerPortalBackend.dto.HotelsDTO;
import com.example.CustomerPortalBackend.dto.UserDTO;
import com.example.CustomerPortalBackend.entity.Hotels;
import com.example.CustomerPortalBackend.entity.User;
import com.example.CustomerPortalBackend.enums.Role;
import com.example.CustomerPortalBackend.payload.request.LoginRequest;
import com.example.CustomerPortalBackend.payload.request.SearchHotelsRequest;
import com.example.CustomerPortalBackend.payload.response.ApiResponse;
import com.example.CustomerPortalBackend.repository.HotelsRepository;
import com.example.CustomerPortalBackend.repository.UserRepository;
import com.example.CustomerPortalBackend.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final HotelsRepository hotelsRepository;

    @Override
    public ApiResponse<List<UserDTO>> createUser(List<UserDTO> listOfUserDTO) {
        List<User> users = listOfUserDTO.stream()
                .map(userDTO -> modelMapper.map(userDTO, User.class)).toList();
        List<User> savedUsers = new ArrayList<>();
        for (User user : users) {
            user.setRole(Role.USER);
            user.setEnable(true);
            savedUsers.add(userRepository.save(user));
        }
        List<UserDTO> userDTOList = savedUsers.stream()
                .map(user -> modelMapper.map(user, UserDTO.class)).toList();
        return ApiResponse.<List<UserDTO>>builder()
                .success(true)
                .status(HttpStatus.CREATED)
                .message("Users created successfully")
                .data(userDTOList)
                .timestamp(Instant.now())
                .build();
    }

    @Override
    public ApiResponse<UserDTO> loginUser(LoginRequest loginRequest) {
        if (loginRequest.email()==null || loginRequest.password()==null) {
            return buildErrorResponse("Please Enter the Credentials right", HttpStatus.UNAUTHORIZED);
        }
        Optional<User> optionalUser = authenticateUser(loginRequest);
        if (optionalUser.isEmpty()) {
            return buildErrorResponse("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
        if (optionalUser.get().getEnable() == false) {
            return buildErrorResponse("User is disabled", HttpStatus.FORBIDDEN);
        }
        UserDTO userDTO = modelMapper.map(optionalUser.get(), UserDTO.class);
        userDTO.setEnable(true);
        return ApiResponse.<UserDTO>builder()
                .success(true)
                .status(HttpStatus.OK)
                .message("Login successful")
                .data(userDTO)
                .timestamp(Instant.now())
                .build();
    }

    @Override
    public ApiResponse<List<HotelsDTO>> getListOfHotelsTheUserHave(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return ApiResponse.<List<HotelsDTO>>builder()
                    .success(false)
                    .status(HttpStatus.NOT_FOUND)
                    .message("User not found")
                    .timestamp(Instant.now())
                    .build();
        }
        User user = optionalUser.get();
        List<HotelsDTO> hotelsDTOList = mapHotelsToDTO(user.getHotelsList());
        return ApiResponse.<List<HotelsDTO>>builder()
                .success(true)
                .status(HttpStatus.OK)
                .message("Hotels fetched successfully")
                .data(hotelsDTOList)
                .timestamp(Instant.now())
                .build();
    }

    @Override
    public ApiResponse<List<HotelsDTO>> searchListOfHotelsByUser(SearchHotelsRequest searchHotelsRequest){
        Optional<User> optionalUser = authenticateUser(searchHotelsRequest.getLoginRequest());
        if (optionalUser.isEmpty()) {
            return buildErrorResponse("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
        Optional<List<Hotels>> optionalHotels = hotelsRepository
                .findByNameContainingIgnoreCase(searchHotelsRequest.getName());
        if (optionalHotels.isEmpty()) {
            return buildErrorResponse("No hotels found matching the criteria", HttpStatus.NOT_FOUND);
        }
        List<Hotels> hotelsList = optionalHotels.get();
        return ApiResponse.<List<HotelsDTO>>builder()
                .success(true)
                .message("Hotels fetched successfully")
                .status(HttpStatus.OK)
                .data(mapHotelsToDTO(hotelsList))
                .timestamp(Instant.now())
                .build();
    }

    @Override
    public ApiResponse<List<HotelsDTO>> addListOfHotelsByUser(SearchHotelsRequest searchHotelsRequest) {
        Optional<User> optionalUser = authenticateUser(searchHotelsRequest.getLoginRequest());
        if (optionalUser.isEmpty()) {
            return buildErrorResponse("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
        User user = optionalUser.get();
        Optional<List<Hotels>> optionalHotels = hotelsRepository
                .findByNameContainingIgnoreCase(searchHotelsRequest.getName());
        if (optionalHotels.isEmpty()) {
            return buildErrorResponse("No hotels found matching the criteria", HttpStatus.NOT_FOUND);
        }
        List<Hotels> hotelsList = optionalHotels.get();
        if (user.getHotelsList() == null) {
            user.setHotelsList(new ArrayList<>());
        }
        user.getHotelsList().addAll(hotelsList);
        userRepository.save(user);
        return ApiResponse.<List<HotelsDTO>>builder()
                .success(true)
                .message("Hotels added successfully")
                .status(HttpStatus.OK)
                .data(mapHotelsToDTO(user.getHotelsList()))
                .timestamp(Instant.now())
                .build();
    }

    private Optional<User> authenticateUser(LoginRequest loginRequest) {
        Optional<User> optionalUser = userRepository.findByEmail(loginRequest.email());

        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }

        User user = optionalUser.get();
        if (!user.getPassword().equals(loginRequest.password())) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    private List<HotelsDTO> mapHotelsToDTO(
            List<Hotels> hotelsList
    ) {

        return hotelsList.stream()
                .distinct()
                .map(hotel -> modelMapper.map(hotel, HotelsDTO.class))
                .toList();
    }

    private <T> ApiResponse<T> buildErrorResponse(
            String message,
            HttpStatus status
    ) {

        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .status(status)
                .timestamp(Instant.now())
                .build();
    }
}