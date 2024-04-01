package com.proj.restreserve.payment.service;

import com.proj.restreserve.cart.entity.Cart;
import com.proj.restreserve.cart.entity.CartMenu;
import com.proj.restreserve.cart.repository.CartMenuRepository;
import com.proj.restreserve.cart.repository.CartRepository;
import com.proj.restreserve.cart.service.CartService;
import com.proj.restreserve.menu.repository.MenuRepository;
import com.proj.restreserve.payment.entity.Payment;
import com.proj.restreserve.payment.entity.PaymentMenu;
import com.proj.restreserve.payment.repository.PaymentMenuRepository;
import com.proj.restreserve.payment.repository.PaymentRepository;
import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.restaurant.repository.RestaurantRepository;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final UserRepository userRepository;

    private final CartRepository cartRepository;
    private final CartMenuRepository cartMenuRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMenuRepository paymentMenuRepository;
    private final MenuRepository menuRepository;
    private final CartService cartService;
    private final RestaurantRepository restaurantRepository;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String useremail = authentication.getName();
        return userRepository.findByUseremail(useremail);
    }

    @Transactional
    public void processCartToPayment(String restaurantid,String cartid) {
        User user=getCurrentUser();
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        List<CartMenu> cartMenus = cartMenuRepository.findByCart(cart);

        if (cartMenus.isEmpty()) {
            throw new RuntimeException("CartMenu is empty");
        }

        double totalAmount = cartMenus.stream()
                .mapToDouble(cartMenu -> Double.parseDouble(cartMenu.getMenu().getPrice()) * cartMenu.getMenucount())
                .sum();

        // 결제 정보 생성 및 저장
        Payment payment = new Payment();
        payment.setTotalprice(String.valueOf(totalAmount));
        payment.setPaymentcheck(false);
        payment.setDay(LocalDate.now());
        payment.setUser(user);
        Restaurant restaurant = restaurantRepository.findById(restaurantid)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        payment.setRestaurant(restaurant);
        paymentRepository.save(payment);

        // 결제 메뉴 정보 생성 및 저장
        for (CartMenu cartMenu : cartMenus) {
            PaymentMenu paymentMenu = new PaymentMenu();
            paymentMenu.setPayment(payment);
            paymentMenu.setMenu(cartMenu.getMenu());
            paymentMenu.setCount(cartMenu.getMenucount());
            paymentMenuRepository.save(paymentMenu);
        }
        cartService.removeCart(cartid);
    }
}
