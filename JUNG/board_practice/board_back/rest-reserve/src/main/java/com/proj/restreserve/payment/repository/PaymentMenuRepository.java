package com.proj.restreserve.payment.repository;

import com.proj.restreserve.payment.entity.Payment;
import com.proj.restreserve.payment.entity.PaymentMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMenuRepository extends JpaRepository <PaymentMenu,String> {
}
