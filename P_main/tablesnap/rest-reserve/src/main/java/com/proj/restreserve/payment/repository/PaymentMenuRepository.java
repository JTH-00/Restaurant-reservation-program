package com.proj.restreserve.payment.repository;

import com.proj.restreserve.payment.entity.Payment;
import com.proj.restreserve.payment.entity.PaymentMenu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMenuRepository extends JpaRepository <PaymentMenu,String> {
    @EntityGraph(attributePaths = "paymentid")
    List<PaymentMenu> findByPayment_Paymentid(String paymentid);
}
