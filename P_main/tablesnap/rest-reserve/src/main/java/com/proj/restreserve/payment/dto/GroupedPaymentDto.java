package com.proj.restreserve.payment.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class GroupedPaymentDto {
    private String paymentId;
    private List<PaymentMenuDto> paymentMenus;
    private String totalprice;
    private LocalDate day;
    private String restaurant;
    private Boolean paymentcheck;

}
