package com.proj.restreserve.payment.service;

import com.proj.restreserve.cart.dto.CartDto;
import com.proj.restreserve.cart.service.CartService;
import com.proj.restreserve.menu.repository.MenuRepository;
import com.proj.restreserve.payment.dto.PaymentDto;
import com.proj.restreserve.payment.dto.PaymentMenuDto;
import com.proj.restreserve.payment.entity.Payment;
import com.proj.restreserve.payment.entity.PaymentMenu;
import com.proj.restreserve.payment.repository.PaymentMenuRepository;
import com.proj.restreserve.payment.repository.PaymentRepository;
import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.restaurant.repository.RestaurantRepository;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final CartService cartService;
    private final PaymentRepository paymentRepository;
    private final PaymentMenuRepository paymentMenuRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String useremail = authentication.getName();
        return userRepository.findByUseremail(useremail);
    }

}
