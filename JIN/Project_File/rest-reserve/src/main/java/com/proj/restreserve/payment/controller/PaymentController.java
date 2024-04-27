package com.proj.restreserve.payment.controller;

import com.proj.restreserve.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping("/admin/restaurant/payment/refuse/{visitid}")
    public ResponseEntity<String> refuseVisit(@PathVariable String paymentid){
        paymentService.refusePayment(paymentid);
        return ResponseEntity.ok("예약 거절");
    }
    @PostMapping("/admin/restaurant/payment/check/{visitid}")
    public ResponseEntity<String> checkVisit(@PathVariable String paymentid){
        paymentService.checkPayment(paymentid);
        return ResponseEntity.ok("예약 확인");
    }
}
