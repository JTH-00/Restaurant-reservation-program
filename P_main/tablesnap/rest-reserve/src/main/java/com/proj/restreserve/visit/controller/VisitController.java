package com.proj.restreserve.visit.controller;

import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.visit.dto.VisitDto;
import com.proj.restreserve.visit.entity.Visit;
import com.proj.restreserve.visit.service.VisitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/")
@Tag(name= "Visit", description = "업주,사용자 메뉴 방문예약 관련 API")
public class VisitController {
    private final VisitService visitService;
    @GetMapping("/admin/restaurant/visit/list")
    @Operation(summary = "업주 방문신청 리스트 조회", description = "자신의 매장의 신청받은 포장 주문들을 조회합니다." +
            "파라미터로 현재 페이지 수를 받으며, 없을 시 1페이지로 고정됩니다.")
    public ResponseEntity<Page<Visit>> showVisit(@RequestParam(required = false, defaultValue = "1") int page){
        return ResponseEntity.ok(visitService.showVisitReserve(page,10));
    }

    @PostMapping("user/restaurant/reserve/{restaurantid}")
    @Operation(summary = "사용자 방문신청", description = "해당 매장의 메뉴를 포장예약 신청합니다." +
            "VisitDto로 방문 시간대와 인원을 입력하여 저장합니다."+
            "방문할 매장 id를 파라미터로 받습니다.")
    public ResponseEntity<String> showRestaurant(@Valid @RequestBody VisitDto visitDto, @PathVariable String restaurantid) {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantid(restaurantid);
        visitDto.setRestaurant(restaurant);
        this.visitService.reserveVisit(visitDto);
        return ResponseEntity.ok("예약성공");
    }
    @PostMapping("/admin/restaurant/visit/refuse/{visitid}")
    @Operation(summary = "업주 방문신청 거절", description = "고객(일반 사용자)의 방문예약 신청을 거절합니다." +
            "해당 예약의 id값을 파라미터로 받습니다.")
    public ResponseEntity<String> refuseVisit(@PathVariable String visitid){
        visitService.refuseVisit(visitid);
        return ResponseEntity.ok("예약 거절");
    }
    @PostMapping("/admin/restaurant/visit/check/{visitid}")
    @Operation(summary = "업주 방문신청 확인", description = "고객(일반 사용자)의 방문예약을 확인합니다." +
            "해당 예약의 id값을 파라미터로 받습니다." +
            "고객이 방문한 뒤 업주가 확인을 눌러 마무리합니다.")
    public ResponseEntity<String> checkVisit(@PathVariable String visitid){
        visitService.checkVisit(visitid);
        return ResponseEntity.ok("예약 확인");
    }
}
