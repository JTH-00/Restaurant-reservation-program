package com.proj.restreserve.payment.controller;

import com.proj.restreserve.cart.repository.CartMenuRepository;
import com.proj.restreserve.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/restaurant")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/takeout/{restaurantid}")
    public ResponseEntity<?> processPayment(@PathVariable("restaurantid") String restaurantid,@RequestParam("cartid") String cartid) {
        paymentService.processCartToPayment(restaurantid, cartid);
            return ResponseEntity.ok().body("결제가 성공적으로 처리되었습니다.");
    }
}