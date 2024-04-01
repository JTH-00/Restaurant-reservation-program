package com.proj.restreserve.cart.controller;

import com.proj.restreserve.cart.dto.CartDto;
import com.proj.restreserve.cart.entity.CartMenu;
import com.proj.restreserve.cart.service.CartService;
import com.proj.restreserve.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private final PaymentService paymentService;

    @GetMapping("/cart")
    public ResponseEntity<?> cartlist() {
        try {
            CartDto cartDto = cartService.findCartMenu(); // 현재 로그인한 사용자의 장바구니 정보 조회
            return ResponseEntity.ok(cartDto); // CartInfo에는 장바구니 메뉴 목록과 총 금액이 포함됨
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cart/addmenu")
    public ResponseEntity<?> addMenuToCart(@RequestParam String menuid, @RequestParam Integer count) {
        return ResponseEntity.ok(cartService.addMenuToCart(menuid, count));
    }

    @PutMapping("/cart/updatemenucount")
    public ResponseEntity<?> updateMenuCountInCart(@RequestParam Integer cartmenuid, @RequestParam boolean increase) {
        CartMenu updatedCartMenu = cartService.updateMenuCountInCart(cartmenuid, increase);
        if (updatedCartMenu == null) {
            return ResponseEntity.ok("수량이 0이므로 삭제되었습니다");
        } else {
            // CartMenu가 업데이트된 경우, 해당 CartMenu 반환
            return ResponseEntity.ok(updatedCartMenu);
        }
    }

    @DeleteMapping("/cart/removemenu")
    public ResponseEntity<?> removeMenuFromCart(@RequestParam Integer cartmenuid) {
        cartService.removeMenuFromCart(cartmenuid);
        return ResponseEntity.ok("수량 전체를 삭제하였습니다");
    }
}