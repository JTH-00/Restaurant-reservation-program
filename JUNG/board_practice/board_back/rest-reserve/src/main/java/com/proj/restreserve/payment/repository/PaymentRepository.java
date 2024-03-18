package com.proj.restreserve.payment.repository;

import com.proj.restreserve.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository <Payment,String> {
}
