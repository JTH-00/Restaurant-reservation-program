package com.proj.restreserve.user.controller;

import com.proj.restreserve.restaurant.dto.SelectRestaurantDto;
import com.proj.restreserve.restaurant.service.RestaurantService;
import com.proj.restreserve.user.dto.SelectUserDto;
import com.proj.restreserve.user.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/superadmin")
public class SuperAdminController {
    private final RestaurantService restaurantService;
    private final MyPageService myPageService;

    @GetMapping("/permitpage")
    public ResponseEntity<Page<SelectRestaurantDto>> showPermitRestaurants(@RequestParam(required = false, defaultValue = "1") int page){
        return ResponseEntity.ok(restaurantService.showPermitRestaurant(page,10));
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
    @GetMapping("/ban/restaurant")
    public ResponseEntity<Page<SelectRestaurantDto>> showBannedRestaurant(@RequestParam(required = false, defaultValue = "1") int page){
        return ResponseEntity.ok(restaurantService.showBannedRestaurant(page,10));
    }
    @GetMapping("/ban/user")
    public ResponseEntity<Page<SelectUserDto>> showBannedUser(@RequestParam(required = false, defaultValue = "1") int page){
        return ResponseEntity.ok(myPageService.showBannedUser(page,10));
    }
    @GetMapping("/ban/restaurant/{restaurantid}")
    public ResponseEntity<SelectRestaurantDto> showBannedRestaurant(@PathVariable String restaurantid){
        return ResponseEntity.ok(restaurantService.findBanRestaurant(restaurantid).get());
    }
}
