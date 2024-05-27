package com.proj.restreserve.payment.repository;

import com.proj.restreserve.payment.entity.Payment;
import com.proj.restreserve.payment.entity.PaymentMenu;
import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.visit.entity.Visit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository <Payment,String> {
    List<Payment> findByUserAndPaymentcheckFalse(User user);
    Page<Payment> findByPaymentcheckFalseAndRestaurant(Restaurant restaurant, Pageable pageable);
}
