package com.proj.restreserve.visit.controller;

import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.visit.dto.VisitDto;
import com.proj.restreserve.visit.entity.Visit;
import com.proj.restreserve.visit.service.VisitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/")
public class VisitController {
    private final VisitService visitService;
    @GetMapping("/admin/restaurant/visit/list")
    public ResponseEntity<Page<Visit>> showVisit(@RequestParam(required = false, defaultValue = "1") int page){
        return ResponseEntity.ok(visitService.showVisitReserve(page,10));
    }

    @PostMapping("user/restaurant/reserve/{restaurantid}")
    public ResponseEntity<String> showRestaurant(@Valid @RequestBody VisitDto visitDto, @PathVariable String restaurantid) {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantid(restaurantid); // Set the restaurantid in the Restaurant object
        visitDto.setRestaurant(restaurant); // Set the Restaurant object in the VisitDto
        this.visitService.reserveVisit(visitDto);
        return ResponseEntity.ok("예약성공"); // Assuming "Reservation Successful" message
    }
    @PostMapping("/admin/restaurant/visit/refuse/{visitid}")
    public ResponseEntity<String> refuseVisit(@PathVariable String visitid){
        visitService.refuseVisit(visitid);
        return ResponseEntity.ok("예약 거절");
    }
    @PostMapping("/admin/restaurant/visit/check/{visitid}")
    public ResponseEntity<String> checkVisit(@PathVariable String visitid){
        visitService.checkVisit(visitid);
        return ResponseEntity.ok("예약 확인");
    }
}
