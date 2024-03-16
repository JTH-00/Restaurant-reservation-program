package com.proj.restreserve.restaurant;

import com.proj.restreserve.pageload.PageloadDto;
import com.proj.restreserve.pageload.PageloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class RestraurantController {
    private final RestaurantService restaurantService;
    private final PageloadService pageloadService;
    @GetMapping("/rest/{restaurantid}")
    public ResponseEntity<PageloadDto> showRestaurant(@PathVariable String restaurantid){
        return ResponseEntity.ok(pageloadService.pageload(restaurantid));//레스토랑 상세 페이지
    }
}
