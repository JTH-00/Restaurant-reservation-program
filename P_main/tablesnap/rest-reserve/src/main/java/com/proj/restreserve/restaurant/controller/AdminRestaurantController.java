package com.proj.restreserve.restaurant.controller;

import com.proj.restreserve.restaurant.dto.RestaurantDto;
import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminRestaurantController {

    private final RestaurantService restaurantService;
    @PostMapping("/registration")
    public ResponseEntity<Restaurant> registrestaurant(@ModelAttribute RestaurantDto restaurantDto, @RequestParam("files") List<MultipartFile> files) {
        return ResponseEntity.ok(restaurantService.regist(restaurantDto, files));
    }

}
