package com.example.CustomerPortalBackend.controller;


import com.example.CustomerPortalBackend.dto.HotelsDTO;
import com.example.CustomerPortalBackend.payload.response.ApiResponse;
import com.example.CustomerPortalBackend.service.HotelsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels/")
@RequiredArgsConstructor
public class HotelsController {
    private final HotelsService hotelsService;

    @PostMapping("add-hotel")
    /*@PreAuthorize("hasRole('ADMIN')")*/
    public ResponseEntity<ApiResponse<List<HotelsDTO>>> addHotel(@RequestBody List<HotelsDTO> listOfHotelsDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelsService.addHotels(listOfHotelsDTO));
    }

    @GetMapping("list-of-hotels-by-name/{names}")
    /*@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")*/
    public ResponseEntity<ApiResponse<List<HotelsDTO>>> getListOfHotelsOnlineByNames(@PathVariable String names){
        return ResponseEntity.ok().body(hotelsService.getListOfHotelsOnlineByNames(names));
    }
}
