package com.proj.restreserve.visit.controller;

import com.proj.restreserve.user.dto.UserDto;
import com.proj.restreserve.visit.dto.VisitDto;
import com.proj.restreserve.visit.entity.Visit;
import com.proj.restreserve.visit.service.VisitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/restaurant/reserve")
public class VisitController {
    private final VisitService visitService;
    @PostMapping("/{restaurantid}")
    public ResponseEntity<String> showRestaurant(@Valid @RequestBody VisitDto visitDto, @PathVariable String restaurantid){
        visitDto.setRestaurantid(restaurantid);
        this.visitService.reserveVisit(visitDto);
        return ResponseEntity.ok("예약성공");//레스토랑 상세 페이지
    }
}
