package com.proj.restreserve.restaurant.controller;

import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.restaurant.dto.RestaurantDto;
import com.proj.restreserve.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/restaurant/list")
    public ResponseEntity <List<RestaurantDto>> restaurantlist(){
        List<RestaurantDto> restaurantDtos = restaurantService.findRestaurant();
        return ResponseEntity.ok(restaurantDtos);
    }

    @PostMapping("/admin/registration")
    public ResponseEntity<Restaurant> registrestaurant(@ModelAttribute RestaurantDto restaurantDto, @RequestParam("files") List<MultipartFile> files) {
        return ResponseEntity.ok(restaurantService.regist(restaurantDto, files));
    }
}
