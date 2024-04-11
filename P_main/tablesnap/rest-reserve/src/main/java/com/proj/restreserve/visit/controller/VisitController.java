package com.proj.restreserve.visit.controller;

import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.visit.dto.VisitDto;
import com.proj.restreserve.visit.service.VisitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/restaurant/reserve")
public class VisitController {
    private final VisitService visitService;
    @PostMapping("/{restaurantid}")
    public ResponseEntity<String> showRestaurant(@Valid @RequestBody VisitDto visitDto, @PathVariable String restaurantid) {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantid(restaurantid); // Set the restaurantid in the Restaurant object
        visitDto.setRestaurant(restaurant); // Set the Restaurant object in the VisitDto
        this.visitService.reserveVisit(visitDto);
        return ResponseEntity.ok("예약성공"); // Assuming "Reservation Successful" message
    }
}
