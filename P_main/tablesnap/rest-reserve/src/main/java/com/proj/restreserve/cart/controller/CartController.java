package com.proj.restreserve.cart.controller;

import com.proj.restreserve.cart.dto.CartDto;
import com.proj.restreserve.cart.entity.CartMenu;
import com.proj.restreserve.cart.service.CartService;
import com.proj.restreserve.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name= "Cart", description = "사용자의 장바구니 API")
public class CartController {

    private final CartService cartService;
    @GetMapping("/cart")
    @Operation(summary = "장바구니 리스트 조회", description = "자신이 장바구니에 등록한 메뉴들을 조회합니다.")
    public ResponseEntity<?> cartlist() {
        try {
            CartDto cartDto = cartService.findCartMenu(); // 현재 로그인한 사용자의 장바구니 정보 조회
            return ResponseEntity.ok(cartDto); // CartInfo에는 장바구니 메뉴 목록과 총 금액이 포함됨
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cart/addmenu")
    @Operation(summary = "장바구니에 메뉴 추가", description = "선택한 메뉴를 장바구니에 저장합니다." +
            "메뉴 id와 그 메뉴의 수량을 파라미터로 받습니다.")
    public ResponseEntity<?> addMenuToCart(@RequestParam String menuid, @RequestParam Integer count) {
        return ResponseEntity.ok(cartService.addMenuToCart(menuid, count));
    }

    @PutMapping("/cart/updatemenucount")
    @Operation(summary = "장바구니 메뉴 수량 변경", description = "장바구니에 저장된 메뉴의 수량을 변경합니다.<br>" +
            "장바구니의 저장된 메뉴 id와 그 메뉴의 + 또는 - 를 boolean타입의 true,false의 형태의 파라미터로 받습니다.<br>" +
            "해당 메뉴의 수량이 0이 되면 장바구니에서 삭제하게 됩니다.")
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
    @Operation(summary = "장바구니 메뉴 삭제", description = "장바구니에 저장된 메뉴를 삭제합니다.<br>" +
            "장바구니의 저장된 메뉴 id를 파라미터로 받습니다.")
    public ResponseEntity<?> removeMenuFromCart(@RequestParam Integer cartmenuid) {
        cartService.removeMenuFromCart(cartmenuid);
        return ResponseEntity.ok("수량 전체를 삭제하였습니다");
    }
}