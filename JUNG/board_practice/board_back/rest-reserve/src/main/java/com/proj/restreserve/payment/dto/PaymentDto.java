package com.proj.restreserve.payment.dto;

import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.user.entity.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentDto {
    private String paymentid;
    private String totalprice;
    private Boolean paymentcheck;
    private LocalDate day;
    private Restaurant restaurant;
    private User user;
}