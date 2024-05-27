package com.proj.restreserve.payment.dto;

import com.proj.restreserve.menu.dto.SelectMenuDto;
import com.proj.restreserve.restaurant.entity.Restaurant;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MappingPaymentMenuDto {
    private String paymentmenuid;
    private SelectMenuDto menu;
    private Integer count;
}
