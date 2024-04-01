package com.proj.restreserve.restaurant.controller;

import com.proj.restreserve.detailpage.dto.DetailPageDto;
import com.proj.restreserve.detailpage.service.DetailPageService;
import com.proj.restreserve.restaurant.dto.SelectRestaurantDto;
import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.restaurant.dto.RestaurantDto;
import com.proj.restreserve.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RestaurantController {
    private final DetailPageService detailPageService;
    private final RestaurantService restaurantService;

    @GetMapping("/user/restaurant/list")
    public ResponseEntity <List<RestaurantDto>> restaurantlist(){
        List<RestaurantDto> restaurantDtos = restaurantService.restaurantAll();
        return ResponseEntity.ok(restaurantDtos);
    }

    @PostMapping("/user/restaurant/list/favorite/{restaurantid}")
    public ResponseEntity<String> addFavoriteRestaurant(@PathVariable String restaurantid) {
        restaurantService.addFavoriteRestaurant(restaurantid);
        return ResponseEntity.ok("Favorite restaurant added successfully.");
    }

    @PostMapping("/admin/registration")
    public ResponseEntity<Restaurant> registrestaurant(@ModelAttribute RestaurantDto restaurantDto, @RequestParam("files") List<MultipartFile> files) {
        return ResponseEntity.ok(restaurantService.regist(restaurantDto, files));
    }
    @GetMapping("/main")
    public ResponseEntity<List<List<SelectRestaurantDto>>> showRestaurant(){
        return ResponseEntity.ok(restaurantService.showMainPage());//레스토랑 상세 페이지
    }
    @GetMapping("/user/restaurant/{restaurantid}")
    public ResponseEntity<DetailPageDto> showRestaurant(@PathVariable String restaurantid){
        return ResponseEntity.ok(detailPageService.pageload(restaurantid,1));//레스토랑 상세 페이지
    }
}