package com.proj.restreserve.payment.repository;

import com.proj.restreserve.payment.entity.Payment;
import com.proj.restreserve.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository <Payment,String> {
    List<Payment> findByUser(User user);
}
