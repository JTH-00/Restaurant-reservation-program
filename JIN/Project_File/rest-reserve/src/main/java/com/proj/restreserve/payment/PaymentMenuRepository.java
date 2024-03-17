package com.proj.restreserve.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PaymentMenuRepository extends JpaRepository<PaymentMenu,String> {
    List<PaymentMenu> findByPaymentid_Paymentid(String paymentid);
}
