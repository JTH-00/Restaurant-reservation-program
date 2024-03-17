package com.proj.restreserve.detailpage;

import lombok.Data;

import java.util.Date;

@Data
public class ReviewPaymentDto {//리뷰작성자의 결제정보 DTO
    private String paymentid;
    private String totalprice;

    private Date day;

    private DetailAndReviewPageUserDto userid;

    private ReviewRestaurantDto restaurantid;
}
