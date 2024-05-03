package com.proj.restreserve.payment.dto;

import com.proj.restreserve.menu.dto.MenuDto;
import com.proj.restreserve.menu.dto.SelectMenuDto;
import com.proj.restreserve.menu.entity.Menu;
import com.proj.restreserve.payment.entity.Payment;
import lombok.Data;

@Data
public class PaymentMenuDto {
    private String paymentmenuid;
    private SelectMenuDto menu;
    private Integer count;
}
