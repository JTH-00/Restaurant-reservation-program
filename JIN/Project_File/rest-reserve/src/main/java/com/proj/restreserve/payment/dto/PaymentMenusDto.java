package com.proj.restreserve.payment.dto;

import lombok.Data;
@Data
public class PaymentMenusDto {//총 주문한 메뉴의 리스트 DTO
    private PaymentMenuDto menuid;
    private int count;
}
