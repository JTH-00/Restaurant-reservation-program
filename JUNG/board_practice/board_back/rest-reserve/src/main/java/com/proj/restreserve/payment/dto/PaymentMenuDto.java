package com.proj.restreserve.payment.dto;

import com.proj.restreserve.menu.entity.Menu;
import com.proj.restreserve.payment.entity.Payment;
import lombok.Data;

@Data
public class PaymentMenuDto {
    private String paymentmenuid;
    private Payment payment;
    private Menu menu;
    private Integer count;
}
