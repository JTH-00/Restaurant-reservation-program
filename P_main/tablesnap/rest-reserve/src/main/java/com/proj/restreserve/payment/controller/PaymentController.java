package com.proj.restreserve.payment.controller;

import com.proj.restreserve.payment.entity.Payment;
import com.proj.restreserve.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
@Tag(name= "Payment", description = "업주,사용자 메뉴 포장예약 관련 API")
public class PaymentController {
    private final PaymentService paymentService;
    @GetMapping("/admin/restaurant/payment/list")
    @Operation(summary = "업주 포장신청 리스트 조회", description = "자신의 매장의 신청받은 포장 주문들을 조회합니다.<br>" +
            "파라미터로 현재 페이지 수를 받으며, 없을 시 1페이지로 고정됩니다.")
    public ResponseEntity<Page<Payment>> showVisit(@RequestParam(required = false, defaultValue = "1") int page){
        return ResponseEntity.ok(paymentService.showPaymentReserve(page,10));
    }
    @PostMapping("/admin/restaurant/payment/refuse/{paymentid}")
    @Operation(summary = "업주 포장신청 거절", description = "고객(일반 사용자)의 포장예약 신청을 거절합니다.<br>" +
            "해당 예약의 id값을 파라미터로 받습니다.")
    public ResponseEntity<String> refusePayment(@PathVariable String paymentid){
        paymentService.refusePayment(paymentid);
        return ResponseEntity.ok("예약 거절");
    }
    @PostMapping("/admin/restaurant/payment/check/{paymentid}")
    @Operation(summary = "업주 포장신청 확인", description = "고객(일반 사용자)의 포장예약을 확인합니다.<br>" +
            "해당 예약의 id값을 파라미터로 받습니다.<br>" +
            "고객이 포장 메뉴를 수령한 뒤 업주가 확인을 눌러 마무리합니다.")
    public ResponseEntity<String> checkPayment(@PathVariable String paymentid){
        paymentService.checkPayment(paymentid);
        return ResponseEntity.ok("예약 확인");
    }

    @PostMapping("/user/restaurant/takeout/{restaurantid}")
    @Operation(summary = "사용자 포장신청", description = "해당 매장의 메뉴를 포장예약 신청합니다.<br>" +
            "사용자의 카트id와 주문할 메뉴의 매장 id를 파라미터로 받습니다.")
    public ResponseEntity<?> processPayment(@PathVariable("restaurantid") String restaurantid,@RequestParam("cartid") String cartid) {
        paymentService.processCartToPayment(restaurantid, cartid);
            return ResponseEntity.ok().body("포장주문이 성공적으로 처리되었습니다.");
    }
}