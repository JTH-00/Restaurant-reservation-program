package com.proj.restreserve.cart.service;

import com.proj.restreserve.cart.dto.CartDto;
import com.proj.restreserve.cart.entity.Cart;
import com.proj.restreserve.cart.entity.CartMenu;
import com.proj.restreserve.cart.repository.CartMenuRepository;
import com.proj.restreserve.cart.repository.CartRepository;
import com.proj.restreserve.menu.entity.Menu;
import com.proj.restreserve.menu.repository.MenuRepository;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartMenuRepository cartMenuRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String useremail = authentication.getName();
        return userRepository.findByUseremail(useremail);
    }

    public CartDto findCartMenu() {
        User user = getCurrentUser();

        Optional<Cart> cartOptional = cartRepository.findByUser(user);
        if (!cartOptional.isPresent()) {
            throw new RuntimeException("Cart not found");
        }

        Cart cart = cartOptional.get();
        List<CartMenu> cartMenus = cartMenuRepository.findByCart(cart);

        if (cartMenus.isEmpty()) {
            throw new RuntimeException("CartMenu is empty");
        }

        // 상품의 총 주문금액 계산
        double totalAmount = cartMenus.stream()
                .mapToDouble(cartMenu -> Double.parseDouble(cartMenu.getMenu().getPrice()) * cartMenu.getMenucount())
                .sum();

        return new CartDto(cartMenus, totalAmount); // CartInfo는 장바구니 정보와 총 금액을 담는 DTO 클래스
    }

    public Cart createOrGetCart() {
        User user = getCurrentUser();
        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user); // 현재 사용자를 장바구니에 설정
            return cartRepository.save(newCart);
        });
    }

    public CartMenu addMenuToCart(String menuid, Integer count) {
        Cart cart = createOrGetCart(); // 현재 사용자의 장바구니를 가져오거나 새로 생성
        Menu menu = menuRepository.findById(menuid)
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        // 현재 장바구니에 있는 메뉴들 가져오기
        List<CartMenu> cartMenus = cartMenuRepository.findByCart(cart);
        if (!cartMenus.isEmpty()) {
            // 장바구니에 메뉴가 있을 경우, 모든 메뉴의 restaurantid를 가져와서 새로 추가하려는 메뉴와 비교
            String currentRestaurantId = cartMenus.get(0).getMenu().getRestaurant().getRestaurantid();
            if (!menu.getRestaurant().getRestaurantid().equals(currentRestaurantId)) {
                throw new RuntimeException("Cannot add menu from a different restaurant");
            }
        }

        Optional<CartMenu> existingCartMenu = cartMenuRepository.findByCartAndMenu(cart, menu);
        CartMenu cartMenu;

        if (existingCartMenu.isPresent()) {
            // 이미 존재하는 경우, 수량만 증가
            cartMenu = existingCartMenu.get();
            cartMenu.setMenucount(cartMenu.getMenucount() + count);
        } else {
            cartMenu = new CartMenu();
            cartMenu.setCart(cart);
            cartMenu.setMenu(menu);
            cartMenu.setMenucount(count);
        }
        return cartMenuRepository.save(cartMenu);
    }
    private void checkAndDeleteCartIfEmpty(String cartid) {
        // cartId를 사용하여 관련된 CartMenu 항목이 있는지 확인
        boolean isEmpty = !cartMenuRepository.existsByCart_Cartid(cartid);
        if (isEmpty) {
            // CartMenu 항목이 없으면 Cart 삭제
            cartRepository.deleteById(cartid);
        }
    }

    public CartMenu updateMenuCountInCart(Integer cartmenuid, boolean increase) {
        CartMenu cartMenu = cartMenuRepository.findById(cartmenuid)
                .orElseThrow(() -> new RuntimeException("CartMenu not found"));

        // 수량 증가 또는 감소
        int newCount = cartMenu.getMenucount() + (increase ? 1 : -1);

        // 수량이 0이면 해당 CartMenu 삭제
        if (newCount <= 0) {
            cartMenuRepository.deleteById(cartmenuid);
            checkAndDeleteCartIfEmpty(cartMenu.getCart().getCartid());
            return null; // 삭제 후 null 반환
        } else {
            // 수량 업데이트
            cartMenu.setMenucount(newCount);
            return cartMenuRepository.save(cartMenu);
        }
    }

    public void removeMenuFromCart(Integer cartmenuid) {
        CartMenu cartMenu = cartMenuRepository.findById(cartmenuid)
                .orElseThrow(() -> new RuntimeException("CartMenu not found"));
        String cartid = cartMenu.getCart().getCartid();
        cartMenuRepository.deleteById(cartmenuid);
        // CartMenu가 삭제된 후 Cart가 비었는지 확인, 비었다면 Cart 삭제
        checkAndDeleteCartIfEmpty(cartid);
    }

    public void removeCart(String cartid) {
        Cart cart=cartRepository.findByCartid(cartid);
        List<CartMenu> cartMenus = cartMenuRepository.findByCart(cart);
        for (CartMenu cartMenu : cartMenus) {
            cartMenuRepository.delete(cartMenu);
        }
        cartRepository.deleteById(cartid);
    }
}
