package com.proj.restreserve.restaurant.controller;

import com.proj.restreserve.menu.dto.MenuDto;
import com.proj.restreserve.restaurant.dto.RestaurantDto;
import com.proj.restreserve.restaurant.service.RestaurantService;
import jakarta.validation.Valid;
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
    @PostMapping(value = "/registration", consumes = {"multipart/form-data"})
    public ResponseEntity<?> registrestaurant(
            @Valid @RequestPart("restaurantDto") RestaurantDto restaurantDto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @RequestPart(value = "menuDtos") List<MenuDto> menuDtos,
            @RequestPart(value = "menuImageFiles", required = false) List<MultipartFile> menuImageFiles) {
        return ResponseEntity.ok().body("가게 및 메뉴 등록 완료");
    }

}
