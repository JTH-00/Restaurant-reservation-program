package com.proj.restreserve.restaurant.controller;

import com.proj.restreserve.menu.dto.MenuDto;
import com.proj.restreserve.restaurant.dto.RestaurantDto;
import com.proj.restreserve.restaurant.entity.Restaurant;
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
    public ResponseEntity<String> registrestaurant(
            @Valid @RequestPart("restaurantDto") RestaurantDto restaurantDto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @RequestPart(value = "menuDtos") List<MenuDto> menuDtos,
            @RequestPart(value = "menuImageFiles", required = false) List<MultipartFile> menuImageFiles) {
        restaurantService.regist(restaurantDto, files, menuDtos, menuImageFiles);
        return ResponseEntity.ok("가게 및 메뉴 등록 성공");
    }
    @GetMapping(value = "/admin/checkpermit")
    public ResponseEntity<Restaurant> checkPermit() {//가게 관리, 가게 승인여부 체크하는 페이지
        return ResponseEntity.ok(restaurantService.checkPermit());
    }
}
