package com.proj.restreserve.payment.dto;

import com.proj.restreserve.menu.dto.SelectMenuDto;
import com.proj.restreserve.payment.entity.Payment;
import com.proj.restreserve.restaurant.entity.Restaurant;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentMenuDto {
    private String paymentmenuid;
    private SelectMenuDto menu;
    private Integer count;
    private String totalprice;
    private LocalDate day;
    private Restaurant restaurant;
    private Boolean paymentcheck;
    private String payment;
}
