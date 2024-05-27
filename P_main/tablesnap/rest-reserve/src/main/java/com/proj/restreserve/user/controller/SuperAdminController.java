package com.proj.restreserve.user.controller;

import com.proj.restreserve.restaurant.dto.SelectRestaurantDto;
import com.proj.restreserve.restaurant.service.RestaurantService;
import com.proj.restreserve.user.dto.SelectUserDto;
import com.proj.restreserve.user.service.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/superadmin")
@Tag(name= "SuperAdmin", description = "관리자 가게 승인 및 차단조회 API")
public class SuperAdminController {
    private final RestaurantService restaurantService;
    private final MyPageService myPageService;

    @GetMapping("/permitpage")
    @Operation(summary = "승인요청된 매장 조회", description = "승인요청된 매장을 리스트로 조회합니다.<br>" +
            "파라미터로 현재 페이지 수를 받으며, 없을 시 1페이지로 고정됩니다.")
    public ResponseEntity<Page<SelectRestaurantDto>> showPermitRestaurants(@RequestParam(required = false, defaultValue = "1") int page){
        return ResponseEntity.ok(restaurantService.showPermitRestaurant(page,10));
    }
    @GetMapping("/permitpage/permit/{restaurantid}")
    @Operation(summary = "매장 승인", description = "해당 매장을 승인합니다.<br>" +
            "해당매장의 매장id를 파라미터로 받으며, 승인합니다.<br>" +
            "이후 승인요청된 매장 리스트에 조회되지 않습니다.")
    public ResponseEntity<String> permitRestaurant(@PathVariable String restaurantid){
        restaurantService.permitRestaurant(restaurantid);
        return ResponseEntity.ok("가게를 승인했습니다.");
    }
    @GetMapping("/permitpage/deny/{restaurantid}")
    @Operation(summary = "매장 거부", description = "해당 매장을 거부합니다.<br>" +
            "해당매장의 매장id를 파라미터로 받으며, 거부합니다.<br>"+
            "이후 승인요청된 매장 리스트에 조회되지 않습니다.")
    public ResponseEntity<String> changeSalesStatus(@PathVariable String restaurantid){
        restaurantService.denyRestaurant(restaurantid);
        return ResponseEntity.ok("가게 승인을 거부합니다.");
    }
    @GetMapping("/ban/restaurant")
    @Operation(summary = "차단된 매장 조회", description = "차단된 매장을 리스트로 조회합니다.<br>" +
            "파라미터로 현재 페이지 수를 받으며, 없을 시 1페이지로 고정됩니다.")
    public ResponseEntity<Page<SelectRestaurantDto>> showBannedRestaurant(@RequestParam(required = false, defaultValue = "1") int page){
        return ResponseEntity.ok(restaurantService.showBannedRestaurant(page,10));
    }
    @GetMapping("/ban/user")
    @Operation(summary = "차단된 사용자 조회", description = "차단된 사용자를 리스트로 조회합니다.<br>" +
            "파라미터로 현재 페이지 수를 받으며, 없을 시 1페이지로 고정됩니다.")
    public ResponseEntity<Page<SelectUserDto>> showBannedUser(@RequestParam(required = false, defaultValue = "1") int page){
        return ResponseEntity.ok(myPageService.showBannedUser(page,10));
    }
    @GetMapping("/ban/restaurant/{restaurantid}")
    @Operation(summary = "차단된 매장 상세페이지", description = "차단된 매장의 상세페이지를 조회합니다.<br>" +
            "차단된 매장의 매장id를 파라미터로 받습니다.")
    public ResponseEntity<SelectRestaurantDto> showBannedRestaurant(@PathVariable String restaurantid){
        return ResponseEntity.ok(restaurantService.findBanRestaurant(restaurantid).get());
    }
}
