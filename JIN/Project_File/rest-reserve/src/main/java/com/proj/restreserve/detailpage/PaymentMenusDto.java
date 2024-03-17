package com.proj.restreserve.detailpage;

import lombok.Data;
@Data
public class PaymentMenusDto {//리뷰의 총 주문한 메뉴의 리스트 DTO
    private PaymentMenuDto menuid;

    private int count;
}
