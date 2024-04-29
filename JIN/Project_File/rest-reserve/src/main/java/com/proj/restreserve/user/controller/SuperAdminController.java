package com.proj.restreserve.user.controller;

import com.proj.restreserve.restaurant.dto.SelectRestaurantDto;
import com.proj.restreserve.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/superadmin")
public class SuperAdminController {
    private final RestaurantService restaurantService;

    @GetMapping("/permitpage")
    public ResponseEntity<Page<SelectRestaurantDto>> showPermitRestaurants(){
        return ResponseEntity.ok(restaurantService.showPermitRestaurant(1,10));
    }
    @GetMapping("/permitpage/permit/{restaurantid}")
    public ResponseEntity<String> permitRestaurant(@PathVariable String restaurantid){
        restaurantService.permitRestaurant(restaurantid);
        return ResponseEntity.ok("가게를 승인했습니다.");
    }
    @GetMapping("/permitpage/deny/{restaurantid}")
    public ResponseEntity<String> changeSalesStatus(@PathVariable String restaurantid){
        restaurantService.denyRestaurant(restaurantid);
        return ResponseEntity.ok("가게 승인을 거부합니다.");
    }
}
