package com.proj.restreserve.review;

import com.proj.restreserve.detailpage.DetailAndReviewPageUserDto;
import com.proj.restreserve.detailpage.ReviewPaymentDto;
import com.proj.restreserve.detailpage.PaymentMenusDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ReviewDto {
    private String reviewid;
    private Integer scope;
    private String content;
    private Date date;
    private DetailAndReviewPageUserDto userid;
    private ReviewPaymentDto payment;
    private List<PaymentMenusDto> paymentMenusDtos;
}