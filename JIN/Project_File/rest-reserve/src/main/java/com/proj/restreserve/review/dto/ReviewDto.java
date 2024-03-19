package com.proj.restreserve.review.dto;

import com.proj.restreserve.detailpage.dto.DetailUserDto;
import com.proj.restreserve.payment.dto.PaymentMenusDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ReviewDto {
    private String reviewid;
    private Integer scope;
    private String content;
    private Date date;
    private DetailUserDto userid;
    private List<PaymentMenusDto> paymentMenusDtos;
    private List<String> reviewimagelinks;
}